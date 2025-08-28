package com.fuzora.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component("filterConfig")
public class FilterConfiguration implements Function<JsonNode, Map<String, Object>> {

	private FilterNode fNode;

	@Override
	public Map<String, Object> apply(JsonNode node) {
		Map<String, Object> returnStatus = new HashMap<>();
		try {
			fNode = FilterParser.parse(node);

			returnStatus.put("status", "ok");
			returnStatus.put("data", "");
		} catch (Exception e) {
			returnStatus.put("status", "no");
			returnStatus.put("data", e.getMessage());
		}

		return returnStatus;
	}

	public FilterNode getFilterNode() {
		return fNode;
	}
}
