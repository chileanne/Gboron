package com.gboron.ekpei.gboron.Modelclass;

import static com.gboron.ekpei.gboron.R.id.album;
import static com.gboron.ekpei.gboron.R.id.artist;

/**
 * Created by EKPEI on 10/1/2018.
 */

public class Albumpojo {
   // String artist;
    String name;
    String no_songs;
    String artist;
    int id;


    public Albumpojo(String name, String artist, String no_songs, int id) {
       // this.artist = artist;
        this.name = name;
       this.no_songs =no_songs;
        this.artist=artist;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getNo_songs() {
        return no_songs;
    }

    public String getArtist() {
        return artist;
    }

    public int getId() {
        return id;
    }
}
