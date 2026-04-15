# Helpdesk API

A RESTful API built with Spring Boot for managing helpdesk users, supporting two user types: **Customers** and **Service
Providers**.

## Tech Stack

- **Java 21**
- **Spring Boot 4.1**
- **Spring Data JPA** — database access
- **H2** — in-memory database (development)
- **MapStruct** — object mapping between DTOs and entities
- **Lombok** — boilerplate reduction

## Project Structure

```
src/main/java/com/senagust/helpdesk/
├── controller/       # HTTP request handling
├── service/          # Business logic (interface + impl)
├── repository/       # Database access
├── model/            # JPA entities
├── dto/              # Request and response objects
└── mapper/           # MapStruct mappers
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

Access the in-memory database UI at:

```
http://localhost:8080/api/h2-console
```

Use the following connection settings:

| Field    | Value                  |
|----------|------------------------|
| JDBC URL | `jdbc:h2:mem:helpdesk` |
| Username | `sa`                   |
| Password | *(leave empty)*        |

## API Endpoints

### Users

| Method   | Endpoint          | Description                                     |
|----------|-------------------|-------------------------------------------------|
| `POST`   | `/api/users`      | Create a new user                               |
| `GET`    | `/api/users`      | List all active users                           |
| `GET`    | `/api/users/{id}` | Get a user by ID                                |
| `DELETE` | `/api/users/{id}` | Soft-delete a user (sets `isActive` to `false`) |

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

| Status          | Meaning                                 |
|-----------------|-----------------------------------------|
| `404 Not Found` | User does not exist or has been deleted |
| `409 Conflict`  | Email is already in use                 |
