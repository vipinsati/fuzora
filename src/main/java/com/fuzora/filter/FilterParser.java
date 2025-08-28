package com.fuzora.filter;

import java.util.ArrayList;
import java.util.List;

//FilterParser.java
import com.fasterxml.jackson.databind.JsonNode;

public class FilterParser {

	private FilterParser() {
	}

	public static FilterNode parse(JsonNode node) throws Exception {
		if (node.has("AND") || node.has("OR")) {
			FilterGroup group = new FilterGroup();
			String key = node.has("AND") ? "AND" : "OR";
			group.type = key.equals("AND") ? FilterGroup.Type.AND : FilterGroup.Type.OR;

			List<FilterNode> children = new ArrayList<>();
			for (JsonNode child : node.get(key)) {
				children.add(parse(child));
			}
			group.filters = children;
			return group;
		} else {
			FilterCondition condition = new FilterCondition();
			condition.field = node.get("field").asText();
			condition.operation = node.get("operation").asText();
			condition.value = node.get("value");
			return condition;
		}
	}
}
