package com.fuzora.protocol.http.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fuzora.protocol.http.model.HTTPServiceRequest;

@Service
public class HTTPService implements Function<HTTPServiceRequest, Map<String, Object>> {

	private RestTemplate restTemplate;

	public HTTPService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(HTTPService.class);

	@Override
	public Map<String, Object> apply(HTTPServiceRequest request) {
		String httpType = request.getRequestMethod();
		String url = request.getRequestUrl();
		JsonNode body = (JsonNode) request.getRequestBody();
		JsonNode auth = request.getAuth();
		JsonNode headers1 = request.getHeaders();
		Map<String, String> headersMap = new HashMap<>();

		if (headers1 != null)
			headers1.fields().forEachRemaining(entry -> headersMap.put(entry.getKey(), entry.getValue().asText()));

		HttpHeaders headers = new HttpHeaders();
		headersMap.forEach(headers::set);

		headers = decorateWithAuth(headers, auth);

//		if (auth != null && auth.has("token")) {
//			headers.set("Authorization", "Bearer " + auth.get("token").asText());
//		}

		switch (httpType.toUpperCase()) {
		case "GET":
			return handleGet(url, headers);
		case "POST":
			return handlePost(url, body, headers);
		case "PUT":
			return handlePut(url, body, headers);
		case "DELETE":
			return handleDelete(url, headers);
		default:
			throw new IllegalArgumentException("Unsupported HTTP Type: " + httpType);
		}
	}

	private Map<String, Object> handleGet(String url, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
		return buildResponse(response);
	}

	private Map<String, Object> handlePost(String url, JsonNode body, HttpHeaders headers) {
		HttpEntity<JsonNode> entity = new HttpEntity<>(body, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
		return buildResponse(response);
	}

	private Map<String, Object> handlePut(String url, JsonNode body, HttpHeaders headers) {
		HttpEntity<JsonNode> entity = new HttpEntity<>(body, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JsonNode.class);
		return buildResponse(response);
	}

	private Map<String, Object> handleDelete(String url, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, JsonNode.class);
		return buildResponse(response);
	}

	private Map<String, Object> buildResponse(ResponseEntity<JsonNode> response) {
		Map<String, Object> result = new HashMap<>();
		result.put("status", response.getStatusCode().value());
		result.put("body", response.getBody().toString());
		return result;
	}

	private HttpHeaders decorateWithAuth(HttpHeaders headers, JsonNode auth) {
		switch (auth.get("authType").asText()) {
		case "oauth":
			headers.set("Authorization", "Bearer " + auth.get("token").asText());
			break;
		case "basic":
			String username = auth.get("username").asText();
			String pwd = auth.get("password").asText();
			String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + pwd).getBytes());
			headers.set("Authorization", basicAuth);
			break;
		default:
			LOGGER.warn("Unknow auth type encountered. Setting nothing to headers.");
		}
		return headers;
	}
}
