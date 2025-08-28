package com.fuzora.transformer.runner;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.transformer.TransformerSchema;

@Service
public class TransformerConfig implements Function<JsonNode, Map<String, Object>> {

	private final ObjectMapper mapper = new ObjectMapper();

	private TransformerSchema schema;

	@Override
	public Map<String, Object> apply(JsonNode t) {

		try {
			schema = mapper.readValue(t.toString(), TransformerSchema.class);
		} catch (Exception e) {
			// handle exceptions here
		}
		return Collections.emptyMap();
	}

	public TransformerSchema getSchema() {
		return schema;
	}

}
