package com.gima.aroundyou.client;

import java.io.IOException;

import okhttp3.Response;

/**
 * This interface is called when the
 */

public interface SolrClientIndexRequestCallback {

    public void onFailure(IOException e);
    public void onSuccess(Response response);
}
