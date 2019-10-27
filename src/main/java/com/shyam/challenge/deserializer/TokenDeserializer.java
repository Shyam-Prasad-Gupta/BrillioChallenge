package com.shyam.challenge.deserializer;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyam.challenge.entity.Token;

public class TokenDeserializer implements Deserializer<Token> {

	@Override
	public Token deserialize(String topic, byte[] data) {
		Token token = null;
		ObjectMapper objMapper = new ObjectMapper();
		try {
			token = objMapper.readValue(data, Token.class);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return token;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		Deserializer.super.configure(configs, isKey);
	}

	@Override
	public void close() {
		Deserializer.super.close();
	}

}
