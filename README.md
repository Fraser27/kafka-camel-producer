
# Steps to build

## Build the project
mvn clean install

## Build docker image
docker build --tag=kafka-camel-producer:latest .

# Run docker
docker run -p8080:8080 kafka-camel-producer:latest