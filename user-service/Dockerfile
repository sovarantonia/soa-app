FROM openjdk:17
# Define build argument for JAR file location
ARG JAR_FILE=target/user-service-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} userservice.jar

# Expose the application's port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/userservice.jar"]