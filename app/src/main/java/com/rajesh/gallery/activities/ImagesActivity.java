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
import com.rajesh.gallery.adapter.ImageGridAdapter;
import com.rajesh.gallery.model.MediaObject;
import com.rajesh.gallery.utils.MediaType;
import com.rajesh.gallery.utils.Utils;


import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    private ArrayList<MediaObject> arrayListAlbums;
    private ImageGridAdapter campaignAdapter;
    private RecyclerView rvCampaign;
    private String bucketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_images);
        getIntentData(getIntent());

        InitializeViews();
    }

    private void getIntentData(Intent intent) {

        if(intent != null){
            bucketName = intent.getStringExtra("BucketName");
        }
    }


    private void InitializeViews() {

        rvCampaign = (RecyclerView) findViewById(R.id.rv_images);

        rvCampaign.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCampaign.setLayoutManager(gridLayoutManager);

        initPhotoImages(bucketName);

        campaignAdapter = new ImageGridAdapter(arrayListAlbums,this);
        rvCampaign.setAdapter(campaignAdapter);


    }

    /**
     * find image list for given bucket name
     * @param bucketName
     */
    private void initPhotoImages(String bucketName) {
        try {
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            String searchParams = null;
            String bucket = bucketName;
            searchParams = "bucket_display_name = \"" + bucket + "\"";

            // final String[] columns = { MediaStore.Images.Media.DATA,
            // MediaStore.Images.Media._ID };
            Cursor mPhotoCursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    searchParams, null, orderBy + " DESC");

            if (mPhotoCursor.getCount() > 0) {

                arrayListAlbums = new ArrayList<MediaObject>();

                arrayListAlbums.addAll(Utils.extractMediaList(mPhotoCursor,
                        MediaType.PHOTO));

            }

            // setAdapter(mImageCursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
