package com.fuzora.utilities;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fuzora.protocol.HTTPService;

import java.util.Base64;
import java.util.Map;

@Service
public class AuthenticatorService {

	private final HTTPService httpFunctionService;
	private final ObjectMapper objectMapper;

	public AuthenticatorService(HTTPService httpFunctionService, ObjectMapper objectMapper) {
		this.httpFunctionService = httpFunctionService;
		this.objectMapper = objectMapper;
	}

	public Map<String, Object> authenticateWithOAuth(String tokenUrl, String clientId, String clientSecret,
			String grantType, String code, String redirectUri) {
		ObjectNode requestPayload = objectMapper.createObjectNode();
		requestPayload.put("url", tokenUrl);
		requestPayload.put("httpType", "POST");
		ObjectNode body = objectMapper.createObjectNode();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("grant_type", grantType);

		if ("authorization_code".equalsIgnoreCase(grantType)) {
			body.put("code", code);
			body.put("redirect_uri", redirectUri);
		}

		requestPayload.set("body", body);
		return httpFunctionService.apply(requestPayload);
	}

	public Map<String, Object> authenticateWithBasicAuth(String url, String username, String password) {
		ObjectNode requestPayload = objectMapper.createObjectNode();
		requestPayload.put("url", url);
		requestPayload.put("httpType", "GET");
		ObjectNode headers = objectMapper.createObjectNode();
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
		headers.put("Authorization", basicAuth);
		requestPayload.set("headers", headers);
		return httpFunctionService.apply(requestPayload);
	}

	public Map<String, Object> authenticateWithJWT(String url, String jwtToken) {
		ObjectNode requestPayload = objectMapper.createObjectNode();
		requestPayload.put("url", url);
		requestPayload.put("httpType", "GET");
		ObjectNode headers = objectMapper.createObjectNode();
		headers.put("Authorization", "Bearer " + jwtToken);
		requestPayload.set("headers", headers);
		return httpFunctionService.apply(requestPayload);
	}

	public Map<String, Object> authenticateWithApiKey(String url, String apiKeyHeader, String apiKeyValue) {
		ObjectNode requestPayload = objectMapper.createObjectNode();
		requestPayload.put("url", url);
		requestPayload.put("httpType", "GET");
		ObjectNode headers = objectMapper.createObjectNode();
		headers.put(apiKeyHeader, apiKeyValue);
		requestPayload.set("headers", headers);
		return httpFunctionService.apply(requestPayload);
	}
}
