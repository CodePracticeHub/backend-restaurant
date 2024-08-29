# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the application, skipping both compilation and execution of tests
RUN mvn clean package -Dmaven.test.skip=true

# Use the official Tomcat image as the runtime container
FROM tomcat:9.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*

# Set the working directory for Tomcat
WORKDIR /usr/local/tomcat

# Copy the built WAR file to the Tomcat webapps directory
COPY --from=build /app/target/restaurant-management-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/ROOT.jar

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
