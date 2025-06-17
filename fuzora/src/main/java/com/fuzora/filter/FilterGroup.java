package com.fuzora.filter;

import java.util.List;

//FilterGroup.java
import com.fasterxml.jackson.databind.JsonNode;

public class FilterGroup implements FilterNode {
 public enum Type { AND, OR }

 public Type type;
 public List<FilterNode> filters;

 @Override
 public boolean evaluate(JsonNode data) {
     if (type == Type.AND) {
         return filters.stream().allMatch(f -> f.evaluate(data));
     } else {
         return filters.stream().anyMatch(f -> f.evaluate(data));
     }
 }
}
