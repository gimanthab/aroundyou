
package com.gima.aroundyou.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gima.aroundyou.R;
import com.gima.aroundyou.properties.AddEventProperties;

public class AddDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtAddTitle;
    private EditText txtAddDesc;
    private AddEventProperties addEventProperties;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        Button btnNext = (Button)findViewById(R.id.btnEventDescNext);
        Button btnBack = (Button)findViewById(R.id.btnEventDescBack);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txtAddDesc = (EditText)findViewById(R.id.txtEventDesc);
        txtAddTitle = (EditText)findViewById(R.id.txtEventTitle);
        addEventProperties = AddEventProperties.getInstance();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEventDescNext) {
            String eventTitle = txtAddTitle.getText().toString().trim();
            String eventDesc = txtAddDesc.getText().toString().trim();
            if (eventTitle.isEmpty()) {
                txtAddTitle.setError("Event Title cannot be empty!");
            } else if (eventDesc.isEmpty()) {
                txtAddDesc.setError("Event Description cannot be empty!");
            } else {
                addEventProperties.setEventTitle(eventTitle);
                addEventProperties.setEventDescription(eventDesc);
                Intent intent = new Intent(AddDescriptionActivity.this, AddExpiryDateActivity.class);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnEventDescBack) {
            addEventProperties.setEventDescription(null);
            addEventProperties.setEventTitle(null);
            finish();
        }
    }
}
