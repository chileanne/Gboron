package com.gboron.ekpei.gboron;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gboron.ekpei.gboron.Adapter.SongsAdapter;
import com.gboron.ekpei.gboron.Modelclass.Songpojo;
import com.gboron.ekpei.gboron.Service.Musicservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.widget.MediaController.MediaPlayerControl;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.gboron.ekpei.gboron.R.drawable.b;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment{
    private RecyclerView mrecycler;
    private SongsAdapter mSongsdapter;
    private ArrayList<Songpojo> mSongpojo;
    private Musicservice musicservice;
    private Intent playIntent;
    private Button play;
    private Button rep;
    private Button nexts;
    private Button prevs;
    private Button mshuffule;
    private SeekBar mseekbar;
    private Handler mhandler;
    private Utilities utils;
    private TextView songTotalDurationLabel,songCurrentDurationLabel,mDue;
    private boolean musicbond;
    private int mstate=0;
    private int STATEPAUSED=1;
    private int STATEPLYING=2;
    private Runnable mrunnable;
    public int mcurrentpositin;
    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_music, container, false);
        //seekbar
        mseekbar=(SeekBar) view.findViewById(R.id.mseek);


       // mseekbar.setOnSeekBarChangeListener(this);
        songCurrentDurationLabel=(TextView)  view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel =(TextView)  view.findViewById(R.id.songTotalDurationLabel);
        mDue= (TextView)  view.findViewById(R.id.due);
        rep=(Button) view.findViewById(R.id.rep);
        mshuffule=(Button) view.findViewById(R.id.shuff);
        nexts=(Button) view.findViewById(R.id.next);
       play=(Button) view.findViewById(R.id.play);
        prevs=(Button) view.findViewById(R.id.prev);
        mhandler=new Handler();


        //clicking to next nd previus
        nexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"nxt",Toast.LENGTH_LONG).show();
                musicservice.nextsong();
            }
        });

        prevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicservice.previoussong();
            }
        });

        //click to playsong
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mstate==STATEPAUSED) {
                    //check to see if its playing if yes change the button text to pause if no change
                    //d button txt to play
                    mstate=STATEPLYING;
                    play.setText("play");
                    musicservice.pauseplaysong();
                   // getAudiosttz();
                   seekmethod();
                }else{
                    mstate=STATEPAUSED;
                            play.setText("pause");
                    musicservice.playpausesongs();
                }
            }
        });

        //click to repeat songs
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"rep",Toast.LENGTH_LONG).show();

            }
        });


        //click to enabling shuffling
        mshuffule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicservice.setShuffle(getActivity());
            }
        });


        mrecycler=(RecyclerView) view.findViewById(R.id.musicrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecycler.getContext(),
                linearLayoutManager.getOrientation());
       mrecycler.setLayoutManager(linearLayoutManager);
        mrecycler.addItemDecoration(dividerItemDecoration);

        mSongpojo=new ArrayList<>();
        //loding all song
        loadSongs();
        Collections.sort(mSongpojo, new Comparator<Songpojo>(){
            public int compare(Songpojo a, Songpojo b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });


        mSongsdapter=new SongsAdapter(mSongpojo,getActivity());
        //click on a song and play
        mrecycler.setAdapter(mSongsdapter);
        //onecick listner set to a music
        mSongsdapter.setOnItemClickListener(new SongsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LinearLayout l, View view, Songpojo ob, int position) {

                mstate=STATEPAUSED;
                seekmethod();
                play.setText("pause");
                seekmethod();

               // getAudiosttz();
                Toast.makeText(getActivity(),ob.getSongUrl(),Toast.LENGTH_LONG).show();
                musicservice.setsongpostion(position);
                musicservice.playsong();
          // getAudiosttz();

             //   updateProgressBar();




            }
        });


        return view;
    }



    private void getAudiosttz() {
        int duration=musicservice.musicduration()/1000;
        int due=(musicservice.musicduration() - musicservice.muscicurrentposition())/1000;
        int pass=duration-due;
        songCurrentDurationLabel.setText(pass + "seconds");
        songTotalDurationLabel.setText(duration+"seconds");
        mDue.setText(due  +"seconds");


    }

    private void seekmethod() {
        int m=musicservice.musicduration()/1000;
        mseekbar.setMax(m);
         mrunnable=new Runnable() {
            @Override
            public void run() {
               // if(  musicservice.isPng()){
                   int c=musicservice.muscicurrentposition()/1000;
                   mcurrentpositin=c;
                    mseekbar.setProgress(mcurrentpositin);
                  //  getAudiosttz();
              //  }
                mhandler.postDelayed(mrunnable,50);
            }
        };
        mhandler.postDelayed(mrunnable,50);

    }


    //obtaining all songs from external and internal storge
    private void loadSongs(){

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor = getActivity().getContentResolver().query(uri,null,selection,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{

                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                int id= cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    Songpojo s = new Songpojo(name,artist,url,title,id);
                    mSongpojo.add(s);

                }while (cursor.moveToNext());
            }

            cursor.close();


        }
    }

  private ServiceConnection mserviceconnectin =new ServiceConnection() {
       @Override
       public void onServiceConnected(ComponentName name, IBinder service) {
           Musicservice.Playibinder binder=(Musicservice.Playibinder) service;
           //get service
           musicservice=binder.getService();
           musicservice.setsonglist(mSongpojo);
           musicbond=true;

       }

       @Override
       public void onServiceDisconnected(ComponentName name) {
            musicbond=false;
       }
   };


    //start and bind the service when the activity starts
    @Override
    public void onStart() {
        super.onStart();

        if(playIntent==null){
            playIntent = new Intent(getActivity(), Musicservice.class);
          getActivity().bindService(playIntent,mserviceconnectin, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);
        }
    }

    /* public void updateProgressBar() {
       mhandler.postDelayed(mUpdateTimeTask, 100);
    }


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
          // int totalDuration = musicservice.musicduration();
            //int currentDuration = musicservice.muscicurrentposition();

            // Displaying Total Duration time
         //   songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
           // songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            //int progress = utils.getProgressPercentage(currentDuration, totalDuration);
            //Log.d("Progress", ""+progress);

            if (musicservice.isPng()) {
                int u = musicservice.musicduration();
                mseekbar.setProgress(u);
            }
            // Running this thread after 100 milliseconds
            mhandler.postDelayed(this, 100);
        }
    };*/




}



