package com.rajesh.gallery.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rajesh.gallery.R;
import com.rajesh.gallery.activities.SelectedVideoActivity;
import com.rajesh.gallery.model.MediaObject;
import com.rajesh.gallery.utils.Utils;

import java.util.ArrayList;

public class VideoGridAdapter extends RecyclerView.Adapter<VideoGridAdapter.MyViewHolder> {

    private ArrayList<MediaObject> moviesList;
    private Context context;

    public VideoGridAdapter(ArrayList<MediaObject> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.context = ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_gallery_videos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MediaObject movie = moviesList.get(position);

        if (!TextUtils.isEmpty(movie.getPath())) {
            Utils.loadImageFromUrl(holder.imgThumb, context, movie.getPath());


            /*Bitmap bMap = ThumbnailUtils.createVideoThumbnail(movie.getPath(),
                    MediaStore.Video.Thumbnails.MINI_KIND);

            holder.imgThumb.setImageBitmap(bMap);*/


        }

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SelectedVideoActivity.class);
                i.putExtra("videoPath",movie.getPath());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumb;


        public MyViewHolder(View view) {
            super(view);

            imgThumb = (ImageView) view.findViewById(R.id.album_thumbnail);

        }
    }

}
