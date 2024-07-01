# Start with a base image containing java runtime
FROM openjdk:17-jdk-slim

# Information around who maintains the image
MAINTAINER duykhanh0703.id.vn

# Add the application's  jar to the image
COPY target/Farming-App-0.0.1-SNAPSHOT.jar Farming-App-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "Farming-App-0.0.1-SNAPSHOT.jar"]