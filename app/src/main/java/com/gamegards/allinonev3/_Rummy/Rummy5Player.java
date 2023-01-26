package com.gamegards.allinonev3._Rummy;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gamegards.allinonev3.Bottom_listDialog;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.gamegards.allinonev3.Interface.ApiRequest;
import com.gamegards.allinonev3.Interface.Callback;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.Utils.Animations;
import com.gamegards.allinonev3.Utils.Funtions;
import com.gamegards.allinonev3.Utils.MyDragShadowBuilder;
import com.gamegards.allinonev3.Utils.SoundPool;
import com.gamegards.allinonev3.model.CardModel;
import com.gamegards.allinonev3.model.Usermodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.view.View.DRAG_FLAG_OPAQUE;
import static com.gamegards.allinonev3.Fragments.ActiveTables_BF.SEL_TABLE;
import static com.gamegards.allinonev3.Utils.Funtions.SetBackgroundImageAsDisplaySize;
import static com.gamegards.allinonev3._TeenPatti.PublicTable.MY_PREFS_NAME;
import static com.gamegards.allinonev3.Utils.Funtions.ANIMATION_SPEED;
import static com.gamegards.allinonev3.Utils.Funtions.convertDpToPixel;

public class Rummy5Player extends AppCompatActivity implements Animation.AnimationListener {

    String INVALID = "Invalid";

    String IMPURE_SEQUENCE = "Impure sequence";
    String PURE_SEQUENCE = "Pure Sequence";
    String SET = "set";

    String DECLARE_BACK = "";
    String JOKER_CARD = "JK";
    String MAIN_JOKER_CARD = "JKR1";
    String MAIN_JOKER_CARD_2 = "JKR2";
    String DUMMY_CARD = "backside_card";

    int IMPURE_SEQUENCE_VALUE = 4;
    int PURE_SEQUENCE_VALUE = 5;
    int SET_VALUE = 6;


    LinearLayout rlt_addcardview;

    FrameLayout fl13;
    Animation animFadein,animMove;
    int height,width;
    ArrayList<CardModel> cardModelArrayList = new ArrayList<>();
    ArrayList<Usermodel> userModelArrayList = new ArrayList<>();
    //    ArrayList<CardModel> cardImageList = new ArrayList<>();

    Animation animMoveCardsPlayerwinner1, animMoveCardsPlayerwinner2
            ,animMoveCardsPlayerwinner3,animMoveCardsPlayerwinner4,animMoveCardsPlayerwinner5;

    RelativeLayout rltwinnersymble1,rltwinnersymble2,rltwinnersymble3,rltwinnersymble4,rltwinnersymble5
            ,rltwinnersymble6,rltwinnersymble7;

    ImageView imgpl1glow,imgpl2glow,imgpl3glow,imgpl4glow,imgpl5glow,imgpl6glow,imgpl7glow;

    ImageView imgpl1circle,imgpl2circle,imgpl3circle,imgpl4circle,imgpl5circle,imgpl6circle,imgpl7circle;

    TextView txtPlay1wallet,txtPlay2wallet,txtPlay3wallet,txtPlay4wallet,txtPlay5wallet,txtPlay6wallet,txtPlay7wallet;

    View /*lnrPlay1wallet,*/lnrPlay2wallet,lnrPlay3wallet,lnrPlay4wallet,lnrPlay5wallet,lnrPlay6wallet,lnrPlay7wallet;


    TextView txtPlay2,txtPlay3,txtPlay4,txtPlay5,txtPlay6,txtPlay7;

    int pStatus = 100;
    int pStatusprogress = 0;

    boolean isProgressrun1 = true;
    boolean isProgressrun2 = true;
    boolean isProgressrun3 = true;
    boolean isProgressrun4 = true;
    boolean isProgressrun5 = true;
    boolean isProgressrun6 = true;
    boolean isProgressrun7 = true;

    ProgressBar mProgress1, mProgress2, mProgress3, mProgress4, mProgress5,mProgress6,mProgress7;
    CountDownTimer counttimerforstartgame;
    CountDownTimer mCountDownTimer1,mCountDownTimer2,mCountDownTimer3,mCountDownTimer4,mCountDownTimer5
            ,mCountDownTimer6,mCountDownTimer7;


    Activity context ;
    String user_id_player1 = "";
    String user_id_player2 = "";
    String user_id_player3 = "";
    String user_id_player4 = "";
    String user_id_player5 = "";
    String user_id_player6 = "";
    String user_id_player7 = "";


    ArrayList<CardModel> rs_cardlist_group1 ;
    ArrayList<CardModel> rp_cardlist_group2 ;
    ArrayList<CardModel> bl_cardlist_group3 ;
    ArrayList<CardModel> bp_cardlist_group4 ;
    ArrayList<CardModel> joker_cardlist_group5 ;

    ArrayList<CardModel> ext_group1 ;
    ArrayList<CardModel> ext_group2 ;
    ArrayList<CardModel> ext_group3 ;
    ArrayList<CardModel> ext_group4 ;
    ArrayList<CardModel> ext_group5 ;

    ArrayList<CardModel> selectedcardvalue ;
    ArrayList<ArrayList<CardModel>> grouplist ;
    ImageView /*ivallcard,*/ivpickcard,iv_jokercard,ivFinishDesk;


    RelativeLayout ivallcard;
    float centreX , centreY;

    int timmersectlarge = 60000;
    int timmersectsmall = 1000;

    Button bt_creategroup,bt_sliptcard,bt_discard,bt_startgame,bt_drop,bt_declare,bt_finish;
    boolean isSplit = false;
    String selectedpatti = "";
    String selectedpatti_id = "";

    String joker_card = "" ;
    SharedPreferences cardPref;
    String Pref_cards = "cards_";
    TextView tv_gameid,tv_tableid;
    int min_entry = 0;

    ImageView ivDropCreate;
    boolean isCardPick = false;
    boolean isPlayer2 = false;

    int loos_point = 20;
    boolean isFirstChall = false;
    Switch touchmode;

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().hasExtra("player2"))
            isPlayer2 = true;

        context = this;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        DECLARE_BACK = getString(R.string.declare_back);

        Initialization();


        InitCoutDown();


        UserProgressCount();


        findViewById(R.id.iv_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottom_listDialog != null)
                    bottom_listDialog.show(getSupportFragmentManager(), bottom_listDialog.getTag());

            }
        });

//        findViewById(R.id.tvPoints).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(gameUsersPoint_bt != null)
//                {
//                    if(pointUserlist != null && pointUserlist.size() > 0)
//                    {
//                        if(!gameUsersPoint_bt.isAdded())
//                            gameUsersPoint_bt.show(getSupportFragmentManager(), gameUsersPoint_bt.getTag());
//                    }
//                    else {
//                        Toast.makeText(context, "Please wait we are getting records.", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//
//            }
//        });
    }

    RelativeLayout rl;
    private void Initialization() {



        if(getIntent().hasExtra("min_entry"))
            min_entry = getIntent().getIntExtra("min_entry",0);

        RelativeLayout rltParentLayout = findViewById(R.id.rltParentLayout);
        ImageView imgTable = findViewById(R.id.imgTable);

        SetBackgroundImageAsDisplaySize(this,rltParentLayout,R.drawable.bgnew1);
        LoadImage().load(R.drawable.table_v5).into(imgTable);

        touchmode = findViewById(R.id.touchmode);
        touchmode.setVisibility(View.GONE);
        tv_gameid = findViewById(R.id.tv_gameid);
        tv_tableid = findViewById(R.id.tv_tableid);

        cardPref = getSharedPreferences("cardPref",MODE_PRIVATE);

        if (cardPref.getLong("ExpiredDate", -1) > System.currentTimeMillis()) {
            // read email and password
        } else {
            SharedPreferences.Editor editor = cardPref.edit();
            editor.clear();
            editor.apply();
        }

        Funtions.LOGE("Rummy5Player","ExpiredDate : "+cardPref.getLong("ExpiredDate", -1) +
                "\ncurrentTimeMillis : "+System.currentTimeMillis());

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String textToCache = "Some text";
//            boolean success = GetCacheDirExample.writeAllCachedText(this, "myCacheFile.txt", textToCache);
//            String readText = GetCacheDirExample.readAllCachedText(this, "myCacheFile.txt");

//            for (File filepath:
//                    GetCacheDirExample.getCashFiles(context)) {
//
//                Funtions.LOGE("getCashFiles",""+filepath.toPath());
//            }

//            Funtions.LOGE("myCacheFile",""+readText);

        findViewById(R.id.rlt_highlighted_pick).setVisibility(View.INVISIBLE);
        findViewById(R.id.rlt_highlighted_gadhi).setVisibility(View.INVISIBLE);

        if(isPlayer2)
        {
            findViewById(R.id.lnr_new2player).setVisibility(View.VISIBLE);

            findViewById(R.id.rtlAmpire).setVisibility(View.GONE);
            findViewById(R.id.rltplayer2).setVisibility(View.GONE);
            findViewById(R.id.rltplayer3).setVisibility(View.GONE);
            findViewById(R.id.rltplayer4).setVisibility(View.GONE);
            findViewById(R.id.rltplayer5).setVisibility(View.GONE);
            findViewById(R.id.rltplayer6).setVisibility(View.GONE);
            findViewById(R.id.rltplayer7).setVisibility(View.GONE);

        }
        else {
            findViewById(R.id.lnr_new2player).setVisibility(View.GONE);

            findViewById(R.id.rtlAmpire).setVisibility(View.VISIBLE);
            findViewById(R.id.rltplayer2).setVisibility(View.VISIBLE);
            findViewById(R.id.rltplayer3).setVisibility(View.VISIBLE);
            findViewById(R.id.rltplayer4).setVisibility(View.VISIBLE);
            findViewById(R.id.rltplayer5).setVisibility(View.VISIBLE);
//            findViewById(R.id.rltplayer6).setVisibility(View.VISIBLE);
//            findViewById(R.id.rltplayer7).setVisibility(View.VISIBLE);
        }

        ivDropCreate =  findViewById(R.id.ivDropCreate);
        ivDropCreate.setVisibility(View.GONE);

        findViewById(R.id.iv_rightarrow).setVisibility(View.GONE);
        findViewById(R.id.iv_leftarrow).setVisibility(View.GONE);


        mProgress1 = (ProgressBar) findViewById(R.id.circularProgressbar1);
        if(isPlayer2)
            mProgress2 = (ProgressBar) findViewById(R.id.circularProgressbar2_new);
        else
            mProgress2 = (ProgressBar) findViewById(R.id.circularProgressbar2);

        mProgress3 = (ProgressBar) findViewById(R.id.circularProgressbar3);
        mProgress4 = (ProgressBar) findViewById(R.id.circularProgressbar4);
        mProgress5 = (ProgressBar) findViewById(R.id.circularProgressbar5);
        mProgress6 = (ProgressBar) findViewById(R.id.circularProgressbar6);
        mProgress7 = (ProgressBar) findViewById(R.id.circularProgressbar7);

        initializeProgress(mProgress1);
        initializeProgress(mProgress2);
        initializeProgress(mProgress3);
        initializeProgress(mProgress4);
        initializeProgress(mProgress5);
        initializeProgress(mProgress6);
        initializeProgress(mProgress7);

        rs_cardlist_group1 = new ArrayList<>();
        rp_cardlist_group2 = new ArrayList<>();
        bl_cardlist_group3 = new ArrayList<>();
        bp_cardlist_group4 = new ArrayList<>();
        joker_cardlist_group5 = new ArrayList<>();
        selectedcardvalue = new ArrayList<>();

        rl = ((RelativeLayout)findViewById(R.id.sticker_animation_layout));


        ext_group1 = new ArrayList<>();
        ext_group2 = new ArrayList<>();
        ext_group3 = new ArrayList<>();
        ext_group4 = new ArrayList<>();
        ext_group5 = new ArrayList<>();


        grouplist = new ArrayList<>();


        rlt_addcardview=findViewById(R.id.rlt_addcardview);

        rltwinnersymble1=findViewById(R.id.rltwinnersymble1);

        if(isPlayer2)
        {
            rltwinnersymble2=findViewById(R.id.rltwinnersymble2_new);
            findViewById(R.id.rltwinnersymble2).setVisibility(View.GONE);
        }
        else
            rltwinnersymble2=findViewById(R.id.rltwinnersymble2);

        rltwinnersymble3=findViewById(R.id.rltwinnersymble3);
        rltwinnersymble4=findViewById(R.id.rltwinnersymble4);
        rltwinnersymble5=findViewById(R.id.rltwinnersymble5);
        rltwinnersymble6=findViewById(R.id.rltwinnersymble6);
        rltwinnersymble7=findViewById(R.id.rltwinnersymble7);

        rltwinnersymble1.setVisibility(View.GONE);
        rltwinnersymble2.setVisibility(View.GONE);
        rltwinnersymble3.setVisibility(View.GONE);
        rltwinnersymble4.setVisibility(View.GONE);
        rltwinnersymble5.setVisibility(View.GONE);

        ScaleAnimation scaler = new ScaleAnimation((float) 0.7, (float) 1.0, (float) 0.7, (float) 1.0);
        scaler.setRepeatCount(Animation.INFINITE);
        scaler.setDuration(40);

        animMoveCardsPlayerwinner1 = AnimationUtils.loadAnimation(context,
                R.anim.movetoanotherwinner);
        animMoveCardsPlayerwinner2 = AnimationUtils.loadAnimation(context,
                R.anim.movetoanotherleftcornerwinner);


        bt_creategroup=findViewById(R.id.iv_creategroup);
        bt_declare=findViewById(R.id.bt_declare);
        bt_drop=findViewById(R.id.bt_drop);
        bt_finish=findViewById(R.id.bt_finish);
        DrobButtonVisible(false);
        DeclareButtonVisible(false);
        bt_startgame=findViewById(R.id.bt_startgame);
        bt_discard=findViewById(R.id.iv_discard);
        bt_sliptcard=findViewById(R.id.iv_sliptcard);
        ivallcard=findViewById(R.id.ivallcard);


        imgpl1glow=findViewById(R.id.imgpl1glow);
        if(isPlayer2)
            imgpl2glow=findViewById(R.id.imgpl2_newglow);
        else
            imgpl2glow=findViewById(R.id.imgpl2glow);


        imgpl3glow=findViewById(R.id.imgpl3glow);
        imgpl4glow=findViewById(R.id.imgpl4glow);
        imgpl5glow=findViewById(R.id.imgpl5glow);
        imgpl6glow=findViewById(R.id.imgpl6glow);
        imgpl7glow=findViewById(R.id.imgpl7glow);


        ivpickcard=findViewById(R.id.ivpickcard);
        iv_jokercard=findViewById(R.id.iv_jokercard);
        ivFinishDesk=findViewById(R.id.ivfindeck);

        imgpl1circle = findViewById(R.id.imgpl1circle);
        if(isPlayer2)
            imgpl2circle = findViewById(R.id.imgpl2_newcircle);
        else
            imgpl2circle=findViewById(R.id.imgpl2circle);

        imgpl3circle = findViewById(R.id.imgpl3circle);
        imgpl4circle = findViewById(R.id.imgpl4circle);
        imgpl5circle = findViewById(R.id.imgpl5circle);
        imgpl6circle = findViewById(R.id.imgpl6circle);
        imgpl7circle = findViewById(R.id.imgpl7circle);

        txtPlay1wallet = findViewById(R.id.txtPlay1wallet);

        if(isPlayer2)
            txtPlay2wallet = findViewById(R.id.txtPlay2_newwallet);
        else
            txtPlay2wallet = findViewById(R.id.txtPlay2wallet);

        txtPlay3wallet = findViewById(R.id.txtPlay3wallet);
        txtPlay4wallet = findViewById(R.id.txtPlay4wallet);
        txtPlay5wallet = findViewById(R.id.txtPlay5wallet);
        txtPlay6wallet = findViewById(R.id.txtPlay6wallet);
        txtPlay7wallet = findViewById(R.id.txtPlay7wallet);

        if(isPlayer2)
            lnrPlay2wallet = findViewById(R.id.lnruserdetails2_new);
        else
            lnrPlay2wallet = findViewById(R.id.lnruserdetails2);

        lnrPlay3wallet = findViewById(R.id.lnruserdetails3);
        lnrPlay4wallet = findViewById(R.id.lnruserdetails4);
        lnrPlay5wallet = findViewById(R.id.lnruserdetails5);
        lnrPlay6wallet = findViewById(R.id.lnruserdetails6);
        lnrPlay7wallet = findViewById(R.id.lnruserdetails7);

        lnrPlay2wallet.setVisibility(View.INVISIBLE);
        lnrPlay3wallet.setVisibility(View.INVISIBLE);
        lnrPlay4wallet.setVisibility(View.INVISIBLE);
        lnrPlay5wallet.setVisibility(View.INVISIBLE);
        lnrPlay6wallet.setVisibility(View.INVISIBLE);
        lnrPlay7wallet.setVisibility(View.INVISIBLE);


        if(isPlayer2)
            txtPlay2 = findViewById(R.id.txtPlay2_new);
        else
            txtPlay2 = findViewById(R.id.txtPlay2);

        txtPlay3 = findViewById(R.id.txtPlay3);
        txtPlay4 = findViewById(R.id.txtPlay4);
        txtPlay5 = findViewById(R.id.txtPlay5);
        txtPlay6 = findViewById(R.id.txtPlay6);
        txtPlay7 = findViewById(R.id.txtPlay7);

        txtPlay2.setText("");
        txtPlay3.setText("");
        txtPlay4.setText("");
        txtPlay5.setText("");
        txtPlay6.setText("");
        txtPlay7.setText("");

        iv_jokercard=findViewById(R.id.iv_jokercard);

        txtGameFinish=findViewById(R.id.txtGameFinish);


        OnClickListener();


    }

    private void initializeProgress(ProgressBar progressBar){

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        progressBar.setProgressDrawable(drawable);
        progressBar.setProgress(pStatusprogress);
        progressBar.setMax(timmersectlarge/1000);

    }

    int count = 8;
    TextView txtGameFinish;
    Timer timerstatus;
    int timertime = 6000;
    SharedPreferences prefs;
    private void InitCoutDown() {


        API_CALL_get_table();


        timerstatus = new Timer();

        timerstatus.scheduleAtFixedRate(new TimerTask() {

                                            @Override
                                            public void run() {

                                                API_CALL_status();



                                            }

                                        },
                //Set how long before to start calling the TimerTask (in milliseconds)
                timertime,
                //Set the amount of time between each execution (in milliseconds)
                timertime);



        counttimerforstartgame = new CountDownTimer(8000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                isFirstGame = true;
                centreX=ivallcard.getX() + ivallcard.getWidth()  / 2;
                centreY=ivallcard.getY() + ivallcard.getHeight() / 2;

                //                Funtions.LOGE("MainActivity","centreX : "+centreX+" / "+"centreY :"+centreY);

                count--;
                txtGameFinish.setVisibility(View.VISIBLE);
                txtGameFinish.setText("Round will start in " + (millisUntilFinished/1000)+" second");


            }

            @Override
            public void onFinish() {
                txtGameFinish.setVisibility(View.GONE);
                API_CALL_start_game();

            }


        }.start();


    }
    SoundPool soundPool;
    private void UserProgressCount() {
        //        soundPool  = new SoundPool(context);

        //Progress -
        mCountDownTimer1 = new CountDownTimer(timmersectlarge, timmersectsmall) {

            @Override
            public void onTick(long millisUntilFinished) {
                imgpl1glow.setVisibility(View.VISIBLE);
                isProgressrun1 = false;
                //                pStatus--;
                pStatus -= 2;
                pStatusprogress++;
                mProgress1.setProgress((int) pStatusprogress * 1);
                // txtCounttimer1.setVisibility(View.VISIBLE);
                //txtCounttimer1.setText(pStatus+"");

                if (pStatusprogress >= 30) {
                    //                    soundPool.PlaySound(R.raw.teenpattitick);
                    PlaySaund(R.raw.teenpattitick);
                }

                //                ((TextView)findViewById(R.id.tv_player1_count)).setText(""+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                pStatusprogress = 0;
                mProgress1.setProgress(100);
                mProgress1.setProgress(0);
                imgpl1glow.setVisibility(View.GONE);
                isProgressrun1 = true;

                ExitFromGames();

            }

        };

        mCountDownTimer2 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl2glow.setVisibility(View.VISIBLE);
                    isProgressrun2 = false;
                    pStatusprogress++;
                    mProgress2.setProgress((int) pStatusprogress * 1);
                    pStatus--;

                    //                    OnUserProgressStartorEnd("onstart",
                    //                            imgpl2glow,
                    //                            isProgressrun2,
                    //                            pStatusprogress,
                    //                            mProgress2,
                    //                            pStatus);


                }
                else {
                    isProgressrun2 = true;
                    pStatusprogress = 0;
                    mProgress2.setProgress(100);
                    mProgress2.setProgress(0);
                    imgpl2glow.setVisibility(View.GONE);
                    //                    OnUserProgressStartorEnd("end",
                    //                            imgpl2glow,
                    //                            isProgressrun2,
                    //                            pStatusprogress,
                    //                            mProgress2,
                    //                            pStatus);
                }

            }
        });


        mCountDownTimer3 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl3glow.setVisibility(View.VISIBLE);
                    isProgressrun3 = false;
                    pStatusprogress++;
                    mProgress3.setProgress((int) pStatusprogress * 1);
                    pStatus--;



                }
                else {
                    isProgressrun3 = true;
                    pStatusprogress = 0;
                    mProgress3.setProgress(100);
                    mProgress3.setProgress(0);
                    imgpl2glow.setVisibility(View.GONE);


                }

            }
        });

        mCountDownTimer4 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl4glow.setVisibility(View.VISIBLE);
                    isProgressrun4 = false;
                    pStatusprogress++;
                    mProgress4.setProgress((int) pStatusprogress * 1);
                    pStatus--;

                }
                else {
                    isProgressrun4 = true;
                    pStatusprogress = 0;
                    mProgress4.setProgress(100);
                    mProgress4.setProgress(0);
                    imgpl4glow.setVisibility(View.GONE);




                }

            }
        });

        mCountDownTimer5 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl5glow.setVisibility(View.VISIBLE);
                    isProgressrun5 = false;
                    pStatusprogress++;
                    mProgress5.setProgress((int) pStatusprogress * 1);
                    pStatus--;


                }
                else {
                    isProgressrun5 = true;
                    pStatusprogress = 0;
                    mProgress5.setProgress(100);
                    mProgress5.setProgress(0);
                    imgpl5glow.setVisibility(View.GONE);


                }

            }
        });


        mCountDownTimer6 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl6glow.setVisibility(View.VISIBLE);
                    isProgressrun6 = false;
                    pStatusprogress++;
                    mProgress6.setProgress((int) pStatusprogress * 1);
                    pStatus--;


                }
                else {
                    isProgressrun6 = true;
                    pStatusprogress = 0;
                    mProgress6.setProgress(100);
                    mProgress6.setProgress(0);
                    imgpl6glow.setVisibility(View.GONE);


                }

            }
        });

        mCountDownTimer7 = Funtions.onUserCountDownTimer(context, timmersectlarge,timmersectsmall, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp.equals("onTick"))
                {

                    imgpl7glow.setVisibility(View.VISIBLE);
                    isProgressrun7 = false;
                    pStatusprogress++;
                    mProgress7.setProgress((int) pStatusprogress * 1);
                    pStatus--;


                }
                else {
                    isProgressrun7 = true;
                    pStatusprogress = 0;
                    mProgress7.setProgress(100);
                    mProgress7.setProgress(0);
                    imgpl7glow.setVisibility(View.GONE);


                }

            }
        });

    }

    private void Player1CancelCountDown(){
//            mCountDownTimer1.onFinish();
        mCountDownTimer1.cancel();
        checkMyCards();
    }

    private void OnClickListener(){

//        findViewById(R.id.bt_sharewallet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                API_CALL_share_wallet();
//            }
//        });

        findViewById(R.id.imgback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivallcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isMyChaal)
                {
                    Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(opponent_game_declare)
                    return;

                if(isSplit)
                {
                    animation_type = "pick";
                    API_CALL_get_card();
                }else {
                    Toast.makeText(context, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                }

            }
        });


        ivpickcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isMyChaal)
                {
                    Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(opponent_game_declare)
                    return;

                if(isSplit)
                {
                    animation_type = "drop_pick";
                    API_CALL_get_drop_card();
                }else {
                    Toast.makeText(context, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                }
//
            }
        });

        bt_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //                if(!isMyChaal)
                //                {
                //                    Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
                //                    return;
                //                }

                //                if(isSplit)
                //                {
                //                    Toast.makeText(MainActivity.this, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                //                    return;
                //                }

                if(isFirstChall)
                    loos_point = 20;
                else
                    loos_point = 40;

                Funtions.Dialog_CancelAppointment(context, "Drop", "drop ? You will lose this game by "+loos_point+" points.", new Callback() {
                    @Override
                    public void Responce(String resp, String type, Bundle bundle) {

                        if(resp.equalsIgnoreCase("yes"))
                            API_CALL_pack_game();
                    }
                });


            }
        });

        bt_declare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    if(!isMyChaal && !opponent_game_declare)
//                    {
//                        Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                if(isSplit)
                {
                    API_CALL_declare();
                    bt_declare.setVisibility(View.GONE);
                    bt_drop.setVisibility(View.GONE);
                }else {
                    Toast.makeText(context, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                }

                //                GetCardFromLayout();

            }
        });

        bt_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                API_CALL_start_game();

            }
        });

        bt_creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animation_type = "group";
                //                API_CALL_card_value();
                CreateGroupFromSelect(false);
                bt_sliptcard.setVisibility(View.GONE);

            }
        });

        bt_sliptcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animation_type = "normal";

                SplitCardtoGroup();

                //                bt_declare.setVisibility(View.VISIBLE);
            }
        });

        bt_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isMyChaal)
                {
                    Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedcardvalue.size() == 1)
                {
                    selectedpatti = selectedcardvalue.get(0).Image;
                    selectedpatti_id = selectedcardvalue.get(0).card_id;
                }

                if (selectedpatti.length()>0){

                    if(isSplit)
                    {
                        animation_type = "drop";
                        API_CALL_drop_card(null,0);
                    }
                    else {
                        Toast.makeText(context, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(context, ""+getString(R.string.select_card_error_message), Toast.LENGTH_SHORT).show();

                }



            }
        });

        findViewById(R.id.bt_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isMyChaal)
                {
                    Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedcardvalue.size() == 1)
                {
                    selectedpatti = selectedcardvalue.get(0).Image;
                    selectedpatti_id = selectedcardvalue.get(0).card_id;
                }

                if (selectedpatti.length()>0){

                    if(isSplit)
                    {
                        loos_point = 40;
                        Funtions.Dialog_CancelAppointment(context, "Finish", "finish ? You will lose this game by "+loos_point+" points if you wrong declare.", new Callback() {
                            @Override
                            public void Responce(String resp, String type, Bundle bundle) {

                                if(resp.equalsIgnoreCase("yes"))
                                {
                                    animation_type = "finish_desk";
                                    isFinishDesk = true;

                                    API_CALL_drop_card(null,0);
                                }


                            }
                        });
                    }
                    else {
                        Toast.makeText(context, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(context, ""+getString(R.string.select_card_error_message), Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void DeclareButtonVisible(boolean visible){
//        bt_declare.setVisibility(visible ? View.VISIBLE : View.GONE);
        bt_finish.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void DrobButtonVisible(boolean visible){
//        bt_declare.setVisibility(visible ? View.VISIBLE : View.GONE);
        bt_drop.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        Funtions.showDialoagonBack(context, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(type.equals("exit"))
                {
                    StopSound();

                    API_CALL_leave_table("1");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finishAffinity();

                        }
                    },500);

                }
                else if(type.equals("next"))
                {
                    API_CALL_leave_table("0");
                    StopSound();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finish();
                        }
                    },500);
                }

            }
        });
    }

    private void TranslateLayout(View imageView, int position){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PlaySaund(R.raw.teenpatticardflip_android);
            }
        },position);


        //        Funtions.LOGE("MainActivity","width : "+width+" / "+"height :"+height);
        //        Funtions.LOGE("MainActivity","centreX : "+centreX+" / "+"centreY :"+centreY);

        final TranslateAnimation animationt = new TranslateAnimation(width,
                0, height, 0);
        animationt.setDuration(position);
        animationt.setFillAfter(true);
        animationt.setAnimationListener(this);

        imageView.startAnimation(animationt);


    }

    boolean animationon = false;
    private void DropTranslationAnimation(){

        animationon = true;

        final View fromView, toView, shuttleView;

        fromView = imgpl1circle;
        toView = ivpickcard;


        int fromLoc[] = new int[2];
        fromView.getLocationOnScreen(fromLoc);
        float startX = fromLoc[0];
        float startY = fromLoc[1];

        int toLoc[] = new int[2];
        toView.getLocationOnScreen(toLoc);
        float destX = toLoc[0];
        float destY = toLoc[1];

        rl.setVisibility(View.VISIBLE);
        rl.removeAllViews();
        final ImageView sticker = new ImageView(this);


        String uriuser2 = "@drawable/teenpatti_backcard";  // where myresource
        // " +
        int stickerId = getResources().getIdentifier(uriuser2,
                null,
                getPackageName());

        int card_hieght = (int) getResources().getDimension(R.dimen.card_hieght);

        if(stickerId > 0)
            LoadImage().load(stickerId).into(sticker);

        sticker.setLayoutParams(new ViewGroup.LayoutParams(card_hieght, card_hieght));
        rl.addView(sticker);

        shuttleView = sticker;

        Animations anim = new Animations();
        Animation a = anim.fromAtoB(startX, startY, destX, destY, null, ANIMATION_SPEED, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                shuttleView.setVisibility(View.GONE);
                fromView.setVisibility(View.VISIBLE);
                animationon = false;
                sticker.setVisibility(View.GONE);
            }
        });
        sticker.setAnimation(a);
        a.startNow();


        Rect fromRect = new Rect();
        Rect toRect = new Rect();
        fromView.getGlobalVisibleRect(fromRect);
        toView.getGlobalVisibleRect(toRect);

        PlaySaund(R.raw.teenpatticardflip_android);


        //        AnimatorSet animatorSet =
        //                getViewToViewScalingAnimator(rootView, shuttleView, fromRect, toRect, ANIMATION_SPEED, 0);
        //
        //        animatorSet.addListener(new Animator.AnimatorListener() {
        //            @Override
        //            public void onAnimationStart(Animator animation) {
        //                shuttleView.setVisibility(View.VISIBLE);
        ////                fromView.setVisibility(View.INVISIBLE);
        //            }
        //
        //            @Override
        //            public void onAnimationEnd(Animator animation) {
        //                shuttleView.setVisibility(View.GONE);
        //                fromView.setVisibility(View.VISIBLE);
        //                animationon = false;
        //            }
        //
        //            @Override
        //            public void onAnimationCancel(Animator animation) {
        //
        //            }
        //
        //            @Override
        //            public void onAnimationRepeat(Animator animation) {
        //
        //            }
        //        });
        //        animatorSet.start();

    }

    private void PickCardTranslationAnimation(){

        animationon = true;

        View animationview = findViewById(R.id.animationview);

        final View fromView, toView, shuttleView;

        fromView = ivallcard;
        toView = animationview;


        int fromLoc[] = new int[2];
        fromView.getLocationOnScreen(fromLoc);
        float startX = fromLoc[0];
        float startY = fromLoc[1];

        int toLoc[] = new int[2];
        toView.getLocationOnScreen(toLoc);
        float destX = toLoc[0];
        float destY = toLoc[1];

        rl.setVisibility(View.VISIBLE);
        rl.removeAllViews();
        final ImageView sticker = new ImageView(this);


        String uriuser2 = "@drawable/teenpatti_backcard";  // where myresource
        // " +
        int stickerId = getResources().getIdentifier(uriuser2,
                null,
                getPackageName());

        int card_hieght = (int) getResources().getDimension(R.dimen.card_hieght);

        if(stickerId > 0)
            LoadImage().load(stickerId).into(sticker);

        sticker.setLayoutParams(new ViewGroup.LayoutParams(card_hieght, card_hieght));
        rl.addView(sticker);

        shuttleView = sticker;

        Animations anim = new Animations();
        Animation a = anim.fromAtoB(startX, startY, destX-100, destY-250, null, ANIMATION_SPEED, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                shuttleView.setVisibility(View.GONE);
                fromView.setVisibility(View.VISIBLE);
                animationon = false;
                sticker.setVisibility(View.GONE);
            }
        });
        sticker.setAnimation(a);
        a.startNow();


        Rect fromRect = new Rect();
        Rect toRect = new Rect();
        fromView.getGlobalVisibleRect(fromRect);
        toView.getGlobalVisibleRect(toRect);

        Log.e("MainActivity","FromView : "+fromRect);
        Log.e("MainActivity","toView : "+toRect);

        PlaySaund(R.raw.teenpatticardflip_android);


        //        AnimatorSet animatorSet = getViewToViewScalingAnimator(rootView, shuttleView, fromRect, toRect, ANIMATION_SPEED, 0);
        //
        //        animatorSet.addListener(new Animator.AnimatorListener() {
        //            @Override
        //            public void onAnimationStart(Animator animation) {
        //                shuttleView.setVisibility(View.VISIBLE);
        ////                fromView.setVisibility(View.INVISIBLE);
        //            }
        //
        //            @Override
        //            public void onAnimationEnd(Animator animation) {
        //                shuttleView.setVisibility(View.GONE);
        //                fromView.setVisibility(View.VISIBLE);
        //                animationon = false;
        //            }
        //
        //            @Override
        //            public void onAnimationCancel(Animator animation) {
        //
        //            }
        //
        //            @Override
        //            public void onAnimationRepeat(Animator animation) {
        //
        //            }
        //        });
        //        animatorSet.start();

    }

    private void DropPickTranslationAnimation(){

        animationon = true;


        View rootView = findViewById(R.id.ivpickcard);
        final View fromView, toView, shuttleView;

        fromView = ivpickcard;
        toView = imgpl1circle;


        int fromLoc[] = new int[2];
        fromView.getLocationOnScreen(fromLoc);
        float startX = fromLoc[0];
        float startY = fromLoc[1];

        int toLoc[] = new int[2];
        toView.getLocationOnScreen(toLoc);
        float destX = toLoc[0];
        float destY = toLoc[1];

        rl.setVisibility(View.VISIBLE);
        rl.removeAllViews();
        final ImageView sticker = new ImageView(this);


        String uriuser2 = "@drawable/teenpatti_backcard";  // where myresource
        // " +
        int stickerId = getResources().getIdentifier(uriuser2,
                null,
                getPackageName());

        int card_hieght = (int) getResources().getDimension(R.dimen.card_hieght);

        if(stickerId > 0)
            LoadImage().load(stickerId).into(sticker);

        sticker.setLayoutParams(new ViewGroup.LayoutParams(card_hieght, card_hieght));
        rl.addView(sticker);

        shuttleView = sticker;

        Animations anim = new Animations();
        Animation a = anim.fromAtoB(startX, startY, destX, destY, null, ANIMATION_SPEED, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                shuttleView.setVisibility(View.GONE);
                fromView.setVisibility(View.VISIBLE);
                animationon = false;
                sticker.setVisibility(View.GONE);
            }
        });
        sticker.setAnimation(a);
        a.startNow();


        Rect fromRect = new Rect();
        Rect toRect = new Rect();
        fromView.getGlobalVisibleRect(fromRect);
        toView.getGlobalVisibleRect(toRect);

        PlaySaund(R.raw.teenpatticardflip_android);


        //        AnimatorSet animatorSet =
        //                getViewToViewScalingAnimator(rootView, shuttleView, fromRect, toRect, ANIMATION_SPEED, 0);
        //
        //        animatorSet.addListener(new Animator.AnimatorListener() {
        //            @Override
        //            public void onAnimationStart(Animator animation) {
        //                shuttleView.setVisibility(View.VISIBLE);
        ////                fromView.setVisibility(View.INVISIBLE);
        //            }
        //
        //            @Override
        //            public void onAnimationEnd(Animator animation) {
        //                shuttleView.setVisibility(View.GONE);
        //                fromView.setVisibility(View.VISIBLE);
        //                animationon = false;
        //            }
        //
        //            @Override
        //            public void onAnimationCancel(Animator animation) {
        //
        //            }
        //
        //            @Override
        //            public void onAnimationRepeat(Animator animation) {
        //
        //            }
        //        });
        //        animatorSet.start();

    }


    String table_id = "1",game_id = "",Main_Game_ID = "";
    private void API_CALL_get_table() {

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        if(isPlayer2)
            params.put("no_of_players","2");
        else
            params.put("no_of_players","5");

        if(getIntent().hasExtra(SEL_TABLE))
        {
            String boot_value = getIntent().getStringExtra(SEL_TABLE);
            params.put("boot_value", boot_value);
        }
        else {
            return;
        }

        ApiRequest.Call_Api(this, Const.get_table, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                try {

                    Log.v("get_table" , "working -   "+resp);

                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if(code.equalsIgnoreCase("200"))
                    {
                        table_id = jsonObject
                                .getJSONArray("table_data")
                                .getJSONObject(0)
                                .optString("table_id");

                        JSONArray table_users = jsonObject.optJSONArray("table_data");

                        if(table_users != null)
                        {
                            //                            UserResponse(table_users);
                        }

                    }
                    else {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }


    float user_wallet_1 = -1;
    private void UserResponse(JSONArray table_users) throws JSONException {
        //---------------------------------------------User arrange ----------------

        int pp1 = 0;

        for (int i = 0; i < table_users.length(); i++) {
            String use_temp = prefs.getString("user_id", "");

            if (table_users.getJSONObject(i).getString("user_id").equals(use_temp)) {
                pp1 = i;
            }
        }

        for (int i = 0; i < pp1; i++) {

            JSONObject temp = table_users.getJSONObject(0);

            for (int j = 0; j < table_users.length() - 1; j++) {

                table_users.put(j, table_users.get(j + 1));//=jsonArrayuser

            }

            table_users.put(table_users.length() - 1,
                    temp);
        }

        user_id_player1 = "";
        user_id_player2 = "";
        user_id_player3 = "";
        user_id_player4 = "";
        user_id_player5 = "";
        user_id_player6 = "";
        user_id_player7 = "";

        if(isPlayer2)
        {
            for (int k = 0; k < table_users.length(); k++) {
                if (k == 0) {

                    String name = table_users.getJSONObject(0).getString("name");
                    user_id_player1 = table_users.getJSONObject(0).getString(
                            "user_id");
                    String profile_pic = table_users.getJSONObject(0).getString("profile_pic");
                    String walletplayer1 = table_users.getJSONObject(0).getString("wallet");
//                long numberamount = Float.parseFloat(walletplayer1);
//                txtPlay1wallet.setText("" + NumberFormat.getNumberInstance(Locale.US).format(numberamount));
                    txtPlay1wallet.setText("" + walletplayer1);
//                    LoadImage().load(Const.IMGAE_PATH + profile_pic).into(imgpl1circle);
                    LoadImage().load(Const.IMGAE_PATH + profile_pic).into(imgpl1circle);

                    if (user_id_player1.equals(prefs.getString("user_id", ""))) {


                    } else {

                        Toast.makeText(context, "Your are timeout from " +
                                        "this table Join again.",
                                Toast.LENGTH_LONG).show();


                        finish();
                    }
                    // imgchipuser1.setVisibility(View.VISIBLE);
                    // first player
                }
                else {

                    user_id_player2 = table_users.getJSONObject(k).getString("user_id");
                    InitializeUsers(user_id_player2,
                            table_users.getJSONObject(k),
                            txtPlay2,
                            txtPlay2wallet,
                            imgpl2circle,
                            lnrPlay2wallet);

                    if(!user_id_player2.equals("0") && !user_id_player2.equals(""))
                        break;

                }
            }
        }
        else
            for (int k = 0; k < table_users.length(); k++) {
                if (k == 0) {

                    String name = table_users.getJSONObject(0).getString("name");
                    user_id_player1 = table_users.getJSONObject(0).getString(
                            "user_id");
                    String profile_pic = table_users.getJSONObject(0).getString("profile_pic");
                    String walletplayer1 = table_users.getJSONObject(0).getString("wallet");
                    float numberamount = Float.parseFloat(walletplayer1);
                    user_wallet_1 = numberamount;
                    txtPlay1wallet.setText("" + NumberFormat.getNumberInstance(Locale.US).format(numberamount));
                    LoadImage().load(Const.IMGAE_PATH + profile_pic).into(imgpl1circle);

                    if (user_id_player1.equals(prefs.getString("user_id", ""))) {


                    } else {

                        Toast.makeText(context, "Your are timeout from " +
                                        "this table Join again.",
                                Toast.LENGTH_LONG).show();


                        finish();
                    }
                    // imgchipuser1.setVisibility(View.VISIBLE);
                    //first player
                }
                else if (k == 1) {

                    user_id_player2 = table_users.getJSONObject(k).getString("user_id");
                    InitializeUsers(user_id_player2,
                            table_users.getJSONObject(k),
                            txtPlay2,
                            txtPlay2wallet,
                            imgpl2circle,
                            lnrPlay2wallet);
                }
                else if (k == 2) {

                    user_id_player3 = table_users.getJSONObject(k).getString("user_id");

                    InitializeUsers(user_id_player3,
                            table_users.getJSONObject(k),
                            txtPlay3,
                            txtPlay3wallet,
                            imgpl3circle,
                            lnrPlay3wallet);
                }
                else if (k == 3) {

                    user_id_player4 = table_users.getJSONObject(k).getString("user_id");

                    InitializeUsers(user_id_player4,
                            table_users.getJSONObject(k),
                            txtPlay4,
                            txtPlay4wallet,
                            imgpl4circle,
                            lnrPlay4wallet);
                }
                else if (k == 4) {

                    user_id_player5 = table_users.getJSONObject(k).getString("user_id");

                    InitializeUsers(user_id_player5,
                            table_users.getJSONObject(k),
                            txtPlay5,
                            txtPlay5wallet,
                            imgpl5circle,
                            lnrPlay5wallet);
                }
//            else if (k == 5) {
//
//                user_id_player6 = table_users.getJSONObject(k).getString("user_id");
//
//                InitializeUsers(user_id_player6,
//                        table_users.getJSONObject(k),
//                        txtPlay6,
//                        txtPlay6wallet,
//                        imgpl6circle,
//                        lnrPlay6wallet);
//            }
//            else if(k == 6){
//
//                user_id_player7 = table_users.getJSONObject(k).getString("user_id");
//
//                InitializeUsers(user_id_player7,
//                        table_users.getJSONObject(k),
//                        txtPlay7,
//                        txtPlay7wallet,
//                        imgpl7circle,
//                        lnrPlay7wallet);
//            }
            }

    }

    private RequestManager LoadImage()
    {
        return  Glide.with(context);
    }

    private void API_CALL_pack_game() {

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        Submitcardslist = GetCardFromLayout();

        params.put("json",""+Submitcardslist);


        //        Log.v("userinfo","user_id "+prefs.getString("user_id", "")+"game_id" +game_id);

        ApiRequest.Call_Api(this, Const.pack_game, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                Log.v("pack_game",resp);

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if(code.equalsIgnoreCase("200"))
                    {
                        isFirstChall = false;
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                        bt_drop.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private void API_CALL_leave_table(final String value) {

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        Submitcardslist = GetCardFromLayout();

        params.put("json",""+Submitcardslist);


        //        Log.v("userinfo","user_id "+prefs.getString("user_id", "")+"game_id" +game_id);

        ApiRequest.Call_Api(this, Const.leave_table, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                Log.v("pack_game",resp);

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if(code.equalsIgnoreCase("200"))
                    {

                        //                        Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }


    String Submitcardslist = "";
    boolean isApiCalling = false;
    private void API_CALL_declare() {

        if(isApiCalling)
            return;

        isApiCalling = true;

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        Submitcardslist = GetCardFromLayout();

        params.put("json",""+Submitcardslist);

        String url = "";

        if(bt_declare.getText().toString().trim().equalsIgnoreCase(DECLARE_BACK))
            url = Const.declare_back;
        else
            url = Const.declare;

        ApiRequest.Call_Api(this, url, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {


                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if(code.equalsIgnoreCase("200"))
                    {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                        bt_declare.setVisibility(View.GONE);
                        bt_drop.setVisibility(View.GONE);
                        game_declare = true;
                    }
                    else {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    bt_declare.setVisibility(View.GONE);
                    bt_drop.setVisibility(View.GONE);
                }

                isApiCalling = false;
                isFinishDesk = false;
            }
        });



    }

    private String GetCardFromLayout(){

        JSONArray jsonArray = new JSONArray();

        try {


            if(!isSplit)
            {

                for (int k = 0; k < 1 ; k++) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("card_group",""+0);

                    JSONArray jsonArray1 = new JSONArray();

                    for (int i = 0; i < rlt_addcardview.getChildCount() ; i++) {

                        View view = rlt_addcardview.getChildAt(i);

                        jsonArray1.put(""+view.getTag());

                        jsonObject.put("cards",jsonArray1);
                    }

                    jsonArray.put(jsonObject);

                }



            }
            else {


                for (int i = 0; i < rlt_addcardview.getChildCount() ; i++) {

                    JSONObject jsonObject = new JSONObject();

                    JSONArray jsonArray1 = new JSONArray();

                    View view = rlt_addcardview.getChildAt(i);
                    LinearLayout lnr_group_card = view.findViewById(R.id.lnr_group_card);
                    jsonObject.put("card_group",""+lnr_group_card.getTag());

                    for (int j = 0; j < lnr_group_card.getChildCount() ; j++) {

                        View view2 = lnr_group_card.getChildAt(j);
                        jsonArray1.put(""+view2.getTag());

                        //                    Log.e("MainActivity","Layout Tags : "+view2.getTag());

                        jsonObject.put("cards",jsonArray1);
                    }


                    jsonArray.put(jsonObject);

                }


                //            Log.e("MainActivity","Layout Tags : "+jsonArray.toString());


            }

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("MainActivity","Layout Tags : "+e.getMessage());


        }


        cardPref.edit().putString(Pref_cards+game_id,""+jsonArray.toString()).apply();
        cardPref.edit().putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12)).apply();

        Funtions.LOGE("Rummy5Player",""+jsonArray.toString());
        return jsonArray.toString();
    }

    private void API_CALL_start_game() {


        RestartGameActivity();

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        ApiRequest.Call_Api(this, Const.start_game, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                Log.v("start_game",resp);
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if(code.equalsIgnoreCase("200"))
                    {
                        game_id = jsonObject.optString("game_id");
                        Main_Game_ID = jsonObject.optString("game_id");
                        bt_startgame.setVisibility(View.GONE);
                    }
                    else if(code.equalsIgnoreCase("406"))
                    {

                    }
                    else {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    boolean isStatusApiCalling = false;
    private void API_CALL_status() {

        if(isStatusApiCalling)
            return;

        isStatusApiCalling = true;

        userModelArrayList.clear();

        if(rlt_addcardview.getChildCount() > 0)
            Submitcardslist = GetCardFromLayout();


        HashMap params = new HashMap();
        params.put("game_id",""+game_id);
        params.put("table_id",""+table_id);
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));
        //        Log.v("userinfo","user_id "+prefs.getString("user_id", "")+"game_id" +game_id);

        ApiRequest.Call_Api(this, Const.status, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    Parse_responseStatus(jsonObject);
                    //                    Log.v("status" , "working -   "+jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                    isStatusApiCalling = false;
                }

                isStatusApiCalling = false;


            }
        });



    }

    private void API_CALL_getCardList() {

        //        GetNextCartValue();


        HashMap params = new HashMap();
        params.put("game_id",""+game_id);
        //        params.put("table_id","1");
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        ApiRequest.Call_Api(this, Const.my_card, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                Log.v("my_card" , resp);
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    RestartGameActivity();
                    DrobButtonVisible(true);

                    String previous_cards =  cardPref.getString(Pref_cards+game_id,"");

                    if(previous_cards != null && !previous_cards.equals("") && !previous_cards.equalsIgnoreCase("[{\"card_group\":\"0\"}]"))
                    {
                        Parse_response(jsonObject);
//                        Parse_PreviousCards(previous_cards,jsonObject);
                    }
                    else {
                        Parse_response(jsonObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        //        cardListAdapter.notifyDataSetChanged();

    }

    private void Parse_PreviousCards(String previous_cards, JSONObject jsonObject) throws JSONException{

        JSONArray jsonArray = new JSONArray(previous_cards);
        int groups_lenth = jsonArray.length();

        if(groups_lenth > 0)
        {

            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            JSONArray cardsArray1 = jsonObject1.getJSONArray("cards");

            if(cardsArray1.length() > 0)
            {
                String cardvalue = cardsArray1.get(0).toString();
                if(cardvalue != null && !cardvalue.equalsIgnoreCase("") && !cardvalue.equalsIgnoreCase("null"))
                {
                    for (int i = 0; i < groups_lenth ; i++) {

                        if(i == 0)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),rs_cardlist_group1,jsonObject);
                        else if(i == 1)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),rp_cardlist_group2,jsonObject);
                        else if(i == 2)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),bl_cardlist_group3,jsonObject);
                        else if(i == 3)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),bp_cardlist_group4,jsonObject);
                        else if(i == 4)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),joker_cardlist_group5,jsonObject);
                        else if(i == 5)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),ext_group1,jsonObject);
                        else if(i == 6)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),ext_group2,jsonObject);
                        else if(i == 7)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),ext_group3,jsonObject);
                        else if(i == 8)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),ext_group4,jsonObject);
                        else if(groups_lenth == 9)
                            addCardsonListFromJson(jsonArray.getJSONObject(i),ext_group5,jsonObject);


                    }

                    findViewById(R.id.rlt_dropgameview).setVisibility(View.GONE);
                    isgamestarted = true;
                    API_CALL_Sort_card_value(null,0,0);

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                checkMyCards();
//                            }
//                        },1000);

                }
                else
                    Parse_response(jsonObject);

            }
            else
                Parse_response(jsonObject);


        }
        else
            Parse_response(jsonObject);


    }

    private void checkMyCards() {

        int my_count = 0;
        for (int i = 0; i < rlt_addcardview.getChildCount() ; i++) {

            View view = rlt_addcardview.getChildAt(i);
            LinearLayout lnr_group_card = view.findViewById(R.id.lnr_group_card);

            for (int j = 0; j < lnr_group_card.getChildCount() ; j++) {
                my_count++;
            }

        }

        if(my_count != 13)
        {

            int extra_cards_count = my_count - 10;

            HashMap params = new HashMap();
            params.put("game_id",""+game_id);
            params.put("user_id",""+prefs.getString("user_id", ""));
            params.put("token",""+prefs.getString("token", ""));

            ApiRequest.Call_Api(this, Const.my_card, params, new Callback() {
                @Override
                public void Responce(String resp, String type, Bundle bundle) {
                    try {
                        JSONObject jsonObject = new JSONObject(resp);


                        String code = jsonObject.optString("code");
                        String message = jsonObject.optString("message");

                        if(code.equalsIgnoreCase("200"))
                        {
                            JSONArray cardsArray = jsonObject.optJSONArray("cards");

                            if(cardsArray != null && cardsArray.length() > 0)
                            {

                                for (int k = 0; k < rlt_addcardview.getChildCount() ; k++) {

                                    View view = rlt_addcardview.getChildAt(k);
                                    LinearLayout lnr_group_card = view.findViewById(R.id.lnr_group_card);

                                    for (int j = 0; j < lnr_group_card.getChildCount() ; j++) {
                                        boolean isCardAvaialble = false;
                                        View view2 = lnr_group_card.getChildAt(j);
                                        String mycardid = view2.getTag().toString().trim();


                                        for (int i = 0; i < cardsArray.length() ; i++) {

                                            JSONObject cardObject = cardsArray.getJSONObject(i);
                                            CardModel model = new CardModel();

                                            model.card_id = cardObject.optString("card");

                                            if(model.card_id.equalsIgnoreCase(mycardid))
                                            {
                                                isCardAvaialble =true;
                                                break;
//
                                            }

                                        }

                                        if(!isCardAvaialble)
                                        {
//
//                                                    lnr_group_card.removeViewAt(j);
                                            RemoveCardFromArrayLists(mycardid);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    animation_type = "reset_card";
                                                    API_CALL_Sort_card_value(null,0,0);


                                                }
                                            },500);

                                        }

                                    }

                                }



                            }

                        }


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }


    }


    private void addCardsonListFromJson(JSONObject jsonObject,ArrayList<CardModel> arrayList,JSONObject apiCardsList) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        JSONArray cardsArray = apiCardsList.optJSONArray("cards");

        for (int i = 0; i < jsonArray.length() ; i++) {
            CardModel model = new CardModel();
            model.Image = ""+jsonArray.get(i);

            if(model.Image.substring(model.Image.length() - 1).equalsIgnoreCase("_"))
            {
                model.Image = removeLastChars(model.Image,1);
            }

            model.card_id = ""+jsonArray.get(i);


            model.card_group = getColorCode(model.Image);


            boolean isCardAvaialble = false;

            for (int k = 0; k < cardsArray.length() ; k++) {

                JSONObject cardObject = cardsArray.getJSONObject(k);
                CardModel storeCardModel = new CardModel();

                storeCardModel.card_id = cardObject.optString("card");

                if(storeCardModel.card_id.equalsIgnoreCase(model.card_id))
                {
                    isCardAvaialble =true;
                    break;
                }

            }

            if (isCardAvaialble) {
                arrayList.add(model);
            }

        }

    }

    boolean isDeclareVisible = false;
    private void RestartGameActivity(){
        findViewById(R.id.rlt_dropgameview).setVisibility(View.GONE);
        isSplit = false;
        rs_cardlist_group1.clear();
        rp_cardlist_group2.clear();
        bl_cardlist_group3.clear();
        bp_cardlist_group4.clear();
        joker_cardlist_group5.clear();
        ext_group1.clear();
        ext_group2.clear();
        ext_group3.clear();
        ext_group4.clear();
        ext_group5.clear();
        selectedcardvalue.clear();
        grouplist.clear();
        rlt_addcardview.removeAllViews();
        bt_sliptcard.setVisibility(View.GONE);
        bt_creategroup.setVisibility(View.GONE);
        bt_declare.setVisibility(View.GONE);
//        bt_drop.setVisibility(View.VISIBLE);
        DrobButtonVisible(false);
        bt_declare.setText(getString(R.string.declare));
        game_declare = false;
        opponent_game_declare = false;
        isFinishDesk = false;
        isFirstChall = true;
        isgamestarted = false;
        isGamePacked = false;
        cardModelArrayList.clear();
        userModelArrayList.clear();
        findViewById(R.id.iv_rightarrow).setVisibility(View.GONE);
        findViewById(R.id.iv_leftarrow).setVisibility(View.GONE);

        String uri1 = "@drawable/" + "finish_deck";  // where myresource " +

        int imageResource1 = getResources().getIdentifier(uri1, null,
                getPackageName());
        LoadImage().load(imageResource1).into(ivFinishDesk);

    }

    @Override
    protected void onDestroy() {
        StopSound();
        timerstatus.cancel();
        super.onDestroy();

    }

    boolean isgamestarted = false;
    boolean game_declare = false;
    boolean opponent_game_declare = false;
    boolean isGamePacked = false;
    boolean isFirstGame = false;
    ArrayList<Usermodel> pointUserlist ;
    private void Parse_responseStatus(JSONObject jsonObject) throws JSONException {

        String code = jsonObject.optString("code");
        String message = jsonObject.optString("message");
        JSONArray table_users = jsonObject.optJSONArray("table_users");
        JSONArray game_users = jsonObject.optJSONArray("game_users");
        JSONObject table_detail = jsonObject.optJSONObject("table_detail");

        if(table_detail != null)
        {
            int boot_value = table_detail.optInt("boot_value",0);
            int point_value = boot_value/80;
            min_entry = point_value * 100;

            Funtions.showToast(context,""+min_entry);
        }

        tv_gameid.setText("Game id : "+game_id);
        tv_tableid.setText("Table id : "+table_id);

        String table_amount = jsonObject.optString("table_amount","80");

        ((TextView) findViewById(R.id.txttotalpoints)).setText("Total Points : "+table_amount);

        if(game_users != null)
        {
            for (int i = 0; i < game_users.length() ; i++) {
                JSONObject jsonObject1 = game_users.getJSONObject(i);
                String user_id = jsonObject1.getString("user_id");
                String packed = jsonObject1.optString("packed");

                if(user_id.equals(prefs.getString("user_id", "")))
                {
                    if(packed.equals("1"))
                    {
                        isgamestarted = false;
                        complategameUIChange();
                        isGamePacked = true;
                        ((TextView)findViewById(R.id.tv_gamemessage)).setText(""+getString(R.string.drop_message));
                        findViewById(R.id.rlt_dropgameview).setVisibility(View.VISIBLE);
                    }
                    else {
                        isGamePacked = false;
                    }
                }

            }
        }

        if(table_users != null)
        {
            UserResponse(table_users);
        }


        if(code.equalsIgnoreCase("200"))
        {


            JSONArray drop_card = jsonObject.optJSONArray("drop_card");
            JSONArray game_users_cards = jsonObject.optJSONArray("game_users_cards");

            joker_card = jsonObject.optString("joker");

            if(joker_card.substring(joker_card.length() - 1).equalsIgnoreCase("_"))
            {
                joker_card = removeLastChars(joker_card,1);
            }


            String game_status = jsonObject.optString("game_status");
            String declare_user_id = jsonObject.optString("declare_user_id");
            boolean declare = jsonObject.optBoolean("declare");

            if(declare_user_id != null &&
                    !declare_user_id.equals(prefs.getString("user_id", "")) && declare && !game_declare)
            {
                opponent_game_declare = true;
                Toast.makeText(context, ""+getString(R.string.declare_game), Toast.LENGTH_SHORT).show();
                bt_declare.setText(DECLARE_BACK);
                bt_declare.setVisibility(View.VISIBLE);
            }
            else {

                if(!declare_user_id.equalsIgnoreCase("0"))
                {
                    if(declare)
                    {
                        game_declare = declare;
                        bt_declare.setVisibility(View.GONE);
                    }
                }

            }

            String winner_user_id = jsonObject.optString("winner_user_id");

            if(game_status.equalsIgnoreCase("2") || game_status.equalsIgnoreCase("0"))
            {
                //                bt_startgame.setVisibility(View.VISIBLE);
                isgamestarted = false;
                complategameUIChange();
                makeWinnertoPlayer(winner_user_id);
                game_id = jsonObject.optString("active_game_id");

                if(!isFirstGame)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            count = 8;
                            counttimerforstartgame.start();
                            isFirstGame = true;
                        }
                    },3000);
                }



            }
            else if(game_status.equalsIgnoreCase("1"))
            {
                bt_startgame.setVisibility(View.GONE);
                rltwinnersymble1.setVisibility(View.GONE);
                rltwinnersymble2.setVisibility(View.GONE);
                rltwinnersymble3.setVisibility(View.GONE);
                rltwinnersymble4.setVisibility(View.GONE);
                rltwinnersymble5.setVisibility(View.GONE);
                rltwinnersymble6.setVisibility(View.GONE);
                rltwinnersymble7.setVisibility(View.GONE);

                if(!isgamestarted && !isGamePacked)
                    API_CALL_getCardList();
                else  if(isgamestarted && rlt_addcardview.getChildCount() <= 0)
                    API_CALL_getCardList();


                String chaal = jsonObject.getString("chaal");
                makeHightLightForChaal(chaal);
            }

            String urijokar = "@drawable/" + joker_card.toLowerCase();  // where myresource " +
            //
            int imageResource2 = getResources().getIdentifier(urijokar, null,
                    getPackageName());
            if(imageResource2 > 0)
                LoadImage().load(imageResource2).into(iv_jokercard);

            //---------------------------------------------User Aarange------------------



            for (int i = 0; i < table_users.length() ; i++) {

                JSONObject tables_Object = table_users.getJSONObject(i);
                Usermodel model = new Usermodel();
                model.userid = tables_Object.optString("user_id");


                model.seat_position = tables_Object.optString("seat_position");


                model.userName = tables_Object.optString("name");
                model.userMobile = tables_Object.optString("mobile");
                model.userImage = tables_Object.optString("profile_pic");
                model.userWallet = tables_Object.optString("wallet");

                userModelArrayList.add(model);
            }


            if(drop_card != null)
            {

                if(drop_card.length() > 0)
                {
                    for (int i = 0; i < drop_card.length() ; i++) {

                        JSONObject drop_Object = drop_card.getJSONObject(i);
                        CardModel model = new CardModel();
                        model.Image = drop_Object.optString("card");
                        if(!model.Image.equals("") && !model.Image.equals("null") &&
                                model.Image.substring(model.Image.length() - 1).equalsIgnoreCase("_"))
                        {
                            model.Image = removeLastChars(model.Image,1);
                        }
                        String uri1 = "@drawable/" + model.Image.toLowerCase();  // where myresource " +



                        int imageResource1 = getResources().getIdentifier(uri1, null,
                                getPackageName());

                        if(imageResource1 > 0)
                        {
                            LoadImage().load(imageResource1).into(ivpickcard);

                            if(opponent_game_declare || game_declare)
                                LoadImage().load(imageResource1).into(ivFinishDesk);
                        }


                        String src_joker_cards = "";
                        src_joker_cards = joker_card.substring(joker_card.length() - 1);

                        if(src_joker_cards != null && !src_joker_cards.equals(""))
                        {
                            if(src_joker_cards.contains(model.Image.substring(model.Image.length() - 1)))
                            {

                                ((ImageView)findViewById(R.id.iv_jokercard2)).setVisibility(View.VISIBLE);

                            }
                            else {
                                ((ImageView)findViewById(R.id.iv_jokercard2)).setVisibility(View.GONE);
                            }
                        }



                    }
                }
                else {
                    ivpickcard.setImageDrawable(getResources().getDrawable(R.drawable.teenpatti_backcard));
                }



            }

            if(game_users_cards != null)
            {

                ArrayList<CardModel> game_users_cards_list = new ArrayList<>();

                for (int i = 0; i < game_users_cards.length() ; i++) {


                    JSONObject game_object = game_users_cards.getJSONObject(i);
                    JSONObject json_user = game_object.getJSONObject("user");
                    CardModel model = new CardModel();
                    String user_id = json_user.optString("user_id");
                    model.user_id = user_id;

                    for (Usermodel usermodel: userModelArrayList) {
                        if(user_id.equals(usermodel.userid))
                        {
                            model.user_name = usermodel.userName;
                        }
                    }

                    model.score = json_user.optInt("score",0);
                    model.won = json_user.optInt("win",0);

                    // result = 1 user drop
                    // result = 0 user normal functional

                    model.result = json_user.optInt("result",0);
                    model.packed = json_user.optInt("packed",0);

                    model.winner_user_id = winner_user_id;
                    model.joker_card = joker_card;
                    model.game_id = Main_Game_ID;

                    ArrayList<CardModel> groups_cardlist = new ArrayList<>();
                    if(model.user_id.equals(prefs.getString("user_id", "")))
                    {

                        if(Submitcardslist == null || Submitcardslist.equals(""))
                            Submitcardslist = GetCardFromLayout();

                        String str_jsonArray = Submitcardslist;

                        JSONArray jsonArray = new JSONArray(str_jsonArray);

                        if(jsonArray.length() > 0)
                        {
                            for (int k = 0; k < jsonArray.length() ; k++) {
                                JSONObject json_cardlist = jsonArray.getJSONObject(k);
                                ArrayList<CardModel> user_cards = new ArrayList<>();
                                CardModel group_model = new CardModel();

                                JSONArray json_cards = json_cardlist.getJSONArray("cards");
                                for (int j = 0; j < json_cards.length() ; j++) {

                                    CardModel model_cards = new CardModel();
                                    model_cards.Image = json_cards.getString(j);
                                    user_cards.add(model_cards);

                                    group_model.groups_cards = user_cards;

                                }
                                groups_cardlist.add(group_model);
                                model.groups_cards = groups_cardlist;

                            }
                        }
                        else {
                            JSONArray group_array = json_user.optJSONArray("cards");

//                            if(group_array == null)
//                                return;

                            if(group_array == null)
                            {
                                for (int k = 0; k < 1 ; k++) {
                                    CardModel group_model = new CardModel();
                                    ArrayList<CardModel> user_cards = new ArrayList<>();

                                    for (int j = 0; j < 13 ; j++) {

                                        CardModel model_cards = new CardModel();
                                        model_cards.card_group = DUMMY_CARD;
                                        model_cards.Image = DUMMY_CARD;
                                        user_cards.add(model_cards);

                                        group_model.groups_cards = user_cards;
                                    }

                                    groups_cardlist.add(group_model);
                                    model.groups_cards = groups_cardlist;

                                }

                            }
                            else {
                                for (int k = 0; k < group_array.length() ; k++) {
                                    CardModel group_model = new CardModel();
                                    ArrayList<CardModel> user_cards = new ArrayList<>();
                                    JSONObject cards_object = group_array.getJSONObject(k);

                                    String card_group = cards_object.optString("card_group");

                                    JSONArray cards_array = cards_object.getJSONArray("cards");

                                    for (int j = 0; j < cards_array.length() ; j++) {
                                        //                                JSONObject card_object = cards_array.getJSONObject(j);

                                        CardModel model_cards = new CardModel();
                                        model_cards.card_group = card_group;
                                        model_cards.Image = cards_array.getString(j);
                                        user_cards.add(model_cards);

                                        group_model.groups_cards = user_cards;
                                    }

                                    groups_cardlist.add(group_model);
                                    model.groups_cards = groups_cardlist;

                                }
                            }
                        }

                    }
                    else {

                        JSONArray group_array = json_user.optJSONArray("cards");

//                        if(group_array == null)
//                            return;

                        if(group_array == null)
                        {
                            for (int k = 0; k < 1 ; k++) {
                                CardModel group_model = new CardModel();
                                ArrayList<CardModel> user_cards = new ArrayList<>();

                                for (int j = 0; j < 13 ; j++) {

                                    CardModel model_cards = new CardModel();
                                    model_cards.card_group = DUMMY_CARD;
                                    model_cards.Image = DUMMY_CARD;
                                    user_cards.add(model_cards);

                                    group_model.groups_cards = user_cards;
                                }

                                groups_cardlist.add(group_model);
                                model.groups_cards = groups_cardlist;

                            }

                        }
                        else {
                            for (int k = 0; k < group_array.length() ; k++) {
                                CardModel group_model = new CardModel();
                                ArrayList<CardModel> user_cards = new ArrayList<>();
                                JSONObject cards_object = group_array.getJSONObject(k);

                                String card_group = cards_object.optString("card_group");

                                JSONArray cards_array = cards_object.getJSONArray("cards");

                                for (int j = 0; j < cards_array.length() ; j++) {
                                    //                                JSONObject card_object = cards_array.getJSONObject(j);

                                    CardModel model_cards = new CardModel();
                                    model_cards.card_group = card_group;
                                    model_cards.Image = cards_array.getString(j);
                                    user_cards.add(model_cards);

                                    group_model.groups_cards = user_cards;
                                }

                                groups_cardlist.add(group_model);
                                model.groups_cards = groups_cardlist;

                            }
                        }

                    }

                    game_users_cards_list.add(model);

                }

                Funtions.showDeclareDailog(this, game_users_cards_list, new Callback() {
                    @Override
                    public void Responce(String resp, String type, Bundle bundle) {


                        if(user_wallet_1 != -1 && (user_wallet_1 < min_entry))
                        {
                            ExitFromGames();
                            return;
                        }

                        if(resp.equalsIgnoreCase("startgame"))
                        {
                            API_CALL_start_game();
                        }


                    }
                });

            }

        }
        else if(code.equalsIgnoreCase("403"))
        {
            ExitFromGames();
        }
        else {
            game_id = jsonObject.optString("active_game_id");
            //            Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        }


    }


    private void ExitFromGames(){
        API_CALL_leave_table("0");
        StopSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        },500);
    }

    boolean isPayDialogShow = false;

    private void InitializeUsers
            (String user_id_player,JSONObject table_users,TextView txtPlay,TextView txtPlaywallet,ImageView imgplcircle,View lnrPlaywallet)
            throws JSONException {

        String table_id1 = table_users.getString(
                "table_id");
        final String name1 = table_users.getString(
                "name");
        user_id_player = table_users.getString(
                "user_id");
        String profile_pic1 = table_users.getString("profile_pic");
        String walletplayer2 = table_users.getString(
                "wallet");


        if (user_id_player.equals("0")) {

            txtPlay.setText("");
            txtPlaywallet.setVisibility(View.INVISIBLE);
            lnrPlaywallet.setVisibility(View.INVISIBLE);

            String uriuser2 = "@drawable/avatar";  // where myresource
            // " +
            int imageResourceuser2 = getResources().getIdentifier(uriuser2,
                    null,
                    getPackageName());

            LoadImage().load(imageResourceuser2).into(imgplcircle);
        }
        else {

            txtPlay.setText(name1);
            txtPlaywallet.setVisibility(View.VISIBLE);
            lnrPlaywallet.setVisibility(View.VISIBLE);

            float numberamount = Float.parseFloat(walletplayer2);
            txtPlaywallet.setText("" + NumberFormat.getNumberInstance(Locale.US).format(numberamount));

            LoadImage().load(Const.IMGAE_PATH + profile_pic1).into(imgplcircle);

        }

    }

    private void Parse_response(JSONObject jsonObject) throws JSONException {

        String code = jsonObject.optString("code");
        String message = jsonObject.optString("message");

        if(code.equalsIgnoreCase("200"))
        {
//            findViewById(R.id.iv_rightarrow).setVisibility(View.VISIBLE);
//            findViewById(R.id.iv_leftarrow).setVisibility(View.VISIBLE);
//            findViewById(R.id.iv_rightarrow).startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
//            findViewById(R.id.iv_leftarrow).startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));


            JSONArray cardsArray = jsonObject.optJSONArray("cards");

            if(cardsArray != null && cardsArray.length() > 0)
            {
                findViewById(R.id.rlt_dropgameview).setVisibility(View.GONE);
                isgamestarted = true;

                for (int i = 0; i < cardsArray.length() ; i++) {

                    JSONObject cardObject = cardsArray.getJSONObject(i);
                    CardModel model = new CardModel();
                    //                    model.card_id = cardObject.optString("id");

                    model.Image = cardObject.optString("card");

                    if(model.Image.substring(model.Image.length() - 1).equalsIgnoreCase("_"))
                    {
                        model.Image = removeLastChars(model.Image,1);
                    }
                    //                    Toast.makeText(context, ""+removeLastChars(model.Image,1), Toast.LENGTH_SHORT).show();

                    model.card_id = cardObject.optString("card");

//                        addCardsBahar(""+ model.Image,i);


                    model.card_group = cardObject.optString("card_group");

                    cardModelArrayList.add(model);
                }

                bt_sliptcard.setVisibility(View.GONE);
                SplitCardtoGroup();

            }
            else {
                ((TextView)findViewById(R.id.tv_gamemessage)).setText(""+getString(R.string.cards_getting_error));
                findViewById(R.id.rlt_dropgameview).setVisibility(View.VISIBLE);
            }

        }
        else {
            Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        }

    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }


    private void CreateGroupFromSelect(boolean isDrag){

        int count = 0;

        for (int i = 0; i < selectedcardvalue.size() ; i++) {
            Funtions.LOGE("MainActvity","\n"+selectedcardvalue.get(i).toString());
            CardModel model = selectedcardvalue.get(i);
            if(model.isSelected)
            {
                count++;
            }

        }

        if(count < 3 && !isDrag)
        {
            Toast.makeText(this, ""+getString(R.string.minimum_grouping), Toast.LENGTH_SHORT).show();
            return;
        }


        for (int i = 0; i < selectedcardvalue.size() ; i++) {

            CardModel model = selectedcardvalue.get(i);

            RemoveCardFromArrayLists(model.card_id);

        }

        if(ext_group1.size() == 0){
            ext_group1.addAll(selectedcardvalue);
        }
        else if(ext_group2.size() == 0){
            ext_group2.addAll(selectedcardvalue);
        }
        else if(ext_group3.size() == 0){
            ext_group3.addAll(selectedcardvalue);
        }
        else if(ext_group4.size() == 0){
            ext_group4.addAll(selectedcardvalue);
        }
        else if(ext_group5.size() == 0){
            ext_group5.addAll(selectedcardvalue);
        }

        else if(rs_cardlist_group1.size() == 0)
        {
            rs_cardlist_group1.addAll(selectedcardvalue);
        }
        else if(rp_cardlist_group2.size() == 0)
        {
            rp_cardlist_group2.addAll(selectedcardvalue);
        }
        else if(bl_cardlist_group3.size() == 0)
        {
            bl_cardlist_group3.addAll(selectedcardvalue);
        }
        else if(bp_cardlist_group4.size() == 0)
        {
            bp_cardlist_group4.addAll(selectedcardvalue);
        }
        else if(joker_cardlist_group5.size() == 0)
        {
            joker_cardlist_group5.addAll(selectedcardvalue);
        }

        ArrayList<ArrayList<CardModel>> templist = new ArrayList<>();

        if(rs_cardlist_group1.size() > 0)
        {
            templist.add(rs_cardlist_group1);
        }

        if(rp_cardlist_group2.size() > 0)
        {
            templist.add(rp_cardlist_group2);
        }

        if(bl_cardlist_group3.size() > 0)
        {
            templist.add(bl_cardlist_group3);
        }

        if(bp_cardlist_group4.size() > 0)
        {
            templist.add(bp_cardlist_group4);
        }

        if(joker_cardlist_group5.size() > 0)
        {
            templist.add(joker_cardlist_group5);
        }

        if(ext_group1.size() > 0)
        {
            templist.add(ext_group1);
        }

        if(ext_group2.size() > 0)
        {
            templist.add(ext_group2);
        }

        if(ext_group3.size() > 0)
        {
            templist.add(ext_group3);
        }

        if(ext_group4.size() > 0)
        {
            templist.add(ext_group4);
        }

        if(ext_group5.size() > 0)
        {
            templist.add(ext_group5);
        }


        //        for (int i = 0; i < templist.size() ; i++) {
        //
        //
        //            API_CALL_Sort_card_value(templist.get(i),templist.size(),i);
        //
        //        }

        loopgroupsize = 0;
        if(temp_grouplist != null)
            temp_grouplist.clear();

        temp_grouplist = templist;
        API_CALL_Sort_card_value(temp_grouplist.get(loopgroupsize),temp_grouplist.size(),loopgroupsize);


    }

    String group_params = "";

    private void API_CALL_Sort_card_value(final ArrayList<CardModel> arrayList, final int size, final int position) {

        AddSplit_to_layout();

//            HashMap params = new HashMap();
//            params.put("user_id",""+prefs.getString("user_id", ""));
//            params.put("token",""+prefs.getString("token", ""));
//
//            Submitcardslist = GetCardFromLayout();
//            params.put("json",""+Submitcardslist);
//
//            int count = 0;
//
//            group_params = "";
//
//            for (int i = 0; i < arrayList.size(); i++) {
//
//                CardModel model = arrayList.get(i);
//                count++;
//                String card_params = "card_" + count;
//
//                if(!group_params.equals(""))
//                    group_params = group_params + " , " +card_params +" : "+model.Image;
//                else
//                    group_params = card_params +" : "+model.Image;
//
//                params.put(card_params, model.Image);
//
//            }
//
////            Funtions.LOGE("Rummy5Player","CardValue : Group_"+CardValue(arrayList));
//
//
//            ApiRequest.Call_Api(this, Const.card_value, params, new Callback() {
//                @Override
//                public void Responce(String resp, String type, Bundle bundle) {
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(resp);
//                        String code,message;
//                        code = jsonObject.getString("code");
//                        message = jsonObject.getString("message");
//
//                        if (code.equalsIgnoreCase("200"))
//                        {
//
//                            for (int i = 0; i < arrayList.size() ; i++) {
//
//                                CardModel model = arrayList.get(i);
//
//                                int card_value = (int) jsonObject.getJSONArray("card_value").get(0);
//                                model.group_value_params = group_params;
//                                model.group_value_response = ""+card_value;
//
//                                if(card_value == IMPURE_SEQUENCE_VALUE)
//                                    model.group_value = IMPURE_SEQUENCE;
//                                else
//                                if(card_value == PURE_SEQUENCE_VALUE)
//                                    model.group_value = PURE_SEQUENCE;
//                                else if(card_value == SET_VALUE)
//                                    model.group_value = SET;
//                                else
//                                    model.group_value = INVALID;
//
//                                model.value_grp = card_value;
//
//                            }
//
//
//                        }
//                        else if(code.equals("406"))
//                        {
//
//                            InvalidGroup(arrayList);
//                        }
//                        else {
//
//                            InvalidGroup(arrayList);
//
//                            Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
//                        }
//
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//
//                        InvalidGroup(arrayList);
//
//                    }
//
//                    isGroupNameSet = false;
//
//                    try {
//                        if(loopgroupsize != size)
//                        {
//                            API_CALL_Sort_card_value(temp_grouplist.get(loopgroupsize),size,loopgroupsize);
//                            loopgroupsize++;
//                        }
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//                    if(position == (size - 1))
//                        AddSplit_to_layout();
//
//
//                }
//            });

    }

    public ArrayList<CardModel> CardValue(ArrayList<CardModel> arrayList) {

        int $rule = 0;
        int $value = 0;
        int group_size = arrayList.size();

        arrayList.get(0).group_value_params = group_params;
        arrayList.get(0).group_value_response = ""+$rule;

        arrayList.get(0).group_value = ""+getGroupStatus($rule);
        arrayList.get(0).group_points = ""+getGroupPoints(arrayList);
        arrayList.get(0).value_grp = $rule;

        arrayList =  setValueList(arrayList,$rule,arrayList.get(0).group_points,arrayList.get(0).group_value);

        if (group_size <= 2) {

            Collections.sort(arrayList, new Comparator<CardModel>() {
                @Override
                public int compare(CardModel o1, CardModel o2) {

                    int cardValue1 = Integer.parseInt(convertCardIntoNumber(o1.Image));
                    int cardValue2 = Integer.parseInt(convertCardIntoNumber(o2.Image));

                    return cardValue1 - cardValue2;
                }
            });

            Collections.reverse(arrayList);

            return arrayList;
        }

        if(joker_card.substring(joker_card.length() - 1).equalsIgnoreCase("_"))
        {
            joker_card = removeLastChars(joker_card,1);
        }

        String $joker_num = "" + joker_card.substring(2);

        String $set = "";
        String $color_group = "";

        String $card1_color = "";
        String $card2_color = "";
        String $card3_color = "";
        String $card4_color = "";
        String $card5_color = "";
        String $card6_color = "";
        String $card7_color = "";
        String $card8_color = "";
        String $card9_color = "";
        String $card10_color = "";

        String $card1_color_set = "";
        String $card2_color_set = "";
        String $card3_color_set = "";
        String $card4_color_set = "";
        String $card5_color_set = "";
        String $card6_color_set = "";
        String $card7_color_set = "";
        String $card8_color_set = "";
        String $card9_color_set = "";
        String $card10_color_set = "";

        String $card1_num = "-1";
        String $card2_num = "-1";
        String $card3_num = "-1";
        String $card4_num = "-1";
        String $card5_num = "-1";
        String $card6_num = "-1";
        String $card7_num = "-1";
        String $card8_num = "-1";
        String $card9_num = "-1";
        String $card10_num = "-1";

        String $card1_num_set = "-1";
        String $card2_num_set = "-1";
        String $card3_num_set = "-1";
        String $card4_num_set = "-1";
        String $card5_num_set = "-1";
        String $card6_num_set = "-1";
        String $card7_num_set = "-1";
        String $card8_num_set = "-1";
        String $card9_num_set = "-1";
        String $card10_num_set = "-1";

        for (int i = 0; i < group_size; i++) {
            CardModel cards_model = arrayList.get(i);


            String _cards_name = cards_model.Image;

            if(_cards_name.equalsIgnoreCase(DUMMY_CARD))
                continue;

            if(_cards_name.equalsIgnoreCase(MAIN_JOKER_CARD) || _cards_name.equalsIgnoreCase(MAIN_JOKER_CARD_2))
                _cards_name = joker_card;

            if (i == 0) {

                $card1_color = getColorCode(_cards_name);
                $card1_num = getCardNumber(_cards_name);
                $card1_color_set = $card1_color;
                $card1_num_set = $card1_num;
            }

            if (i == 1) {

                $card2_color = getColorCode(_cards_name);
                $card2_num = getCardNumber(_cards_name);
                $card2_color_set = $card2_color;
                $card2_num_set = $card2_num;
            }

            if (i == 2) {

                $card3_color = getColorCode(_cards_name);
                $card3_num = getCardNumber(_cards_name);
                $card3_color_set = $card3_color;
                $card3_num_set = $card3_num;

            }

            if (i == 3) {

                $card4_color = getColorCode(_cards_name);

                $card4_num = getCardNumber(_cards_name);

                $card4_color_set = $card4_color;
                $card4_num_set = $card4_num;
            }

            if (i == 4) {

                $card5_color = getColorCode(_cards_name);

                $card5_num = getCardNumber(_cards_name);

                $card5_color_set = $card5_color;
                $card5_num_set = $card5_num;

            }

            if (i == 5) {

                $card6_color = getColorCode(_cards_name);

                $card6_num = getCardNumber(_cards_name);

                $card6_color_set = $card6_color;
                $card6_num_set = $card6_num;

            }

            if (i == 6) {

                $card7_color = getColorCode(_cards_name);

                $card7_num = getCardNumber(_cards_name);

                $card7_color_set = $card7_color;
                $card7_num_set = $card7_num;

            }

            if (i == 7) {

                $card8_color = getColorCode(_cards_name);

                $card8_num = getCardNumber(_cards_name);

                $card8_color_set = $card8_color;
                $card8_num_set = $card8_num;

            }

            if (i == 8) {

                $card9_color = getColorCode(_cards_name);

                $card9_num = getCardNumber(_cards_name);

                $card9_color_set = $card9_color;
                $card9_num_set = $card9_num;

            }

            if (i == 9) {

                $card10_color = getColorCode(_cards_name);

                $card10_num = getCardNumber(_cards_name);

                $card10_color_set = $card10_color;
                $card10_num_set = $card10_num;

            }

        }

        // Check Wild Joker And Convert To Joker Card
        if($card1_num.equalsIgnoreCase($joker_num))
        {
            $card1_color = JOKER_CARD;
            $card1_num = "0";
            $card1_num_set = $card1_num;
        }
        if($card2_num.equalsIgnoreCase($joker_num))
        {
            $card2_color = JOKER_CARD;
            $card2_num = "0";
            $card2_num_set = $card2_num;
        }
        if($card3_num.equalsIgnoreCase($joker_num))
        {
            $card3_color = JOKER_CARD;
            $card3_num = "0";
            $card3_num_set = $card3_num;
        }
        if($card4_num.equalsIgnoreCase($joker_num))
        {
            $card4_color = JOKER_CARD;
            $card4_num = "0";
            $card4_num_set = $card4_num;

        }
        if($card5_num.equalsIgnoreCase($joker_num))
        {
            $card5_color = JOKER_CARD;
            $card5_num = "0";
            $card5_num_set = $card5_num;

        }
        if($card6_num.equalsIgnoreCase($joker_num))
        {
            $card6_color = JOKER_CARD;
            $card6_num = "0";
            $card6_num_set = $card6_num;

        }
        if($card7_num.equalsIgnoreCase($joker_num))
        {
            $card7_color = JOKER_CARD;
            $card7_num = "0";
            $card7_num_set = $card7_num;

        }
        if($card8_num.equalsIgnoreCase($joker_num))
        {
            $card8_color = JOKER_CARD;
            $card8_num = "0";
            $card8_num_set = $card8_num;

        }
        if($card9_num.equalsIgnoreCase($joker_num))
        {
            $card9_color = JOKER_CARD;
            $card9_num = "0";
            $card9_num_set = $card9_num;

        }
        if($card10_num.equalsIgnoreCase($joker_num))
        {
            $card10_color = JOKER_CARD;
            $card10_num = "0";
            $card10_num_set = $card10_num;

        }
        // END Check Wild Joker And Convert To Joker Card


// Color Group Code
        if (!$card1_color.equalsIgnoreCase(JOKER_CARD)) {
            $set = $card1_num_set;
            $color_group = $card1_color;
        } else if (!$card2_color.equalsIgnoreCase(JOKER_CARD)) {
            $set = $card2_num_set;
            $color_group = $card2_color;
        } else if (!$card3_color.equalsIgnoreCase(JOKER_CARD)) {
            $set = $card3_num_set;
            $color_group = $card3_color;
        } else if (!$card4_color.equalsIgnoreCase(JOKER_CARD)) {
            $set = $card4_num_set;
            $color_group = $card4_color;
        } else if (!$card5_color.equalsIgnoreCase(JOKER_CARD)) {
            $set = $card5_num_set;
            $color_group = $card5_color;
        } else {
            $set = $card6_num_set;
            $color_group = $card6_color;
        }
        //END Color Group Cod

        // Convert Joker to Vurtual Card
        int $joker_count = 0;
        if ($card1_color.equalsIgnoreCase(JOKER_CARD)) {
            $card1_num_set = $set;
            $card1_color = $color_group;
            $card1_color_set = "";
            $joker_count++;
        }

        if ($card2_color.equalsIgnoreCase(JOKER_CARD)) {
            $card2_num_set = $set;
            $card2_color = $color_group;
            $card2_color_set = "";

            $joker_count++;
        }

        if ($card3_color.equalsIgnoreCase(JOKER_CARD)) {
            $card3_num_set = $set;
            $card3_color = $color_group;
            $card3_color_set = "";

            $joker_count++;
        }

        if ($card4_color.equalsIgnoreCase(JOKER_CARD)) {
            $card4_num_set = $set;
            $card4_color = $color_group;
            $card4_color_set = "";

            $joker_count++;
        }

        if ($card5_color.equalsIgnoreCase(JOKER_CARD)) {
            $card5_num_set = $set;
            $card5_color = $color_group;
            $card5_color_set = "";

            $joker_count++;
        }

        if ($card6_color.equalsIgnoreCase(JOKER_CARD)) {
            $card6_num_set = $set;
            $card6_color = $color_group;
            $card6_color_set = "";

            $joker_count++;
        }

        if ($card7_color.equalsIgnoreCase(JOKER_CARD)) {
            $card7_num_set = $set;
            $card7_color = $color_group;
            $card7_color_set = "";

            $joker_count++;
        }

        if ($card8_color.equalsIgnoreCase(JOKER_CARD)) {
            $card8_num_set = $set;
            $card8_color = $color_group;
            $card8_color_set = "";

            $joker_count++;
        }

        if ($card9_color.equalsIgnoreCase(JOKER_CARD)) {
            $card9_num_set = $set;
            $card9_color = $color_group;
            $card9_color_set = "";

            $joker_count++;
        }

        if ($card10_color.equalsIgnoreCase(JOKER_CARD)) {
            $card10_num_set = $set;
            $card10_color = $color_group;
            $card10_color_set = "";

            $joker_count++;
        }

        //END Convert Joker to Vurtual Card

        $card1_num_set = ConvertSpecialtoNumber($card1_num_set);
        $card2_num_set = ConvertSpecialtoNumber($card2_num_set);
        $card3_num_set = ConvertSpecialtoNumber($card3_num_set);
        $card4_num_set = ConvertSpecialtoNumber($card4_num_set);
        $card5_num_set = ConvertSpecialtoNumber($card5_num_set);
        $card6_num_set = ConvertSpecialtoNumber($card6_num_set);
        $card7_num_set = ConvertSpecialtoNumber($card7_num_set);
        $card8_num_set = ConvertSpecialtoNumber($card8_num_set);
        $card9_num_set = ConvertSpecialtoNumber($card9_num_set);
        $card10_num_set = ConvertSpecialtoNumber($card10_num_set);

        int numberOne = Integer.parseInt($card1_num_set);
        int numberTwo = Integer.parseInt($card2_num_set);
        int numberThree = Integer.parseInt($card3_num_set);
        int numberFour = Integer.parseInt($card4_num_set);
        int numberFive = Integer.parseInt($card5_num_set);
        int numberSix = Integer.parseInt($card6_num_set);
        int numberSeven = Integer.parseInt($card7_num_set);
        int numberEight = Integer.parseInt($card8_num_set);
        int numberNine = Integer.parseInt($card9_num_set);
        int numberTen = Integer.parseInt($card10_num_set);

        int CardNumberArray[] = {numberOne,numberTwo,numberThree,numberFour,numberFive,numberSix,numberSeven,numberEight,numberNine,numberTen};
        String CardColorArray[] = {$card1_color_set,$card2_color_set,$card3_color_set,$card4_color_set,$card5_color_set,$card6_color_set,$card7_color_set,$card8_color_set,$card9_color_set,$card10_color_set};

        List<Integer> numlist = new ArrayList<Integer>();
        for(int i= 0;i<CardNumberArray.length;i++)
        {
            if(CardNumberArray[i] >= 0)
            {
                numlist.add(CardNumberArray[i]);
            }

        }

        boolean isColorMatch = false;
        boolean isset = false;
        outerloop:
        for (int i = 0; i < CardColorArray.length; i++) {
            for (int j = i+1; j < CardColorArray.length; j++) {
                // compare list.get(i) and list.get(j)

                if((CardColorArray[i].equalsIgnoreCase(CardColorArray[j])
                        && (Funtions.checkisStringValid(CardColorArray[i]) || Funtions.checkisStringValid(CardColorArray[j])))) {
                    isColorMatch = true;
                    break outerloop;
                }
                else {
                    isColorMatch = false;
                }

            }
        }

        if(!isColorMatch)
        {
            outerloop1:
            for (int i = 0; i < numlist.size(); i++) {

                int card_number = numlist.get(i);

                for (int j = i+1; j < numlist.size(); j++) {
                    // compare list.get(i) and list.get(j)

                    int card_number1  = numlist.get(j);


                    if(card_number == card_number1) {
                        isset = true;
                    }
                    else {
                        isset = false;
                        break outerloop1;
                    }

                }
            }
        }

        if(isset)
        {
            $rule = 6;
        }
        else {

            boolean $color = false;

            $color = false;

            String[] CardColorArray1 = {$card1_color,$card2_color,$card3_color,$card4_color,$card5_color,$card6_color,$card7_color,$card8_color,$card9_color,$card10_color};

            List<String> colorlist = new ArrayList<String>();
            for(int i= 0;i<CardColorArray1.length;i++)
            {
                if(Funtions.checkisStringValid(CardColorArray1[i]))
                {
                    colorlist.add(CardColorArray1[i]);
                }

            }

            outerloop3:
            for (int i = 0; i < colorlist.size() ; i++) {

                String card_color = colorlist.get(i);

                for (int j = 0; j < colorlist.size(); j++) {

                    String card_color2 = colorlist.get(j);


                    if(card_color.equalsIgnoreCase(card_color2))
                    {
                        $color = true;
                    }
                    else {
                        $color = false;
                        break outerloop3;
                    }

                }

            }

//                if (($card1_color.equalsIgnoreCase($card2_color)) && ($card2_color.equalsIgnoreCase($card3_color)))
//                {
//
//                    if (Funtions.checkisStringValid($card6_color) && !$card5_color.equalsIgnoreCase($card6_color)) {
//                        return $value;
//                    } else if (Funtions.checkisStringValid($card5_color) && !$card4_color.equalsIgnoreCase($card5_color)) {
//                        return $value;
//                    } else if (Funtions.checkisStringValid($card4_color) && !$card3_color.equalsIgnoreCase($card4_color)) {
//                        return $value;
//                    }
//
//                    $color = true;
//
//                }
//                else {
//                    return $value;
//                }

            $card1_num = ConvertSpecialtoNumber($card1_num);
            $card2_num = ConvertSpecialtoNumber($card2_num);
            $card3_num = ConvertSpecialtoNumber($card3_num);
            $card4_num = ConvertSpecialtoNumber($card4_num);
            $card5_num = ConvertSpecialtoNumber($card5_num);
            $card6_num = ConvertSpecialtoNumber($card6_num);
            $card7_num = ConvertSpecialtoNumber($card7_num);
            $card8_num = ConvertSpecialtoNumber($card8_num);
            $card9_num = ConvertSpecialtoNumber($card9_num);
            $card10_num = ConvertSpecialtoNumber($card10_num);

            numberOne = Integer.parseInt($card1_num);
            numberTwo = Integer.parseInt($card2_num);
            numberThree = Integer.parseInt($card3_num);
            numberFour = Integer.parseInt($card4_num);
            numberFive = Integer.parseInt($card5_num);
            numberSix = Integer.parseInt($card6_num);
            numberSeven = Integer.parseInt($card7_num);
            numberEight = Integer.parseInt($card8_num);
            numberNine = Integer.parseInt($card9_num);
            numberTen = Integer.parseInt($card10_num);

            int CardNumberArray1[] = {numberOne,numberTwo,numberThree,numberFour,numberFive,numberSix,numberSeven,numberEight,numberNine,numberTen};

            numlist = new ArrayList<Integer>();
            for(int i= 0;i<CardNumberArray1.length;i++)
            {
                if(CardNumberArray1[i] >= 0)
                {
                    numlist.add(CardNumberArray1[i]);
                }

            }

            Integer $arr[] = numlist.toArray(new Integer[numlist.size()]);


//                int $arr[];
//                if (isset(numberSix)) {
//                    $arr = new int[]{numberOne, numberTwo, numberThree, numberFour, numberFive, numberSix};
//                }
//                else if (isset(numberFive)) {
//                    $arr = new int[]{numberOne, numberTwo, numberThree, numberFour, numberFive};
//                }
//                else if (isset(numberFour)) {
//                    $arr = new int[]{numberOne, numberTwo, numberThree, numberFour};
//                }
//                else {
//                    $arr = new int[]{numberOne, numberTwo, numberThree};
//                }


            Arrays.sort($arr);

            boolean $sequence = false;
            int $ace_joker_count = $joker_count;
            int $total_card = $arr.length;

            for (int j = 0; j < $arr.length - 1; j++) {

                int $val = $arr[j];

                if($val!=0 && $total_card>(j+1))
                {
                    if ($arr[j] + 1 == $arr[j + 1]) {
                        // Not sequential
                        $sequence = true;

                    }
                    else if(($val + 2) == $arr[j+1] && $joker_count> 0)
                    {
                        $joker_count--;
                        $sequence = true;
                    }
                    else {
                        $sequence = false;
                        break;
                    }
                }


            }

            if ($sequence && $color) {
                $value = $arr[0];
                $rule = ($value == 0) ? 4 : 5;
            }

            if ($rule == 0) {

                if(in_array(14,$arr))
                {
                    $arr = str_replace(14,1,$arr);
                    Arrays.sort($arr);

                    for (int j = 0; j < $arr.length - 1; j++) {

                        int $val = $arr[j];

                        if($val!=0 && $total_card>(j+1))
                        {
                            if ($arr[j] + 1 == $arr[j + 1]) {
                                // Not sequential
                                $sequence = true;

                            }
                            else if(($val + 2) == $arr[j+1] && $ace_joker_count> 0)
                            {
                                $ace_joker_count--;
                                $sequence = true;
                            }
                            else {
                                $sequence = false;
                                break;
                            }
                        }


                    }


                }

                if ($sequence && $color) {
                    $value = $arr[0];
                    $rule = ($value == 0) ? 4 : 5;
                }

            }

        }

        if(!isset)
        {
            Collections.sort(arrayList, new Comparator<CardModel>() {
                @Override
                public int compare(CardModel o1, CardModel o2) {

                    int cardValue1 = Integer.parseInt(convertCardIntoNumber(o1.Image));
                    int cardValue2 = Integer.parseInt(convertCardIntoNumber(o2.Image));

                    return cardValue1 - cardValue2;
                }
            });

            Collections.reverse(arrayList);

        }

        arrayList.get(0).group_value_params = group_params;
        arrayList.get(0).group_value_response = ""+$rule;

        String card_group_value = getGroupStatus($rule);

        if(card_group_value.equals(INVALID))
        {
            arrayList.get(0).group_points = ""+getGroupPoints(arrayList);
            arrayList.get(0).group_value = ""+card_group_value;
        }
        else
        {
            arrayList.get(0).group_points = ""+card_group_value;
            arrayList.get(0).group_value = card_group_value;
        }

        arrayList.get(0).value_grp = $rule;

        arrayList = setValueList(arrayList,$rule,arrayList.get(0).group_points,arrayList.get(0).group_value);

        return arrayList;
    }

    private ArrayList<CardModel> setValueList(final ArrayList<CardModel> arrayList,int $rule,String group_points,String group_value){

        for (int i = 0; i < arrayList.size() ; i++) {

            CardModel model = arrayList.get(i);

            model.group_value_params = group_params;
            model.group_value_response = ""+$rule;

            model.group_value = ""+group_value;
            model.group_points = ""+group_points;
            model.value_grp = $rule;

        }

        return  arrayList;
    }

    private String convertCardIntoNumber(String current_card){


        String $joker_num = "" + joker_card.substring(2);

        if(current_card.equalsIgnoreCase(MAIN_JOKER_CARD) || current_card.equalsIgnoreCase(MAIN_JOKER_CARD_2))
            current_card = joker_card;



        String $card_num = getCardNumber(current_card);


        if($card_num.equalsIgnoreCase($joker_num))
        {
            $card_num = "0";
        }


        $card_num = ConvertSpecialtoNumber($card_num);



        return $card_num;
    }

    private String getGroupStatus(int card_value){

        String group_value = INVALID;

        if(card_value == IMPURE_SEQUENCE_VALUE)
            group_value = IMPURE_SEQUENCE;
        else
        if(card_value == PURE_SEQUENCE_VALUE)
            group_value = PURE_SEQUENCE;
        else if(card_value == SET_VALUE)
            group_value = SET;
        else
            group_value = INVALID;

        return group_value;
    }

    private int getGroupPoints(final ArrayList<CardModel> arrayList){
        int $sum = 0;
        String $joker_num = "" + joker_card.substring(2);
        for (int i = 0; i < arrayList.size() ; i++) {

            String current_card = arrayList.get(i).Image;

            if(current_card.equalsIgnoreCase(DUMMY_CARD))
                continue;

            if(current_card.equalsIgnoreCase(MAIN_JOKER_CARD) || current_card.equalsIgnoreCase(MAIN_JOKER_CARD_2))
                current_card = joker_card;


            String $card2_color = getColorCode(current_card);
            String $card2_num = getCardNumber(current_card);


            if($card2_num.equalsIgnoreCase($joker_num))
            {
                $card2_color = JOKER_CARD;
                $card2_num = "0";
            }


            $card2_num = ConvertSpecialtoNumber($card2_num);


            int card2 = Integer.parseInt($card2_num);

            if(card2 > 10)
                card2 = 10;

            $sum = $sum+card2;

        }

        return $sum;
    }

    private Integer[] str_replace(int value,int replace,Integer[] array)
    {

        for (int i = 0; i < array.length ; i++) {

            if(array[i] == value)
            {
                array[i] = replace;
            }

        }

        return array;
    }


    private boolean in_array(int value,Integer[] array)
    {
        boolean isAvailable = false;

        for (int i = 0; i < array.length ; i++) {

            if(array[i] == value)
            {
                isAvailable = true;
                break;
            }

        }

        return isAvailable;
    }

    private boolean isset(int $card_num) {

        if($card_num >= 0)
            return true;

        return false;
    }

    private String getColorCode(String card_name){
        return card_name.substring(0, 2);
    }

    private String getCardNumber(String card_name){
        return card_name.substring(2);
    }


    private String ConvertSpecialtoNumber(String $card1_num_set){

        $card1_num_set = $card1_num_set.equalsIgnoreCase("J") ? "11" : $card1_num_set;
        $card1_num_set = $card1_num_set.equalsIgnoreCase("Q") ? "12" : $card1_num_set;
        $card1_num_set = $card1_num_set.equalsIgnoreCase("K") ? "13" : $card1_num_set;
        $card1_num_set = $card1_num_set.equalsIgnoreCase("A") ? "14" : $card1_num_set;


        return $card1_num_set;
    }

    private void InvalidGroup(ArrayList<CardModel> arrayList){
        for (int i = 0; i < arrayList.size() ; i++) {

            CardModel model = arrayList.get(i);

            int card_value = 0;

            model.group_value = INVALID;

            model.value_grp = card_value;
            model.group_value_params = group_params;
            model.group_value_response = ""+card_value;

        }

    }

    private void API_CALL_drop_card(final ArrayList<CardModel> arrayList, final int countnumber) {

        if(!isMyChaal)
        {
            Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();

//                if(isViewonTouch)
//                {
            setTouchViewVisible(true);
            ResetCardtoPosition(arrayList,countnumber);
//                }

            return;
        }


        removeCardFromGroup(selectedpatti_id);

        HashMap params = new HashMap();


        //        if(selectedpatti.substring(selectedpatti.length() - 1).equalsIgnoreCase("_"))
        //        {
        //            selectedpatti = removeLastChars(selectedpatti,1);
        //        }

        params.put("card",""+selectedpatti_id);
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        Submitcardslist = GetCardFromLayout();
        params.put("json",""+Submitcardslist);

        //        RemoveCardFromArray();




        ApiRequest.Call_Api(this, Const.drop_card, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code,message;
                    code = jsonObject.getString("code");
                    message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("200"))
                    {
                        isCardPick = false;
                        DrobButtonVisible(true);
                        isViewonTouch = false;
                        bt_discard.setVisibility(View.GONE);
                        DeclareButtonVisible(false);
//                            removeCardFromGroup(selectedpatti_id);


                    }
                    else {


                        isFinishDesk = false;
                        restoreRemoveCard();

//                            setTouchViewVisible(true);
//                            ResetCardtoPosition(arrayList, countnumber);

                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                    if(isViewonTouch)
                    {
                        setTouchViewVisible(true);
                        isViewonTouch = false;

                        isFinishDesk = false;
                        restoreRemoveCard();
                        ResetCardtoPosition(arrayList,countnumber);
                    }
                }

                API_CALL_status();

            }
        });


        //        cardListAdapter.notifyDataSetChanged();

    }

    private void setTouchViewVisible(boolean visible){

        if(_view != null)
            _view.setVisibility(visible ? View.VISIBLE : View.GONE);

    }

    private void removeCardFromGroup(String selectedpatti_id) {

        if (ext_group1.size() > 0) {
            RemoveCard(selectedpatti_id, ext_group1);
        }

        if (ext_group2.size() > 0) {
            RemoveCard(selectedpatti_id, ext_group2);
        }

        if (ext_group3.size() > 0) {
            RemoveCard(selectedpatti_id, ext_group3);
        }

        if (ext_group4.size() > 0) {
            RemoveCard(selectedpatti_id, ext_group4);
        }

        if (ext_group5.size() > 0) {
            RemoveCard(selectedpatti_id, ext_group5);
        }


        if (rs_cardlist_group1.size() > 0) {
            RemoveCard(selectedpatti_id, rs_cardlist_group1);
        }

        if (rp_cardlist_group2.size() > 0) {
            RemoveCard(selectedpatti_id, rp_cardlist_group2);
        }

        if (bl_cardlist_group3.size() > 0) {
            RemoveCard(selectedpatti_id, bl_cardlist_group3);
        }

        if (bp_cardlist_group4.size() > 0) {
            RemoveCard(selectedpatti_id, bp_cardlist_group4);
        }

        if (joker_cardlist_group5.size() > 0) {
            RemoveCard(selectedpatti_id, joker_cardlist_group5);
        }

//                            AddSplit_to_layout();

        GetGroupValueFromTouch(animation_type);

    }

    private boolean getCardApiCalling = false;
    private void API_CALL_get_card() {

        if(getCardApiCalling)
            return;

        getCardApiCalling = true;



//        CardModel dummy_card_model = new CardModel();
//        dummy_card_model.card_id = DUMMY_CARD;
//        dummy_card_model.Image = DUMMY_CARD;
//        AddCardinEmptyList(dummy_card_model);
//
////                            AddSplit_to_layout();
//        animation_type = "pick";
//        GetGroupValueFromTouch(animation_type);


        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        ApiRequest.Call_Api(this, Const.get_card, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code,message;
                    code = jsonObject.getString("code");
                    message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("200"))
                    {
                        isCardPick = true;
                        DrobButtonVisible(false);

                        rlt_addcardview.removeAllViews();
                        JSONArray drop_card = jsonObject.optJSONArray("card");
                        JSONObject cardObject = drop_card.getJSONObject(0);
                        String card = cardObject.getString("cards");
                        String card_id = cardObject.optString("id");


                        CardModel model = new CardModel();
                        model.Image = card;

                        if(model.Image.substring(model.Image.length() - 1).equalsIgnoreCase("_"))
                        {
                            model.Image = removeLastChars(model.Image,1);
                        }

                        model.card_id = card;

                        RemoveCardFromArrayLists(DUMMY_CARD);
                        AddCardinEmptyList(model);
                        animation_type = "pick";

//                            AddSplit_to_layout();


                    }
                    else {
                        animation_type = "erro_pick";
                    }
                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();


                }
                catch (JSONException e) {
                    e.printStackTrace();
                    animation_type = "erro_pick";
                    getCardApiCalling = false;
                    RemoveCardFromArrayLists(DUMMY_CARD);
                }

                RemoveCardFromArrayLists(DUMMY_CARD);
                GetGroupValueFromTouch(animation_type);
                getCardApiCalling = false;
                API_CALL_status();


            }
        });



        //        cardListAdapter.notifyDataSetChanged();

    }

    private void AddCardinEmptyList(CardModel model){
        if (ext_group1.size() == 0) {
            ext_group1.add(model);
        } else if (ext_group2.size() == 0) {
            ext_group2.add(model);
        } else if (ext_group3.size() == 0) {
            ext_group3.add(model);
        } else if (ext_group4.size() == 0) {
            ext_group4.add(model);
        } else if (ext_group5.size() == 0) {
            ext_group5.add(model);
        }

        else if (rs_cardlist_group1.size() == 0) {
            rs_cardlist_group1.add(model);
        } else if (rp_cardlist_group2.size() == 0) {
            rp_cardlist_group2.add(model);
        } else if (bl_cardlist_group3.size() == 0) {
            bl_cardlist_group3.add(model);
        } else if (bp_cardlist_group4.size() == 0) {
            bp_cardlist_group4.add(model);
        } else if (joker_cardlist_group5.size() == 0) {
            joker_cardlist_group5.add(model);
        }
    }

    private void API_CALL_get_drop_card() {

        HashMap params = new HashMap();
        params.put("user_id",""+prefs.getString("user_id", ""));
        params.put("token",""+prefs.getString("token", ""));

        Submitcardslist = GetCardFromLayout();

        params.put("json",""+Submitcardslist);


        ApiRequest.Call_Api(this, Const.get_drop_card, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                Log.v("get_card" , " "+resp);

                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code,message;
                    code = jsonObject.getString("code");
                    message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("200"))
                    {
                        isCardPick = true;
                        DrobButtonVisible(false);

                        rlt_addcardview.removeAllViews();
                        JSONArray drop_card = jsonObject.optJSONArray("card");
                        JSONObject cardObject = drop_card.getJSONObject(0);
                        String card = cardObject.getString("card");
                        String card_id = cardObject.optString("id");

                        CardModel model = new CardModel();
                        model.Image = card;

                        if(model.Image.substring(model.Image.length() - 1).equalsIgnoreCase("_"))
                        {
                            model.Image = removeLastChars(model.Image,1);
                        }

                        model.card_id = card;


                        AddCardinEmptyList(model);

                        animation_type = "drop_pick";
                        GetGroupValueFromTouch(animation_type);
//                            AddSplit_to_layout();

                    }
                    else {
                    }
                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                API_CALL_status();


            }
        });


        //        cardListAdapter.notifyDataSetChanged();

    }

    private void RemoveCardFromArray() {

        if(isSplit)
        {

            if(rs_cardlist_group1.size() > 0)
            {

                for (Iterator<CardModel> it = rs_cardlist_group1.iterator(); it.hasNext();) {
                    if (it.next().isSelected) {
                        it.remove();
                    }
                }
            }

            if(rp_cardlist_group2.size() > 0)
            {
                for (Iterator<CardModel> it = rp_cardlist_group2.iterator(); it.hasNext();) {
                    if (it.next().isSelected) {
                        it.remove();
                    }
                }
            }

            if(bl_cardlist_group3.size() > 0)
            {
                for (Iterator<CardModel> it = bl_cardlist_group3.iterator(); it.hasNext();) {
                    if (it.next().isSelected) {
                        it.remove();
                    }
                }
            }

            if(bp_cardlist_group4.size() > 0)
            {
                for (Iterator<CardModel> it = bp_cardlist_group4.iterator(); it.hasNext();) {
                    if (it.next().isSelected) {
                        it.remove();
                    }
                }
            }

            if(joker_cardlist_group5.size() > 0)
            {
                for (Iterator<CardModel> it = joker_cardlist_group5.iterator(); it.hasNext();) {
                    if (it.next().isSelected) {
                        it.remove();
                    }
                }
            }

        }
        else {


            for (Iterator<CardModel> it = cardModelArrayList.iterator(); it.hasNext();) {
                if (it.next().isSelected) {
                    it.remove();
                }
            }

            //            for (int i = 0; i < arraysize ; i++) {
            //                CardModel model = cardModelArrayList.get(i);
            //                if(model.isSelected)
            //                {
            //                    cardModelArrayList.remove(model);
            //                    removearray = removearray - 1;
            ////                    count++;
            //                    String card_params = "card_"+count;
            ////                    params.put(card_params,model.Image);
            //                }
            //
            //            }
        }

    }

    private void RemoveCard(String card_value,ArrayList<CardModel> modelArray){

        for (int i = 0; i < modelArray.size() ; i++) {
            CardModel model = modelArray.get(i);
            if (model.card_id.equalsIgnoreCase(card_value))
            {
                removemodel = model;
                removeCardList = new ArrayList<>(modelArray);
                modelArray.remove(model);
            }

        }

    }

    private void restoreRemoveCard(){

        outer :
        for (int i = 0; i < removeCardList.size() ; i++) {

            CardModel removemodel = removeCardList.get(i);

            boolean found = false;
            for (CardModel model : ext_group1) {
                if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                    found = true;
                    break;
                }
            }

            if (found)
            {
                ext_group1.clear();
                ext_group1.addAll(removeCardList);
                break outer;
            }
            else
            {
                for (CardModel model : ext_group2) {
                    if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                        found = true;
                        break;
                    }
                }

                if (found)
                {
                    ext_group2.clear();
                    ext_group2.addAll(removeCardList);
                    break outer;
                }
                else
                {

                    for (CardModel model : ext_group3) {
                        if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                            found = true;
                            break;
                        }
                    }

                    if (found)
                    {
                        ext_group3.clear();
                        ext_group3.addAll(removeCardList);
                        break outer;
                    }
                    else
                    {

                        for (CardModel model : ext_group4) {
                            if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                found = true;
                                break;
                            }
                        }


                        if (found)
                        {
                            ext_group4.clear();
                            ext_group4.addAll(removeCardList);
                            break outer;
                        }
                        else
                        {
                            for (CardModel model : ext_group5) {
                                if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                    found = true;
                                    break ;
                                }
                            }


                            if (found)
                            {
                                ext_group5.clear();
                                ext_group5.addAll(removeCardList);
                                break outer;
                            }
                            else
                            {
                                for (CardModel model : rs_cardlist_group1) {
                                    if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                        found = true;
                                        break;
                                    }
                                }


                                if (found)
                                {
                                    rs_cardlist_group1.clear();
                                    rs_cardlist_group1.addAll(removeCardList);
                                    break outer;
                                }
                                else
                                {
                                    for (CardModel model : rp_cardlist_group2) {
                                        if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                            found = true;
                                            break;
                                        }
                                    }


                                    if (found)
                                    {
                                        rp_cardlist_group2.clear();
                                        rp_cardlist_group2.addAll(removeCardList);
                                        break outer;
                                    }
                                    else
                                    {
                                        for (CardModel model : bl_cardlist_group3) {
                                            if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                                found = true;
                                                break;
                                            }
                                        }


                                        if (found)
                                        {
                                            bl_cardlist_group3.clear();
                                            bl_cardlist_group3.addAll(removeCardList);
                                            break outer;
                                        }
                                        else
                                        {
                                            for (CardModel model : bp_cardlist_group4) {
                                                if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                                    found = true;
                                                    break;
                                                }
                                            }


                                            if (found)
                                            {
                                                bp_cardlist_group4.clear();
                                                bp_cardlist_group4.addAll(removeCardList);
                                                break outer;
                                            }
                                            else
                                            {
                                                for (CardModel model : joker_cardlist_group5) {
                                                    if (removemodel.card_id.equalsIgnoreCase(model.card_id)) {
                                                        found = true;
                                                        break;
                                                    }
                                                }


                                                if (found)
                                                {
                                                    joker_cardlist_group5.clear();
                                                    joker_cardlist_group5.addAll(removeCardList);
                                                    break outer;
                                                }
                                                else if(i == (removeCardList.size() - 1)) {

                                                    if(rs_cardlist_group1.size() == 0)
                                                    {
                                                        rs_cardlist_group1.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(rp_cardlist_group2.size() == 0)
                                                    {
                                                        rp_cardlist_group2.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(bl_cardlist_group3.size() == 0)
                                                    {
                                                        bl_cardlist_group3.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(bp_cardlist_group4.size() == 0)
                                                    {
                                                        bp_cardlist_group4.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(joker_cardlist_group5.size() == 0)
                                                    {
                                                        joker_cardlist_group5.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else
                                                    if(ext_group1.size() == 0){
                                                        ext_group1.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(ext_group2.size() == 0){
                                                        ext_group2.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(ext_group3.size() == 0){
                                                        ext_group3.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(ext_group4.size() == 0){
                                                        ext_group4.addAll(removeCardList);
                                                        break outer;
                                                    }
                                                    else if(ext_group5.size() == 0){
                                                        ext_group5.addAll(removeCardList);
                                                        break outer;
                                                    }

                                                }

                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }

                }
            }


        }

        removeCardList.clear();
        animation_type = "restore_animation";
        GetGroupValueFromTouch(animation_type);
    }

    public void complategameUIChange(){

        Submitcardslist = GetCardFromLayout();

        rs_cardlist_group1.clear();
        rp_cardlist_group2.clear();
        bl_cardlist_group3.clear();
        bp_cardlist_group4.clear();
        joker_cardlist_group5.clear();
        ext_group1.clear();
        ext_group2.clear();
        ext_group3.clear();
        ext_group4.clear();
        ext_group5.clear();
        selectedcardvalue.clear();
        grouplist.clear();
        if(removeCardList != null)
            removeCardList.clear();
        rlt_addcardview.removeAllViews();
        bt_sliptcard.setVisibility(View.GONE);
        bt_drop.setVisibility(View.GONE);
        bt_declare.setVisibility(View.GONE);
        cardPref.edit().putString(Pref_cards+game_id,"").apply();
    }

    boolean isMyChaal = false;
    public void makeHightLightForChaal(String chaal_user_id) {

        if(chaal_user_id.equals(prefs.getString("user_id","")))
        {

            isMyChaal = true;

            findViewById(R.id.rlt_highlighted_pick).setVisibility(View.VISIBLE);
            findViewById(R.id.rlt_highlighted_gadhi).setVisibility(View.VISIBLE);

            findViewById(R.id.rlt_highlighted_pick).startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
            findViewById(R.id.rlt_highlighted_gadhi).startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        }
        else {
            isMyChaal = false;

            findViewById(R.id.rlt_highlighted_pick).setVisibility(View.INVISIBLE);
            findViewById(R.id.rlt_highlighted_gadhi).setVisibility(View.INVISIBLE);

            findViewById(R.id.rlt_highlighted_pick).clearAnimation();
            findViewById(R.id.rlt_highlighted_gadhi).clearAnimation();


        }

        if (chaal_user_id.equals(user_id_player1)) {

            if (isProgressrun1) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer1.start();
                isProgressrun1 = false;
                PlaySaund(R.raw.buttontouchsound);
            }

            highlight("1");

        }
        else if (chaal_user_id.equals(user_id_player2)) {

            if (isProgressrun2) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer2.start();
                isProgressrun2 = false;
                PlaySaund(R.raw.buttontouchsound);

            }

            highlight("2");
        }
        else if (chaal_user_id.equals(user_id_player3)) {

            if (isProgressrun3) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer3.start();
                isProgressrun3 = false;
                PlaySaund(R.raw.buttontouchsound);

            }

            highlight("3");
        }
        else if (chaal_user_id.equals(user_id_player4)) {

            if (isProgressrun4) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer4.start();
                isProgressrun4 = false;
                PlaySaund(R.raw.buttontouchsound);

            }

            highlight("4");

        }
        else if (chaal_user_id.equals(user_id_player5)) {

            if (isProgressrun5) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer5.start();
                isProgressrun5 = false;
                PlaySaund(R.raw.buttontouchsound);

            }


            highlight("5");

        }
        else if (chaal_user_id.equals(user_id_player6)) {

            if (isProgressrun6) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer6.start();
                isProgressrun6 = false;
                PlaySaund(R.raw.buttontouchsound);

            }

            highlight("6");

        }
        else if (chaal_user_id.equals(user_id_player7)) {

            if (isProgressrun7) {

                pStatus = 100;
                pStatusprogress = 0;
                mCountDownTimer7.start();
                isProgressrun7 = false;
                PlaySaund(R.raw.buttontouchsound);

            }

            highlight("7");

        }

    }

    public void highlight(String player){

        if(!player.equals("1"))
        {
            isProgressrun1 = true;
            Player1CancelCountDown();
            mProgress1.setProgress(0);
            imgpl1glow.setVisibility(View.GONE);
        }


        if(!player.equals("2"))
        {
            isProgressrun2 = true;
            mCountDownTimer2.cancel();
            mProgress2.setProgress(0);
            imgpl2glow.setVisibility(View.GONE);
        }


        if(!player.equals("3"))
        {
            isProgressrun3 = true;
            mCountDownTimer3.cancel();
            mProgress3.setProgress(0);
            imgpl3glow.setVisibility(View.GONE);
        }


        if(!player.equals("4"))
        {
            isProgressrun4 = true;
            mCountDownTimer4.cancel();
            mProgress4.setProgress(0);
            imgpl4glow.setVisibility(View.GONE);
        }


        if(!player.equals("5"))
        {
            isProgressrun5 = true;
            mCountDownTimer5.cancel();
            mProgress5.setProgress(0);
            imgpl5glow.setVisibility(View.GONE);
        }


        if(!player.equals("6"))
        {
            isProgressrun6 = true;
            mCountDownTimer6.cancel();
            mProgress6.setProgress(0);
            imgpl6glow.setVisibility(View.GONE);
        }


        if (!player.equals("7"))
        {
            isProgressrun7 = true;
            mCountDownTimer7.cancel();
            mProgress7.setProgress(0);
            imgpl7glow.setVisibility(View.GONE);
        }


    }


    public void makeWinnertoPlayer(String chaal_user_id) {
        mProgress1.setProgress(0);
        mProgress2.setProgress(0);
        mProgress3.setProgress(0);
        mProgress4.setProgress(0);
        mProgress5.setProgress(0);
        mCountDownTimer1.cancel();
        mCountDownTimer2.cancel();
        mCountDownTimer3.cancel();
        mCountDownTimer4.cancel();
        mCountDownTimer5.cancel();
        mCountDownTimer6.cancel();
        mCountDownTimer7.cancel();

        isProgressrun1 = true;
        isProgressrun2 = true;
        isProgressrun3 = true;
        isProgressrun4 = true;
        isProgressrun5 = true;
        isProgressrun6 = true;
        isProgressrun7 = true;


        PlaySaund(R.raw.tpb_battle_won);

        rltwinnersymble1.setVisibility(View.GONE);
        rltwinnersymble2.setVisibility(View.GONE);
        rltwinnersymble3.setVisibility(View.GONE);
        rltwinnersymble4.setVisibility(View.GONE);
        rltwinnersymble5.setVisibility(View.GONE);
        rltwinnersymble6.setVisibility(View.GONE);
        rltwinnersymble7.setVisibility(View.GONE);


        if (chaal_user_id.equals(user_id_player1)) {

            rltwinnersymble1.setVisibility(View.VISIBLE);

        } else if (chaal_user_id.equals(user_id_player2)) {


            rltwinnersymble2.setVisibility(View.VISIBLE);
        }
        else if (chaal_user_id.equals(user_id_player3)) {


            rltwinnersymble3.setVisibility(View.VISIBLE);
        }
        else if (chaal_user_id.equals(user_id_player4)) {


            rltwinnersymble4.setVisibility(View.VISIBLE);
        }
        else if (chaal_user_id.equals(user_id_player5)) {


            rltwinnersymble5.setVisibility(View.VISIBLE);
        }
        else if (chaal_user_id.equals(user_id_player6)) {


            rltwinnersymble6.setVisibility(View.VISIBLE);
        }
        else if (chaal_user_id.equals(user_id_player7)) {


            rltwinnersymble7.setVisibility(View.VISIBLE);
        }

    }


    public void StopSound(){

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer = null;
        }

        if(mCountDownTimer1 != null)
        {
            Player1CancelCountDown();
        }

        if(mCountDownTimer2 != null)
        {
            mCountDownTimer2.cancel();
        }

        if(mCountDownTimer3 != null)
        {
            mCountDownTimer3.cancel();
        }

        if(mCountDownTimer4 != null)
        {
            mCountDownTimer4.cancel();
        }

        if(mCountDownTimer5 != null)
        {
            mCountDownTimer5.cancel();
        }

        if(mCountDownTimer6 != null)
        {
            mCountDownTimer6.cancel();
        }

        if(mCountDownTimer7 != null)
        {
            mCountDownTimer7.cancel();
        }

    }

    MediaPlayer mediaPlayer ;
    public void PlaySaund(int saund) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = prefs.getString("issoundon", "1");

        if (value.equals("1")) {
            mediaPlayer = MediaPlayer.create(this,
                    saund);
            mediaPlayer.start();

        } else {


        }

    }

    boolean isGroupNameSet = false;
    int loopgroupsize = 0;
    ArrayList<ArrayList<CardModel>> temp_grouplist;
    private void SplitCardtoGroup(){
        rs_cardlist_group1.clear();
        rp_cardlist_group2.clear();
        bl_cardlist_group3.clear();
        bp_cardlist_group4.clear();
        joker_cardlist_group5.clear();
        ext_group1.clear();
        ext_group2.clear();
        ext_group3.clear();
        ext_group4.clear();
        ext_group5.clear();
        selectedcardvalue.clear();
        grouplist.clear();
        rlt_addcardview.removeAllViews();
        bt_sliptcard.setVisibility(View.GONE);
        isSplit = true;
        animation_type = "normal";


        for (int i = 0; i < cardModelArrayList.size() ; i++) {

            String card_tag = cardModelArrayList.get(i).card_group;

            if(card_tag.equalsIgnoreCase("rs"))
            {
                rs_cardlist_group1.add(cardModelArrayList.get(i));
            }
            else if(card_tag.equalsIgnoreCase("rp"))
            {
                rp_cardlist_group2.add(cardModelArrayList.get(i));
            }
            else if(card_tag.equalsIgnoreCase("bl"))
            {
                bl_cardlist_group3.add(cardModelArrayList.get(i));
            }
            else if(card_tag.equalsIgnoreCase("bp"))
            {
                bp_cardlist_group4.add(cardModelArrayList.get(i));
            }
            else if(card_tag.equalsIgnoreCase("jk"))
            {
                joker_cardlist_group5.add(cardModelArrayList.get(i));
            }

        }

        temp_grouplist = new ArrayList<>() ;


        if(rs_cardlist_group1.size() > 0)
        {
            temp_grouplist.add(rs_cardlist_group1);
        }

        if(rp_cardlist_group2.size() > 0)
        {
            temp_grouplist.add(rp_cardlist_group2);
        }

        if(bl_cardlist_group3.size() > 0)
        {
            temp_grouplist.add(bl_cardlist_group3);
        }

        if(bp_cardlist_group4.size() > 0)
        {
            temp_grouplist.add(bp_cardlist_group4);
        }

        if(joker_cardlist_group5.size() > 0)
        {
            temp_grouplist.add(joker_cardlist_group5);
        }


        int temp_size = temp_grouplist.size();
        //        for (int i = 0; i < temp_size ; i++) {
        //
        ////            if(!isGroupNameSet)
        ////            {
        ////                isGroupNameSet = true;
        //                API_CALL_Sort_card_value(temp_grouplist.get(i),temp_grouplist.size(),i);
        ////            }
        //
        //        }

        loopgroupsize = 0;
        API_CALL_Sort_card_value(temp_grouplist.get(loopgroupsize),temp_grouplist.size(),loopgroupsize);


    }


    int grouplist_size  = 0;
    Bottom_listDialog bottom_listDialog;

    private void AddSplit_to_layout(){
        isSplit = true;
        rlt_addcardview.removeAllViews();
        selectedcardvalue.clear();
        grouplist.clear();

//            if(!opponent_game_declare)
        isDeclareVisible = true;

        if(rs_cardlist_group1.size() > 0)
        {
            grouplist.add(rs_cardlist_group1);

            //            CreateGroups(rs_cardlist_group1);
        }

        if(rp_cardlist_group2.size() > 0)
        {
            grouplist.add(rp_cardlist_group2);

            //            CreateGroups(rp_cardlist_group2);
        }

        if(bl_cardlist_group3.size() > 0)
        {
            grouplist.add(bl_cardlist_group3);

            //            CreateGroups(bl_cardlist_group3);
        }

        if(bp_cardlist_group4.size() > 0)
        {
            grouplist.add(bp_cardlist_group4);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(joker_cardlist_group5.size() > 0)
        {
            grouplist.add(joker_cardlist_group5);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(ext_group1.size() > 0)
        {
            grouplist.add(ext_group1);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(ext_group2.size() > 0)
        {
            grouplist.add(ext_group2);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(ext_group3.size() > 0)
        {
            grouplist.add(ext_group3);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(ext_group4.size() > 0)
        {
            grouplist.add(ext_group4);

            //            CreateGroups(bp_cardlist_group4);
        }

        if(ext_group5.size() > 0)
        {
            grouplist.add(ext_group5);

            //            CreateGroups(bp_cardlist_group4);
        }


        grouplist_size = grouplist.size();

        bottom_listDialog = new Bottom_listDialog(grouplist);


        for (int i = 0; i < grouplist.size() ; i++) {

            CreateGroups(grouplist.get(i),i);

        }

    }

    private void CreateGroups(ArrayList<CardModel> cardImageList, int count) {

        final View view = LayoutInflater.from(this).inflate(R.layout.item_grouplayout,  null);
        view.setId(count);
        TextView tv_status = view.findViewById(R.id.tv_status);
        ImageView iv_status = view.findViewById(R.id.iv_status);
        LinearLayout lnr_group_card = view.findViewById(R.id.lnr_group_card);
        //        lnr_group_card.setId(count);
        RelativeLayout rltCardView = view.findViewById(R.id.rltCardView);
        RelativeLayout rlt_icons = view.findViewById(R.id.rlt_icons);

        cardImageList = CardValue(cardImageList);

        if(cardImageList.get(0).group_value.equalsIgnoreCase(INVALID))
            tv_status.setText(""+cardImageList.get(0).group_value+"("+cardImageList.get(0).group_points+")");
        else
            tv_status.setText(cardImageList.get(0).group_points);


        lnr_group_card.setTag(""+cardImageList.get(0).value_grp);

//        String grou_json = ""+getGroupJson(cardImageList.get(0));
//        Funtions.LOGE("group_json",""+grou_json);

        if(count == 1)
        {
//            view.findViewById(R.id.iv_arrow_left).setVisibility(View.VISIBLE);
//            view.findViewById(R.id.iv_arrow_right).setVisibility(View.VISIBLE);
        }
        else
        {
            view.findViewById(R.id.iv_arrow_left).setVisibility(View.GONE);
            view.findViewById(R.id.iv_arrow_right).setVisibility(View.GONE);
        }

        int Imageresours = 0;
        if(cardImageList.get(0).group_value.equalsIgnoreCase(INVALID))
        {
            isDeclareVisible = false;
            Imageresours = Funtions.GetResourcePath("invalid_circlebg",this);
        }
        else {
            Imageresours = Funtions.GetResourcePath("valid_circlebg",this);
//                isDeclareVisible = true;
        }

        if(opponent_game_declare)
            isDeclareVisible = true;

        if(isDeclareVisible && (grouplist.size()-1) == count)
        {
//            bt_declare.setVisibility(View.VISIBLE);
            bt_declare.setVisibility(View.GONE);

            if(isFinishDesk)
            {
                isFinishDesk = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        API_CALL_declare();
                    }
                },500);

            }

        }
        else
        {

            if(isFinishDesk && (grouplist.size()-1) == count)
            {
                isFinishDesk = false;
                API_CALL_pack_game();
            }

            bt_declare.setVisibility(View.GONE);
        }


        iv_status.setImageResource(Imageresours);


        for (int i = 0; i < cardImageList.size() ; i++) {

            AddCardtoGroup(cardImageList.get(i).Image,i,lnr_group_card,cardImageList);

        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = (String) view.getTag();
                //                Toast.makeText(MainActivity.this , tag, Toast.LENGTH_LONG).show();

            }
        });

        final ArrayList<CardModel> finalCardImageList = cardImageList;
        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                int action = event.getAction();
                View view1 = (View) event.getLocalState();

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.invalidate();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        v.invalidate();
                        break;

                    case DragEvent.ACTION_DROP:

                        //                        Toast.makeText(context, ""+view.getId(), Toast.LENGTH_SHORT).show();

                        if(group_id == v.getId())
                        {

                            Toast.makeText(context, ""+view.getId()+" / "+group_id, Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        CardModel model ;
                        selectedpatti = String.valueOf(_view.getTag());
                        selectedpatti_id = String.valueOf(_view.getTag());
                        Funtions.LOGE("RummyGame","selectedpatti : "+selectedpatti+
                                "\n selectedpatti_id : "+selectedpatti_id);
                        RemoveCardFromArrayLists(selectedpatti_id);

                        if(removemodel != null)
                        {
                            model = removemodel;
                        }
                        else {
                            model = new CardModel();
                            model.Image = selectedpatti;
                            model.Image = selectedpatti_id;
                        }


                        finalCardImageList.add(model);

                        GetGroupValueFromTouch("Touch");

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int valueInPixels = (int) getResources().getDimension(R.dimen.margin_left_group);

        if(grouplist_size >= 5)
        {
            valueInPixels = 10 /** grouplist_size*/;
        }

        int leftmargin = 0;
        if(count == 0)
        {
            leftmargin = (int) convertDpToPixel(valueInPixels,this);
        }



        layoutParams.setMargins((int) leftmargin, (int) convertDpToPixel(0,this), 0, 0);

//        int rightMargin = (int) getResources().getDimension(R.dimen.card_right_margin);

        if(grouplist_size == count)
        {
//            if(!Funtions.isViewFullyVisible(rltCardView))
//            {
////                    Toast.makeText(context, "Cards Getting Hide", Toast.LENGTH_SHORT).show();
////                    layoutParams.setMargins((int) leftmargin, (int) convertDpToPixel(0,this), (int) convertDpToPixel(0,this), 0);
//            }
        }



        ViewGroup.LayoutParams params = rlt_addcardview.getLayoutParams();


        rlt_addcardview.setLayoutParams(params);

        rlt_addcardview.addView(view,layoutParams);

    }

    private JSONObject getGroupJson(CardModel cardModel){
        JSONObject group_json = null;

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("card_value",""+cardModel.Image);
            jsonObject.put("card_id",""+cardModel.card_id);

            jsonObject.put("group_value_params",""+cardModel.group_value_params);
            jsonObject.put("group_value_response",""+cardModel.group_value_response);
            jsonObject.put("group_value",""+cardModel.group_value);
            jsonObject.put("group_points",""+cardModel.group_points);

            group_json = jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return group_json;
    }

    private void GetGroupValueFromTouch(String animationType){

        if(temp_grouplist != null)
            temp_grouplist.clear();
        else {
            temp_grouplist = new ArrayList<>();
        }


        if(rs_cardlist_group1.size() > 0)
        {
            temp_grouplist.add(rs_cardlist_group1);

        }

        if(rp_cardlist_group2.size() > 0)
        {
            temp_grouplist.add(rp_cardlist_group2);

        }

        if(bl_cardlist_group3.size() > 0)
        {
            temp_grouplist.add(bl_cardlist_group3);

        }

        if(bp_cardlist_group4.size() > 0)
        {
            temp_grouplist.add(bp_cardlist_group4);

        }

        if(joker_cardlist_group5.size() > 0)
        {
            temp_grouplist.add(joker_cardlist_group5);

        }

        if(ext_group1.size() > 0)
        {
            temp_grouplist.add(ext_group1);

        }

        if(ext_group2.size() > 0)
        {
            temp_grouplist.add(ext_group2);

        }

        if(ext_group3.size() > 0)
        {
            temp_grouplist.add(ext_group3);

        }

        if(ext_group4.size() > 0)
        {
            temp_grouplist.add(ext_group4);

        }

        if(ext_group5.size() > 0)
        {
            temp_grouplist.add(ext_group5);
        }

        loopgroupsize = 0;

        animation_type = animationType;
        API_CALL_Sort_card_value(temp_grouplist.get(loopgroupsize),temp_grouplist.size(),loopgroupsize);

    }

    CardModel removemodel;
    ArrayList<CardModel> removeCardList;
    private void RemoveCardFromArrayLists(String selectedpatti_id){

        if(ext_group1.size() > 0)
        {
            RemoveCard(selectedpatti_id,ext_group1);
        }

        if(ext_group2.size() > 0)
        {
            RemoveCard(selectedpatti_id,ext_group2);
        }

        if(ext_group3.size() > 0)
        {
            RemoveCard(selectedpatti_id,ext_group3);
        }

        if(ext_group4.size() > 0)
        {
            RemoveCard(selectedpatti_id,ext_group4);
        }

        if(ext_group5.size() > 0)
        {
            RemoveCard(selectedpatti_id,ext_group5);
        }


        if(rs_cardlist_group1.size() > 0)
        {
            RemoveCard(selectedpatti_id,rs_cardlist_group1);
        }

        if(rp_cardlist_group2.size() > 0)
        {
            RemoveCard(selectedpatti_id,rp_cardlist_group2);
        }

        if(bl_cardlist_group3.size() > 0)
        {
            RemoveCard(selectedpatti_id,bl_cardlist_group3);
        }

        if(bp_cardlist_group4.size() > 0)
        {
            RemoveCard(selectedpatti_id,bp_cardlist_group4);
        }

        if(joker_cardlist_group5.size() > 0)
        {
            RemoveCard(selectedpatti_id,joker_cardlist_group5);
        }


    }

    private final int right_margin = -35;
    private final int cardTopMargin = 15;
    private final int cardBottomMargin = 10;
    private final int cardLeftMargin = 5;
    private void addCardsBahar(String image_card , final int countnumber) {

        View view = LayoutInflater.from(this).inflate(R.layout.item_card,  null);
        ImageView imgcards = view.findViewById(R.id.imgcard);
        view.setTag(image_card+"");

        final ImageView iv_jokercard = view.findViewById(R.id.iv_jokercard);
        String src_joker_cards = "";
        src_joker_cards = joker_card.substring(joker_card.length() - 1);

        if(src_joker_cards != null && !src_joker_cards.equals(""))
        {
            if(src_joker_cards.contains(image_card.substring(image_card.length() - 1)))
            {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_jokercard.setVisibility(View.VISIBLE);
                    }
                },4000);

            }
            else {
                iv_jokercard.setVisibility(View.GONE);
            }
        }


        int valueInPixels = (int) getResources().getDimension(R.dimen.margin_left);
        int left_margin = 0;
        if(countnumber == 0)
        {
            left_margin = (int) convertDpToPixel(valueInPixels,this);
        }

        //        Toast.makeText(this, ""+valueInPixels, Toast.LENGTH_SHORT).show();


        final int finalLeft_margin = left_margin;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = (String) view.getTag();
                //                Toast.makeText(MainActivity.this , tag, Toast.LENGTH_LONG).show();

                float CardMargin = cardTopMargin;

                CardModel model = cardModelArrayList.get(countnumber);
                Funtions.LOGE("MainActivity","Card Position : "+countnumber);

                model.isSelected = !model.isSelected;


                if (model.isSelected)
                {
                    CardMargin = 0;

                    if(selectedcardvalue.size() == 0) {
                        selectedcardvalue.add(model);

                        selectedpatti = model.Image;
                        selectedpatti_id = model.card_id;
                    }

                    for (int i = 0; i < selectedcardvalue.size() ; i++) {

                        CardModel selectmodel = selectedcardvalue.get(i);

                        if(!selectmodel.card_id.equals(model.card_id))
                        {
                            selectedcardvalue.add(model);
                            selectedpatti = model.Image;
                            selectedpatti_id = model.card_id;
                            break;
                        }

                    }

                }
                else {

                    for (int i = 0; i < selectedcardvalue.size() ; i++) {

                        if(selectedcardvalue.get(i).card_id.contains(model.card_id))
                        {
                            selectedcardvalue.remove(model);
                            selectedpatti = "";
                        }

                    }

                }


                setMargins(view, finalLeft_margin,(int) convertDpToPixel(CardMargin, context)
                        ,(int) convertDpToPixel(right_margin, context), (int) convertDpToPixel(cardBottomMargin, context));

                //                bt_creategroup.setVisibility(View.VISIBLE);

                if(selectedcardvalue.size() == 1)
                {
                    bt_sliptcard.setVisibility(View.GONE);
                    bt_discard.setVisibility(View.VISIBLE);
                    DeclareButtonVisible(true);
                }
                else
                {
                    bt_sliptcard.setVisibility(View.VISIBLE);
                    bt_discard.setVisibility(View.GONE);
                    DeclareButtonVisible(false);
                }

            }
        });


        String imagename = image_card;
        if(image_card.contains("id")) {
            imagename = image_card.substring(11);
        }

        Funtions.LOGE("MainActivity","imagename : "+imagename);
        int imageResource = Funtions.GetResourcePath(imagename,this);

        if(imageResource > 0)
            LoadImage().load(imageResource).into(imgcards);

        TranslateLayout( imgcards, countnumber*200);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.
                setMargins(left_margin, (int) convertDpToPixel(cardTopMargin,this),
                        (int) convertDpToPixel(right_margin,this), (int) convertDpToPixel(10,this));
        ViewGroup.LayoutParams params = rlt_addcardview.getLayoutParams();


        if (countnumber==0){
            params.width = (int) convertDpToPixel(85,this);
        }else {
            params.width = (int) convertDpToPixel(85,this) * countnumber;
        }

        rlt_addcardview.setLayoutParams(params);
        rlt_addcardview.requestLayout();
        rlt_addcardview.addView(view,layoutParams);


    }


    String animation_type = "normal";
    private static final int MAX_CLICK_DURATION = 500;
    private long startClickTime;
    private void AddCardtoGroup(String image_card , final int countnumber, final LinearLayout addlayout,
                                final ArrayList<CardModel> arrayList) {

        View view = LayoutInflater.from(this).inflate(R.layout.item_card,  null);
        ImageView imgcards = view.findViewById(R.id.imgcard);
        //        view.setTag(image_card+"");
        view.setTag (arrayList.get(countnumber).card_id+"");


        final ImageView iv_jokercard = view.findViewById(R.id.iv_jokercard);

        String src_joker_cards = "";
        src_joker_cards = joker_card.substring(joker_card.length() - 1);

        if(src_joker_cards != null && !src_joker_cards.equals(""))
        {
            if(src_joker_cards.contains(image_card.substring(image_card.length() - 1)))
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_jokercard.setVisibility(View.VISIBLE);
                    }
                },4000);

            }
            else {
                iv_jokercard.setVisibility(View.GONE);
            }
        }

        if(selectedcardvalue.size() == 1 && !opponent_game_declare && isCardPick)
        {
            bt_discard.setVisibility(View.VISIBLE);
            DeclareButtonVisible(true);
        }
        else
        {
            bt_discard.setVisibility(View.GONE);
            DeclareButtonVisible(false);
        }


        if(selectedcardvalue.size() >= 2)
            bt_creategroup.setVisibility(View.VISIBLE);
        else
            bt_creategroup.setVisibility(View.GONE);



//        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(touchmode.isChecked())
                {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                            return onTouchMainCard(v,event,arrayList,countnumber);
                        }
                        case MotionEvent.ACTION_UP: {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if(clickDuration < MAX_CLICK_DURATION) {
                                //click event has occurred
                                onGroupsCardClick(v,arrayList,countnumber);

                                return true;
                            } else {
                                // your code for move and drag
                                group_id = addlayout.getId();
                                return onTouchMainCard(v,event,arrayList,countnumber);
                            }
                        }
                    }
                    return false;
                }

//                if (gestureDetector.onTouchEvent(event)) {
//                    // single tap
//
//                    onGroupsCardClick(v,arrayList,countnumber);
//
//                    return true;
//                } else {
//                    // your code for move and drag
//                    group_id = addlayout.getId();
//                    return onTouchMainCard(v,event,arrayList,countnumber);
//                }

                return false;
            }
        });



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onGroupsCardClick(view,arrayList,countnumber);

            }
        });


        String imagename = image_card;
        if(image_card.contains("id")) {
            imagename = image_card.substring(11);
        }

        int imageResource = Funtions.GetResourcePath(imagename,this);

        if(imageResource > 0)
            LoadImage().load(imageResource).into(imgcards);


        if(animation_type.equals("normal"))
            TranslateLayout(imgcards, countnumber*200);
        else if(animation_type.equals("drop") && !animationon)
            DropTranslationAnimation();
        else if(animation_type.equals("pick") && !animationon)
            PickCardTranslationAnimation();
        else if(animation_type.equals("drop_pick") && !animationon)
            DropPickTranslationAnimation();


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //        if(countnumber != 0)
        //        {
        layoutParams.setMargins(0,
                (int) convertDpToPixel(cardTopMargin,this),
                (int) convertDpToPixel(right_margin,this), 0);
        //        }

        setMargins(ivDropCreate,(int) convertDpToPixel(80,this),(int) convertDpToPixel(20,this)
                ,0,0);

//        ViewGroup.LayoutParams params = addlayout.getLayoutParams();
//
//        float layout_widith = convertDpToPixel(50, this) * arrayList.size();
//
//        params.width = (int) layout_widith;
//
        int average_card_size = 12;
//
//        if (countnumber == 0) {
//            params.width = (int) convertDpToPixel(80 + average_card_size, this);
//        } else if (countnumber == 1) {
//            params.width = (int) convertDpToPixel(110 + average_card_size, this) * countnumber;
//        } else if (countnumber == 2) {
//            params.width = (int) convertDpToPixel(65 + average_card_size, this) * countnumber;
//        } else if (countnumber == 3) {
//            params.width = (int) convertDpToPixel(55 + average_card_size, this) * countnumber;
//        } else if (countnumber == 4) {
//            params.width = (int) convertDpToPixel(48 + average_card_size, this) * countnumber;
//        } else if (countnumber == 5) {
//            params.width = (int) convertDpToPixel(45 + average_card_size, this) * countnumber;
//        } else if (countnumber == 6) {
//            params.width = (int) convertDpToPixel(42 + average_card_size, this) * countnumber;
//        } else {
//            params.width = (int) convertDpToPixel(45 + average_card_size, this) * countnumber;
//        }



//        addlayout.setLayoutParams(params);
//        addlayout.requestLayout();

        addlayout.addView(view,layoutParams);

        if(countnumber == (arrayList.size() - 1))
        {
            ViewGroup.LayoutParams params = addlayout.getLayoutParams();
            float initial_width = params.width;
//            params.width = (int) (initial_width + convertDpToPixel(50,this));

            float half_card_size = right_margin * arrayList.size();
            float card_width = 80;


            float group_width = card_width * arrayList.size();
            float full_group_width =  group_width - half_card_size;

            if(arrayList.size() == 1)
            {
                params.width = (int) convertDpToPixel(80 ,this) * arrayList.size();
            }
            else
            if(arrayList.size() <= 3)
            {
                params.width = (int) convertDpToPixel(50,this) * arrayList.size();
            }
            else {
                params.width = (int) convertDpToPixel(42,this) * arrayList.size();
            }
            //            else {
//                params.width = (int) full_group_width;
//            }

            addlayout.setLayoutParams(params);
            addlayout.requestLayout();
        }


    }

    private void onGroupsCardClick(View view, ArrayList<CardModel> arrayList, int countnumber) {

        View imgalphacard = view.findViewById(R.id.imgalphacard);

        if(game_declare || arrayList.size() == 0)
        {
            return;
        }


        String tag = (String) view.getTag();
        //                Toast.makeText(MainActivity.this , tag, Toast.LENGTH_LONG).show();

        float CardMargin = cardTopMargin;
        CardModel model = arrayList.get(countnumber);
        Funtions.LOGE("MainActivity","Card Position : "+countnumber);

        model.isSelected = !model.isSelected;

        if (model.isSelected)
        {
            CardMargin = 0;
            if(selectedcardvalue.size() == 0)
            {
                selectedpatti = model.Image;
                selectedpatti_id = model.card_id;
                selectedcardvalue.add(model);
            }

            for (int i = 0; i < selectedcardvalue.size() ; i++) {

                CardModel selectmodel = selectedcardvalue.get(i);

                if(!selectmodel.card_id.equals(model.card_id))
                {
                    selectedcardvalue.add(model);

                    selectedpatti = model.Image;
                    selectedpatti_id = model.card_id;

                    break;
                }

            }

            if(imgalphacard != null)
                imgalphacard.setVisibility(View.VISIBLE);

        }
        else {

            for (int i = 0; i < selectedcardvalue.size() ; i++) {

                //                        if(selectedcardvalue.get(i).Image.contains(model.Image))
                //                        {
                //                            selectedcardvalue.remove(model);
                selectedpatti = "";
                //                        }
                RemoveCard(model.card_id,selectedcardvalue);

            }

            if(imgalphacard != null)
                imgalphacard.setVisibility(View.GONE);

        }


        setMargins(view,0,(int) convertDpToPixel(CardMargin, context)
                ,(int) convertDpToPixel(right_margin, context),0);


        if(selectedcardvalue.size() == 1 && !opponent_game_declare && isCardPick)
        {
            bt_discard.setVisibility(View.VISIBLE);
            DeclareButtonVisible(true);
        }
        else
        {
            bt_discard.setVisibility(View.GONE);
            DeclareButtonVisible(false);
        }

        if(selectedcardvalue.size() >= 2)
            bt_creategroup.setVisibility(View.VISIBLE);
        else
            bt_creategroup.setVisibility(View.GONE);

    }

    private GestureDetector gestureDetector;

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


    View _view;
    ViewGroup _root,remove_viewgroup;
    int group_id = -1;
    LinearLayout.LayoutParams view_linearparams ;
    private int _xDelta;
    private int _yDelta;
    boolean isCardDrop = false;
    boolean isFinishDesk = false;
    public void InitilizeRootView(final ArrayList<CardModel> arrayList, final int countnumber){

        _root = (ViewGroup)findViewById(R.id.root);
        _root.setVisibility(View.VISIBLE);

        ivpickcard.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        setTouchViewVisible(false);
                        CreateGroupViewVisible(true);
                        isCardDrop = false;

                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }

                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
//                            view.invalidate();
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
//                            if(isCardDrop)
//                                return false;
//
//                                                setTouchViewVisible(true);
//                            isViewonTouch = false;
//
//
//                            ResetCardtoPosition(arrayList,countnumber);

                        view.invalidate();

                        return true;

                    case DragEvent.ACTION_DROP:

                        selectedpatti = String.valueOf(_view.getTag());
                        selectedpatti_id = String.valueOf(_view.getTag());

                        Funtions.LOGE("RummyGame","selectedpatti : "+selectedpatti+
                                "\n selectedpatti_id : "+selectedpatti_id);

                        animation_type = "swap_animation";

                        API_CALL_drop_card(arrayList,countnumber);
                        //                        Toast.makeText(context, "Card Drop"+selectedpatti, Toast.LENGTH_SHORT).show();
                        isCardDrop = true;

                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:

                        if(isCardDrop)
                            return false;

                        setTouchViewVisible(true);
                        CreateGroupViewVisible(false);
                        isViewonTouch = false;


                        ResetCardtoPosition(arrayList,countnumber);

                        return true;
                }
                return false;
            }
        });

        ivDropCreate.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        CreateGroupViewVisible(true);
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }

                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        view.invalidate();
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:


                        view.invalidate();

                        return true;

                    case DragEvent.ACTION_DROP:

                        CardModel model ;
                        selectedpatti = String.valueOf(_view.getTag());
                        selectedpatti_id = String.valueOf(_view.getTag());

                        Funtions.LOGE("RummyGame","selectedpatti : "+selectedpatti+
                                "\n selectedpatti_id : "+selectedpatti_id);
                        RemoveCardFromArrayLists(selectedpatti_id);

                        if(removemodel != null)
                        {
                            model = removemodel;
                        }
                        else {
                            model = new CardModel();
                            model.Image = selectedpatti;
                            model.Image = selectedpatti_id;
                        }


                        selectedcardvalue.add(model);
                        animation_type = "swap_animation";
                        CreateGroupFromSelect(true);
                        CreateGroupViewVisible(false);
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:

                        CreateGroupViewVisible(false);
                        isViewonTouch = false;

                        return true;
                }
                return false;
            }
        });

        ivFinishDesk.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:

                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }

                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
//                            view.invalidate();
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:

                        view.invalidate();

                        return true;

                    case DragEvent.ACTION_DROP:

                        selectedpatti = String.valueOf(_view.getTag());
                        selectedpatti_id = String.valueOf(_view.getTag());
                        animation_type = "finish_desk";
                        isFinishDesk = true;
                        API_CALL_drop_card(arrayList,countnumber);
                        isCardDrop = true;

                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:

                        ResetCardtoPosition(arrayList,countnumber);

                        return true;
                }
                return false;
            }
        });



    }

    private void CreateGroupViewVisible(boolean isVisible){

//        ivDropCreate.setVisibility(isVisible ?  View.VISIBLE : View.GONE);
        ivDropCreate.setVisibility(isVisible ?  View.GONE : View.GONE);

    }

    boolean isViewonTouch = false;
    private boolean onTouchMainCard(View cardview, MotionEvent event, final ArrayList<CardModel> arrayList, final int countnumber){

        InitilizeRootView(arrayList,countnumber);

        isCardDrop = false;

        //        if(!isMyChaal)
        //        {
        ////            Toast.makeText(context, ""+getString(R.string.chaal_error_messsage), Toast.LENGTH_SHORT).show();
        //            return false;
        //        }

        if(!isSplit)
        {
            //            Toast.makeText(MainActivity.this, ""+getString(R.string.sort_error_message), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isViewonTouch)
            return false;


        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                if(cardview.getLayoutParams() instanceof RelativeLayout.LayoutParams)
                {
                    RelativeLayout.LayoutParams  lParams = (RelativeLayout.LayoutParams) cardview.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                }
                else
                {
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) cardview.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                }


                break;
            case MotionEvent.ACTION_UP:
                ResetCardtoPosition(arrayList,countnumber);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:

                _view = cardview;
//                    view_linearparams = (LinearLayout.LayoutParams) _view.getLayoutParams();
//                    if(_view.getParent() != null) {
//                        remove_viewgroup = ((ViewGroup)_view.getParent());
//                        group_id = remove_viewgroup.getId();
//                        ((ViewGroup)_view.getParent()).removeView(_view);
//                    }
//
//                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
//                            , ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.leftMargin = X - _xDelta;
//                    layoutParams.topMargin = Y - _yDelta;
//                    layoutParams.rightMargin = -250;
//                    layoutParams.bottomMargin = -250;
//                    _view.setLayoutParams(layoutParams);
//
//                    _view.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            return Rummy5Player.this.onTouch(v,event,arrayList,countnumber);
//                        }
//                    });

//                                        setTouchViewVisible(true);

//                    _root.addView(_view);
                isViewonTouch = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(isViewonTouch)
                        {
                            ResetCardtoPosition(arrayList,countnumber);
                        }

                    }
                },5000);

                ClipData data = ClipData.newPlainText("","");

                MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(_view);
//                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(_view);
                _view.startDrag(data, shadowBuilder, _view, DRAG_FLAG_OPAQUE);


                break;
        }
        return true;

    }


    public boolean onTouch(View view, MotionEvent event, final ArrayList<CardModel> arrayList, final int countnumber) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:


                if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams)
                {
                    RelativeLayout.LayoutParams  lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                }
                else
                {
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                }


                break;
            case MotionEvent.ACTION_UP:

                Toast.makeText(context, "View release", Toast.LENGTH_SHORT).show();

                ResetCardtoPosition(arrayList,countnumber);

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:


                if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams)
                {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                }
                else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);

                }

                break;
        }
        _root.invalidate();
        return true;
    }

    private void ResetCardtoPosition(final ArrayList<CardModel> arrayList, final int countnumber){
        if(_view != null)
        {
//                                    setTouchViewVisible(true);
//                if(_view.getParent() != null) {
//                    _root = ((ViewGroup)_view.getParent());
//                    ((ViewGroup)_view.getParent()).removeView(_view); // <- fix
//                }
        }


//            if(remove_viewgroup != null) {
//                _view.setLayoutParams(view_linearparams);
//                remove_viewgroup.addView(_view);
//            }

        isViewonTouch = false;
        isCardDrop = false;

        if(_view != null)
            _view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

//                    if (gestureDetector.onTouchEvent(event)) {
//                        // single tap
//
//                        onGroupsCardClick(v,arrayList,countnumber);
//
//                        return true;
//                    } else {
//                        // your code for move and drag
//                        return onTouchMainCard(v,event,arrayList,countnumber);
//                    }

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                            return onTouchMainCard(v,event,arrayList,countnumber);
                        }
                        case MotionEvent.ACTION_UP: {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if(clickDuration < MAX_CLICK_DURATION) {
                                //click event has occurred
                                onGroupsCardClick(v,arrayList,countnumber);

                                return true;
                            } else {
                                // your code for move and drag
                                return onTouchMainCard(v,event,arrayList,countnumber);
                            }
                        }
                    }

                    return true;
                }
            });


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        animationon = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();

            //            Funtions.LOGE("MainAcitvity","Left : "+left);

        }

    }


}