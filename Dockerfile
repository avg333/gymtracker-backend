#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
MAINTAINER https://github.com/avg333
COPY "./target/gymtracker-backend-0.0.1-SNAPSHOT.jar" "app.jar"
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

CMD java $_JAVA_OPTIONS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -jar app.jar
