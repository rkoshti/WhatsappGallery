package com.rajesh.gallery.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rajesh.gallery.R;
import com.rajesh.gallery.adapter.VideoGridAdapter;
import com.rajesh.gallery.model.MediaObject;
import com.rajesh.gallery.utils.MediaType;
import com.rajesh.gallery.utils.Utils;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {

    private ArrayList<MediaObject> arrayListAlbums;
    private VideoGridAdapter campaignAdapter;
    private RecyclerView rvCampaign;
    private String bucketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_videos);

        getIntentData(getIntent());

        InitializeViews();
    }

    private void getIntentData(Intent intent) {

        if(intent != null){
            bucketName = intent.getStringExtra("BucketName");
        }
    }


    private void InitializeViews() {

        rvCampaign = (RecyclerView) findViewById(R.id.rv_videos);

        rvCampaign.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCampaign.setLayoutManager(gridLayoutManager);

        initVideoImages(bucketName);

        campaignAdapter = new VideoGridAdapter(arrayListAlbums,this);
        rvCampaign.setAdapter(campaignAdapter);


    }

    /**
     * find video list for given bucket name
     *
     * @param bucketName
     */
    private void initVideoImages(String bucketName) {
        try {
            final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
            String searchParams = null;
            String bucket = bucketName;
            searchParams = "bucket_display_name = \"" + bucket + "\"";


            Cursor mVideoCursor = getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    searchParams, null, orderBy + " DESC");

            if (mVideoCursor.getCount() > 0) {


                arrayListAlbums = new ArrayList<MediaObject>();


                arrayListAlbums.addAll(Utils.extractMediaList(mVideoCursor,
                        MediaType.VIDEO));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
