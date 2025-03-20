package com.fuzora.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class HttpPollingConfig implements Function<JsonNode, Map<String, Object>> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String pollingInterval;
	private String authType;
	private String url;
	private String method;
	private JsonNode body;
	private JsonNode headers;

	@Override
	public Map<String, Object> apply(JsonNode jsonNode) {
		try {
			// Extract values and set them as class variables
			this.pollingInterval = jsonNode.has("polling_interval") ? jsonNode.get("polling_interval").asText() : null;
			this.authType = jsonNode.has("authType") ? jsonNode.get("authType").asText() : null;
			this.url = jsonNode.has("url") ? jsonNode.get("url").asText() : null;
			this.method = jsonNode.has("method") ? jsonNode.get("method").asText() : null;
			this.body = jsonNode.has("body") ? jsonNode.get("body") : null;
			this.headers = jsonNode.has("headers") ? jsonNode.get("headers") : null;

			// Convert extracted values into a Map
			Map<String, Object> resultMap = new HashMap<>();

			return resultMap;
		} catch (Exception e) {
			throw new RuntimeException("Error processing JSON", e);
		}
	}

	// Getters for accessing extracted values
	public String getPollingInterval() {
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
}