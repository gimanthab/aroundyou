package com.gima.aroundyou.solr.client;

import org.apache.solr.common.SolrInputDocument;

import java.util.Map;

/**
 * This class represents the indexDocument used by the app, (extends the solr document)
 */

public class IndexDocument extends SolrInputDocument {

    public IndexDocument(String... fields) {
        super(fields);
    }

    public IndexDocument(Map<String, IndexDocumentField> fields) {
        super(SolrClientUtils.getSolrFields(fields));
    }
}