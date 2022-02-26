# Bookshelf Backend Application

This is a sample backend project written in Java and Spring Boot to implement some operations on a Bookshelf.

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mekucuker_bookshelf&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=mekucuker_bookshelf)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mekucuker_bookshelf&metric=security_rating)](https://sonarcloud.io/dashboard?id=mekucuker_bookshelf)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mekucuker_bookshelf&metric=coverage)](https://sonarcloud.io/dashboard?id=mekucuker_bookshelf)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mekucuker_bookshelf&metric=code_smells)](https://sonarcloud.io/dashboard?id=mekucuker_bookshelf)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=mekucuker_bookshelf&metric=sqale_index)](https://sonarcloud.io/dashboard?id=mekucuker_bookshelf)

This project was implemented by following the principles of OOP, clean layered architecture and best practices of RestAPIs. In this project, it was aimed to achieve a high code coverage with unit and integration tests. It also provides well-implemented API and method documentation. In addition, some examples of design patterns such as factory and strategy can be found in the project.

## Technology Stack

* Spring Boot 2.6.3
* Java 17
* Gradle 7.3.3
* JUnit 5
* Testcontainers for integration tests
* MongoDB
* Spring Data as ODM
* Spring Validation
* AOP for logging (Slf4j was implemented)
* Lombok
* ModelMapper
* OpenAPI with Springdoc (also deploys Swagger UI)
* JavaDoc
* EqualsVerifier for equals and hashCode tests
* Docker
* Docker-compose

The project can be run by using 'docker-compose up' command after Docker image of the project was built. However, the yml configuration has been split into two parts such that docker-compose.bookshelf.yml and docker-compose.mongo.yml to provide the ability to run the project on the IDE. They can be run together or separately by giving the file names with '-f' option. There is also Mongo Express configuration inside the docker-compose.mongo.yml file which is a MongoDB tool provides an admin interface for the database.

Context path of the project is '/bookshelf/api/v1' and Swagger UI can be reached by adding '/swagger' to the context path.

---

<b>Mehmet Emin Küçüker</b>, Software Engineer, [Linkedin](https://linkedin.com/in/mehmeteminkucuker)
