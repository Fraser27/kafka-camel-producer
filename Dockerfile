FROM maven:3.6-openjdk-11 as build
MAINTAINER Fraser Sequeira
# Copy over your source code 
COPY src /app/src
COPY pom.xml /app
# Change to app directory
WORKDIR /app
# Run maven clean and package 
RUN mvn clean install

FROM amazoncorretto:11
COPY --from=build /app/target/*.jar /opt/app.jar
EXPOSE 8080
# Configure the entry point  
ENTRYPOINT ["java","-jar","/opt/app.jar"]
# COPY target/kafka-camel-producer-1.0.jar kafka-camel-producer.jar
# ENTRYPOINT ["java","-jar","/kafka-camel-producer.jar"]