# Use a base image with Java 21
FROM eclipse-temurin:21

# Create a directory in the container
WORKDIR /app

# Copy the rest of the application to the container
COPY . .

# Copy the built JAR file to the root directory
RUN cp build/libs/fastfood-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]