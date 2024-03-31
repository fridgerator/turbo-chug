FROM confluentinc/cp-kafka-connect:7.6.0

RUN confluent-hub install --no-prompt --verbose mongodb/kafka-connect-mongodb:latest

ADD ./lib-0.0.2-SNAPSHOT.jar /usr/share/confluent-hub-components/mongodb-kafka-connect-mongodb/lib/lib-0.0.2-SNAPSHOT.jar
