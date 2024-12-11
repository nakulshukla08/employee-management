#!/bin/bash

# Base URL of the API
BASE_URL="http://localhost:8080/employee-management/api"

# Create Employee
echo "Creating Employee..."
CREATE_RESPONSE=$(curl --silent --location --request POST "$BASE_URL/profile" \
--header "Accept: application/json" \
--header "Content-Type: application/json" \
--data '{
    "firstName" : "Johnny",
    "lastName" : "Trash",
    "departmentName": "Engineering",
    "salary" : "5000"
}')

echo "Create Response: $CREATE_RESPONSE"

# Extract the Employee ID from the response
EMPLOYEE_ID=$(echo "$CREATE_RESPONSE" | jq -r '.employeeId')

if [[ "$EMPLOYEE_ID" == "null" || -z "$EMPLOYEE_ID" ]]; then
  echo "Error: Failed to create employee or unable to extract employee ID."
  exit 1
fi

echo "Employee created successfully with ID: $EMPLOYEE_ID"

# Retrieve Employee
echo "Retrieving Employee with ID: $EMPLOYEE_ID..."
RETRIEVE_RESPONSE=$(curl --silent --location --request GET "$BASE_URL/profile/$EMPLOYEE_ID?includePayroll=true" \
--header "Accept: application/json")

echo "Retrieve Response: $RETRIEVE_RESPONSE"

