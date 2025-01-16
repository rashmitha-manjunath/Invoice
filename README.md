## Overview

The Invoice Management API provides a RESTful interface for managing invoices and payments in a Spring Boot application. It allows clients to create, retrieve, update, and delete invoices, as well as process payments and handle overdue invoices.

## Technologies

- Java 11+
- Spring Boot
- Spring Web
- Lombok (optional for boilerplate code)
- Gradle for dependency management

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11+
- Gradle
- Integrated Development Environment (IDE), e.g., IntelliJ IDEA, Eclipse

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/invoice-management-api.git
   cd invoice-management-api

2. Build the project:
   ```bash
   ./gradlew build 

3. Run the application:
   ```bash
   ./gradlew bootRun

3. The API will be available at:
   ```bash
   http://localhost:8080/invoices.


## Endpoints
POST : /invoices

GET : /invoices

GET : /invoices/{id}

POST : /invoices/{id}/payments

PUT : /invoices/{id}/payments

DELETE : /invoices/{id}

POST : /invoices/process-overdue

## NOTE
1. I have tested all the functionalities using postman and documented along with curl cmd. I have attached test document to this repository.
2. I have added Dockerfile & docker-compose file but unable to test it as my windows does not support docker.
