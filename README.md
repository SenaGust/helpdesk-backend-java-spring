# Helpdesk API

A RESTful API built with Spring Boot for managing helpdesk users, supporting two user types: **Customers** and **Service
Providers**.

## Tech Stack

- **Java 25**
- **Spring Boot 4.1**
- **Spring Security** — JWT authentication and endpoint protection
- **Spring Data JPA** — database access
- **H2** — file-based database (development)
- **Flyway** — database migrations
- **MapStruct** — object mapping between DTOs and entities
- **Lombok** — boilerplate reduction
- **Hibernate Validator** — Bean Validation for request DTOs
- **JJWT** — JWT token generation and validation

## Project Structure

```
src/main/java/com/senagust/helpdesk/
├── config/           # Spring beans (security, Flyway, H2 console)
├── controller/       # HTTP request handling
├── dto/              # Request and response objects
├── exception/        # Custom exceptions and global exception handler
├── filter/           # JWT authentication filter
├── mapper/           # MapStruct mappers
├── model/            # JPA entities
├── repository/       # Database access
├── service/          # Business logic (interface + impl)
└── validation/       # Custom validation annotations
```

## Data Model

Users are modeled using **JPA JOINED inheritance**:

```
users (base table)
├── customers
└── service_providers (has available slot hours)
```

| User Type          | Extra fields                                           |
|--------------------|--------------------------------------------------------|
| `CUSTOMER`         | —                                                      |
| `SERVICE_PROVIDER` | `availableSlotHours` (list of integers, e.g. 8, 9, 14) |

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+

### Running the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api`.

### H2 Console

Access the file-based database UI at:

```
http://localhost:8080/api/h2-console
```

Use the following connection settings:

| Field    | Value                       |
|----------|-----------------------------|
| JDBC URL | `jdbc:h2:file:./data/helpdesk` |
| Username | `sa`                        |
| Password | *(leave empty)*             |

## API Endpoints

### Authentication

| Method | Endpoint          | Description               | Auth required |
|--------|-------------------|---------------------------|---------------|
| `POST` | `/api/auth/login` | Login and receive a JWT   | No            |

#### Login — Request Body

```json
{
  "email": "john@example.com",
  "password": "secret"
}
```

#### Login — Response

```json
{
  "accessToken": "eyJhbGci..."
}
```

All protected endpoints require the token in the `Authorization` header:

```
Authorization: Bearer eyJhbGci...
```

### Users

| Method   | Endpoint                      | Description                                     | Auth required |
|----------|-------------------------------|-------------------------------------------------|---------------|
| `POST`   | `/api/users`                  | Create a new user                               | No            |
| `GET`    | `/api/users`                  | List all active users                           | Yes           |
| `GET`    | `/api/users/{id}`             | Get a user by ID                                | Yes           |
| `PATCH`  | `/api/users/{id}`             | Update a user's name                            | Yes           |
| `DELETE` | `/api/users/{id}`             | Soft-delete a user (sets `isActive` to `false`) | Yes           |
| `POST`   | `/api/users/{id}/password`    | Change a user's password                        | Yes           |
| `POST`   | `/api/users/{id}/reactivate`  | Reactivate a soft-deleted user                  | Yes           |

### Create User — Request Body

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "secret",
  "userType": "CUSTOMER"
}
```

For a Service Provider, include available slot hours:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com",
  "password": "secret",
  "userType": "SERVICE_PROVIDER",
  "availableSlotHours": [
    8,
    9,
    10,
    14,
    17
  ]
}
```

## Error Responses

All errors follow this format:

```json
{
  "status": 404,
  "error": "User not found with id: 5c158863-...",
  "path": "/api/users/5c158863-...",
  "timestamp": "2026-04-15T18:58:15.259Z"
}
```

| Status                    | Meaning                                      |
|---------------------------|----------------------------------------------|
| `400 Bad Request`         | Invalid or missing input fields              |
| `404 Not Found`           | User does not exist or has been deleted      |
| `405 Method Not Allowed`  | Wrong HTTP method used for the endpoint      |
| `409 Conflict`            | Email already in use or user already active  |
| `500 Internal Server Error` | Unexpected server error                    |
