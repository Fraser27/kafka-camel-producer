#!/usr/bin/bash 
Green='\033[0;32m'
Red='\033[0;31m'
NC='\033[0m'

sudo su -s "$0"

printf "$Green Enter a username for kafka-manager $NC"
read username

printf "$Green Enter a password for kafka-manager $NC"
read passwd

printf "$Green Enter zookeeper  string $NC"
read zookeeperurl

printf "$Green Enter a bootstrap brokers string $NC"
read bootstrapbrokers

printf "$Green Installing docker $NC"
amazon-linux-extras install docker -y
printf "$Green Start docker service $NC"
service docker start
printf "$Green Give docker permissions to ec2-user $NC"
usermod -a -G docker ec2-user
# printf "$Green Install docker compose $NC"
# curl -L https://github.com/docker/compose/releases/download/1.24.1/docker-compose-`uname -s`-`uname -m` -o /usr/bin/docker-compose
# chmod +x /usr/bin/docker-compose

printf "$Green Run Kafka-manager on port 9000 $NC"
image="deltaprojects/kafka-manager"
container="kafka-manager"

if docker container ls -a --format '{{.Names}}' | grep -q $container; then
  printf "$Green Remove any existing $container container $NC"
  docker stop $container
  sleep 5
  echo 'Wait for 5 seconds to stop kafka-manager..'
  docker container rm -f $container
  docker container prune
fi

printf "$Green Run the kafka-manager container $NC"
docker run -d --name kafka-manager -p 9000:9000 -e ZK_HOSTS="$zookeeperurl" -e KAFKA_MANAGER_USERNAME="$username" -e KAFKA_MANAGER_PASSWORD="$passwd" deltaprojects/kafka-manager

container="kafka-microservices"
if docker container ls -a --format '{{.Names}}' | grep -q $container; then
  printf "$Green Remove any existing  $container container $NC"
  docker container rm $container
fi

# Substitute bootstrap brokers
#!/bin/bash
prop_file="src/main/resources/application.properties" 

prop_name="kafka.brokers"

new_prop_value="$bootstrapbrokers"

if grep -Eq "^${prop_name}[[:space:]]*=" "$prop_file"; then
# Replace property value
  # This command works on linux not on mac
  sed -i "s/^${prop_name}=.*/$prop_name=$new_prop_value/g" $prop_file
  # This command works on mac not on linux
  #sed -i '' "s/^${prop_name}=.*/$prop_name=$new_prop_value/g" $prop_file
  echo "Property $prop_name updated successfully in $prop_file"
else
  echo "Property $prop_name does not exist in $prop_file"
fi

printf "$Green Build and run the microservices demo project $NC"
docker build -t "kafka-microservices:Dockerfile" .
docker run -d --name kafka-microservices -p 8080:8080 kafka-microservices:Dockerfile