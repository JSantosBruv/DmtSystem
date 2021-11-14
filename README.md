# DmtSystem
Backoffice platform used to manage orders, products, workers and clients.

## Functionalities
* CRUDs of Orders, Users and Products
* Authentication and Authorization of Requests
* Export Order registries to Excel
* Order's Calendar with several visual state indicators
* Logging of operations

## Tech Used
* Spring Boot, Spring MVC, Spring Security and Spring Data
* Hibernate
* MySQL
* Java 
* Maven
* HTML, CSS, jQuery, Thymeleaf

## How to Play with it

### Must have
* Java 8
* MySQL
* Maven

### How to run

Update MySQL credentials and database name in [application.properties](src/main/resources/application.properties).

Run in root folder

`mvn package` To create executable jar

`java -jar target/dmtSystem-stable.jar` To run jar

Web Server is exposed on port 8080 with a built in admin user: 

User: admin

Pass: superStrongPassword
