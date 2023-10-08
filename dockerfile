FROM openjdk:11-jre-slim 

# Set the working directory to /app
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY target/spring-boot-postgresql-0.0.1-SNAPSHOT.jar /app/spring-boot-postgresql-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Define environment variable for PostgreSQL connection (can be overridden by Compose)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

CMD ["java", "-jar", "spring-boot-postgresql-0.0.1-SNAPSHOT.jar"]