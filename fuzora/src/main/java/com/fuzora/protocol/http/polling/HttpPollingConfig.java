package com.fuzora.protocol.http.polling;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component("http_polling_input_config")
public class HttpPollingConfig implements Function<JsonNode, Map<String, Object>> {

	private int pollingInterval;
	private String authType;
	private JsonNode authBody;
	private String url;
	private String method;
	private JsonNode body;
	private JsonNode headers;

	@Override
	public Map<String, Object> apply(JsonNode jsonNode) {
		try {
			// Extract values and set them as class variables
			this.pollingInterval = jsonNode.has("polling_interval") ? jsonNode.get("polling_interval").asInt() : null;
			this.authType = jsonNode.has("authType") ? jsonNode.get("authType").asText() : null;
			this.url = jsonNode.has("url") ? jsonNode.get("url").asText() : null;
			this.method = jsonNode.has("method") ? jsonNode.get("method").asText() : null;
			this.body = jsonNode.has("body") ? jsonNode.get("body") : null;
			this.headers = jsonNode.has("headers") ? jsonNode.get("headers") : null;
			this.authBody = jsonNode.has("auth") ? jsonNode.get("auth") : null;
			// Convert extracted values into a Map
			Map<String, Object> resultMap = new HashMap<>();
			
			return resultMap;
		} catch (Exception e) {
			throw new RuntimeException("Error processing JSON", e);
		}
	}

	// Getters for accessing extracted values
	public int getPollingInterval() {
		return pollingInterval;
	}

	public String getAuthType() {
		return authType;
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public JsonNode getBody() {
		return body;
	}

	public JsonNode getHeaders() {
		return headers;
	}

	public JsonNode getAuthBody() {
		return authBody;
	}
}