####Go to the root dirctory of Kafka programs and run this script files to run the zookeeper server and Kafka server\

#script to run the zookeeper server
sh ../kafka-2.3.0-src/bin/zookeeper-server-start.sh ../kafka-2.3.0-src/config/zookeeper.properties

#script to run the Kafka server
sh ../kafka-2.3.0-src/bin/kafka-server-start.sh ../kafka-2.3.0-src/config/server.properties

#script to run another Kafka server
sh ../kafka-2.3.0-src/bin/kafka-server-start.sh ../kafka-2.3.0-src/config/server-1.properties

#script to run create the topic
sh ../kafka-2.3.0-src/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic MyTokenTopic --partitions 10