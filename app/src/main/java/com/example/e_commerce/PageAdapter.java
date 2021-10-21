package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentStatePagerAdapter {

    ArrayList<Taps> taps = new ArrayList<Taps>();

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void addTap(Taps tap){
        taps.add(tap);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return taps.get(position).getTap_Fragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return taps.get(position).getTap_Name();
    }

    @Override
    public int getCount() {
        return taps.size();
    }
}
