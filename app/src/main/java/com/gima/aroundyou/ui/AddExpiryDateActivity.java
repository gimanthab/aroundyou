package com.gima.aroundyou.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.gima.aroundyou.R;
import com.gima.aroundyou.properties.AddEventProperties;
import com.gima.aroundyou.solrclient.Constants;
import com.gima.aroundyou.solrclient.IndexInputDocument;
import com.gima.aroundyou.solrclient.IndexerClientException;
import com.gima.aroundyou.solrclient.SolrClient;
import com.gima.aroundyou.solrclient.SolrClientIndexRequestCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Response;

/**
 * This class represents the logc related to expiry date of the new events
 */

public class AddExpiryDateActivity extends AppCompatActivity implements View.OnClickListener, SolrClientIndexRequestCallback {

    private CalendarView calendar;
    private AddEventProperties addEventProperties;
    private SolrClient solrClient;
    private String TAG = AddExpiryDateActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expiry_date);
        Button btnNext = (Button) findViewById(R.id.btnExpirySubmit);
        Button btnBack = (Button) findViewById(R.id.btnExpiryBack);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        calendar = (CalendarView) findViewById(R.id.calendarExpiry);
        addEventProperties = AddEventProperties.getInstance();
        solrClient = new SolrClient(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnExpirySubmit) {
            addEventProperties.setEventExpiryDate(calendar.getDate());

            try {
                IndexInputDocument document = createNewEventDocument();
                List<IndexInputDocument> docs = new ArrayList<>();
                docs.add(document);
                final ProgressDialog progressDialog = new ProgressDialog(AddExpiryDateActivity.this);
                progressDialog.setTitle(getString(R.string.adding_new_event));
                progressDialog.setMessage(getString(R.string.please_wait_while_adding_event));
                progressDialog.show();
                solrClient.indexLocationData(docs, new SolrClientIndexRequestCallback() {
                    @Override
                    public void onFailure(IOException e) {
                        Log.e(TAG, "Adding location data failed. Error: " + e.getMessage(), e);
                        progressDialog.dismiss();
                        goToHomeActivity(false);
                    }

                    @Override
                    public void onSuccess(Response response) {
                        progressDialog.dismiss();
                        goToHomeActivity(true);
                    }
                });
            } catch (IndexerClientException e) {
                Log.e(TAG, "Error while adding location data: " + e.getMessage(), e);
            }

        } else if (v.getId() == R.id.btnExpiryBack) {
            addEventProperties.setEventExpiryDate(null);
            finish();
        }
    }

    private void goToHomeActivity(boolean submitSuccess) {
        addEventProperties.reset();
        Intent intent = new Intent(AddExpiryDateActivity.this, AroundYouActivity.class);
        intent.putExtra(Constants.SUBMIT_SUCCESSFUL, submitSuccess);
        startActivity(intent);
    }

    private IndexInputDocument createNewEventDocument() throws IndexerClientException {
        IndexInputDocument document = new IndexInputDocument();
        document.addField(IndexInputDocument.FIELD_ID, generateUUID());
        document.addField(IndexInputDocument.FIELD_CATEGORY, addEventProperties.getEventCategories());
        document.addField(IndexInputDocument.FIELD_TITLE, addEventProperties.getEventTitle());
        document.addField(IndexInputDocument.FIELD_DESCRIPTION, addEventProperties.getEventDescription());
        document.addField(IndexInputDocument.FIELD_EXPIRY_DATE, addEventProperties.getEventExpiryDate());
        document.addField(IndexInputDocument.FIELD_MAP_LOCATION, getLatLngAsString((LatLng) addEventProperties.getEventLocation()));
        return document;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String getLatLngAsString(LatLng latLng) {
        String latLngString = null;
        if (latLng != null) {
            latLngString = latLng.latitude + ", " + latLng.longitude;
        }
        return latLngString;
    }
    @Override
    public void onFailure(IOException e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Something went wrong while adding your event. Please try again later")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSuccess(Response response) {

    }
}
