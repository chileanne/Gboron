package com.gboron.ekpei.gboron;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gboron.ekpei.gboron.Adapter.Albumadpter;
import com.gboron.ekpei.gboron.Adapter.SongsAdapter;
import com.gboron.ekpei.gboron.Modelclass.Albumpojo;
import com.gboron.ekpei.gboron.Modelclass.Songpojo;

import java.util.ArrayList;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mrecycler;
   private ArrayList<Albumpojo> malbumpoj;
    private Albumadpter malbumadapter;


    String v;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mrecycler = (RecyclerView) view.findViewById(R.id.musicrecycler);
        malbumpoj = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecycler.getContext(),
                linearLayoutManager.getOrientation());
        mrecycler.setLayoutManager(linearLayoutManager);
        mrecycler.addItemDecoration(dividerItemDecoration);
        loadalbums();

        malbumadapter=new Albumadpter(malbumpoj,getActivity());
        mrecycler.setAdapter(malbumadapter);
        return view;
    }

    private void loadalbums (){
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
       // String selection = MediaStore.Audio.Albums.;
        Cursor cursor = getActivity().getContentResolver().query(uri,null,null,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                    String no_songs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                   // String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    int id= cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    Albumpojo s = new Albumpojo(name,artist,no_songs,id);
                   malbumpoj.add(s);






                }while (cursor.moveToNext());
            }

            cursor.close();


        }
    }



}


