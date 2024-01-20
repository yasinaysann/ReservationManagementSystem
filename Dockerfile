FROM openjdk:17-jdk-slim


RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:resolve

COPY src ./src


ENTRYPOINT ["mvn", "spring-boot:run"]
