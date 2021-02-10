# Coding Assignment (IKEA)

### Author

**Savino Bizzoca**

## Introduction

[![Build Status](https://travis-ci.com/savbiz/warehouse-api.svg?token=zMwRmhcr5kJ6irypfMjZ&branch=main)](https://travis-ci.com/github/savbiz/warehouse-api)

The assignment is to implement a warehouse software.

## Assignment Requirements

- `GET /api/v1/products/` (Get all products and quantity of each that is an available with the current inventory)
- `DELETE /api/v1/products/{id}` (Remove (Sell) a product and update the inventory accordingly)

***

## How to run the application

The application has been developed in **Java 11**.

The application will run on port 8080.

To check Swagger UI, navigate to http://localhost:8080/warehouse/swagger-ui/.

***

## Docker-Compose

The project also contains a docker-compose file which will setup and start a dockerized PostgreSQL database:

- `mvn clean package`
- `docker-compose up`

Afterwards start the application with:
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

***

## Libraries used

* **Spring Boot Starter Web**
  [_Starter for building com.savbiz.ikea.warehouse.web, including RESTful, applications using Spring MVC. Uses Tomcat as
  the default embedded container_]

* **Project Lombok**
  [_Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java._]

* **Springfox Boot Starter & UI**
  [_JSON API documentation for spring based applications._]

* **Spring Boot Starter Data JPA**
  [_Starter for using Spring Data JPA with Hibernate._]

* **Bean Validation API**
  [_JSR 380 is a specification of the Java API for bean validation, part of Jakarta EE and JavaSE._]

* **ModelMapper**
  [_ModelMapper is an intelligent, refactoring safe object mapping library that automatically maps objects to each
  other._]

* **Apache Commons Lang**
  [_Apache Commons Lang, a package of Java utility classes for the classes that are in java.lang's hierarchy, or are
  considered to be so standard as to justify existence in java.lang._]

* **Spring Boot Starter Actuator**
  [_Starter for using Spring Boot's Actuator which provides production ready features to help you monitor and manage
  your application._]

* **Spring Cloud Starter Sleuth with Brave**
  [_Spring Cloud Sleuth provides Spring Boot auto-configuration for distributed tracing._]

* **PostgreSQL**
  [_PostgreSQL Database._]

* **Flyway Core**
  [_DB Migration tool._]

* **Spring Boot Starter Data JPA**
  [_Starter for using Spring Data JPA with Hibernate._]

Used for testing:

* **Spring Boot Starter Test**
  [_Starter for testing Spring Boot applications with libraries including JUnit Jupiter, Hamcrest and Mockito._]

* **Test Containers**
  [_Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container._]
