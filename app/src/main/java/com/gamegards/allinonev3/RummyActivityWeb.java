package com.gamegards.allinonev3;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class RummyActivityWeb extends AppCompatActivity {


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


        setContentView(R.layout.webview);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initWebView();
    }


    private void initWebView() {
        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new MyWebViewClient());
        String url = "https://androappstech.com/teenpattiweb/";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setAllowFileAccessFromFileURLs(true);
//        settings.setAllowUniversalAccessFromFileURLs(true);

        // Load in the game's HTML file
       // webView.loadData("https://androappstech.com/teenpattiweb", "text/html", "UTF-8");

        // webView.loadUrl("https://androappstech.com/teenpattiweb");
    }

    private int getScale(int desiredWidth, int desiredHeight) {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Double heightRatio = new Double(height) / new Double(desiredHeight);
        Double widthRatio = new Double(width) / new Double(desiredWidth);
        Double chosen = Math.min(heightRatio, widthRatio);
        return (int) (chosen * 100D);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            return true;
        }
    }
}
