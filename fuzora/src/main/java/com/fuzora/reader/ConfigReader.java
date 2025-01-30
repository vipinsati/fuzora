package com.fuzora.reader;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.constants.AppConstants;
import com.fuzora.external.impl.TriggerConfig;
import com.fuzora.protocol.configuration.AMQPConfiguration;

@Service
public class ConfigReader implements ApplicationContextAware {

	@Value("classpath:config.json")
	private Resource jsonResource;

	private JsonNode configFile;

	private JsonNode triggerConfig;
	private JsonNode filterConfig;
	private JsonNode transformerConfig;
	private JsonNode actionConfig;
	private String triggerProtocol;
	private String actionProtocol;

	@Autowired
	AMQPConfiguration amqpConfiguration;

	public void readConfigFiles() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		configFile = objectMapper.readTree(jsonResource.getInputStream());
		this.triggerConfig = configFile.get(AppConstants.TRIGGER);
		this.actionConfig = configFile.get(AppConstants.ACTION);
		this.transformerConfig = configFile.get(AppConstants.TRANSFORMER);
		this.filterConfig = configFile.get(AppConstants.FILTER);
		this.triggerProtocol = configFile.get(AppConstants.T_PROTOCOL).asText();
		this.actionProtocol = configFile.get(AppConstants.A_PROTOCOL).asText();

		readAndDeployConfigs();

	}

	@Autowired(required = false)
	TriggerConfig triggerConfigg;

	private void readAndDeployConfigs() {
		if (this.triggerConfigg != null)
			triggerConfigg.apply(null);
		else
			System.out.println("It's null");
//		deployTriggerConfigs(this.triggerConfig);

	}

	@SuppressWarnings("unchecked")
	private void deployTriggerConfigs(JsonNode triggerConfig) {
		// deploy trigger config
		Function<JsonNode, Map<String, Object>> t_conf_app = (Function<JsonNode, Map<String, Object>>) this.applicationContext
				.getBean(this.triggerProtocol);
		t_conf_app.apply(this.triggerConfig);

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

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
