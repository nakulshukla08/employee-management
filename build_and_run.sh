#!/bin/bash

# Function to display usage
usage() {
    echo "Usage: $0 [monolith|microservice]"
    echo "Options:"
    echo "  monolith      - Build and run the application as a single monolithic service."
    echo "  microservice  - Build and run the services as independent microservices."
    exit 1
}

# Check if argument is passed
if [ $# -eq 0 ]; then
    usage
fi

MODE=$1

# Docker Compose File
DOCKER_COMPOSE_FILE="db/docker-compose.yaml"

# Target subdirectories for build outputs
MONOLITH_DIR="app/target"
PROFILE_DIR="app/target/profile"
PAYROLL_DIR="app/target/payroll"


# Function to kill existing Java processes
kill_java_processes() {
    echo "Stopping any running Java processes for services..."
    pkill -f employee-profile-app.jar
    pkill -f employee-payroll-app.jar
    echo "Java processes stopped."
}

# Function to clean all build directories
clean_directories() {
    echo "Cleaning build directories..."
    rm -rf ${MONOLITH_DIR} ${PROFILE_DIR} ${PAYROLL_DIR}
    echo "Build directories cleaned."
}

# Function to stop existing Postgres container
stop_postgres() {
    echo "Stopping Postgres Docker container..."
    docker-compose -f ${DOCKER_COMPOSE_FILE} down
    echo "Postgres container stopped."
}

# Function to start Postgres container
start_postgres() {
    echo "Starting Postgres Docker container..."
    docker-compose -f ${DOCKER_COMPOSE_FILE} up -d
    echo "Waiting for Postgres to be ready..."
    until docker exec postgres_container pg_isready -U employee_user > /dev/null 2>&1; do
        echo "Waiting for Postgres..."
        sleep 2
    done
    echo "Postgres is ready!"
}

# Function to build and run as a monolith
build_monolith() {
    echo "Building as a monolith..."
    ./mvnw clean install -Pmonolith
    echo "Running monolith application on default port 8080..."
    java -jar ${MONOLITH_DIR}/employee-management-app-0.0.1-SNAPSHOT.jar
}

# Function to build and run as microservices
build_microservices() {
    echo "Building and running as separate microservices..."

    echo "Building employee-profile module..."
    ./mvnw -Demployee-profile clean install
    echo "Building employee-payroll module..."
    ./mvnw -Demployee-payroll clean install

    echo "Running employee-profile service on port 8080..."
    cd ${PROFILE_DIR}
    java -Ddeployment.mode=micro -Dserver.port=8080 -jar employee-profile-app.jar &
    cd -

    echo "Running employee-payroll service on port 9090..."
    cd ${PAYROLL_DIR}
    java -Dserver.port=9090 -jar employee-payroll-app.jar &
    cd -
}

# Stop existing Java processes before starting
kill_java_processes

# Clean directories before building
clean_directories

# Stop Postgres
stop_postgres

# Start Postgres
start_postgres

# Execute based on the mode
case "$MODE" in
    monolith)
        build_monolith
        ;;
    microservice)
        build_microservices
        ;;
    *)
        echo "Invalid option!"
        usage
        ;;
esac