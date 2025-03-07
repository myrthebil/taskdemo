# TaskDemo

This repository contains two services for executing CRUD operations on **Tasks** and **Users**.

## Project Structure
This project contains the frontend and backend code for TaskDemo, with Docker support for seamless deployment.

## Prerequisites

Before running the project locally, ensure you have the following installed:

- [Docker](https://www.docker.com/get-started) for containerized deployment
- [Node.js](https://nodejs.org/) for running the frontend application
- [Maven](https://maven.apache.org/) for managing backend dependencies

### Backend
[backend](./backend) contains a **multi-module Maven** setup with the following modules:

- **`common-model`** – Shared models and utilities.
- **`task-service`** – Manages tasks.
- **`user-service`** – Manages users.

The parent **`pom.xml`** handles common dependencies and extends `spring-boot-starter-parent` for
consistent configuration.

### Frontend
Navigate to the frontend directory:

`cd frontend`

Install frontend dependencies using npm:

`npm install`

Run the frontend development server:

`npm run dev`

The frontend will now be available at http://localhost:3000.

## Local Development

To run and test the services locally, use **`docker-compose.yaml`**, which sets up the following
components:

### **1. PostgreSQL (Version 14)**

A PostgreSQL database with these tables:

- `assigned_user_task`
- `tasks`
- `users`

### **2. Adminer (Database UI)**

Adminer provides a web interface to interact with the database.

- **URL:** `http://localhost:8080`
- **Login Credentials:**
    - **Server:** `postgres-db`
    - **Username:** `myuser`
    - **Password:** `mypassword`
    - **Database:** `mydatabase`

### **3. Task Service**

Runs the Task API service.

- **Build the image before running:**
  ```sh
  mvn spring-boot:build-image -pl task-service
  ```  

### **4. User Service**

Runs the User API service.

- **Build the image before running:**
  ```sh
  mvn spring-boot:build-image -pl user-service
  ```  

### **5. Seeding Initial Data**

Once all services are running, execute the following command to create sample users and tasks:

```sh
bash scripts/initial-seed.sh
```  

## Running Everything

1. **Start the services:**
   ```sh
   docker-compose up -d
   ```  
2. **Verify Adminer at** `http://localhost:8080`.
3. **Run `initial-seed.sh` to populate test data.**

## Code Quality

Frontend: ESLint is used for linting. Run the following command to check for any code style violations:

`npm run lint`

Backend: The backend uses Checkstyle for code style enforcement. Configuration can be found in the [checkstyle.xml](./backend/checkstyle.xml) file.