FROM openjdk:17
# Define build argument for JAR file location
ARG JAR_FILE=target/kafka-logger-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} kafkalogger.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/kafkalogger.jar"]