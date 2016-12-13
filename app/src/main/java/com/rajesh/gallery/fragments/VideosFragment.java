package com.rajesh.gallery.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajesh.gallery.R;
import com.rajesh.gallery.activities.GalleryActivity;
import com.rajesh.gallery.adapter.VideoAlbumAdapter;
import com.rajesh.gallery.model.GalleryPhotoAlbum;


import java.util.ArrayList;


public class VideosFragment extends Fragment  {

    private GalleryActivity parentActivity;
    private View view;

    private ArrayList<GalleryPhotoAlbum> arrayListAlbums;
    private VideoAlbumAdapter campaignAdapter;
    private RecyclerView rvCampaign;

    public VideosFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_videos, container, false);

        findViews();

        new Thread(new Runnable() {
            @Override
            public void run() {

                getVideoList();
            }
        }).start();

        return view;
    }

    private void findViews() {


        rvCampaign = (RecyclerView) view.findViewById(R.id.rv_videos);

        rvCampaign.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCampaign.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (GalleryActivity) getActivity();


    }

   /* @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

            if(parentActivity != null){
                if(view != null){

                    getVideoList();

                }
            }

        }
    }*/


    /**
     * video count find based on bucket name(Album name)
     *
     * @param bucketName
     * @return
     */
    private int videoCountByAlbum(String bucketName) {

        try {
            final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
            String searchParams = null;
            String bucket = bucketName;
            searchParams = "bucket_display_name = \"" + bucket + "\"";

            // final String[] columns = { MediaStore.Video.Media.DATA,
            // MediaStore.Video.Media._ID };
            Cursor mVideoCursor = parentActivity.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    searchParams, null, orderBy + " DESC");

            if (mVideoCursor.getCount() > 0) {

                return mVideoCursor.getCount();
            }
            mVideoCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * retrieve video album list and set
     */
    private void getVideoList() {

        // which image properties are we querying
        String[] PROJECTION_BUCKET = { MediaStore.Video.VideoColumns.BUCKET_ID,
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME, MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns.DATA };
        // We want to order the albums by reverse chronological order. We abuse
        // the
        // "WHERE" parameter to insert a "GROUP BY" clause into the SQL
        // statement.
        // The template for "WHERE" parameter is like:
        // SELECT ... FROM ... WHERE (%s)
        // and we make it look like:
        // SELECT ... FROM ... WHERE (1) GROUP BY 1,(2)
        // The "(1)" means true. The "1,(2)" means the first two columns
        // specified
        // after SELECT. Note that because there is a ")" in the template, we
        // use
        // "(2" to match it.
        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

        // Get the base URI for the People table in the Contacts content
        // provider.
        Uri images = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = parentActivity.getContentResolver().query(images, PROJECTION_BUCKET,
                BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        Log.v("ListingImages", " query count=" + cur.getCount());

        arrayListAlbums = new ArrayList<>();

        GalleryPhotoAlbum album;

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String data;
            long bucketId;

            int bucketColumn = cur
                    .getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur
                    .getColumnIndex(MediaStore.Video.Media.DATE_TAKEN);
            int dataColumn = cur.getColumnIndex(MediaStore.Video.Media.DATA);

            int bucketIdColumn = cur
                    .getColumnIndex(MediaStore.Video.Media.BUCKET_ID);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                data = cur.getString(dataColumn);
                bucketId = cur.getInt(bucketIdColumn);

                if (bucket != null && bucket.length() > 0) {
                    album = new GalleryPhotoAlbum();
                    album.setBucketId(bucketId);
                    album.setBucketName(bucket);
                    album.setDateTaken(date);
                    album.setData(data);
                    album.setTotalCount(videoCountByAlbum(bucket));
                    arrayListAlbums.add(album);
                    // Do something with the values.
                    Log.v("ListingImages", " bucket=" + bucket
                            + "  date_taken=" + date + "  _data=" + data
                            + " bucket_id=" + bucketId);
                }

            } while (cur.moveToNext());
        }
        cur.close();


        setData();

    }


    /**
     * album data set on list view
     */
    private void setData() {

        if (arrayListAlbums.size() > 0) {

            if (arrayListAlbums != null) {

                if (arrayListAlbums.size() > 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            campaignAdapter = new VideoAlbumAdapter(arrayListAlbums,parentActivity);
                            rvCampaign.setAdapter(campaignAdapter);
                        }
                    });


                }
            }
        }
    }

}
