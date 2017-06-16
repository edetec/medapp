# About MEDAPP
MEDAPP is a simple example of Web System  for medical clinic records 
that provides the registers of doctors, specialties and patients.

## Demo
http://35.185.21.209/

## Prerequisites
* Postgresql 9.x or Docker
* JDK 1.8
* Maven 3.*
* Bower

## Installing
1. download or clone the project 
2. prepare the database (with Docker only run docker-compose up) 
3. Go to the src/main/webapp/ folder and execute:
```
bower install
```
4. Go to the root folder and execute:
```
mvn clean install
```

## Testing
Run the following maven command on the console in the root directory of the project
```
mvn test
```

## Technologies
 * Angular JS 1.x
 * RDash rdash-angular
 * Jersey 2.23
 * Servlet 3.0
 * Hibernate 5.2
 * JUnit 4
 * Jetty
 * Hsqldb
 
