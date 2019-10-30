package com.shyam.challenge.kafkaconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.shyam.challenge.entity.Token;

public class KafkaTokenConsumer {

	private static String topicName = "MyTokenTopic";
	// private static String topicGroupName = "tokenForAll";
	private static Properties props = new Properties();

	/**
	 * This method is responsible for starting the premium and normal token consumer
	 * token by passing their designated partition number.
	 */
	public static void openTokenCounterForAll() {

		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "com.shyam.challenge.deserializer.TokenDeserializer");
		// props.put("group.id", topicGroupName);

		List<TopicPartition> partitionListForNormalToken = new ArrayList<TopicPartition>();
		for (int i = 3; i < 9; i++) {
			partitionListForNormalToken.add(new TopicPartition(topicName, i));
		}

		TopicPartition tp0 = new TopicPartition(topicName, 0), tp1 = new TopicPartition(topicName, 0),
				tp2 = new TopicPartition(topicName, 2);
		Thread premiumCounterThread = new Thread(new Runnable() {
			@Override
			public void run() {
				openPremiumTokenCounter(Arrays.asList(tp0, tp1, tp2));
			}
		});
		Thread normalCounterThread = new Thread(new Runnable() {
			@Override
			public void run() {
				openNormalTokenCounter(partitionListForNormalToken);
			}
		});
		premiumCounterThread.start();
		normalCounterThread.start();
		try {
			premiumCounterThread.join();
			normalCounterThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Here we are continuously looking for the token in the message or token in the
	 * queue although practicallly we should introduce a delay so that operator at
	 * premium token counter get the minimum time to address the token customer.
	 */
	public static void openPremiumTokenCounter(List<TopicPartition> partitions) {
		KafkaConsumer<String, Token> consumer = new KafkaConsumer<String, Token>(props);
		// consumer.subscribe(Arrays.asList(topicName));

		consumer.assign(partitions);

		while (true) {
			ConsumerRecords<String, Token> tokens = consumer.poll(100);
			for (ConsumerRecord<String, Token> token : tokens) {
				System.out.println("Served the Premium Token Customer: " + token.value().toString());
			}
		}
	}

	/**
	 * Here we are continuously looking for the token in the message or token in the
	 * queue although practicallly we should introduce a delay so that operator at
	 * the normal token counter get the minimum time to address the token customer.
	 */
	public static void openNormalTokenCounter(List<TopicPartition> partitions) {
		KafkaConsumer<String, Token> consumer = new KafkaConsumer<String, Token>(props);
		consumer.assign(partitions);
		while (true) {
			ConsumerRecords<String, Token> tokens = consumer.poll(100);
			for (ConsumerRecord<String, Token> token : tokens) {
				System.out.println("Served the Normal Token Customer: " + token.value().toString());
			}
		}
	}

}
