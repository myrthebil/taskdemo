# TaskDemo

This repository contains a frontend and two services to perform CRUD operations on **Tasks** and **Users**.

## Project Structure

The project consists of both the frontend and backend code for TaskDemo, along with Docker support
for easy deployment.

### Prerequisites

Before running the project locally, ensure you have the following installed:

- [Docker](https://www.docker.com/get-started) for containerized deployment
- [Node.js](https://nodejs.org/) for running the frontend application
- [Maven](https://maven.apache.org/) for managing backend dependencies

### Backend

The backend is set up as a **multi-module Maven project**, located in the [backend directoty](./backend), with the
following modules:

- **`common-model`** – Shared models and utilities.
- **`task-service`** – Manages tasks.
- **`user-service`** – Manages users.

The parent [pom.xml](./backend/pom.xml) file handles common dependencies and extends `spring-boot-starter-parent` for
consistent configuration.

### Frontend

To set up and run the frontend:

Navigate to the frontend directory:

    cd frontend

Install frontend dependencies:

    npm install

Start the development server:

    npm run dev

The frontend will now be available at http://localhost:3000.

## Local Development

To run and test the services locally, use the provided [docker-compose.yaml](./docker-compose.yaml) file, which sets up the
following components:

### **1. PostgreSQL (Version 14)**

A PostgreSQL database with the following tables:

    assigned_user_task
    tasks
    users

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

Build the image before running:

    mvn spring-boot:build-image -pl task-service

### **4. User Service**

Runs the User API service.

Build the image before running:

    mvn spring-boot:build-image -pl user-service

5. Seeding Initial Data

Once all services are running, execute the following command to create sample users and tasks:

    bash scripts/initial-seed.sh

## Running Everything

To start all services and run the project:

Start the services:

    docker-compose up -d

Verify Adminer is running at http://localhost:8080.

Run [initial-seed.sh](./backend/scripts/initial-seed.sh) to populate the database with test data.

## Code Quality

Frontend: ESLint is used for linting. To check for any code style violations, run:

    npm run lint

Backend: The backend uses Checkstyle for code style enforcement. The configuration can be found in
the [checkstyle.xml](./backend/checkstyle.xml) file.