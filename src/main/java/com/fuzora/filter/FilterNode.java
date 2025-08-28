package com.fuzora.filter;

import com.fasterxml.jackson.databind.JsonNode;

public interface FilterNode {
    FilterResult evaluate(JsonNode data);
}