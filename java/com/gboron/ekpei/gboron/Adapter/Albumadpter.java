package com.gboron.ekpei.gboron.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gboron.ekpei.gboron.AlbumActivity;
import com.gboron.ekpei.gboron.Modelclass.Albumpojo;
import com.gboron.ekpei.gboron.Modelclass.Songpojo;
import com.gboron.ekpei.gboron.R;

import java.util.ArrayList;

import static com.gboron.ekpei.gboron.R.drawable.b;

/**
 * Created by EKPEI on 10/1/2018.
 */

public class Albumadpter  extends RecyclerView.Adapter  < Albumadpter.MyHolder>  {
    private ArrayList<Albumpojo> malbumpoj;
    private Context mcontext;

    public Albumadpter(ArrayList<Albumpojo> malbumpoj, FragmentActivity activity) {
        this.malbumpoj= malbumpoj;
        this.mcontext=activity;
    }

    @Override
    public Albumadpter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.albumsingle,parent,false);
        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Albumadpter.MyHolder holder, final int position) {
        Albumpojo b=malbumpoj.get(position);
        holder.gh.setText(b.getName());
        holder.music.setText(b.getArtist());
        holder.name.setText(b.getNo_songs());
        holder.jk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "clikcked",Toast.LENGTH_LONG ).show();
                Albumpojo d=malbumpoj.get(position);
                Intent skipintent = new Intent(v.getContext(), AlbumActivity.class);
                skipintent.putExtra("AlBUMNAME", d.getName());
                v.getContext().startActivity(skipintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return malbumpoj.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView gh,music,name;
        LinearLayout jk;

        public MyHolder(View v) {
            super(v);
            jk=(LinearLayout) v.findViewById(R.id.ll);
            gh=(TextView) v.findViewById(R.id.album);
          music=(TextView) v.findViewById(R.id.artist);
           name=(TextView) v.findViewById(R.id.no_songs);
        }
    }
}
