package com.gboron.ekpei.gboron;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;

import com.gboron.ekpei.gboron.Service.Musicservice;

public class MainActivity extends AppCompatActivity  {
    private ViewPager mviewpager;
    private Sectionpageradapter msectionpageradapter;
    private TabLayout mtablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewpager
        mviewpager = (ViewPager) findViewById(R.id.viewpager);

        //creating the sectionpageradapter that links the 3 views
        msectionpageradapter=new Sectionpageradapter(getSupportFragmentManager());
        mviewpager.setAdapter(msectionpageradapter);

        //setting the tab layout
        mtablayout=(TabLayout) findViewById(R.id.main_tab);
        mtablayout.setupWithViewPager(mviewpager);


    }

}
