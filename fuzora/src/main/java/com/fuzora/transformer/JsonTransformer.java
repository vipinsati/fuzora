package com.fuzora.transformer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;

public class JsonTransformer {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonNode transform(JsonNode input, TransformerSchema schema) {
        ObjectNode result = mapper.createObjectNode();

        for (MappingRule rule : schema.getMappings()) {
            try {
                Object value = extractValue(input, rule);
                setValue(result, rule.getTarget(), value);
            } catch (Exception e) {
                if (rule.getDefaultValue() != null) {
                    setValue(result, rule.getTarget(), rule.getDefaultValue());
                }
            }
        }

        return result;
    }

    private Object extractValue(JsonNode input, MappingRule rule) {
        if (rule.isArray()) {
            List<?> array = JsonPath.read(input.toString(), rule.getSource());
            List<Object> transformed = new ArrayList<>();
            for (Object element : array) {
                ObjectNode tempNode = mapper.convertValue(element, ObjectNode.class);
                Object value = tempNode.get(rule.getSourceField()).asText(); // basic value fetch
                transformed.add(value);
            }
            return transformed;
        } else if (rule.getTransform() != null) {
            return applyTransform(input, rule.getTransform());
        } else {
            try {
                return JsonPath.read(input.toString(), "$." + rule.getSource());
            } catch (Exception e) {
                return rule.getDefaultValue();
            }
        }
    }

    private Object applyTransform(JsonNode input, TransformOperation operation) {
        List<Object> values = new ArrayList<>();
        for (String arg : operation.getArgs()) {
            if (arg.startsWith("$.") || arg.startsWith("$[")) {
                try {
                    Object val = JsonPath.read(input.toString(), arg);
                    values.add(val);
                } catch (Exception e) {
                    values.add(null);
                }
            } else {
                values.add(arg);
            }
        }

        switch (operation.getType()) {
            case UPPERCASE:
                return String.valueOf(values.get(0)).toUpperCase();
            case LOWERCASE:
                return String.valueOf(values.get(0)).toLowerCase();
            case CONCAT:
                return values.stream().map(String::valueOf).reduce("", String::concat);
            case ADD:
                return ((Number) values.get(0)).doubleValue() + ((Number) values.get(1)).doubleValue();
            case SUBTRACT:
                return ((Number) values.get(0)).doubleValue() - ((Number) values.get(1)).doubleValue();
            case MULTIPLY:
                return ((Number) values.get(0)).doubleValue() * ((Number) values.get(1)).doubleValue();
            case DIVIDE:
                return ((Number) values.get(0)).doubleValue() / ((Number) values.get(1)).doubleValue();
            default:
                return null;
        }
    }

    private void setValue(ObjectNode result, String path, Object value) {
        String[] keys = path.split("\\.");
        ObjectNode current = result;
        for (int i = 0; i < keys.length - 1; i++) {
            if (!current.has(keys[i])) {
                current.set(keys[i], mapper.createObjectNode());
            }
            current = (ObjectNode) current.get(keys[i]);
        }
        JsonNode node = mapper.valueToTree(value);
        current.set(keys[keys.length - 1], node);
    }
}
