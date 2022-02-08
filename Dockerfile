FROM openjdk:17-alpine
ADD build/libs/bookshelf-v0.0.1.jar bookshelf-v0.0.1.jar
ENTRYPOINT ["java", "-jar", "/bookshelf-v0.0.1.jar"]