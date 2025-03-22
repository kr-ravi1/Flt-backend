# Use Maven with JDK 21 for building the application
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY . .

# Build the application (skip tests to speed up)
RUN mvn clean package -DskipTests

# Use a smaller JDK 21 runtime for running the app
FROM eclipse-temurin:21-alpine
WORKDIR /app

# Copy JAR from the build stage (update jar name if needed)
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
