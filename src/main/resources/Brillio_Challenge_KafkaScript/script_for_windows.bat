####Go to the root dirctory of Kafka programs and run this script files to run the zookeeper server and Kafka server

#script to run the zookeeper server
../kafka-2.3.0-src/bin/windows/zookeeper-server-start.bat ../kafka-2.3.0-src/config/zookeeper.properties

#script to run the Kafka server
../kafka-2.3.0-src/bin/windows/kafka-server-start.bat ../kafka-2.3.0-src/config/server.properties

#script to run create the topic
../kafka-2.3.0-src/bin/windows/kafka-topics.bat --zookeeper localhost:2181 --create --topic MyTokenTopic --partitions 10