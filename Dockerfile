#
# Build stage
#
FROM maven:3.8.7-openjdk-18 AS MAVEN_BUILD
COPY pom.xml /build/
COPY authapi/src /build/authapi/src/
COPY authapi/pom.xml /build/authapi/
COPY common/src /build/common/src/
COPY common/pom.xml /build/common/
COPY exercisesapi/pom.xml /build/exercisesapi/
COPY springboot/pom.xml /build/springboot/
COPY workoutsapi/pom.xml /build/workoutsapi/
WORKDIR /build/
RUN mvn clean package -DskipTests
RUN dir -s
#
# Package stage
#
FROM openjdk:18
WORKDIR /app
COPY --from=MAVEN_BUILD /build/springboot/target/springboot.jar /app/
ENTRYPOINT ["java","-jar","springboot.jar"]