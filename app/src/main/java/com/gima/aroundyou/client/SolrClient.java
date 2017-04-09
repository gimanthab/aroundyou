package com.gima.aroundyou.client;

import android.content.Context;

import com.gima.aroundyou.R;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * REST client used to communicate with the SolrServer
 */

public class SolrClient {

    private String zkHost;
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private String locationEventsCollection;
    private static final String TAG = SolrClient.class.getSimpleName();

    public SolrClient(Context context) {
        this.zkHost = context.getString(R.string.solr_zk_host);
        this.locationEventsCollection = context.getString(R.string.location_events_data);
    }

    private static List<Map<String, Object>> getLocationDataAsArray(List<IndexInputDocument> documents) {
        List<Map<String, Object>> locationData = new ArrayList<>();
        for (IndexInputDocument document : documents) {
            locationData.add(document.getFields());
        }
        return locationData;
    }

    public void indexLocationData(List<IndexInputDocument> docs) throws IndexerClientException {
        List<Map<String, Object>> locationDataArray = getLocationDataAsArray(docs);
        String locationDataAsString = gson.toJson(locationDataArray);
        Request request = new Request.Builder().url(zkHost + locationEventsCollection +
                Constants.ADD_DOCUMENTS_URL_PATH).post(RequestBody.create(MediaType.parse(Constants.TEXT_JSON),
                locationDataAsString)).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            throw new IndexerClientException("Error while adding location data: " + e.getMessage(), e);
        }
    }

    public void indexLocationData(List<IndexInputDocument> docs, final SolrClientIndexRequestCallback callback) {
        List<Map<String, Object>> locationDataArray = getLocationDataAsArray(docs);
        String locationDataAsString = gson.toJson(locationDataArray);
        Request request = new Request.Builder().url(zkHost + locationEventsCollection +
                Constants.ADD_DOCUMENTS_URL_PATH).post(RequestBody.create(MediaType.parse(Constants.TEXT_JSON),
                locationDataAsString)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                callback.onSuccess(response);
            }
        });
    }


    public List<IndexOutputDocument> searchLocationData(String query) throws IndexerClientException{
        List<IndexOutputDocument> indexOutputDocuments;
        Request request = new Request.Builder().url(zkHost + locationEventsCollection +
                Constants.SEARCH_DOCUMENTS_URL_PATH + query).get().build();
        String str;
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            str = response.body().string();
            indexOutputDocuments = createIndexOutputDocuments(str);
            if (indexOutputDocuments != null) return indexOutputDocuments;

        } catch (IOException e) {
            throw new IndexerClientException("Error while fetching location data: " + e.getMessage(), e);
        }
        return null;
    }

    public void searchLocationData(String query, final SolrClientSearchRequestCallback callback) throws IndexerClientException{

        Request request = new Request.Builder().url(zkHost + locationEventsCollection +
                Constants.SEARCH_DOCUMENTS_URL_PATH + query).get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    String str = response.body().string();
                    List<IndexOutputDocument> indexOutputDocuments = createIndexOutputDocuments(str);
                    callback.onSuccess(indexOutputDocuments);
                }
            });
    }

    private List<IndexOutputDocument> createIndexOutputDocuments(String str) {
        LinkedTreeMap responseTree;
        ArrayList docs;
        List<IndexOutputDocument> indexOutputDocuments;
        if (str != null && !str.isEmpty()) {
            responseTree = gson.fromJson(str, LinkedTreeMap.class);
            if (responseTree.containsKey(Constants.ELEMENT_JSON_RESPONSE)) {
                responseTree = (LinkedTreeMap) responseTree.get(Constants.ELEMENT_JSON_RESPONSE);
                if (responseTree.containsKey(Constants.ELEMENT_JSON_DOCS)) {
                    docs = (ArrayList) responseTree.get(Constants.ELEMENT_JSON_DOCS);
                    indexOutputDocuments = getIndexOutputDocuments(docs);
                    return indexOutputDocuments;

                }
            }
        }
        return new ArrayList<>();
    }

    private List<IndexOutputDocument> getIndexOutputDocuments(List<LinkedTreeMap> docs) {
        List<IndexOutputDocument> documents = new ArrayList<>();
        for (LinkedTreeMap doc : docs) {
            IndexOutputDocument document = new IndexOutputDocument(doc);
            documents.add(document);
        }
        return documents;
    }

    public void close() {
        client = null;
    }

}
