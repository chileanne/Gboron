package com.gboron.ekpei.gboron.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gboron.ekpei.gboron.Modelclass.AlbumdetailsPojo;
import com.gboron.ekpei.gboron.R;

import java.util.ArrayList;

/**
 * Created by EKPEI on 10/8/2018.
 */

public class albumdetails extends RecyclerView.Adapter  < albumdetails.MyHolder>{
    private ArrayList<AlbumdetailsPojo> mpojo;
    private  Context mcontext;

    public albumdetails( ArrayList<AlbumdetailsPojo> malbumdetails, Context applicationContext) {
        this.mpojo= malbumdetails;
        this.mcontext=applicationContext;

    }

    @Override
    public albumdetails.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.albumdetailsing,parent,false);
        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(albumdetails.MyHolder holder, int position) {
        AlbumdetailsPojo d=mpojo.get(position);
        holder.name.setText(d.getDetailsname());


    }

    @Override
    public int getItemCount() {
        return mpojo.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        public MyHolder(View v) {
            super(v);
            name=(TextView) v.findViewById(R.id.detailsnme);
        }
    }
}
