package com.gamegards.allinonev3.Comman;

import androidx.fragment.app.Fragment;

import com.gamegards.allinonev3.Activity.Homepage;


public abstract class BaseFragment extends Fragment {
    public Homepage activity;

    protected void setActivity(Homepage activity) {
        this.activity = activity;
    }

    public abstract void onBack();
}


