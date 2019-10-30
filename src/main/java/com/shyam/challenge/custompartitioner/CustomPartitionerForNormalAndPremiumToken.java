package com.shyam.challenge.custompartitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.utils.Utils;

import com.shyam.challenge.entity.Token;

public class CustomPartitionerForNormalAndPremiumToken implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {

	}

	/**
	 * This customer partitions ensures that the 30 percent of the total partition
	 * of the total partitions will be dedicated to the PREMIUM customer to serve
	 * the token counter. All all the system at premium counter will get exclusive
	 * access to the PREMIUM token to serve the PREMIUM customer.
	 * 
	 * <b>Note:</b>Here we are assuming that we have 10 token counters in total, of
	 * which 3 tokens are reserved exclusively for the premium customer only and
	 * rest 7 for normal customer.
	 */
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

		Integer partitions = cluster.partitionCountForTopic(topic);
		// here we are reserving 30 percent of the partition for the PREMIUM tokens.
		int reservedpartitionsForPremium = (int) Math.abs(partitions * 0.3);
		int partition = 0;

		boolean isPremiumCustomer = ((Token) value).isPremium();
		if (isPremiumCustomer) {
			partition = Utils.toPositive(Utils.murmur2(valueBytes)) % reservedpartitionsForPremium;
		} else {
			partition = Utils.toPositive(Utils.murmur2(valueBytes)) % (partitions - reservedpartitionsForPremium)
					+ reservedpartitionsForPremium;
		}

		return partition;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
