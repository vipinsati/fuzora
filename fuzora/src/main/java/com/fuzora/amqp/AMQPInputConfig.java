package com.fuzora.amqp;

import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AMQPInputConfig implements Function<JsonNode, Map<String, String>> {

	@Override
	public Map<String, String> apply(JsonNode t) {

		return null;
	}

}
