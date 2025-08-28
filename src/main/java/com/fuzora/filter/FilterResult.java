package com.fuzora.filter;


import java.util.HashMap;
import java.util.Map;

public class FilterResult {
    private final boolean success;
    private final String reason;

    private FilterResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public static FilterResult success() {
        return new FilterResult(true, null);
    }

    public static FilterResult failed(String reason) {
        return new FilterResult(false, reason);
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (success) {
            map.put("status", "success");
            
        } else {
            map.put("status", "failed");
            map.put("reason", reason);
        }
        return map;
    }

    public boolean isSuccess() {
        return success;
    }
}
