package com.gima.aroundyou.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gima.aroundyou.R;
import com.gima.aroundyou.properties.AddEventProperties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.lujun.androidtagview.TagContainerLayout;


public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private AddEventProperties addEventProperties;
    TagContainerLayout mTagContainerLayout;
    EditText txtAddTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        addEventProperties = AddEventProperties.getInstance();
        Button btnNext = (Button) findViewById(R.id.btnEventCategoryNext);
        Button btnBack = (Button) findViewById(R.id.btnEventCategoryBack);
        Button btnAddTag = (Button) findViewById(R.id.btnCategoryAddTag);
        Spinner categorySpinner = (Spinner) findViewById(R.id.spinnerCategory);
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tag_panel);
        txtAddTag = (EditText) findViewById(R.id.txtAddTag);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnAddTag.setOnClickListener(this);
        categorySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEventCategoryNext) {
            addEventProperties.setEventCategories(mTagContainerLayout.getTags()
                    .toArray(new String[mTagContainerLayout.getTags().size()]));
            Intent intent = new Intent(AddCategoryActivity.this, AddDescriptionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnEventCategoryBack) {
            addEventProperties.setEventCategories(null);
            finish();
        } else if (v.getId() == R.id.btnCategoryAddTag) {
            String tag = txtAddTag.getText().toString().toLowerCase();
            if (!tag.isEmpty()) {
                List<String> currentTags = mTagContainerLayout.getTags();
                mTagContainerLayout.removeAllTags();
                Set<String> uniqueTags = new HashSet<>(currentTags);
                uniqueTags.add(tag);
                mTagContainerLayout.setTags(uniqueTags.toArray(new String[uniqueTags.size()]));
                txtAddTag.setText("");
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = parent.getItemAtPosition(position).toString().toLowerCase();
        mTagContainerLayout.setTags(category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }
}
