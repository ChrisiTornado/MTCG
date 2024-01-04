# Verwende ein Java-Basisimage
FROM openjdk:latest
COPY target/*jar mtcg.jar
EXPOSE 10001
ENTRYPOINT ["java", "-jar","mtcg.jar"]