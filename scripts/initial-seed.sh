#!/bin/bash

# This script seeds the system by making API calls to the User and Task services.

# API endpoints
USER_SERVICE_URL="http://localhost:8081/api/v1/user"  # Update port if different
TASK_SERVICE_URL="http://localhost:8082/api/v1/task"  # Update port if different

echo "Starting API-based seeding..."

# Create sample users
echo "Creating users..."
curl -X POST "$USER_SERVICE_URL" -H "Content-Type: application/json" -d '{
  "username": "barry_pooter"
}'
echo ""

curl -X POST "$USER_SERVICE_URL" -H "Content-Type: application/json" -d '{
  "username": "mummelwummel"
}'
echo ""

curl -X POST "$USER_SERVICE_URL" -H "Content-Type: application/json" -d '{
  "username": "bracchium_emmendo"
}'
echo ""

# Create sample tasks
echo "Creating tasks..."
curl -X POST "$TASK_SERVICE_URL" -H "Content-Type: application/json" -d '{
  "taskOwner": {
    "username": "mummelwummel",
    "id": 2
  },
  "title": "Fix broken broom",
  "description": "Remove the bent twigs and polish the broom",
  "taskStatus": "TODO",
  "assignedUsers": [{"username": "barry_pooter", "id": 1}, {"username": "bracchium_emmendo", "id": 3}]
}'
echo ""

curl -X POST "$TASK_SERVICE_URL" -H "Content-Type: application/json" -d '{
  "taskOwner": {
    "username": "barry_pooter",
    "id": 1
  },
  "title": "Finish muggle task",
  "description": "Continue conjuring more code for this muggle application",
  "taskStatus": "TODO",
  "assignedUsers": [{"username": "bracchium_emmendo", "id": 3}]
}'
echo ""

echo "Seeding complete."
