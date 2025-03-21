package com.fuzora.protocol.http;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class HTTPServiceRequest {
	private int pollingInterval;
	private String authType;
	private JsonNode auth;
	private String requestUrl;
	private String requestMethod;
	private Object requestBody;
	private JsonNode headers;
}
