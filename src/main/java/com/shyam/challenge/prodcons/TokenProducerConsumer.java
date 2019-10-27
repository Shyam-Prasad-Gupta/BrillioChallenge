package com.shyam.challenge.prodcons;

import java.util.LinkedList;

import com.shyam.challenge.entity.Token;
import com.shyam.challenge.kafkaproducer.KafkaTokenProducer;

public class TokenProducerConsumer {
	
	private LinkedList<Token> tokenList = new LinkedList<Token>();
	static final int CAPACITY = 100;
	
	public void produce() throws InterruptedException{
		int value = 1;
		while(true) {
			synchronized (this) {
				while(tokenList.size() == TokenProducerConsumer.CAPACITY) {
					wait();
				}
				Token token = new Token(value, Math.random() > 0.5 ? true : false);
				tokenList.add(token);
				value++;
				notify();
			}
		}
	}
	
	public void consume() throws InterruptedException{
		while(true) {
			synchronized (this) {
				while(tokenList.size() == 0) {
					notify();
					wait();
				}
				Token token = tokenList.removeFirst();
				KafkaTokenProducer ktp = new KafkaTokenProducer();
				ktp.sendToken(token);
			}
		}
	}

}
