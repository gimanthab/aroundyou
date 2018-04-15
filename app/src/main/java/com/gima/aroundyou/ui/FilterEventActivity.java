package com.gima.aroundyou.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.gima.aroundyou.R;

public class FilterEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_event);
        TabHost filterTab = (TabHost) findViewById(R.id.tabFilter);
        filterTab.setup();

        String categories = getString(R.string.filter_categories);
        String search = getString(R.string.filter_search);
        TabHost.TabSpec categorySpec = filterTab.newTabSpec(categories);
        categorySpec.setContent(R.id.tabCategories);
        categorySpec.setIndicator(categories);
        filterTab.addTab(categorySpec);

        TabHost.TabSpec searchSpec = filterTab.newTabSpec(search);
        searchSpec.setContent(R.id.tabSearch);
        searchSpec.setIndicator(search);
        filterTab.addTab(searchSpec);

    }
}
