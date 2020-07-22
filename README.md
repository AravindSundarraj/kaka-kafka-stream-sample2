# kaka-kafka-stream-sample2
````

this service get the data from upstream topic transaction and transfrom in to 
3 different kafka stream and processor.

The processed events will be send to downstream topics

mvn archetype:generate -DgroupId=com.kaka.group -DartifactId=kaka-kafka-sample2 -DarchetypeArtifactId=maven-archetype-quickstart 
-DinteractiveMode=false
````

# Topic
````
create topic transaction

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic transaction --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic purchases --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic rewards --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic patterns --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic electronics --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic coffee --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic purchases-renew --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic customer_transactions --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic transaction-renew --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic purchases-renew2 --config min.insync.replicas=2

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 2 --partitions 2 --topic join-topic --config min.insync.replicas=2



````

# Kafka - configuration

# start zookeeper
````
zookeeper-server-start.bat %KAFKA_HOME%\config\zookeeper.properties

````
# start kafka broker
````
kafka-server-start.bat %KAFKA_HOME%\config\server.properties

kafka-server-start.bat %KAFKA_HOME%\config\server1.properties
````

# to List the topics
````
kafka-topics.bat --list --bootstrap-server localhost:9092
````

# produce console message in upstream-message topic
````
kafka-console-producer.bat --bootstrap-server localhost:9092 --topic upstream-message

kafka-console-producer.bat --broker-list localhost:9092 --topic upstream-message
````

# consumer console message in downstream-message topic
````
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic transaction  --from-beginning

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic purchases  --from-beginning

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic join-topic  --from-beginning




````

# Adding State for Rewards
````
overall rewards need to be calculated for each customer.
state processor is required to call back the previously sent transactions and calculate the 
rewards with current transaction
Implement ValueTransformer interface

````