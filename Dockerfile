FROM eclipse-temurin:17.0.3_7-jdk-jammy AS build

WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src src
RUN ./gradlew build -x test
FROM eclipse-temurin:17.0.3_7-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENV SERVER_PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/postgres
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]