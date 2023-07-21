# Daily Quote

## Goal: To provide users with inspiring quotes and reflections through a web browser

## Tech Stack:

### Front-end: HTML, CSS, JavaScript, jQuery, AJAX

### Back-end: Spring Boot, Spring MVC, Spring Web, Spring Data JPA、Spring Security, Hibernate, Spring Boot Actuator

### Database: H2

## Usage

### Initial

* How to Start: Use the Spring Boot Maven plugin (`mvn spring-boot:run`) to run the application. It will be accessible
  at http://localhost:8080.
  * Recommended Java version: 17.0.7
  * Recommended Maven version: 3.6.3
  * Recommended OS: linux
* How to Check the Data in the Database: Access [H2-Console](http://localhost:8080/h2-console/) to view the data.

### Scenario

* Login:
    * Nothing can be done before logging in
    * Login Roles:
        * Admin: Can check all the members
        * User: Can't check all the members
* Get Daily Quote:
    * Show the quote of the day in random
    * Users can leave notes and save them
* Get My Quotes:
    * Show the quotes the user has previously saved
    * Users can delete their saved quotes
* Check All Members: Only accessible to Admin

## Features

### API handles CORS requests

* Feature: Defines allowed request origins, methods, and destinations to prevent unexpected resource leakage.
* Tech: Initialize a `CorsFilter` to define the allowed request specifications.

### API uses JSON content negotiation

* Feature: Specifies JSON as the format for data transmission between the client and the server, eliminating the need
  for manual data format conversion during coding.
* Tech:
    * In the frontend AJAX, set contentType: `application/json; charset=utf-8` to inform the backend that the data
      format being sent is JSON.
    * In the backend Controller, set `@RequestMapping(produces = "application/json; charset=utf-8")` to check the
      returned data is in JSON format.

### Apply Spring Data JPA to simplify database operations

* Feature: The framework automatically generates SQL statements, relieving developers from writing and maintaining them
  manually.
* Tech: Hibernate (ORM) automatically converts between objects and database records.

### Each HTTP method (`GET`/`POST`/`PUT`/`PATCH`) has corresponding API implementations

* Feature: The purpose of the APIs aligns with the HTTP methods, making the overall meaning clear and understandable.
* Tech: Spring Web
* Explanation:
    * GET: Retrieve a resource. Read-only operation without modifying data.
    * POST: Create a new resource. Usually includes a request body with the necessary data for the new resource.
    * PUT: Update an existing resource. Usually includes a request body with the updated information for the resource.
    * PATCH: Update a portion of an existing resource. The request body only needs to contain the information to be
      changed.

### Perform executable end-to-end (E2E) tests

* Feature: Simulate user operations to ensure the integrity of the functionality.
* Tech: Use H2 to ensure a clean and independent environment for testing.

### API supports JWT authentication to ensure user identity

* Feature:
    * Use JWT to determine user identity and maintain a certain login status after logging in for a period of time.
    * Use Spring Security to enforce user authorization for the API.
* Tech:
    * JWT
    * Spring Security:
        * `PasswordEncoder`
        * `SecurityConfigurerAdapter`

### Write Unit Tests

* Feature: Validate the code to ensure that the functionality meets expectations and facilitate easier execution of
  future refactoring.
* Tech: 
  * jMockit for mocking dependencies and focusing on verifying core logic.
  * Object Mother Pattern to create test data.

### Use Metrics

* Feature: Periodically send requests to check the system's operational status.
* Tech: Spring Boot Actuator

### Use Integrated Caches

* Feature: Store frequently accessed data to reduce the number of database accesses and improve performance.
* Tech: `CacheManager`, `@Cacheable`

