FROM eclipse-temurin:21

# Set the working directory
WORKDIR /app

# Copy the built JAR file (update the name as needed)
COPY build/libs/*.jar app.jar

# Expose port (optional, update if your app uses a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]