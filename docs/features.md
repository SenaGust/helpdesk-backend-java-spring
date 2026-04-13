# Features Checklist

## Infrastructure
- [ ] Set up Node.js project environment with TypeScript and Express.js
- [ ] Configure PostgreSQL and Prisma or Knex for database access
- [ ] Create Dockerfile and docker-compose for backend and database
- [ ] Configure Vite for the front-end
- [ ] Add TailwindCSS to the front-end
- [ ] Configure environment variables for backend and front-end

## Authentication
- [ ] Configure JWT authentication for login and secure access
- [ ] Implement authentication and role-based authorization middleware

## Admin
- [ ] Create model for Admin accounts
- [ ] Create endpoint to create an Admin account
- [ ] Create endpoint to list Admin accounts
- [ ] Create endpoint to edit an Admin account
- [ ] Create endpoint to delete an Admin account

## Technician
- [ ] Create model for Technician accounts
- [ ] Create endpoint to create a Technician account
- [ ] Create endpoint to list Technician accounts
- [ ] Create endpoint to edit a Technician account
- [ ] Create endpoint to change Technician password
- [ ] Create endpoint to update Technician availability hours
- [ ] Create endpoint to upload Technician profile image
- [ ] Define default availability hours for technicians
- [ ] Allow Technician to change password after first login
- [ ] Allow Admin to edit Technician availability hours

## Customer
- [ ] Create model for Customer accounts
- [ ] Create endpoint to create a Customer account
- [ ] Create endpoint to list Customer accounts
- [ ] Create endpoint to edit a Customer account
- [ ] Create endpoint to delete a Customer account
- [ ] Create endpoint to upload Customer profile image

## Services
- [ ] Create model for Services
- [ ] Create endpoint to create a Service
- [ ] Create endpoint to list Services
- [ ] Create endpoint to edit a Service
- [ ] Create endpoint to deactivate a Service with Soft Delete

## Tickets
- [ ] Create model for Tickets
- [ ] Create endpoint for Customer to create a Ticket selecting Technician and Service
- [ ] Create endpoint to list Customer Tickets
- [ ] Create endpoint to list Tickets assigned to a Technician
- [ ] Create endpoint to list all Tickets (Admin)
- [ ] Create endpoint to add extra Services to a Ticket (Technician)
- [ ] Create endpoint to update Ticket status to Open, In Progress or Closed

## Business Rules
- [ ] Block Customer from editing or deleting a Ticket after creation
- [ ] Block Technician from creating Tickets and editing Customer accounts
- [ ] Delete all Tickets when a Customer account is deleted
- [ ] Implement visibility rules for Admin, Technician and Customer
- [ ] Create input validations with Zod

## Tests
- [ ] Configure Jest for automated backend tests
- [ ] Create tests for authentication
- [ ] Create tests for user endpoints (Admin, Technician, Customer)
- [ ] Create tests for Service endpoints
- [ ] Create tests for Ticket endpoints
- [ ] Test critical endpoints with supertest or similar library

## Seed
- [ ] Create initial seed with 1 Admin, 3 Technicians with availability hours and 5 Services

## Front-end
- [ ] Create responsive front-end layout following Mobile First with Figma
- [ ] Implement API consumption on the front-end

## Deploy
- [ ] Deploy backend on Render
- [ ] Deploy front-end on Vercel or Netlify
- [ ] Validate environment variables and connection string in production
- [ ] Ensure the API is accessible via public URL

## Documentation
- [ ] Create detailed backend README with setup, scripts, variables and deploy link
- [ ] Create detailed front-end README with setup, scripts and deploy link
- [ ] Include instructions for local setup, tests and example users
- [ ] Verify that all functional and profile requirements have been met
