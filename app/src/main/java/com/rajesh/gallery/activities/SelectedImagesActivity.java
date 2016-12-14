package com.rajesh.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rajesh.gallery.R;
import com.rajesh.gallery.adapter.ImageGalleryThumbAdapter;
import com.rajesh.gallery.adapter.ImagePreviewAdapter;
import com.rajesh.gallery.listener.RecyclerItemClickListener;
import com.rajesh.gallery.model.MediaObject;

import java.util.ArrayList;

public class SelectedImagesActivity extends AppCompatActivity {

    private ViewPager pagerImages;
    private ImagePreviewAdapter pagerAdapter;
    private ArrayList<MediaObject> listOfImages;
    private RecyclerView rvImages;
    private ImageGalleryThumbAdapter imageThumbAdapter;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        setContentView(R.layout.activity_selected_images);

        init();

    }

    private void getIntentData(Intent intent) {

        listOfImages = (ArrayList<MediaObject>) intent.getSerializableExtra("images");
        if (listOfImages.size() > 0)
            clearSelection();

    }

    private void clearSelection() {

        for (int i = 0; i < listOfImages.size(); i++) {

            if (i > 0) {

                listOfImages.get(i).setSelected(false);
            }

        }
    }

    private void init() {

        pagerImages = (ViewPager) findViewById(R.id.pager_images);
        rvImages = (RecyclerView) findViewById(R.id.rv_images);
        pagerAdapter = new ImagePreviewAdapter(SelectedImagesActivity.this, listOfImages);
        pagerImages.setAdapter(pagerAdapter);

        rvImages.setLayoutManager(new LinearLayoutManager(SelectedImagesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvImages.setHasFixedSize(true);

        imageThumbAdapter = new ImageGalleryThumbAdapter(listOfImages, SelectedImagesActivity.this);
        rvImages.setAdapter(imageThumbAdapter);

        rvImages.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                listOfImages.get(selectedPosition).setSelected(false);
                imageThumbAdapter.notifyItemChanged(selectedPosition);
                selectedPosition = position;
                listOfImages.get(selectedPosition).setSelected(true);
                imageThumbAdapter.notifyItemChanged(selectedPosition);
                //imageThumbAdapter.notifyDataSetChanged();
                pagerImages.setCurrentItem(selectedPosition, true);

            }
        }));

        pagerImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                listOfImages.get(selectedPosition).setSelected(false);
                //imageThumbAdapter.notifyItemChanged(selectedPosition);
                selectedPosition = position;
                listOfImages.get(selectedPosition).setSelected(true);
               // imageThumbAdapter.notifyItemChanged(selectedPosition);
                imageThumbAdapter.notifyDataSetChanged();
                rvImages.smoothScrollToPosition(position);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onBackPressed() {

        finish();
    }
}
