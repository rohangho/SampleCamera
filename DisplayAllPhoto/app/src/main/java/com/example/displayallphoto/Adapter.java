package com.example.displayallphoto;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Uri> paster;
    Context context;

    public Adapter(ArrayList<Uri> paster, Context context) {
        this.paster = paster;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.displayeradapter, viewGroup, false);
        return new Adapter.ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(paster.get(i)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.display);
    }

    @Override
    public int getItemCount() {
        return paster.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView display;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            display=(ImageView)itemView.findViewById(R.id.imgDisplayer);
        }
    }
}
