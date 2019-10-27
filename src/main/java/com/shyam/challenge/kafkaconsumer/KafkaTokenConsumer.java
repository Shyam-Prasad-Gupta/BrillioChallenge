package com.shyam.challenge.kafkaconsumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.shyam.challenge.entity.Token;

public class KafkaTokenConsumer {

	public static void tokenCounterForAll() {
		String topicName = "MyTokenTopic";
		String topicGroupName = "tokenForAll";

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "com.shyam.challenge.deserializer.TokenDeserializer");
		props.put("group.id", topicGroupName);
		
		KafkaConsumer<String, Token> consumer = new KafkaConsumer<String, Token>(props);
		consumer.subscribe(Arrays.asList(topicName));
		
		while(true) {
			ConsumerRecords<String, Token> tokens = consumer.poll(100);
			for(ConsumerRecord<String, Token> token: tokens) {
				System.out.println("Consumed: " + token.toString());
			}
		}
	}

}
