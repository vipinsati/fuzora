package com.fuzora.filter;

//FilterNode.java (interface)
public interface FilterNode {
	boolean evaluate(com.fasterxml.jackson.databind.JsonNode data);
}
