package com.fuzora.protocol.amqp.output;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fuzora.protocol.amqp.service.RabbitMQService;

@Service("amqp_output_config")
public class AMQPOutputConfig implements Function<JsonNode, Map<String, Object>> {

	RabbitMQService rabbitMQService;

	public AMQPOutputConfig(RabbitMQService rabbitMQService) {
		this.rabbitMQService = rabbitMQService;
	}

	private JsonNode auth;
	private String queue;
	private String exchange;

	@Override
	public Map<String, Object> apply(JsonNode inputConfigData) {
		Map<String, Object> returnStatus = new HashMap<>();

		auth = inputConfigData.get("auth");
		queue = inputConfigData.get("queue").asText();
		exchange = inputConfigData.get("exchange").asText();

		rabbitMQService.createConnection(auth.get("host").asText(), auth.get("port").asInt(),
				auth.get("username").asText(), auth.get("password").asText(), auth.get("virtualHost").asText());

		returnStatus.put("status", "ok");
		returnStatus.put("data", "");
		return returnStatus;

	}

	public RabbitMQService getRabbitMQService() {
		return rabbitMQService;
	}

	public JsonNode getAuth() {
		return auth;
	}

	public String getQueue() {
		return queue;
	}

	public String getExchange() {
		return exchange;
	}

}
