package com.gamegards.allinonev3.MyAccountDetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.RedeemCoins.RedeemModel;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.gamegards.allinonev3.Utils.Funtions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.gamegards.allinonev3.MyAccountDetails.MyWinnigmodel.Andhar_Bahar;
import static com.gamegards.allinonev3.MyAccountDetails.MyWinnigmodel.RUMMY;
import static com.gamegards.allinonev3.MyAccountDetails.MyWinnigmodel.TEEN_PATTI;


public class MyWinningAcitivity extends AppCompatActivity {

    RecyclerView rec_winning;
    MyWinningAdapte myWinningAdapte;
    ArrayList<MyWinnigmodel> myWinnigmodelArrayList;
    private static final String MY_PREFS_NAME = "Login_data" ;
    TextView tb_name,nofound;

    ProgressBar progressBar;
    Activity context = this;

     TextView tvGameRecord;
     TextView tvPurchase;
     TextView tvGame;

     TextView tvRedeemhistory;

//     View image_arrow;
//     View image_arrow1;
//     View image_arrow2;


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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_winning_acitivity);

        progressBar = findViewById(R.id.progressBar);
        rec_winning = findViewById(R.id.recylerview_gifts);
        rec_winning.setLayoutManager(new LinearLayoutManager(this));

        tb_name = findViewById(R.id.txtheader);
        nofound = findViewById(R.id.txtnotfound);
        tb_name.setText("Game History");

        ((ImageView) findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




         tvGameRecord = findViewById(R.id.tvGameRecord);
         tvPurchase = findViewById(R.id.tvPurchase);
         tvGame = findViewById(R.id.tvGame);

        tvRedeemhistory = findViewById(R.id.tvRedeemhistory);

//        image_arrow = findViewById(R.id.image_arrow);
//        image_arrow1 = findViewById(R.id.image_arrow1);
//        image_arrow2 = findViewById(R.id.image_arrow2);


        onClickView();
        UserWinnigAPI(TEEN_PATTI);
    }

    private void onClickView(){
        ((View) findViewById(R.id.tvRedeemhistory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ManageTabs(RedeemModel.REDEEM_LIST);

                tvGame.setText("Redeem Number");


                UsersRedeemList();



            }
        });



        ((View) findViewById(R.id.tvGameRecord)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ManageTabs(RedeemModel.GAME_LIST);


                tvGame.setText("Game");



                UserWinnigAPI(TEEN_PATTI);



            }
        });

        ((View) findViewById(R.id.tvPurchase)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ManageTabs(RedeemModel.TRANSACTION_LIST);


                tvGame.setText("Pay");

//                image_arrow.setVisibility(View.GONE);
//                image_arrow1.setVisibility(View.VISIBLE);

                getPuchaseListAPI();
            }
        });

        ((View) findViewById(R.id.tvTeenpatti)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserWinnigAPI(TEEN_PATTI);

            }
        });

        ((View) findViewById(R.id.tvRummy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserWinnigAPI(RUMMY);


            }
        });

        ((View) findViewById(R.id.tvandharbahar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CALL_API_getANDHAR_BAHAR();

            }
        });
    }

    private void ManageGameType(String viewType){

        getTextView(R.id.tvTeenpatti).setTextColor(viewType.equalsIgnoreCase(TEEN_PATTI)
                ? Funtions.getColor(context,R.color.red)
                : Funtions.getColor(context,R.color.white)
                );

        getTextView(R.id.tvRummy).setTextColor(viewType.equalsIgnoreCase(RUMMY)
                ? Funtions.getColor(context,R.color.red)
                : Funtions.getColor(context,R.color.white)
        );

        getTextView(R.id.tvandharbahar).setTextColor(viewType.equalsIgnoreCase(Andhar_Bahar)
                ? Funtions.getColor(context,R.color.red)
                : Funtions.getColor(context,R.color.white)
        );



    }

    private TextView getTextView(int id){

        return ((TextView)findViewById(id));
    }

    private void ManageTabs(int viewType){

        findViewById(R.id.lnrGamesType).setVisibility(viewType == RedeemModel.GAME_LIST
                ? View.VISIBLE : View.GONE);


        tvGameRecord.setBackground(viewType == RedeemModel.GAME_LIST
                ? getDrawable(R.drawable.d_orange_corner)
                : getDrawable(R.drawable.d_white_corner));

        tvGameRecord.setTextColor(viewType == RedeemModel.GAME_LIST
                ? getResources().getColor(R.color.white)
                : getResources().getColor(R.color.black));

//        image_arrow.setVisibility(viewType == RedeemModel.GAME_LIST
//                ? View.VISIBLE
//                : View.GONE);



        tvPurchase.setBackground(viewType == RedeemModel.TRANSACTION_LIST
                ? getDrawable(R.drawable.d_orange_corner)
                : getDrawable(R.drawable.d_white_corner));

        tvPurchase.setTextColor(viewType == RedeemModel.TRANSACTION_LIST
                ? getResources().getColor(R.color.white)
                : getResources().getColor(R.color.black));

//        image_arrow1.setVisibility(viewType == RedeemModel.TRANSACTION_LIST
//                ? View.VISIBLE
//                : View.GONE);


        tvRedeemhistory.setBackground(viewType == RedeemModel.REDEEM_LIST
                ? getDrawable(R.drawable.d_orange_corner)
                : getDrawable(R.drawable.d_white_corner));

        tvRedeemhistory.setTextColor(viewType == RedeemModel.REDEEM_LIST
                ? getResources().getColor(R.color.white)
                : getResources().getColor(R.color.black));

//        image_arrow2.setVisibility(viewType == RedeemModel.REDEEM_LIST
//                ? View.VISIBLE
//                : View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void UsersRedeemList() {


        final ArrayList<RedeemModel> redeemModelArrayList = new ArrayList();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_Redeem_History_LIST,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Funtions.LOGE("MyWinningActivity",""+Const.USER_Redeem_History_LIST+"\n"+response);

                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            Log.d("response", "onResponse: " + response);

                            if (code.equalsIgnoreCase("200")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("List");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    RedeemModel model = new RedeemModel();
                                    model.setId(jsonObject1.getString("id"));
                                    model.setCoin(jsonObject1.getString("coin"));
                                    model.setMobile(jsonObject1.getString("mobile"));
                                    model.setUser_name(jsonObject1.getString("user_name"));
                                    model.setUser_mobile(jsonObject1.getString("user_mobile"));
                                    model.setStatus(jsonObject1.getString("status"));
                                    model.setUpdated_date(jsonObject1.getString("updated_date"));
                                    model.ViewType = RedeemModel.REDEEM_LIST;


                                    redeemModelArrayList.add(model);
                                }

                                Collections.reverse(redeemModelArrayList);


                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(context, message,
                                            Toast.LENGTH_LONG).show();


                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }

                        UserRedeemHistoryAdapter userWinnerAdapter = new UserRedeemHistoryAdapter(context, redeemModelArrayList);
                        rec_winning.setAdapter(userWinnerAdapter);




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
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


    private void UserWinnigAPI(final String type){

        ManageGameType(type);

        final ProgressDialog progressDialog = new ProgressDialog(MyWinningAcitivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Listing...");
        progressDialog.show();

        myWinnigmodelArrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_WINNIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Funtions.LOGE("MyWinningActivity",""+Const.USER_WINNIG+"\n"+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String code = jsonObject.getString("code");

                            if(code.equals("200"))
                            {


                                JSONArray ListArray = jsonObject.getJSONArray("GameWins");
                                if(ListArray.length() > 0)
                                {


                                    JSONArray arraygame_dataa = jsonObject.optJSONArray("GameLog");
                                    if(arraygame_dataa != null)
                                    {
                                        for (int i = 0; i < arraygame_dataa.length(); i++) {
                                            JSONObject welcome_bonusObject = arraygame_dataa.getJSONObject(i);

                                            MyWinnigmodel model = new MyWinnigmodel();
                                            model.setId(welcome_bonusObject.getString("id"));
                                            model.setAnder_baher_id(welcome_bonusObject.getString("ander_baher_id"));
                                            model.setAdded_date(welcome_bonusObject.getString("added_date"));
                                            model.setAmount(welcome_bonusObject.getString("amount"));
                                            model.setWinning_amount(welcome_bonusObject.getString("winning_amount"));
                                            model.amount = welcome_bonusObject.optString("winning_amount");
                                            model.invest = welcome_bonusObject.optInt("invest",0);
                                            model.setBet(welcome_bonusObject.getString("bet"));
                                            model.setRoom_id(welcome_bonusObject.getString("room_id"));
                                            model.game_type = Andhar_Bahar;
                                            model.ViewType = RedeemModel.GAME_LIST;
                                            myWinnigmodelArrayList.add(model);

                                        }
                                    }

                                    if(type.equalsIgnoreCase(TEEN_PATTI))
                                    {

                                        JSONArray TeenPattiGameLog = jsonObject.optJSONArray("TeenPattiGameLog");
                                        if(TeenPattiGameLog != null)
                                        {
                                            for (int i = 0; i < TeenPattiGameLog.length() ; i++) {

                                                JSONObject ListObject= TeenPattiGameLog.getJSONObject(i);
                                                MyWinnigmodel usermodel = new MyWinnigmodel();

                                                usermodel.id = ListObject.optString("game_id");
                                                usermodel.table_id = ListObject.optString("game_id");
                                                usermodel.amount = ListObject.optString("winning_amount");
                                                usermodel.invest = ListObject.optInt("invest",0);
                                                usermodel.winner_id = ListObject.optString("winner_id");
                                                usermodel.added_date = ListObject.optString("added_date");
                                                usermodel.game_type = TEEN_PATTI;
                                                usermodel.ViewType = RedeemModel.GAME_LIST;

                                                myWinnigmodelArrayList.add(usermodel);
                                            }
                                        }

                                        Collections.reverse(myWinnigmodelArrayList);
                                    }


                                    if(type.equalsIgnoreCase(RUMMY))
                                    {
                                        JSONArray RummyGameLog = jsonObject.optJSONArray("RummyGameLog");
                                        if(RummyGameLog != null)
                                        {
                                            for (int i = 0; i < RummyGameLog.length() ; i++) {

                                                JSONObject ListObject= RummyGameLog.getJSONObject(i);
                                                MyWinnigmodel usermodel = new MyWinnigmodel();

                                                usermodel.id = ListObject.optString("game_id");
                                                usermodel.table_id = ListObject.optString("game_id");

                                                int win_amount = ListObject.optInt("amount",0);
                                                if(win_amount > 0)
                                                    usermodel.amount = ListObject.optString("amount");

                                                usermodel.invest = ListObject.optInt("amount",0);
                                                usermodel.winner_id = ListObject.optString("winner_id");
                                                usermodel.added_date = ListObject.optString("added_date");
                                                usermodel.game_type = RUMMY;
                                                usermodel.ViewType = RedeemModel.GAME_LIST;

                                                myWinnigmodelArrayList.add(usermodel);
                                            }
                                        }

                                    }






                                    myWinningAdapte = new MyWinningAdapte(MyWinningAcitivity.this,myWinnigmodelArrayList);
                                    rec_winning.setAdapter(myWinningAdapte);

                                }
                                else {
                                    nofound.setVisibility(View.VISIBLE);
                                }

                            }
                            else {
                                nofound.setVisibility(View.VISIBLE);
                            }







                        } catch (JSONException e) {
                            e.printStackTrace();
                            nofound.setVisibility(View.VISIBLE);

                        }




                        progressDialog.dismiss();
                        HideProgressBar(true);



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                HideProgressBar(true);
                Toast.makeText(MyWinningAcitivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                nofound.setVisibility(View.VISIBLE);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id",prefs.getString("user_id", ""));
//                params.put("user_id","54");
                Funtions.LOGE("MyWinningActivity",""+Const.USER_WINNIG+"\n"+params);

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

    private void CALL_API_getANDHAR_BAHAR() {

        ManageGameType(Andhar_Bahar);

        myWinnigmodelArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.GETHISTORY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();

                        Funtions.LOGE("CALL_API_getANDHAR_BAHAR",""+Const.GETHISTORY+"\n"+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equalsIgnoreCase("200")) {


                                JSONArray arraygame_dataa = jsonObject.optJSONArray("GameLog");
                                if(arraygame_dataa != null)
                                {
                                    for (int i = 0; i < arraygame_dataa.length(); i++) {
                                        JSONObject welcome_bonusObject = arraygame_dataa.getJSONObject(i);

                                        MyWinnigmodel model = new MyWinnigmodel();
                                        model.setId(welcome_bonusObject.getString("id"));
                                        model.setAnder_baher_id(welcome_bonusObject.getString("ander_baher_id"));
                                        model.setAdded_date(welcome_bonusObject.getString("added_date"));
                                        model.setAmount(welcome_bonusObject.getString("amount"));
                                        model.setWinning_amount(welcome_bonusObject.getString("winning_amount"));
                                        model.amount = welcome_bonusObject.optString("winning_amount");
                                        model.invest = welcome_bonusObject.optInt("amount",0);
                                        model.setBet(welcome_bonusObject.getString("bet"));
                                        model.setRoom_id(welcome_bonusObject.getString("room_id"));
                                        model.game_type = Andhar_Bahar;
                                        model.ViewType = RedeemModel.GAME_LIST;
                                        myWinnigmodelArrayList.add(model);

                                    }
                                }


                            }

                            Collections.reverse(myWinnigmodelArrayList);

                            myWinningAdapte = new MyWinningAdapte(MyWinningAcitivity.this,myWinnigmodelArrayList);
                            rec_winning.setAdapter(myWinningAdapte);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                params.put("room_id", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);

    }


    private void getPuchaseListAPI(){


        final ProgressDialog progressDialog = new ProgressDialog(MyWinningAcitivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Listing...");
        progressDialog.show();

        final ArrayList<MyWinnigmodel> redeemModelArrayList = new ArrayList();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_WINNIG,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Funtions.LOGE("MyWinningActivity",""+Const.USER_WINNIG+"\n"+response);

                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            Log.d("response", "onResponse: " + response);

                            if (code.equalsIgnoreCase("200")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("AllPurchase");
//                                JSONArray WalletLog = jsonObject.getJSONArray("WalletLog");
//
//                                for (int i = 0; i < WalletLog.length(); i++) {
//                                    JSONObject jsonObject1 = WalletLog.getJSONObject(i);
//
//                                    MyWinnigmodel model = new MyWinnigmodel();
//                                    model.setId(jsonObject1.optString("id"));
//                                    model.setCoin(jsonObject1.optString("coin","-"));
//                                    model.price = jsonObject1.optString("price","admin");
//                                    model.added_date = jsonObject1.optString("added_date","-");
//                                    model.setUpdated_date(jsonObject1.optString("added_date","-"));
//                                    model.ViewType = RedeemModel.TRANSACTION_LIST;
//
//
////                                    redeemModelArrayList.add(model);
//                                }


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    MyWinnigmodel model = new MyWinnigmodel();
                                    model.setId(jsonObject1.optString("id"));
                                    model.setCoin(jsonObject1.optString("coin","-"));
                                    model.price = jsonObject1.optString("price","admin");
                                    model.added_date = jsonObject1.optString("updated_date");
                                    model.setUpdated_date(jsonObject1.optString("updated_date"));
                                    model.ViewType = RedeemModel.TRANSACTION_LIST;


                                    redeemModelArrayList.add(model);
                                }

                                myWinningAdapte = new MyWinningAdapte(MyWinningAcitivity.this,redeemModelArrayList);
                                rec_winning.setAdapter(myWinningAdapte);
                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(context, message,
                                            Toast.LENGTH_LONG).show();


                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }

                        progressDialog.dismiss();


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
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

    private void HideProgressBar(boolean visible){
        progressBar.setVisibility(!visible ? View.VISIBLE : View.GONE);
    }

}
