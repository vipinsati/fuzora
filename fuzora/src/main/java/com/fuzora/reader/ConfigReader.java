package com.fuzora.reader;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.protocol.configuration.AMQPConfiguration;

@Service
public class ConfigReader {

	@Value("classpath:config.json")
	private Resource jsonResource;

	private JsonNode configFile;
	
	public JsonNode triggerConfig;
	public JsonNode filterConfig;
	public JsonNode transformerConfig;
	public JsonNode actionConfig;

	@Autowired
	AMQPConfiguration amqpConfiguration;
	
	public void readConfigFiles() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		configFile = objectMapper.readTree(jsonResource.getInputStream());
		this.triggerConfig = configFile.get("trigger");
		this.actionConfig = configFile.get("action");
		this.transformerConfig = configFile.get("transformer");
		this.filterConfig = configFile.get("filter");
		
		readAndDeployConfigs();
		
	}

	private void readAndDeployConfigs() {
		
		
	}

	public JsonNode getActionConfig() {
		return actionConfig;
	}

	public JsonNode getFilterConfig() {
		return filterConfig;
	}

	public JsonNode getTransformerConfig() {
		return transformerConfig;
	}

	public JsonNode getTriggerConfig() {
		return triggerConfig;
	}

}
