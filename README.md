# ktor-jwt-auth-example


This is a sample project that demonstrates how to implement JWT authentication in a Ktor application.

## Overview

The project showcases the following features:

- User registration and login endpoints for authentication
- Secured route that requires JWT authentication
- Token generation and verification using the Auth0 JWT library
- Integration with a MySQL database using KTorm library

## Prerequisites

To run this project, you need to have the following installed:

- Kotlin 1.5.x
- MySQL database
- JDK 1.8 or later
- Gradle 7.x

## Getting Started

1. Clone the repository:

   ```shell
   git clone git@github.com:knoldus/ktor-jwt-auth-example.git

``


 ##  Set up the database:

- Create a new MySQL database with the name ktor_auth_example.
- Update the database connection details in the application.conf file.
- Build and run the project:

 ``` shell
cd ktor-jwt-auth-example
./gradlew run
```
##  Access the API endpoints:

- Registration endpoint: POST /register
- Login endpoint: POST /login
- Secured endpoint (requires authentication): GET /me
##  Configuration
The application can be configured through the application.conf file located in the src/main/resources directory. The following configurations are available:

* ktor.application.port: The port on which the server listens (default: 8080)
* database: Database connection details (URL, username, password)
* jwt: JWT configuration (audience, issuer, secret)
##  Dependencies
The project uses the following dependencies:

- Ktor: A framework for building asynchronous servers and clients
- Ktor Authentication: Provides authentication capabilities for Ktor applications
- Auth0 JWT: A library for handling JWT tokens
- KTorm: A SQL-centric Kotlin ORM library
## License
## LinkedIn Profile
* You can connect with me on LinkedIn: https://www.linkedin.com/in/shashikant-tanti/
