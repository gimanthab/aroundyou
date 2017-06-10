package com.gima.aroundyou.solrclient;

import java.io.IOException;
import java.util.List;

/**
 * This interface is called when a search is completed and the
 */

public interface SolrClientSearchRequestCallback {
    public void onFailure(IOException e);
    public void onSuccess(List<IndexOutputDocument> documents);
}
