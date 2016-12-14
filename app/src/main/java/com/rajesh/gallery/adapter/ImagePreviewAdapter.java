package com.rajesh.gallery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rajesh.gallery.R;
import com.rajesh.gallery.model.MediaObject;
import com.rajesh.gallery.utils.Utils;

import java.util.ArrayList;


public class ImagePreviewAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<MediaObject> itemList;

    public ImagePreviewAdapter(Context context, ArrayList<MediaObject> listOfImages) {

        mContext = context;
        itemList = listOfImages;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.row_preview_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Utils.loadImageFromUrl(imageView, mContext, itemList.get(position).getPath());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}