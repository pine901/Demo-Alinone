package com.gamegards.allinonev3._AdharBahar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.gamegards.allinonev3.Utils.Funtions;
import com.gamegards.allinonev3.Utils.SharePref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.gamegards.allinonev3._AdharBahar.Fragments.GameFragment.MY_PREFS_NAME;

public class GameActivity extends AppCompatActivity {

    LinearLayout lnrfollow,lnrandarpatte,lnrbaharpatte ;
    RelativeLayout rltline;
    Context context =this;
    CountDownTimer counttimerforstartgame;
    CountDownTimer counttimerforcards;
    CountDownTimer counttimerforcardsforAnimation;
    int count = 0;
    private MediaPlayer mp;
    String tagamountselected = "";
    int betcountandar = 1;
    int betcountbahar = 1;
    int countvaue = 0;
    int countvaueforani = 0;
    boolean isConfirm = false;
    boolean IsFirsttimeCall = true;
    String betvalue = "";
    int widthandar = -430;
    int countwidhtbahar = 41;
    int widthbahar = -430;
    int countwidhtanadar = 41;
    int firstcount = 0;
    TextView txtGameFinish,txtName,txtBallence,txtGameRunning,txtGameBets,txt_catander,txt_catbahar,txt_room,txt_gameId,txt_online,txt_min_max,txt_many_cards;
    ImageView imgmaincard;
    Typeface helvatikaboldround;

    ArrayList<String> aaraycards;
    boolean isGameBegning = false;
    int timertime = 4000;
    public String game_id;
    public String room_id;
    public String min_coin;
    public String max_coin;
    public String main_card;
    public String winning;
    public String status = "";
    String bet_id = "";
    private String added_date;
    private String user_id,name,wallet;
    Timer timerstatus;
    ImageView imgCardsandar,imgCardsbahar;
    Animation setanimation;
    ImageView imgmaincardsvaluehiostory;
    Button btnandarpercent,btnbaharpercent;
    String betplace = "";
    boolean canbet = false;
    boolean isInPauseState = false;
    boolean isCardsDisribute = false;
    Button btnCANCEL,btnRepeat,btnDouble,btnconfirm;
    RelativeLayout rltandarbet,rltbaharbet,rltmainviewander,rltmainviewbahar,rlt41more,rlt1to5,rlt6to10,rlt11to15,rlt16to25,rlt26to30,rlt31to35,rlt36to40;

    ImageView imgpl1circle;
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //  builder.setTitle("");
        builder.setMessage("Do you want to exit the game  ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initview();
    }

    private String profile_pic;
    View rtllosesymble1;
    public void initview(){

        // setanimation= AnimationUtils.loadAnimation(this,R.anim.set);

        rltwinnersymble1=findViewById(R.id.rltwinnersymble1);
        rtllosesymble1=findViewById(R.id.rtllosesymble1);

        imgpl1circle = findViewById(R.id.imgpl1circle);
        profile_pic = SharePref.getInstance().getString(SharePref.u_pic);
        Picasso.with(context).load(Const.IMGAE_PATH + profile_pic).into(imgpl1circle);

        imgmaincardsvaluehiostory = findViewById(R.id.imgmaincardsvaluehiostory);
        btnbaharpercent = findViewById(R.id.btnbaharpercent);
        btnandarpercent = findViewById(R.id.btnandarpercent);



        imgCardsandar = findViewById(R.id.imgCardsandar);
        imgCardsbahar = findViewById(R.id.imgCardsbahar);
        lnrfollow = findViewById(R.id.lnrfollow);
        lnrandarpatte = findViewById(R.id.lnrandarpatte);
        // lnrpaate = findViewById(R.id.lnrpaate);
        rltline = findViewById(R.id.rltline);
        lnrbaharpatte = findViewById(R.id.lnrbaharpatte);
        // setBackground(GameActivity.this,findViewById(R.id.activity_main),R.mipmap.src_gameboard);
        txt_room = findViewById(R.id.txt_room);
        txt_gameId = findViewById(R.id.txt_gameId);
        txt_online = findViewById(R.id.txt_online);
        txt_min_max = findViewById(R.id.txt_min_max);
        txt_many_cards = findViewById(R.id.txt_many_cards);
        txtGameFinish = findViewById(R.id.txtGameFinish);
        txtGameBets = findViewById(R.id.txtGameBets);
        imgmaincard = findViewById(R.id.imgmaincard);
        rltandarbet = findViewById(R.id.rltandarbet);
        rltmainviewander = findViewById(R.id.rltmainviewander);
        rltmainviewbahar = findViewById(R.id.rltmainviewbahar);
        rlt1to5 = findViewById(R.id.rlt1to5);
        rlt6to10 = findViewById(R.id.rlt6to10);
        rlt11to15 = findViewById(R.id.rlt11to15);
        rlt16to25 = findViewById(R.id.rlt16to25);

        rlt26to30 = findViewById(R.id.rlt26to30);
        rlt31to35 = findViewById(R.id.rlt31to35);
        rlt36to40 = findViewById(R.id.rlt36to40);
        rlt41more = findViewById(R.id.rlt41more);
        btnRepeat = findViewById(R.id.btnRepeat);

        min_coin= getIntent().getStringExtra("min_coin");
        max_coin= getIntent().getStringExtra("max_coin");
        room_id= getIntent().getStringExtra("room_id");



        txt_room.setText("ROOM ID "+room_id);

        txt_min_max.setText("Min-Max: "+min_coin+" - "+max_coin);

//        imgCards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imgCards.startAnimation(setanimation);
//            }
//        });


        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                //  Toast.makeText(GameActivity.this, "Need to Discuss", Toast.LENGTH_LONG).show();

                if (betplace.length() > 0 ){

                    Toast.makeText(GameActivity.this, "You Already Placed Bet", Toast.LENGTH_LONG).show();

                }else {

                    repeatBet();

                }




            }
        });

        btnDouble = findViewById(R.id.btnDouble);
        btnDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finish();
                // Toast.makeText(GameActivity.this, "Need to Discuss", Toast.LENGTH_LONG).show();

                if (betplace.length() > 0){
                    float valedou = Float.parseFloat(betvalue);
                    float dobff=valedou*2;
                    betvalue=dobff+"";
                    txt_catander.setText(""+betvalue);
                    txt_catbahar.setText(""+betvalue);
                }else {
                    Toast.makeText(GameActivity.this, "You have not Bet yet. ", Toast.LENGTH_LONG).show();

                }


            }
        });


        btnconfirm = findViewById(R.id.btnconfirm);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  finish();
                //Toast.makeText(GameActivity.this, "Need to Discuss", Toast.LENGTH_LONG).show();

                if (betplace.length() > 0){

                    if (betvalue.length() > 0){
                        putbet(betplace);

                        isConfirm = true;

                    }

                }else {
                    Toast.makeText(GameActivity.this, "You have not Bet yet. ", Toast.LENGTH_LONG).show();

                }

            }
        });

        btnCANCEL = findViewById(R.id.btnCANCEL);
        btnCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                betvalue = "";
                betplace = "";
                if (bet_id.length() > 0){

                    cancelbet();

                }else {

                    Toast.makeText(GameActivity.this, "You have not Bet yet. ", Toast.LENGTH_LONG).show();
                    rltmainviewander.setVisibility(View.GONE);
                    rltmainviewbahar.setVisibility(View.GONE);


                }

            }
        });

        txt_catander = findViewById(R.id.txt_catander);
        txt_catbahar = findViewById(R.id.txt_catbahar);
        rltandarbet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConfirm) {

                    Toast.makeText(GameActivity.this, "Bet Already Confirmed So Not Allowed to Put again", Toast.LENGTH_LONG).show();


                } else {

                    if (canbet) {
                        if (tagamountselected.length() > 0) {

                            rltmainviewbahar.setVisibility(View.GONE);
                            txt_catbahar.setText("");

                            if (betplace.equals("1")){

                                betvalue = "";

                            }else {


                            }

                            betplace = "0";
                            float valedou = 0;
                            if (betvalue.length() > 0) {
                                valedou = Float.parseFloat(betvalue);
                            } else {

                                valedou = 0;
                            }
                            float valedoutagamountselected = 0;
                            if (tagamountselected.length() > 0) {
                                valedoutagamountselected = Float.parseFloat(tagamountselected);
                            } else {

                                valedoutagamountselected = 0;
                            }


                            float betvalueint = valedou + valedoutagamountselected;


                            betvalue = betvalueint + "";
                            rltmainviewander.setVisibility(View.VISIBLE);
                            txt_catander.setText("" + betvalue);


                        } else {

                            Toast.makeText(GameActivity.this, "Please Select Bet amount First", Toast.LENGTH_LONG).show();

                        }
                    } else {

                        Toast.makeText(GameActivity.this, "Game Already Started You can not Bet", Toast.LENGTH_LONG).show();

                    }
                }



            }
        });
        rltbaharbet = findViewById(R.id.rltbaharbet);
        rltbaharbet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isConfirm) {

                    Toast.makeText(GameActivity.this, "Bet Already Confirmed So Not Allowed to Put again", Toast.LENGTH_LONG).show();



                } else {


                    if (canbet) {

                        if (tagamountselected.length() > 0) {


                            rltmainviewander.setVisibility(View.GONE);
                            txt_catander.setText("");

                            if (betplace.equals("0")){

                                betvalue = "";

                            }else {


                            }

                            float valedou = 0;
                            if (betvalue.length() > 0) {
                                valedou = Float.parseFloat(betvalue);
                            } else {

                                valedou = 0;
                            }
                            float valedoutagamountselected = 0;
                            if (tagamountselected.length() > 0) {
                                valedoutagamountselected = Float.parseFloat(tagamountselected);
                            } else {

                                valedoutagamountselected = 0;
                            }

                            float betvalueint = valedou + valedoutagamountselected;
                            betvalue = betvalueint + "";
                            rltmainviewbahar.setVisibility(View.VISIBLE);
                            txt_catbahar.setText("" + betvalue);
                            //putbet("1");
                            betplace = "1";

                        } else {

                            Toast.makeText(GameActivity.this, "Please Select Bet amount First", Toast.LENGTH_LONG).show();

                        }

                    } else {

                        Toast.makeText(GameActivity.this, "Game Already Started You can not Bet", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        txtName = findViewById(R.id.txtName);
        txtGameRunning = findViewById(R.id.txtGameRunning);
        txtBallence = findViewById(R.id.txtBallence);
        helvatikaboldround = Typeface.createFromAsset(getAssets(), "fonts/helvetica-rounded-bold-5871d05ead8de.otf");
        txtGameFinish.setTypeface(helvatikaboldround);
        txtGameRunning.setTypeface(helvatikaboldround);
        txtGameBets.setTypeface(helvatikaboldround);
        aaraycards  = new ArrayList<>();
        addCategoryInView("1");
        addCategoryInView("5");
        addCategoryInView("10");
        addCategoryInView("50");
        addCategoryInView("100");
        addCategoryInView("500");
        addCategoryInView("1000");
        addCategoryInView("5000");
        addCategoryInView("7500");
        timerstatus = new Timer();
        timerstatus.scheduleAtFixedRate(new TimerTask() {

                                            @Override
                                            public void run() {

                                                // if (table_id.trim().length() > 0) {

                                                if (isCardsDisribute){



                                                }else {

                                                    getStatus();

                                                }



                                                // }

                                            }

                                        },
//Set how long before to start calling the TimerTask (in milliseconds)
                200,
//Set the amount of time between each execution (in milliseconds)
                timertime);


    }

    private void addCardsAndar(String cat,int countnumber) {

        View view = LayoutInflater.from(context).inflate(R.layout.cards_layout,  null);
        ImageView imgcards = view.findViewById(R.id.cards);
        view.setTag(cat+"");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = (String) view.getTag();
                Toast.makeText(GameActivity.this , tag, Toast.LENGTH_LONG).show();
            }
        });
        String uri1 = "@drawable/" + cat.toLowerCase();  // where myresource " +
        int imageResource1 = getResources().getIdentifier(uri1, null,
                getPackageName());
        Picasso.with(context).load(imageResource1).into(imgcards);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, -33, 0);

        ViewGroup.LayoutParams params = lnrandarpatte.getLayoutParams();
        //  params.height = 100;

        if (countnumber==0){
            params.width = 140;
        }else {
            params.width = 140*countnumber;
        }

        lnrandarpatte.setLayoutParams(params);
        lnrandarpatte.addView(view,layoutParams);
        PlaySaund(R.raw.teenpatticardflip_android);

    }

    private void addCardsBahar(String cat ,int countnumber ) {

        View view = LayoutInflater.from(context).inflate(R.layout.cards_layout,  null);
        ImageView imgcards = view.findViewById(R.id.cards);
        view.setTag(cat+"");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = (String) view.getTag();
                Toast.makeText(GameActivity.this , tag, Toast.LENGTH_LONG).show();
            }
        });
        String uri1 = "@drawable/" + cat.toLowerCase();  // where myresource " +
        int imageResource1 = getResources().getIdentifier(uri1, null,
                getPackageName());
        Picasso.with(context).load(imageResource1).into(imgcards);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, -33, 0);
        ViewGroup.LayoutParams params = lnrbaharpatte.getLayoutParams();
        // params.height = 100;

        if (countnumber==0){
            params.width = 140;
        }else {
            params.width = 140*countnumber;
        }

        lnrbaharpatte.setLayoutParams(params);
        lnrbaharpatte.addView(view,layoutParams);
        PlaySaund(R.raw.teenpatticardflip_android);


    }

    public void PlaySaund(int saund) {



        if (!isInPauseState) {
//            final MediaPlayer mp = MediaPlayer.create(this,
//                    saund);
//            mp.start();
//            try {
//                if (mp.isPlaying()) {
//                    mp.stop();
//                    mp.release();
//                    mp = MediaPlayer.create(context, saund);
//                }
//                mp.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            stopPlaying();
            mp = MediaPlayer.create(this, saund);
            mp.start();

        }


    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private void setSelectedType(String type) {

        LinearLayout lnrfollow = findViewById(R.id.lnrfollow);

        for (int i = 0; i < lnrfollow.getChildCount(); i++) {

            View view = lnrfollow.getChildAt(i);
            TextView txtview = view.findViewById(R.id.txt_cat);

            if(txtview.getText().toString().equalsIgnoreCase(type)){

                txtview.setTextColor(Color.parseColor("#ffffff"));
            }else{

                txtview.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            }

        }

    }

    private void addCategoryInView(String cat) {

        View view = LayoutInflater.from(context).inflate(R.layout.cat_txtview,  null);
        TextView txtview = view.findViewById(R.id.txt_cat);
        txtview.setText(cat+"");
        view.setTag(cat+"");


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                // SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                //  String Tag = prefs.getString("tag", "");
                // if (Tag.equals("")) {
//                    Toast.makeText(GameActivity.this ,"Tag=" +Tag, Toast.LENGTH_LONG).show();
                tagamountselected = (String) view1.getTag();
                TextView txt = view1.findViewById(R.id.txt_cat);
                txt.setTextColor(Color.parseColor("#ffffff"));
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("tag", tagamountselected);
                editor.apply();
//                      Toast.makeText(GameActivity.this ,"tag="+ tag, Toast.LENGTH_LONG).show();
                setSelectedType(tagamountselected);

            }
        });


        //  Log.d("101010","title :"+cat+" "+view.getHeight()+" "+view.getWidth());
        lnrfollow.addView(view);


    }

    private void getStatus() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.AnderBahar,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            Log.v("responce" , response);

                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equalsIgnoreCase("200")) {

                                JSONArray arraygame_dataa = jsonObject.getJSONArray("game_data");

                                for (int i = 0; i < arraygame_dataa.length() ; i++) {
                                    JSONObject welcome_bonusObject = arraygame_dataa.getJSONObject(i);

                                    //  GameStatus model = new GameStatus();
                                    game_id  = welcome_bonusObject.getString("id");
                                    txt_gameId.setText("GAME ID "+game_id);



                                    main_card  = welcome_bonusObject.getString("main_card");
                                    // txt_min_max.setText("Min-Max: "+main_card);
                                    status  = welcome_bonusObject.getString("status");
                                    winning  = welcome_bonusObject.getString("winning");
                                    String end_datetime  = welcome_bonusObject.getString("end_datetime");
                                    added_date  = welcome_bonusObject.getString("added_date");
                                    //  updated_date  = welcome_bonusObject.getString("updated_date");


                                    String uri1 = "@drawable/" + main_card.toLowerCase();  // where myresource " +
                                    int imageResource1 = getResources().getIdentifier(uri1, null,
                                            getPackageName());
                                    Picasso.with(context).load(imageResource1).into(imgmaincard);
                                    Picasso.with(context).load(imageResource1).into(imgmaincardsvaluehiostory);

                                    Random r = new Random();
                                    int randomNumber = r.nextInt(100);
                                    btnandarpercent.setText(String.valueOf(randomNumber)+"%");
                                    btnbaharpercent.setText(String.valueOf(100-randomNumber)+"%");


                                }
                                String onlineuSer = jsonObject.getString("online");
                                txt_online.setText("Online User "+onlineuSer);
                                JSONArray arrayprofile = jsonObject.getJSONArray("profile");

                                for (int i = 0; i < arrayprofile.length() ; i++) {
                                    JSONObject profileObject = arrayprofile.getJSONObject(i);

                                    //  GameStatus model = new GameStatus();
                                    user_id  = profileObject.getString("id");
                                    user_id_player1 = user_id;
                                    name  = profileObject.getString("name");
                                    wallet  = profileObject.getString("wallet");

                                    profile_pic  = profileObject.getString("profile_pic");

                                    Picasso.with(context).load(Const.IMGAE_PATH + profile_pic).into(imgpl1circle);

                                    //  txtBallence.setText(wallet);
                                    txtName.setText(name);

                                }


                                JSONArray arraypgame_cards = jsonObject.getJSONArray("game_cards");

                                for (int i = 0; i < arraypgame_cards.length() ; i++) {
                                    JSONObject cardsObject = arraypgame_cards.getJSONObject(i);

                                    //  GameStatus model = new GameStatus();
                                    String card  = cardsObject.getString("card");
                                    aaraycards.add(card);

                                }
//New Game Started here ------------------------------------------------------------------------
                                if (status.equals("0") && !isGameBegning){
                                    Random r = new Random();
                                    int randomNumber = r.nextInt(100);
                                    btnandarpercent.setText(String.valueOf(randomNumber)+"%");
                                    btnbaharpercent.setText(String.valueOf(100-randomNumber)+"%");

                                    txtGameRunning.setVisibility(View.GONE);
                                    if (counttimerforcards != null){
                                        counttimerforcards.cancel();
                                    }

                                    if (counttimerforcardsforAnimation != null){
                                        counttimerforcardsforAnimation.cancel();
                                    }
                                    canbet = true;
                                    txtBallence.setText(wallet);
                                    firstcount = 0;
                                    count = 0;
                                    countwidhtanadar = 41;
                                    widthandar = -430;
                                    countwidhtbahar = 41;
                                    widthbahar = -430;
                                    isConfirm = false;
                                    bet_id = "";
                                    betplace="";
//                                    imgCardsbahar.setVisibility(View.GONE);
//                                    imgCardsandar.setVisibility(View.GONE);
                                    aaraycards.clear();
                                    countvaue = 0;
                                    betcountandar = 1;
                                    isGameBegning = true;
                                    betvalue = "";
                                    Log.v("startgame" ,"start");
                                    lnrandarpatte.removeAllViews();
                                    lnrbaharpatte.removeAllViews();
                                    rltmainviewander.setVisibility(View.GONE);
                                    txt_catander.setText("0");
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.remove("tag").commit();
                                    setSelectedType("");

                                    rltmainviewbahar.setVisibility(View.GONE);
                                    txt_catbahar.setText("0");
                                    rlt1to5.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt6to10.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt11to15.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt16to25.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt26to30.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt31to35.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt36to40.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));
                                    rlt41more.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border));


                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                                    Date d = null;
                                    try {
                                        d = df1.parse(added_date);

                                    } catch (ParseException ex) {
                                        Log.v("Exception", ex.getLocalizedMessage());
                                    }
                                    long diff = (d.getTime() - c.getTime())/1000;
                                    System.out.println("");

                                    if (diff  > 0) {

                                        Log.i("circle", count+" time "+diff);
                                        long timefinal = 0;
                                        if (IsFirsttimeCall) {
                                            count =  (int) diff;
                                            timefinal = diff * 1000;
                                            IsFirsttimeCall = false;

                                        }else {
                                            count =  15;
                                            timefinal = 15000;

                                        }

                                        if (counttimerforstartgame != null) {
                                            counttimerforstartgame.cancel();
                                        }


                                        counttimerforstartgame = new CountDownTimer(timefinal, 1000) {

                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                count--;
                                                txtGameFinish.setVisibility(View.VISIBLE);
                                                txtGameFinish.setText("" + count);
                                                PlaySaund(R.raw.teenpattitick);

                                            }

                                            @Override
                                            public void onFinish() {

                                                canbet = false;
                                                if (betplace.length() > 0) {

                                                    if (betvalue.length() > 0) {
                                                        putbet(betplace);
                                                    }

                                                } else {
                                                    Toast.makeText(GameActivity.this, "You have not Bet yet. ", Toast.LENGTH_LONG).show();

                                                }
                                                txtGameFinish.setVisibility(View.INVISIBLE);
                                                getStatus();
                                                counttimerforstartgame.cancel();
                                            }


                                        };

                                        counttimerforstartgame.start();


                                    }else {
                                        // getStatus();
                                    }


                                }else if (status.equals("0") && isGameBegning){

                                }

//Game Started here
                                if (status.equals("1") && !isGameBegning){
                                    txtGameRunning.setVisibility(View.VISIBLE);

                                }

                                if (status.equals("1") && isGameBegning){


                                    isGameBegning = false;
                                    Log.v("game" ,"stoped");
                                    if (aaraycards.size() > 0){
                                        if (counttimerforcardsforAnimation != null){
                                            counttimerforcardsforAnimation.cancel();
                                        }

                                        if (counttimerforcards != null){
                                            counttimerforcards.cancel();
                                        }


                                        counttimerforcards = new CountDownTimer(aaraycards.size()*1000, 1000) {

                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                isCardsDisribute = true;
                                                int y = countvaue % 2;

                                                makeWinnertoPlayer("");
                                                makeLosstoPlayer("");


                                                if (y == 0) {



                                                    int chieldcount =   lnrandarpatte.getChildCount();
                                                    View view = null;
                                                    if (chieldcount > 0){
                                                        view = lnrandarpatte.getChildAt(chieldcount-1);
                                                    }else {
                                                        view = lnrandarpatte.getChildAt(chieldcount);
                                                    }


                                                    int[] locationimgandar = new int[2];
                                                    int[] locationlnrandar = new int[2];
                                                    imgCardsandar.getLocationOnScreen(locationimgandar);
                                                    lnrandarpatte.getLocationOnScreen(locationlnrandar);

                                                    //----------------------For Width ----------------
                                                    int[] locationimgandarwidth = new int[2];
                                                    int[] locationlnrandarwidth = new int[2];
                                                    imgCardsandar.getLocationOnScreen(locationimgandarwidth);
                                                    if (view != null){
                                                        view.getLocationOnScreen(locationlnrandarwidth);
                                                    }else {

                                                    }

                                                    TranslateAnimation animation = null;
                                                    int finalwidht = 0;

                                                    if (locationimgandarwidth[0] < 2){

                                                    }else {
                                                        finalwidht = locationlnrandarwidth[0]-locationimgandarwidth[0] ;
                                                        animation = new TranslateAnimation(0, finalwidht,0 , locationlnrandar[1] - locationimgandar[1]);
                                                        animation.setRepeatMode(0);
                                                        animation.setDuration(300);
                                                        animation.setFillAfter(false);
                                                        imgCardsandar.setVisibility(View.VISIBLE);
//
                                                        imgCardsandar.startAnimation(animation);

                                                        firstcount++;
                                                        //  Log.v("width-1" , locationlnrandarwidth[0] +" / "+locationimgandarwidth[0]);


                                                    }

                                                    //   Log.v("width-1" , locationlnrandarwidth[0] +" / "+locationimgandarwidth[0]);
                                                    addCardsAndar(aaraycards.get(countvaue),countvaue);



                                                }else {


                                                    int finalwidhtbahar = 0;
                                                    int chieldcount =   lnrbaharpatte.getChildCount();
                                                    View view1 = null;
                                                    if (chieldcount > 0){
                                                        view1 = lnrbaharpatte.getChildAt(chieldcount-1);
                                                    }else {
                                                        view1 = lnrbaharpatte.getChildAt(chieldcount);
                                                    }

                                                    int[] locationimgbaharwidth = new int[2];
                                                    int[] locationlnrbaharwidth = new int[2];
                                                    imgCardsbahar.getLocationOnScreen(locationimgbaharwidth);
                                                    if (view1 != null){
                                                        view1.getLocationOnScreen(locationlnrbaharwidth);
                                                    }else {

                                                    }

                                                    //----------------------Close ---------------------


                                                    int[] locationimgbahar = new int[2];
                                                    int[] locationlnrbahar = new int[2];
                                                    imgCardsbahar.getLocationOnScreen(locationimgbahar);
                                                    lnrbaharpatte.getLocationOnScreen(locationlnrbahar);


                                                    if (locationimgbaharwidth[0] < 2) {


                                                    }else {

                                                        finalwidhtbahar = locationlnrbaharwidth[0]-locationimgbaharwidth[0] ;
                                                        TranslateAnimation animation = new TranslateAnimation(0, finalwidhtbahar,0 , locationlnrbahar[1]-locationimgbahar[1]);
                                                        animation.setRepeatMode(0);
                                                        animation.setDuration(300);
                                                        animation.setFillAfter(false);
                                                        imgCardsbahar.startAnimation(animation);
                                                    }

                                                    //   Log.v("width" , locationimgbaharwidth +" / "+locationlnrbaharwidth[0]);
                                                    addCardsBahar(aaraycards.get(countvaue),countvaue);
                                                }


                                                if (countvaue > 0 && countvaue < 6){
                                                    rlt1to5.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 5 && countvaue < 11){
                                                    rlt6to10.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 10 && countvaue < 16){
                                                    rlt11to15.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 15 && countvaue < 26){
                                                    rlt16to25.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 25 && countvaue < 31){
                                                    rlt26to30.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 30 && countvaue < 36){
                                                    rlt31to35.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 35 && countvaue < 41){
                                                    rlt36to40.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                if (countvaue > 41){
                                                    rlt41more.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.background_border_withcolor));

                                                }

                                                countvaue++;
                                            }

                                            @Override
                                            public void onFinish() {

//                                                getStatus();
                                                //secondtimestart(18);
                                                txtGameRunning.setVisibility(View.VISIBLE);
                                                isCardsDisribute = false;

                                                if(betplace != null && !betplace.equalsIgnoreCase("") && betplace.equalsIgnoreCase(winning))
                                                {
                                                    makeWinnertoPlayer(SharePref.getU_id());
                                                }
                                                else {

                                                    if(betplace != null && !betplace.equalsIgnoreCase("") && !betplace.equalsIgnoreCase(winning))
                                                    {
                                                        makeLosstoPlayer(SharePref.getU_id());
                                                    }

                                                }


                                            }


                                        };

                                        counttimerforcards.start();


                                    }



                                }else {


                                }

                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(GameActivity.this, message,
                                            Toast.LENGTH_LONG).show();


                                }


                            }

                            if (status.equals("1")) {
                                //  txtGameRunning.setVisibility(View.VISIBLE);
                                txtGameBets.setVisibility(View.GONE);
                            } else {
                                // txtGameRunning.setVisibility(View.GONE);
                                txtGameBets.setVisibility(View.VISIBLE);
                                makeWinnertoPlayer("");
                                makeLosstoPlayer("");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(GameActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));

                params.put("room_id", room_id);
                // params.put("token", "9959cbfce148e91db099d4936b1a9b66");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void putbet(final String type) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.PUTBET,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        Funtions.LOGE("putbet",Const.PUTBET+"\n"+response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");


                            if (code.equalsIgnoreCase("200")) {
                                bet_id = jsonObject.getString("bet_id");
                                wallet = jsonObject.getString("wallet");
                                txtBallence.setText(wallet);
                                Toast.makeText(GameActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                                if (type.equals("0")){
                                    rltmainviewander.setVisibility(View.VISIBLE);
                                    txt_catander.setText(""+betvalue);

                                }else {
                                    rltmainviewbahar.setVisibility(View.VISIBLE);
                                    txt_catbahar.setText(""+betvalue);
                                }

                                betvalue = "";


                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(GameActivity.this, message,
                                            Toast.LENGTH_LONG).show();


                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(GameActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                params.put("game_id", game_id);
                params.put("bet", type);
                params.put("amount", betvalue);
                //  params.put("token", "9959cbfce148e91db099d4936b1a9b66");
                Funtions.LOGE("putbet",Const.PUTBET+"\n"+params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void cancelbet() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.CENCEL_BET,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equals("200")){

                                wallet = jsonObject.getString("wallet");
                                txtBallence.setText(wallet);
                                rltmainviewander.setVisibility(View.GONE);
                                rltmainviewbahar.setVisibility(View.GONE);


                            }
                            Toast.makeText(GameActivity.this, message, Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(GameActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));

                params.put("game_id", game_id);
                params.put("bet_id", bet_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void repeatBet() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.REPEAT_BET,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.v("Repeat Responce" , response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equals("200")){

                                wallet = jsonObject.getString("wallet");
                                String  bet = jsonObject.getString("bet");
                                // bet_id = jsonObject.getString("bet_id");
                                String amount = jsonObject.getString("amount");
                                txtBallence.setText(wallet);
                                betvalue = amount;
                                betplace = bet;
                                if (bet.equals("0")){

                                    rltmainviewander.setVisibility(View.VISIBLE);

                                    txt_catander.setText(""+amount);
                                }else {
                                    txt_catbahar.setText(""+amount);

                                    rltmainviewbahar.setVisibility(View.VISIBLE);
                                }

                            }
                            Toast.makeText(GameActivity.this, message, Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(GameActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));

                params.put("game_id", game_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (counttimerforstartgame != null){
            counttimerforstartgame.cancel();
        }

        if (counttimerforcards != null){
            counttimerforcards.cancel();
        }

//        if (mp !=null ){
//            mp.stop();
//           // mp.release();
//        }

        if (timerstatus !=null ){
            timerstatus.cancel();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        isInPauseState = false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        isInPauseState = true;
//        if (mp !=null ){
//            mp.stop();
//            mp.release();
//        }

    }

    public void secondtimestart(long timevalue){
        Log.v("secondtimelog" , "start again");

        counttimerforstartgame = new CountDownTimer(timevalue, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                count--;
                txtGameFinish.setVisibility(View.VISIBLE);
                txtGameFinish.setText("" + count);
                PlaySaund(R.raw.teenpattitick);

            }

            @Override
            public void onFinish() {

                canbet = false;
                if (betplace.length() > 0) {

                    if (betvalue.length() > 0) {
                        putbet(betplace);
                    }

                } else {
                    Toast.makeText(GameActivity.this, "You have not Bet yet. ", Toast.LENGTH_LONG).show();

                }
                txtGameFinish.setVisibility(View.INVISIBLE);
                getStatus();
                counttimerforstartgame.cancel();
            }


        };

        counttimerforstartgame.start();


    }

    String user_id_player1 = "";
    RelativeLayout rltwinnersymble1;
    public void makeWinnertoPlayer(String chaal_user_id) {

        rltwinnersymble1.setVisibility(View.GONE);


        if (chaal_user_id.equals(user_id_player1)) {
            PlaySaund(R.raw.tpb_battle_won);
            rltwinnersymble1.setVisibility(View.VISIBLE);

        }

    }

    public void makeLosstoPlayer(String chaal_user_id) {

        rltwinnersymble1.setVisibility(View.GONE);
        rtllosesymble1.setVisibility(View.GONE);

        if (chaal_user_id.equals(user_id_player1)) {
            PlaySaund(R.raw.tpb_battle_won);
            rtllosesymble1.setVisibility(View.VISIBLE);

        }

    }

}