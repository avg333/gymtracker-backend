FROM openjdk:19
ADD ./docker-gymtracker.jar docker-gymtracker.jar
ENTRYPOINT ["java","-jar","docker-gymtracker.jar"]