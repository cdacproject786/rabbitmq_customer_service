
FROM eclipse-temurin:20-alpine

LABEL mentainer="arbaazsayed1602@gmail.com"

WORKDIR /app

COPY target/customer_service.jar /app/customer_service.jar

ENTRYPOINT ["java", "-jar", "customer_service.jar"]