package com.gima.aroundyou.solr.client;

import org.apache.solr.common.SolrInputField;

/**
 * This class represents a single attribute which is a part of the IndexDocument (inherited from SolrField)
 */

public class IndexDocumentField extends SolrInputField {

    public IndexDocumentField(String n) {
        super(n);
    }

}
