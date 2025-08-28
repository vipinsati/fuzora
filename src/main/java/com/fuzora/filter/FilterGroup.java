package com.fuzora.filter;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class FilterGroup implements FilterNode {
    public enum Type { AND, OR }

    protected Type type;
    protected List<FilterNode> filters;

    @Override
    public FilterResult evaluate(JsonNode data) {
        if (type == Type.AND) {
            for (FilterNode node : filters) {
                FilterResult result = node.evaluate(data);
                if (!result.isSuccess()) return result; // Short-circuit failure
            }
            return FilterResult.success();
        } else {
            for (FilterNode node : filters) {
                FilterResult result = node.evaluate(data);
                if (result.isSuccess()) return FilterResult.success(); // Short-circuit success
            }
            return FilterResult.failed("None of the OR conditions passed.");
        }
    }
}
