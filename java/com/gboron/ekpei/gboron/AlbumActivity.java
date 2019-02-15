package com.gboron.ekpei.gboron;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gboron.ekpei.gboron.Adapter.Albumadpter;
import com.gboron.ekpei.gboron.Adapter.albumdetails;
import com.gboron.ekpei.gboron.Modelclass.AlbumdetailsPojo;
import com.gboron.ekpei.gboron.Modelclass.Albumpojo;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
     private ArrayList<AlbumdetailsPojo> malbumdetails;
    private  AlbumdetailsPojo mdetailspojo;
    String Album_id;
    String displaname;
    private albumdetails madapter;

    private RecyclerView mrecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mrecycler = (RecyclerView) findViewById(R.id.albumrecy);
        malbumdetails = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecycler.getContext(),
                linearLayoutManager.getOrientation());
        mrecycler.setLayoutManager(linearLayoutManager);
        mrecycler.addItemDecoration(dividerItemDecoration);
        Intent i= getIntent();
        Album_id=i.getExtras().getString("AlBUMNAME");
        loader();
      madapter=new albumdetails( malbumdetails,getApplicationContext());
        mrecycler.setAdapter(madapter);

    }

    private void loader() {
        String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";

        String whereVal[] = { Album_id};

        String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, whereVal, orderBy);


        if(cursor != null){
            if(cursor.moveToFirst()){
                do{

                    displaname= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                  AlbumdetailsPojo d= new AlbumdetailsPojo(displaname);
                    malbumdetails.add(d);



                }while (cursor.moveToNext());
            }

            cursor.close();


        }

    }
}
