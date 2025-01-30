package com.trigger;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fuzora.external.impl.TriggerConfig;

//@Component
public class InputConfig implements TriggerConfig {

	@Override
	public Map<String, Object> apply(JsonNode t) {
		return null;
	}

}
