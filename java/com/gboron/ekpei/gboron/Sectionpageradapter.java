package com.gboron.ekpei.gboron;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by EKPEI on 8/21/2018.
 */

class Sectionpageradapter  extends FragmentPagerAdapter {
    public Sectionpageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MusicFragment musicFragment = new MusicFragment();
                return musicFragment;

            case 1:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 2:
                GistFragment friendsFragment = new GistFragment();
                return friendsFragment;

            default:
                return null;
        }


    }
    @Override
    public int getCount () {
        return 3;
    }

    public  CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Music";

            case 1:
                return "Home";

            case 2:
                return "Gist";

            default:
                return null;
        }
    }
    //setting the text to the 3fragments

    // @Override
    // public CharSequence getPageTitle(int position) {
    //   return super.getPageTitle(position);
    // }

}
