package com.rajesh.gallery.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rajesh.gallery.R;
import com.rajesh.gallery.model.MediaObject;
import com.rajesh.gallery.utils.Utils;

import java.util.ArrayList;

public class ImageGalleryThumbAdapter extends RecyclerView.Adapter<ImageGalleryThumbAdapter.MyViewHolder> {

    private ArrayList<MediaObject> imagesList;
    private Context context;

    public ImageGalleryThumbAdapter(ArrayList<MediaObject> moviesList, Context ctx) {
        this.imagesList = moviesList;
        this.context = ctx;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_gallery_thumb_images, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        final MediaObject movie = imagesList.get(position);

        if(!TextUtils.isEmpty(movie.getPath())){
            Utils.loadImageFromUrl(holder.imgThumb,context,movie.getPath());
        }

        if(movie.isSelected()){
            holder.imgThumb.setBackgroundResource(R.drawable.shape_border);
        }else {
            holder.imgThumb.setBackground(null);
        }


    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumb;
        FrameLayout viewSelected;

        public MyViewHolder(View view) {
            super(view);

            imgThumb = (ImageView) view.findViewById(R.id.album_thumbnail);
            viewSelected = (FrameLayout) view.findViewById(R.id.parent_frame);

        }
    }

}
