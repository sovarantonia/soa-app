FROM openjdk:17
# Define build argument for JAR file location
ARG JAR_FILE=target/notification-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} notification.jar
# Expose the application's port
EXPOSE 8082
# Run the JAR file
ENTRYPOINT ["java", "-jar", "/notification.jar"]