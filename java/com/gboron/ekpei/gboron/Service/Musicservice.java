package com.gboron.ekpei.gboron.Service;

import android.app.Notification;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gboron.ekpei.gboron.Modelclass.Songpojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by EKPEI on 8/22/2018.
 */

public class Musicservice extends Service implements MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaplayer;
    private Uri mSongUri;
    private ArrayList<Songpojo> mlistSongs;
    private int Song_Pos=0;
    private boolean shuffle=false;
    private boolean repeat=false;
    private Random ram;
    private final IBinder MusicBinder= new Playibinder();
    private final String ACTION_STOP="";
    private final String ACTION_NEXT="";
    private final String ACTION_PAUSE="";
    private final String ACTION_PREVIOUS="";


    private static final int STATE_PAUSED=1;
    private static final int STATE_PLAY=2;
    private int mstate=0;



    private static final int REQUEST_CODE_PAUSE=100;
    private static final int REQUEST_CODE_PREVIOUS=101;
    private static final int REQUEST_CODE_NEXT=103;
    private static final int REQUEST_CODE_STOP=104;


    private static final int NOTIFICATION_ID=9;
    private Notification.Builder mNotificationbuilder;
    private Notification mNotification;



    public class Playibinder extends Binder{
            //service connection to play in the background

            public Musicservice getService(){
                return Musicservice.this;
            }

    }


    @Nullable
    @Override
    //onbind called
    public IBinder onBind(Intent intent) {
        return MusicBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //initializing the objects
        mMediaplayer=new MediaPlayer();
        init();
        mMediaplayer.setOnCompletionListener(this);
        mMediaplayer.setOnErrorListener(this);
        mMediaplayer.setOnPreparedListener(this);
        mNotificationbuilder=new Notification.Builder(this);
        ram=new Random();
    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String action=intent.getAction();
            if(!TextUtils.isEmpty(action)){
                if(action.equals(ACTION_PAUSE)){
                  //  playpuasesong();
                }else if(action.equals(ACTION_NEXT)){
                   // nextsong();
                }else if(action.equals(ACTION_PREVIOUS)){
                   // previoussong();
                }else if(action.equals(ACTION_STOP)){
                   // stopsong();
                   // stopself();

                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }*/



    @Override
    public boolean onUnbind(Intent intent) {
        //stop the mediaplayer
        mMediaplayer.stop();
        mMediaplayer.release();
        return false;

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mMediaplayer.reset();
        Song_Pos++;
        nextsong();



       /* try {
            if (Song_Pos != mlistSongs.size()) {
                Song_Pos++;
            } else {
                Song_Pos=0;
                mMediaplayer.setDataSource(getApplicationContext(),mlistSongs.get(Song_Pos).getmSongUri());
            }
        }catch (Exception e){
        }*/

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
           mMediaplayer.start();


    }

    private void  init(){
        mMediaplayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }



 /*   public void otherplay(int k){
        mMediaplayer.reset();
       // int id=n.getId();
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id);
        //set the data source
        try{
            mMediaplayer.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        mMediaplayer.prepareAsync();

    }*/


    public void playsong(){
        mMediaplayer.reset();
        mstate=STATE_PLAY;
        Songpojo n=mlistSongs.get(Song_Pos);
        int id=n.getId();
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id);
        //set the data source
        try{
            mMediaplayer.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

     mMediaplayer.prepareAsync();


    }
    /*public void startSong(Uri Songuri, String SongName){
        //set data and start playing song
       mMediaplayer.reset();
        mstate=STATE_PLAY;
        mSongUri=Songuri;
      try {
          mMediaplayer.setDataSource(getApplicationContext(),mSongUri);
      } catch (IOException e) {
          e.printStackTrace();
      }
      mMediaplayer.prepareAsync();

    }*/

   /* public void playpuasesong() {
        if (mstate == STATE_PAUSED) {
            mstate = STATE_PLAY;
            mMediaplayer.start();

        } else {
            mstate=STATE_PAUSED;
            mMediaplayer.pause();
        }
    }*/

    public void playpausesongs(){
       // mstate = STATE_PLAY;
        mMediaplayer.start();
    }


    public void pauseplaysong(){
       // mstate=STATE_PAUSED;
        mMediaplayer.pause();
    }

    public void nextsong() {
       // startSong(mlistSongs.get(Song_Pos+1).getmSongUri(),mlistSongs.get(Song_Pos+1).getmSongname());
        if(shuffle){
            int xsongpos=Song_Pos;
            while (xsongpos==Song_Pos){
                xsongpos=ram.nextInt((mlistSongs.size() - 1) - 0 + 1) + 0;

            }
            Song_Pos=xsongpos;
        }else {
            Song_Pos++;
            if(Song_Pos>=mlistSongs.size()){
                Song_Pos=0;
            }

        }
        playsong();
    }
   public void previoussong() {
       // startSong(mlistSongs.get(Song_Pos-1).getmSongUri(),mlistSongs.get(Song_Pos-1).getmSongname());
       if(shuffle){
           int xsongpos=Song_Pos;
           while (xsongpos==Song_Pos){
               xsongpos=ram.nextInt(mlistSongs.size());
           }
           Song_Pos=xsongpos;
       }else {
           Song_Pos--;
           if(Song_Pos>=mlistSongs.size()){
               Song_Pos=0;
           }

       }
       playsong();
   }
    public boolean isPng(){
        return mMediaplayer.isPlaying();
    }


     public int musicduration(){
       return  mMediaplayer.getDuration();
     }
     public int muscicurrentposition(){return mMediaplayer.getCurrentPosition();}
    public void seek(int posn){mMediaplayer.seekTo(posn);}
    public void setsonglist(ArrayList<Songpojo> listsong){
        mlistSongs=listsong;
    }
   public void setsongpostion(int position){
       Song_Pos=position;
   }

   public void setShuffle(Context c){
       if(shuffle){
           shuffle=false;
           Toast.makeText(c,"shuffle off", Toast.LENGTH_LONG).show();
       }else{
           shuffle=true;
           Toast.makeText(c,"shuffle on", Toast.LENGTH_LONG).show();
       }
   }

   public void repeat(Context c){
       if(repeat){
           repeat=false;
           Toast.makeText(c,"repeat off", Toast.LENGTH_LONG).show();
       }else{
           repeat=true;
           Toast.makeText(c,"repeat on", Toast.LENGTH_LONG).show();
       }
   }

    /* private void stopsong() {
        mMediaplayer.stop();
    }





    private void stopself() {
    }*/
}

