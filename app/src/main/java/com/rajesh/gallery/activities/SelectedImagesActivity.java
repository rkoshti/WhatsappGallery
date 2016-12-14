package com.rajesh.gallery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rajesh.gallery.R;

public class SelectedImagesActivity extends AppCompatActivity {

    private String videoURI;
    private VideoView videoView;
    private ProgressDialog pDialog;
    private MediaController mediacontroller;
    private String TAG = "SelectedVideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        setContentView(R.layout.activity_selected_video);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        videoView = (VideoView) findViewById(R.id.VideoView);

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
        Log.e(TAG, "getIntentData: " + videoURI);
    }


    @Override
    protected void onResume() {
        super.onResume();

        prepareMediaPlayerForPlayVideo();
    }

    private void prepareMediaPlayerForPlayVideo() {


        // Create a progressbar
        pDialog = new ProgressDialog(SelectedImagesActivity.this);
        // Set progressbar title
        pDialog.setTitle("Please Wait...");
        // Set progressbar message
        pDialog.setMessage("Buffering video...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            mediacontroller = new MediaController(
                    SelectedImagesActivity.this);
            mediacontroller.setAnchorView(videoView);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoURI);
            videoView.setMediaController(mediacontroller);
            videoView.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoView.start();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    @Override
    public void onBackPressed() {

        if (videoView != null) {

            if (videoView.isPlaying())
                videoView.stopPlayback();
        }

        finish();
    }
}
