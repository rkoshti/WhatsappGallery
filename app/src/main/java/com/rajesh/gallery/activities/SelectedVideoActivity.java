package com.rajesh.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rajesh.gallery.R;

public class SelectedVideoActivity extends AppCompatActivity {

    private String videoURI;
    private String TAG = "SelectedVideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        setContentView(R.layout.activity_selected_video);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void getIntentData(Intent intent) {

        videoURI = intent.getStringExtra("videoPath");
        Log.e(TAG, "getIntentData: "  + videoURI );
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
