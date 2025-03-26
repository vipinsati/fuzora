package com.fuzora.utilities;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fuzora.protocol.http.model.HTTPServiceRequest;
import com.fuzora.protocol.http.service.HTTPService;

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
		HTTPServiceRequest hsr = new HTTPServiceRequest();
		hsr.setRequestUrl(redirectUri);
		hsr.setRequestMethod("POST");
		ObjectNode body = objectMapper.createObjectNode();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("grant_type", grantType);

		if ("authorization_code".equalsIgnoreCase(grantType)) {
			body.put("code", code);
			body.put("redirect_uri", redirectUri);
		}
		hsr.setRequestBody(body);
		return httpFunctionService.apply(hsr);
	}

	public Map<String, Object> authenticateWithBasicAuth(String redirectUri, String username, String password) {

		HTTPServiceRequest hsr = new HTTPServiceRequest();
		hsr.setRequestUrl(redirectUri);
		hsr.setRequestMethod("GET");
		ObjectNode headers = objectMapper.createObjectNode();
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
		headers.put("Authorization", basicAuth);
		hsr.setHeaders(headers);

		return httpFunctionService.apply(hsr);
	}

	public Map<String, Object> authenticateWithJWT(String redirectUri, String jwtToken) {
		HTTPServiceRequest hsr = new HTTPServiceRequest();
		hsr.setRequestUrl(redirectUri);
		hsr.setRequestMethod("GET");

		ObjectNode headers = objectMapper.createObjectNode();
		headers.put("Authorization", "Bearer " + jwtToken);
		hsr.setHeaders(headers);
		return httpFunctionService.apply(hsr);
	}

	public Map<String, Object> authenticateWithApiKey(String redirectUri, String apiKeyHeader, String apiKeyValue) {
		HTTPServiceRequest hsr = new HTTPServiceRequest();
		hsr.setRequestUrl(redirectUri);
		hsr.setRequestMethod("GET");
		ObjectNode headers = objectMapper.createObjectNode();
		headers.put(apiKeyHeader, apiKeyValue);
		hsr.setHeaders(headers);
		return httpFunctionService.apply(hsr);
	}
}
