package com.shyam.challenge.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyam.challenge.entity.Token;

public class TokenSerializer implements Serializer<Token> {

	public byte[] serialize(String topic, Token data) {
		byte[] tokenByteArray = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			tokenByteArray = objectMapper.writeValueAsString(data).getBytes();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
		}
		return tokenByteArray;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		Serializer.super.configure(configs, isKey);
	}

	@Override
	public void close() {
		Serializer.super.close();
	}

}
