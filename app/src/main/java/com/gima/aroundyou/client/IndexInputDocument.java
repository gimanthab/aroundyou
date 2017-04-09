package com.gima.aroundyou.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Document which represents the Solr document
 */

public class IndexInputDocument {

    private Map<String, Object> fields;

    public IndexInputDocument() {
        fields = new HashMap<>();
    }

    public IndexInputDocument(Map<String, Object> fields) {
        this.fields = fields;
    }

    public void addField(String name, Object value) throws IndexerClientException {
        if (name != null) {
            this.fields.put(name, value);
        } else {
            throw new IndexerClientException("field name is null.");
        }
    }

    public Object getFieldValue(String name)  {
        if (name == null) {
            return null;
        }
        if (!fields.containsKey(name)) {
            return  null;
        }
        return fields.get(name);
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}
