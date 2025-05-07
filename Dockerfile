# Etapa 1: build com Maven e Java 21
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: imagem leve somente com o JRE e o JAR
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Variáveis de ambiente (substituídas no docker-compose)
ENV POSTGRES_DB=""
ENV POSTGRES_USER=""
ENV POSTGRES_PASSWORD=""
ENV EMAIL_ADMIN=""
ENV PASSWORD_ADMIN=""

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
