# Car-rental-app
This application is a system for managing car-rental company. Company can be run in several cities.
Application provide user with an ability to make car reservations, renting and returning.
Costs of services are being calculated based on cars rental cost per day and rental duration.

## Prerequisites
This project uses following technologies:
- JDK 11,
- Spring + Hibernate
- H2 Database Connector 

You should have access to H2 database. Access to the database is controlled via configuration file in resources.

## What does it do?
This application has following functionalities:
- employees can be placed in specific departments
- user (employee) can manage fleet of cars for rent (add new cars to the fleet, modify or delete existing cars)
- user (any user) can check for cars that are available for rent in given time period
- user (customer) can create reservation for a car in specific time period
- reservations can be cancelled (with cash penalty
for late cancellations)

## What is to be done?
Following functionalities should be improved:
- database need to be switched for MySQL
- basic company revenue calculations
- car search need to be improved (addition of filtering options)
- implement GUI with Angular 
- add functionality for gathering and displaying company statistics 
- redo tests

## How to build

To build this application:
```
mvn package
```

To run this application:
```
cd target 
java -jar applicationName.jar
```

## Authors
Hubert Gradowski
