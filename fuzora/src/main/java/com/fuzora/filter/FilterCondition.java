package com.fuzora.filter;

//FilterCondition.java
import com.fasterxml.jackson.databind.JsonNode;

public class FilterCondition implements FilterNode {
	public String field;
	public String operation;
	public JsonNode value;

	@Override
	public boolean evaluate(JsonNode data) {
		JsonNode fieldValue = data.path(field);
		if (fieldValue.isMissingNode())
			return false;

		switch (operation) {
		case "equals":
			return fieldValue.equals(value);
		case "contains":
			return fieldValue.isTextual() && fieldValue.asText().contains(value.asText());
		case "startsWith":
			return fieldValue.isTextual() && fieldValue.asText().startsWith(value.asText());
		case "endsWith":
			return fieldValue.isTextual() && fieldValue.asText().endsWith(value.asText());
		case "lt":
			return fieldValue.isNumber() && fieldValue.asDouble() < value.asDouble();
		case "lte":
			return fieldValue.isNumber() && fieldValue.asDouble() <= value.asDouble();
		case "gt":
			return fieldValue.isNumber() && fieldValue.asDouble() > value.asDouble();
		case "gte":
			return fieldValue.isNumber() && fieldValue.asDouble() >= value.asDouble();
		default:
			return false;
		}
	}
}
