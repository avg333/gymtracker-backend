#
# Build stage
#
FROM maven:3.8.7-openjdk-18 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package
#
# Package stage
#
FROM openjdk:18
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/docker-gymtracker.jar /app/
ENTRYPOINT ["java","-jar","docker-gymtracker.jar"]