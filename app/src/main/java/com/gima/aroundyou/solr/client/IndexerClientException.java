package com.gima.aroundyou.solr.client;

/**
 * This class represents the Indexer's exceptions
 */

public class IndexerClientException extends Exception {
    public IndexerClientException(String message) {
        super(message);
    }

    public IndexerClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
