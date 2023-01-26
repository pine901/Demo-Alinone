package com.gamegards.allinonev3.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gamegards.allinonev3.Adapter.WelcomeRewardAdapter;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.model.WelcomeModel;

import java.util.ArrayList;

public class WelcomeBonusActivity extends AppCompatActivity {

    RecyclerView Reward_rec;
    WelcomeRewardAdapter welcomeRewardAdapter;

    ArrayList<WelcomeModel> welcomeList = new ArrayList<>();

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_bonus);

        Reward_rec = findViewById(R.id.Reward_rec);
//        Reward_rec.setLayoutManager(new GridLayoutManager(this,5));
//        welcomeRewardAdapter = new WelcomeRewardAdapter(this,welcomeList);
        Reward_rec.setAdapter(welcomeRewardAdapter);

        Reward_rec.setLayoutManager(new LinearLayoutManager(WelcomeBonusActivity.this,RecyclerView.HORIZONTAL,false){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
//                lp.height = getHeight() / 3;
                return true;
            }
        });


    }
}
