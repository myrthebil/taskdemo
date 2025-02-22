# TaskDemo

This repository contains two services for executing CRUD operations on **Tasks** and **Users**.

## Project Structure

This is a **multi-module Maven** setup with the following modules:

- **`common-model`** – Shared models and utilities.
- **`task-service`** – Manages tasks.
- **`user-service`** – Manages users.

The parent **`pom.xml`** handles common dependencies and extends `spring-boot-starter-parent` for
consistent configuration.

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