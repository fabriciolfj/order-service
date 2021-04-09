FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} order-service.jar

ENTRYPOINT ["java", "-jar", "order-service.jar"]