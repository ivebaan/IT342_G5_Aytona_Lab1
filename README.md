# User Authentication System

A full-stack User Registration and Authentication system with Spring Boot backend and React frontend.

## Features

### Backend
- User Registration with validation
- User Login with JWT authentication
- Password encryption using BCrypt
- Protected API endpoints
- MySQL database integration

### Frontend
- User Registration page
- User Login page
- Protected Dashboard page
- JWT token management
- Logout functionality

## Tech Stack

### Backend
- Java 17
- Spring Boot 4.0.2
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- BCrypt
- Maven

### Frontend
- React JS
- Vite
- React Router DOM
- Axios
- Context API

## API Endpoints

| Method | Endpoint | Description | Protected |
|--------|----------|-------------|-----------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |
| GET | `/api/user/me` | Get user profile | Yes |

## Setup Instructions

### 1. Setup MySQL Database

Start MySQL server and create database:

```sql
CREATE DATABASE dbuserauthenticationg5;
```


### 2. Run Backend

```bash
cd backend/userauthentication
mvnw.cmd spring-boot:run
```

Backend runs on `http://localhost:8080`

### 3. Run Frontend

```bash
cd web
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`

## How to Use

1. Open `http://localhost:5173`
2. Register a new account or login
3. Access the dashboard to view profile
4. Logout when done

## Project Structure

```
backend/
  userauthentication/
    src/main/java/com/aytona/userauthentication/
      controller/       - REST Controllers
      dto/              - Data Transfer Objects
      entity/           - JPA Entities
      repository/       - JPA Repositories
      security/         - Security Configuration
      service/          - Business Logic
      util/             - JWT Utility
    src/main/resources/
      application.properties
web/
  src/
    components/         - ProtectedRoute.jsx
    context/            - AuthContext.jsx
    pages/              - Register, Login, Dashboard
    services/           - API Service
```

## Troubleshooting

### MySQL Connection Error
- Ensure MySQL server is running
- Verify credentials in application.properties
- Check if user_auth_db database exists

### Port Already in Use
- Change port in application.properties

### CORS Error
- Verify backend CORS configuration
- Check if backend is running on port 8080




