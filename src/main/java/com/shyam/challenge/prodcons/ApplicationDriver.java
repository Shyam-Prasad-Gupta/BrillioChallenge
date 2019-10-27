package com.shyam.challenge.prodcons;

import com.shyam.challenge.kafkaconsumer.KafkaTokenConsumer;

public class ApplicationDriver {

	public static void main(String... args) {

		final TokenProducerConsumer tpc = new TokenProducerConsumer();
		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					tpc.produce();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					tpc.consume();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		Thread consumerTokenService = new Thread(new Runnable() {
			@Override
			public void run() {
				KafkaTokenConsumer.tokenCounterForAll();
			}
		});

		producerThread.start();
		consumerThread.start();
		consumerTokenService.start();
		try {
			producerThread.join();
			consumerThread.join();
			consumerTokenService.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
