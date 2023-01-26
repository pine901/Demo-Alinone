package com.gamegards.allinonev3._AdharBahar.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gamegards.allinonev3._AdharBahar.Adapter.GamesHistoryAdapter;
import com.gamegards.allinonev3._AdharBahar.Model.GameHistory;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.SampleClasses.Const;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }
    public static final String MY_PREFS_NAME = "Login_data";
    Activity context;
    View view;
    RecyclerView recyclerView;
    GamesHistoryAdapter adapter;
    ArrayList<GameHistory> gameModelArrayList = new ArrayList<>();
    TextView txtNodata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_game, container, false);

        context = getActivity();
        Initialization();
        getAllHistry();
        return view;
    }

    private void Initialization() {
        recyclerView = view.findViewById(R.id.rec_gameslist);
        txtNodata = view.findViewById(R.id.txtNodata);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new GamesHistoryAdapter(context,gameModelArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void getAllHistry() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.GETHISTORY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equalsIgnoreCase("200")) {


                                JSONArray arraygame_dataa = jsonObject.getJSONArray("GameLog");

                                for (int i = 0; i < arraygame_dataa.length(); i++) {
                                    JSONObject welcome_bonusObject = arraygame_dataa.getJSONObject(i);

                                    GameHistory model = new GameHistory();
                                    model.setId(welcome_bonusObject.getString("id"));
                                    model.setAnder_baher_id(welcome_bonusObject.getString("ander_baher_id"));
                                    model.setAdded_date(welcome_bonusObject.getString("added_date"));
                                    model.setAmount(welcome_bonusObject.getString("amount"));
                                    model.setWinning_amount(welcome_bonusObject.getString("winning_amount"));
                                    model.setBet(welcome_bonusObject.getString("bet"));
                                    model.setRoom_id(welcome_bonusObject.getString("room_id"));
                                    gameModelArrayList.add(model);

                                }


                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (gameModelArrayList.size() > 0){
                            txtNodata.setVisibility(View.GONE);
                        }else {
                            txtNodata.setVisibility(View.VISIBLE);
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
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

        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


}