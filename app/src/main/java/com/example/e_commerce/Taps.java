package com.example.e_commerce;

import androidx.fragment.app.Fragment;

public class Taps {
    String tap_Name;
    Fragment tap_Fragment;

    public Taps(String tap_Name, Fragment tap_Fragment) {
        this.tap_Name = tap_Name;
        this.tap_Fragment = tap_Fragment;
    }

    public String getTap_Name() {
        return tap_Name;
    }

    public Fragment getTap_Fragment() {
        return tap_Fragment;
    }

    public void setTap_Name(String tap_Name) {
        this.tap_Name = tap_Name;
    }

    public void setTap_Fragment(Fragment tap_Fragment) {
        this.tap_Fragment = tap_Fragment;
    }
}

