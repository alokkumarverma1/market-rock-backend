# Step 1: Choose a base image with Java installed
FROM eclipse-temurin:21-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy your built jar file into the container
# Replace 'your-app.jar' with the name of your jar
COPY target/dimondinvest-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port your Spring Boot app will run on
EXPOSE 8080

# Step 5: Run the application
ENTRYPOINT ["java","-jar","app.jar"]
