FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/MTCG-1.0.jar MTCG.jar
ENTRYPOINT ["java", "-jar", "/MTCG.jar"]