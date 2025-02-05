# Get all tasks for user bob
curl -H 'Content-Type: application/json' \
      -d '{ "username":"bob" }' \
      -X POST \
      localhost:8080/tasks

# Create a new task for user bob
curl -H 'Content-Type: application/json' \
      -d '{ "taskOwner": { "username": "bob" }, "name": "buy an owl", "description": "white, not female - or is it?" }' \
      -X POST \
      localhost:8080/task