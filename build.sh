#!/bin/bash

# Function to display usage
usage() {
    echo "Usage: $0 [option]"
    echo "Options:"
    echo "  profile     - Build the employee-profile module"
    echo "  payroll     - Build the employee-payroll module"
    echo "  monolith    - Build the entire project in monolith mode"
    exit 1
}

# Check if argument is passed
if [ $# -eq 0 ]; then
    usage
fi

# Switch between options
case "$1" in
    profile)
        echo "Building employee-profile module..."
        ./mvnw -Demployee-profile clean install
        ;;
    payroll)
        echo "Building employee-payroll module..."
        ./mvnw -Demployee-payroll clean install
        ;;
    monolith)
        echo "Building entire project in monolith mode..."
        ./mvnw -Dmonolith clean install
        ;;
    *)
        echo "Invalid option!"
        usage
        ;;
esac

