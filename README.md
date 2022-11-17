
# Project Details
This is a sample Kafka-camel integration with two components

1. Kafka Producer producing events every 1 secs
2. Kafka Consumer reading events every 4 secs

# Steps to build

## Build the project
mvn clean install

## Build docker image
docker build --tag=kafka-camel-producer:latest .

# Run docker
docker run -p8080:8080 kafka-camel-producer:latest