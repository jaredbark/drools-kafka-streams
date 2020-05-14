# drools-kafka-streams
A demo combining Kafka Streams and Drools.

## Start the Kafka cluster
### Download and install Confluent Platform 3.0.0 from ZIP archive
    $ wget http://packages.confluent.io/archive/3.0/confluent-3.0.0-2.11.zip
    $ unzip confluent-3.0.0-2.11.zip
    $ cd confluent-3.0.0
### Start ZooKeeper
    $ ./bin/zookeeper-server-start --daemon ./etc/kafka/zookeeper.properties
### Configure Kafka
    $ vi ./etc/kafka/server.properties
    num.network.threads=1
    delete.topic.enable = true
### Start Kafka
    $ ./bin/kafka-server-start --daemon ./etc/kafka/server.properties

## Prepare the input data
### Create the input topic, named inputTopic1
    $ ./bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic inputTopic1
### Create the output topic, named outputTopic1
    $ ./bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic outputTopic1

## Checking with Java application
### Inspect the output data
    Run ConsumerMain
    Run Drools application
    Run KafkaStreamsDroolsMain
    Send an input data to the input topic
    Run InputProducerMain
    Type a text in prompt

## Checking with the built-in CLI tools
### Inspect the output data
    $ ./bin/kafka-console-consumer --zookeeper localhost:2181 --topic outputTopic1 --from-beginning
### Send an input data to the input topic
    $ echo "a\nb\nc\nd\ne\nf\ng"  > /tmp/input.txt
    $ cat /tmp/input.txt | ./bin/kafka-console-producer --broker-list localhost:9092 --topic inputTopic1

## Delete topic
### Delete topic by using Kafka CLI tools
    $ ./bin/kafka-topics --delete --zookeeper localhost:2181 --topic inputTopic1
    $ ./bin/kafka-topics --delete --zookeeper localhost:2181 --topic outputTopic1
### Check partition once topic not deleted successfully
    $ ./bin/kafka-topics --describe --zookeeper localhost:2181 --topic #TOPIC_NAME#
### Delete partition by using Zookeeper shell
    $ ./bin/zookeeper-shell localhost:2181
    ls /brokers/topics
    rmr /brokers/topics/#TOPIC_NAME#
    quit
    $ ./bin/kafka-topics --describe --zookeeper localhost:2181 --topic #TOPIC_NAME#
### Stop the Kafka cluster
#### Stop ZooKeeper
    $ ./bin/zookeeper-server-stop
#### Stop Kafka
    $ ./bin/kafka-server-stop