package com.example.newtabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;


      public PagerAdapter( FragmentManager fm, int NumberOfTabs ){

        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem( int position ) {
        switch (position){

            case 0:
                Fragment1 fragment1 = new Fragment1();
                return fragment1;
            case 1:
                Fragment2 fragment2 = new Fragment2();
                return fragment2;
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;
            case 3:
                Fragment4 fragment4 = new Fragment4();
                return fragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
