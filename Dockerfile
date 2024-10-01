#FROM ubuntu:latest
#LABEL authors="lj"
#
#ENTRYPOINT ["top", "-b"]

# Use JDK
FROM openjdk

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app/app.jar"]