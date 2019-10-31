Steps to run the project:

1.) Unzip the kafka-2.3.0-src.tgz file in src/main/resources folder(it is where .tgz file is).
2.) Go to the src/main/resources/Brillio_Challenge_KafkaScript directory and make script file executable(for unix or windows bases system)
3.) To make the script file executable(only for unix based system) run command "chmod u+x <file_location>"
4.) Now double click the appropriate script file to run the zookeeper server, kafka server and create a topic
5.) Now go to the java file named ApplicationDriver.java and run as java application.

#Note:- Here zookeeper is using port 2181 and kafka is using port number 9092 so make sure
		that these two ports are not used by any other process.