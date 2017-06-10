package com.gima.aroundyou.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gima.aroundyou.R;

import me.gujun.android.taggroup.TagGroup;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Button btnNext = (Button) findViewById(R.id.btnEventCategoryNext);
        Button btnBack = (Button) findViewById(R.id.btnEventCategoryBack);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEventCategoryNext) {
            Intent intent = new Intent(AddCategoryActivity.this, AddDescriptionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnEventCategoryBack) {
            Intent intent = new Intent(AddCategoryActivity.this, AddMarkerActivity.class);
            startActivity(intent);
        }
    }
}
