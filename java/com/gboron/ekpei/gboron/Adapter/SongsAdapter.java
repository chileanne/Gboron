package com.gboron.ekpei.gboron.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gboron.ekpei.gboron.Modelclass.Songpojo;
import com.gboron.ekpei.gboron.R;

import java.util.ArrayList;

import static com.gboron.ekpei.gboron.R.drawable.b;

/**
 * Created by EKPEI on 8/22/2018.
 */

public class SongsAdapter extends RecyclerView.Adapter  <SongsAdapter.MyHolder> {
    private ArrayList<Songpojo> mSongpojo;
    private Context mcontext;
    private OnItemClickListener mOnItemClickListener;

    public SongsAdapter(ArrayList<Songpojo> mSongpojo, FragmentActivity activity ) {
        this.mSongpojo=mSongpojo;
       this.mcontext=activity;


    }



    public interface OnItemClickListener{
        void onItemClick(LinearLayout l,View view,Songpojo ob, int position);
    }
      //method to be calld from frgment
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener=  mItemClickListener;
    }

    @Override
    public SongsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.musicsinglelayout,parent,false);
        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongsAdapter.MyHolder holder, final int position) {
       final Songpojo b=mSongpojo.get(position);
        holder.gh.setText(b.getArtistname());
        holder.music.setText(b.getSongname());
        holder.mlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(holder.mlinear,v,b,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongpojo.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView gh,music;
        LinearLayout mlinear;
        public MyHolder(View v) {
            super(v);
            gh=(TextView) v.findViewById(R.id.name);
            music=(TextView) v.findViewById(R.id.music);
            mlinear=(LinearLayout)v.findViewById(R.id.linearlayout);


        }
    }
}
