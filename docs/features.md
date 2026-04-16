# Features Checklist

## Infrastructure
- [x] Set up Java project environment with Spring Boot
- [x] Configure H2 in-memory database for development
- [ ] Configure PostgreSQL for production
- [ ] Create Dockerfile and docker-compose for backend and database
- [ ] Configure environment variables for each environment

## Authentication
- [ ] Configure JWT authentication for login and secure access
- [ ] Implement authentication and role-based authorization

## Users
- [x] Create base `User` model with JPA JOINED inheritance
- [x] Create `Customer` model
- [x] Create `ServiceProvider` model with available slot hours
- [x] Create endpoint to create a user (`POST /users`)
- [x] Create endpoint to list active users (`GET /users`)
- [x] Create endpoint to get user by ID (`GET /users/{id}`)
- [x] Create endpoint to update user name (`PATCH /users/{id}`)
- [x] Create endpoint to change password (`POST /users/{id}/password`)
- [x] Create endpoint to soft-delete a user (`DELETE /users/{id}`)
- [x] Create endpoint to reactivate a deleted user (`POST /users/{id}/reactivate`)
- [x] Encrypt passwords with BCrypt before storing

## Validation & Error Handling
- [x] Add input validation to all request DTOs
- [x] Create custom `@ValidPassword` annotation
- [x] Create `GlobalExceptionHandler` with structured error responses
- [x] Handle `404 Not Found`, `409 Conflict`, `400 Bad Request`, `405 Method Not Allowed`
- [ ] Add cross-field validation (e.g. `availableSlotHours` required for `SERVICE_PROVIDER`)

## Services
- [ ] Create model for Services
- [ ] Create endpoint to create a Service
- [ ] Create endpoint to list Services
- [ ] Create endpoint to edit a Service
- [ ] Create endpoint to deactivate a Service with soft delete

## Tickets
- [ ] Create model for Tickets
- [ ] Create endpoint for Customer to create a Ticket selecting Technician and Service
- [ ] Create endpoint to list Customer Tickets
- [ ] Create endpoint to list Tickets assigned to a ServiceProvider
- [ ] Create endpoint to list all Tickets (Admin)
- [ ] Create endpoint to add extra Services to a Ticket
- [ ] Create endpoint to update Ticket status (Open, In Progress, Closed)

## Business Rules
- [ ] Block Customer from editing or deleting a Ticket after creation
- [ ] Block ServiceProvider from creating Tickets and editing Customer accounts
- [ ] Delete all Tickets when a Customer account is deleted
- [ ] Implement visibility rules per user type

## Tests
- [ ] Configure test environment with Spring Boot Test
- [ ] Create tests for user endpoints
- [ ] Create tests for Service endpoints
- [ ] Create tests for Ticket endpoints

## Seed
- [ ] Create initial seed with sample users and services

## Deploy
- [ ] Deploy backend on Render or Railway
- [ ] Configure production database (PostgreSQL)
- [ ] Validate environment variables and connection string in production
- [ ] Ensure the API is accessible via public URL

## Documentation
- [x] Create backend README with setup, stack and API reference
- [ ] Add instructions for local setup and example requests
