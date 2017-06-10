package com.gima.aroundyou.solrclient;

/**
 * Exception which is thrown when something go wrong in indexer
 */

public class IndexerClientException extends Exception {

    public IndexerClientException(String msg) {
        super(msg);
    }

    public IndexerClientException(String msg, Throwable e) {
        super(msg, e);
    }
}
