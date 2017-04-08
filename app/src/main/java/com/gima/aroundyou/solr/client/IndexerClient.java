package com.gima.aroundyou.solr.client;

import android.content.Context;
import android.util.Log;

import com.gima.aroundyou.R;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.io.IOException;
import java.util.List;

/**
 * This class represents the colr client which use to fetch the data in near real time
 */

class IndexerClient {

    private static final String TAG = IndexerClient.class.getSimpleName();
    private SolrClient solrClient;
    private final Object indexClientsLock = new Object();

    public IndexerClient(Context context) throws IndexerClientException {
        InitializeIndexerClient(context);
    }

    private void InitializeIndexerClient(Context context) throws IndexerClientException {
        if (solrClient == null) {
            synchronized (indexClientsLock) {
                if (solrClient == null) {
                        solrClient = new CloudSolrClient.Builder().withZkHost(context.getString(R.string.solr_zk_host)).build();
                }
            }
        }
    }

    public void indexDocuments(String collection, List<IndexDocument> docs) throws IndexerClientException {
        try {
            solrClient.add(collection, SolrClientUtils.getSolrInputDocuments(docs));
            //TODO:should find a better way to commit, there are different overloaded methods of commit
            solrClient.commit(collection);
        } catch (SolrServerException | IOException e) {
            Log.e(TAG, "Error while inserting the documents to index for table: " + collection, e);
            throw new IndexerClientException("Error while inserting the documents to index for table: " + collection, e);
        }
    }
}
