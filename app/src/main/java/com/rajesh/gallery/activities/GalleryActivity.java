package com.rajesh.gallery.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.rajesh.gallery.R;
import com.rajesh.gallery.adapter.GalleryTabsAdapter;
import com.rajesh.gallery.fragments.ImagesFragment;
import com.rajesh.gallery.fragments.VideosFragment;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    public ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);
        if (getSupportActionBar() != null)
            getSupportActionBar().setElevation(0);


        InitializeViews();
    }


    private void InitializeViews() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Method For Setup Swipable Viewpager
     * @param viewPager - instance of view pager
     */

    private void setupViewPager(ViewPager viewPager) {

        GalleryTabsAdapter adapter = new GalleryTabsAdapter(getSupportFragmentManager());

        ImagesFragment imagesFragment = new ImagesFragment();
        VideosFragment videosFragment = new VideosFragment();

        adapter.addFragment(imagesFragment, "Images");
        adapter.addFragment(videosFragment, "Videos");
        viewPager.setAdapter(adapter);

        fragmentList = new ArrayList<>();
        fragmentList.add(imagesFragment);
        fragmentList.add(videosFragment);

    }

}
