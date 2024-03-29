#################
#  Build stage  #
#################
FROM maven:3.9.5-amazoncorretto-21 AS MAVEN_BUILD
#FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package
#################
# Package stage #
#################
FROM amazoncorretto:21
#FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]