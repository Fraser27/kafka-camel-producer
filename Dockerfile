FROM amazoncorretto:11
MAINTAINER Fraser Sequeira
COPY target/kafka-camel-producer-1.0.jar kafka-camel-producer.jar
ENTRYPOINT ["java","-jar","/kafka-camel-producer.jar"]