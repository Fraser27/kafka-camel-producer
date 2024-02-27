#!/usr/bin/bash 
Green='\033[0;32m'
Red='\033[0;31m'
NC='\033[0m'

printf "$Green Enter a username for kafka-manager $NC"
read username

printf "$Green Enter a password for kafka-manager $NC"
read passwd

printf "$Green Installing docker $NC"
amazon-linux-extras install docker -y
printf "$Green Start docker service $NC"
service docker start
printf "$Green Give docker permissions to ec2-user $NC"
usermod -a -G docker ec2-user
printf "$Green Install docker compose $NC"
curl -L https://github.com/docker/compose/releases/download/1.24.1/docker-compose-`uname -s`-`uname -m` -o /usr/bin/docker-compose
chmod +x /usr/bin/docker-compose

printf "$Green Run Kafka-manager on port 9000 $NC"
image="deltaprojects/kafka-manager"
container="kafka-manager"

if docker container ls -a --format '{{.Names}}' | grep -q $container; then
  printf "$Green Remove any existing $container container $NC"
  docker container rm $container
fi

printf "$Green Run the kafka-manager container $NC"
docker run -d --name kafka-manager -p 9000:9000 -e KAFKA_MANAGER_USERNAME=$username -e KAFKA_MANAGER_PASSWORD=$passwd deltaprojects/kafka-manager

container="kafka-microservices"
if docker container ls -a --format '{{.Names}}' | grep -q $container; then
  printf "$Green Remove any existing  $container container $NC"
  docker container rm $container
fi

printf "$Green Build and run the microservices demo project $NC"
docker build -t "kafka-microservices:Dockerfile" .