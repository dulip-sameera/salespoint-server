# Welcome to SalesPoint Server

This project is a Point-of-Sale (POS) system developed using the Spring MVC framework, which is a widely used, robust framework for building Java-based web applications. The application follows the Model-View-Controller (MVC) design pattern, which helps in organizing the code into three main layers:

  1. **Model**: The data structure and business logic are handled here.
  2. **View**: The front-end part, which interacts with users.
  3. **Controller**: Manages the interaction between the Model and View, processing user input and returning responses.

The View is a React based web application.

The system leverages MySQL as its database management system, providing a reliable and scalable solution for storing and managing transactional data, user information, inventory, and sales records. MySQL ensures that all data is consistently stored, and allows for easy queries and updates to the system’s datasets.

## Key Features:

1. Secure API Endpoints: All endpoints exposed by the system are secured to ensure that only authorized users can         interact with the system. This is achieved through authentication and authorization mechanisms, ensuring that only     valid users with the correct permissions can access sensitive information such as sales records, user management,      and inventory adjustments.
  
2. Authentication and Authorization: The system employs security features such as JWT (JSON Web Tokens) which require users to log in and verify their identity before accessing the API. This ensures that every request is validated, and only users with the necessary roles.

3. User Roles and Permissions: The POS system supports multiple user roles with different levels of access. The           authorization mechanism ensures that users can only access and perform actions permitted for their roles.
   The Roles : SUPER_ADMIN, ADMIN, MANAGER, CLERK, CASHIER

## Security Machenisms:
- JWT Authentication: When a user logs in, a secure JWT token is generated. This token is sent with every subsequent API request to authenticate and authorize the user.
- Role-based Access Control(RBAC) - Based on the user's role the system enforce access control that restrict or allow acctions on different parts of the application
- Data Encryption: All the passwords are encrypted before storing in the database

## Purposes of developing this project
- Gaining insight into Layered Architecture and the MVC design pattern.
- Learning how to manage relationships between entities effectively.
- Enhancing skills in using Spring Boot, Spring Data JPA, and Spring Security.

## Tech Stack
- Spring Boot
- Spring Security
- Spring Data JPA
- Lombok

When running the project fill the `application.properties` with correct values.
