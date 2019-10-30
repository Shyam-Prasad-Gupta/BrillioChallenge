package com.shyam.challenge.prodcons;

import java.util.LinkedList;

import com.shyam.challenge.entity.Token;
import com.shyam.challenge.kafkaproducer.KafkaTokenProducer;

public class TokenProducerConsumer {

	private LinkedList<Token> tokenList = new LinkedList<Token>();
	static final int CAPACITY = 100;

	/**
	 * Here in below infinite loop we are continuously producing the token and it is
	 * for demonstration purpose only in real scenario we can let another system
	 * take care of the task of token production based on the demand or as customers
	 * arrive at the application submission counter where they will receive a token.
	 * <br>
	 * <br>
	 * <b>Note:</b> Every time the application restarts the token value starts from 1 and we are
	 * also generating the Normal and Premium token randomly but in actual it will
	 * be created on request basis.
	 * 
	 * @throws InterruptedException
	 */
	public void produce() throws InterruptedException {
		int value = 1;
		while (true) {
			synchronized (this) {
				while (tokenList.size() == TokenProducerConsumer.CAPACITY) {
					wait();
				}
				Token token = new Token(value, Math.random() > 0.5 ? true : false);
				tokenList.add(token);
				value++;
				notify();
			}
		}
	}

	/**
	 * This method acts as token consumer which is responsible for consuming the
	 * token from <code>TokenList</code> It will read the token from the
	 * <code>Token List</code> and fed each token to a Kafka Producer for further processing for service counter.
	 * 
	 * @throws InterruptedException
	 */
	public void consume() throws InterruptedException {
		while (true) {
			synchronized (this) {
				while (tokenList.size() == 0) {
					notify();//this no need to verify the produce once the token will be generated on-demand basis
					wait();
				}
				Token token = tokenList.removeFirst();
				KafkaTokenProducer ktp = new KafkaTokenProducer();
				ktp.sendToken(token);
			}
		}
	}

}
