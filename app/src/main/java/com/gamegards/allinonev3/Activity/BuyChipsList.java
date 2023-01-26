package com.gamegards.allinonev3.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gamegards.allinonev3.Adapter.ChipsBuyAdapter;
import com.gamegards.allinonev3.model.ChipsBuyModel;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.gamegards.allinonev3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.gamegards.allinonev3.Utils.Funtions.SetBackgroundImageAsDisplaySize;


public class BuyChipsList extends AppCompatActivity {
    private static final String MY_PREFS_NAME = "Login_data";
    //ImageView img_back;
    ArrayList<ChipsBuyModel> historyModelArrayList;
    ChipsBuyAdapter historyAdapter;
    RecyclerView rec_history;
    ProgressDialog progressDialog;
    LinearLayout linear_no_history;
    ImageView imgback;

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

//        if(!getIntent().hasExtra("homepage"))
//        {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//        else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        }


        setContentView(R.layout.activity_chips_list);

        RelativeLayout relativeLayout = findViewById(R.id.rlt_parent);
        SetBackgroundImageAsDisplaySize(this,relativeLayout,R.drawable.bghome);



        // img_back=findViewById(R.id.img_back);
        rec_history=findViewById(R.id.rec_history);
        linear_no_history=findViewById(R.id.linear_no_history);
        imgback = findViewById(R.id.imgclosetop);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);

        getChipsList();

        rec_history.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void getChipsList(){
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Const.GET_CHIP_PLAN,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code=jsonObject.getString("code");
                    String message=jsonObject.getString("message");
                    if (code.equals("200")){
                        progressDialog.dismiss();
                        JSONArray jsonArray=jsonObject.getJSONArray("PlanDetails");
                        historyModelArrayList=new ArrayList<>();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            ChipsBuyModel model=new ChipsBuyModel();
                            model.setId(jsonObject1.getString("id"));
                            model.setProname(jsonObject1.getString("coin"));
                            model.setAmount(jsonObject1.getString("price"));
                           // model.setTicket_id(jsonObject1.getString("desc"));

                            historyModelArrayList.add(model);
                        }

                        historyAdapter=new ChipsBuyAdapter(BuyChipsList.this,historyModelArrayList);
                        rec_history.setAdapter(historyAdapter);
                    }
                    else {
                        linear_no_history.setVisibility(View.VISIBLE);
                        // Toast.makeText(HistoryActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header= new HashMap<>();
                header.put("token",Const.TOKEN);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                HashMap<String,String> params= new HashMap<>();
                params.put("token", prefs.getString("token", ""));
                params.put("user_id", prefs.getString("user_id", ""));
                //params.put("user_id", SharedPref.getVal(HistoryActivity.this,SharedPref.id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BuyChipsList.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
