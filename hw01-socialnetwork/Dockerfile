FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY pom.xml /build/pom.xml
COPY hw01-socialnetwork/pom.xml /build/hw01-socialnetwork/pom.xml

RUN mvn -f /build/pom.xml -N install
RUN mvn dependency:go-offline -B -f /build/hw01-socialnetwork/pom.xml

COPY ./hw01-socialnetwork /build/hw01-socialnetwork

WORKDIR /build/hw01-socialnetwork
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/hw01-socialnetwork/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]