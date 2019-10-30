package com.shyam.challenge.kafkaproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.shyam.challenge.entity.Token;

public class KafkaTokenProducer {

	private String topicName = "MyTokenTopic";
	private String key = null;
	private Token token = null;
	private String kafkaServers = "localhost:9092,localhost:9093";

	public void sendToken(Token token) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaServers);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "com.shyam.challenge.serializer.TokenSerializer");
		props.put("partitioner.class", "com.shyam.challenge.custompartitioner.CustomPartitionerForNormalAndPremiumToken");
		
		
		Producer<String, Token> producer = new KafkaProducer<String, Token>(props);

		ProducerRecord<String, Token> producerRecord = new ProducerRecord<String, Token>(topicName, token);

		producer.send(producerRecord);
		//System.out.println("Token sent: " + token.toString());
		producer.close();
	}

}
