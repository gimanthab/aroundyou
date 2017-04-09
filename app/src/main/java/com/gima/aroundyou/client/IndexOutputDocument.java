package com.gima.aroundyou.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the output documents which are a result of a search operation
 */

public class IndexOutputDocument {
    private Map<String, Object> fields;

    public IndexOutputDocument() {
        this.fields = new HashMap<>();
    }

    public IndexOutputDocument(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Object getFieldValue(String name) {
        if (name == null) {
            return null;
        }
        if (!fields.containsKey(name)) {
            return null;
        }
        return fields.get(name);
    }
}
