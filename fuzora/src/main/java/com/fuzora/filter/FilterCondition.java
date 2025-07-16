package com.fuzora.filter;

import com.fasterxml.jackson.databind.JsonNode;

public class FilterCondition implements FilterNode {
	protected String field;
	protected String operation;
	protected JsonNode value;

	@Override
	public FilterResult evaluate(JsonNode data) {
		JsonNode fieldValue = data.path(field);

		if (fieldValue.isMissingNode() || fieldValue.isNull()) {
			return FilterResult.failed("Field '" + field + "' not found or null");
		}

		try {
			return switch (operation) {
			case "equals" -> handleEquals(fieldValue);
			case "contains" -> handleTextOperation(fieldValue, "contains");
			case "startsWith" -> handleTextOperation(fieldValue, "startsWith");
			case "endsWith" -> handleTextOperation(fieldValue, "endsWith");
			case "lt", "lte", "gt", "gte" -> handleNumericOperation(fieldValue);
			default -> FilterResult.failed("Unsupported operation: " + operation);
			};
		} catch (Exception e) {
			return FilterResult.failed("Evaluation error: " + e.getMessage());
		}
	}

	private FilterResult handleEquals(JsonNode fieldValue) {
		return fieldValue.equals(value) ? FilterResult.success()
				: FilterResult.failed("Field '" + field + "' does not equal " + value);
	}

	private FilterResult handleTextOperation(JsonNode fieldValue, String op) {
		if (!fieldValue.isTextual() || !value.isTextual()) {
			return FilterResult.failed("Invalid '" + op + "' usage on non-text field/value");
		}

		String text = fieldValue.asText();
		String target = value.asText();

		boolean result = switch (op) {
		case "contains" -> text.contains(target);
		case "startsWith" -> text.startsWith(target);
		case "endsWith" -> text.endsWith(target);
		default -> false;
		};

		return result ? FilterResult.success()
				: FilterResult.failed("Field '" + field + "' does not " + op + " " + value);
	}

	private FilterResult handleNumericOperation(JsonNode fieldValue) {
		if (!fieldValue.isNumber() || !value.isNumber()) {
			return FilterResult.failed("Invalid numeric operation: " + operation + " on non-numeric types");
		}

		double left = fieldValue.asDouble();
		double right = value.asDouble();

		boolean result = switch (operation) {
		case "lt" -> left < right;
		case "lte" -> left <= right;
		case "gt" -> left > right;
		case "gte" -> left >= right;
		default -> false;
		};

		return result ? FilterResult.success()
				: FilterResult.failed("Numeric condition failed: " + left + " " + operation + " " + right);
	}

}
