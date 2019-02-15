package com.gboron.ekpei.gboron.Modelclass;

import android.net.Uri;

/**
 * Created by EKPEI on 8/22/2018.
 */

public class Songpojo {
    private String Songname;
    private String Artistname;
    private String SongUrl,title;
    private int id;

    public Songpojo(String songname, String artistname, String songUrl, String title, int id) {
        Songname = songname;
        Artistname = artistname;
        SongUrl = songUrl;
        this.title=title;
        this.id=id;
    }

    public String getSongname() {
        return Songname;
    }

    public String getArtistname() {
        return Artistname;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
