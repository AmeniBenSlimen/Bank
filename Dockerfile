
FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/Bank-0.0.3-SNAPSHOT.jar /Bank.war
ENTRYPOINT ["java", "-jar", "/Bank.war"]