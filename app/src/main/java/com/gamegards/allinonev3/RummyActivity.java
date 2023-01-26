package com.gamegards.allinonev3;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class RummyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int value = display.getWidth();
        int densityDpi = (int) (metrics.density * 160f);
         DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double wi = (double) width / (double) dm.xdpi;
        double hi = (double) height / (double) dm.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);


            setContentView(R.layout.testgragdrop);



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
           }

}
