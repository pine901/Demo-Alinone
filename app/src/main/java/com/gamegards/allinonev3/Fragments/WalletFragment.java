package com.gamegards.allinonev3.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.gamegards.allinonev3._TeenPatti.PublicTable.MY_PREFS_NAME;

public class WalletFragment extends Fragment implements AdapterView.OnItemSelectedListener  {


    SharedPreferences prefs ;
    public WalletFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    String selectedType = "";
    EditText edtAmount;
    EditText edtMobileNo;
    View view;
    Spinner spinner;
    TextView txtAccountBallence,txtwinningamount;
    TextView btnAddCash;
    TextView btnWithdrwa;
    String order_id,RazorPay_ID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        Initialization();
        return view;
    }

    private void Initialization() {
        prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        txtAccountBallence = view.findViewById(R.id.txtAccountBallence);
        btnAddCash = view.findViewById(R.id.btnAddCash);
        btnWithdrwa = view.findViewById(R.id.btnWithdrwa);
        txtwinningamount = view.findViewById(R.id.txtwinningamount);
        txtAccountBallence.setText(prefs.getString("wallet", "0"));
        txtwinningamount.setText(prefs.getString("winning_amount", "0"));
        btnAddCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withEditText(view);
            }
        });
        btnWithdrwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdraw(view);
              //  Toast.makeText(getContext(), "Need to Discuss", Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Initialization();
    }

    public void withEditText(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.alert_dialog_addballence, null);
         edtAmount=dialogLayout.findViewById(R.id.edtAmount);
        Button btnSubmit=dialogLayout.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                place_order(edtAmount.getText().toString());
            }
        });
        builder.setView(dialogLayout);

        builder.show();
    }





    public void withdraw(View view) {

        final Dialog builder = new Dialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.withdraw_item, null);
        builder.setContentView(dialogLayout);

        edtMobileNo=dialogLayout.findViewById(R.id.edtMobileNo);
        edtAmount=dialogLayout.findViewById(R.id.edtAmount);
        spinner=dialogLayout.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Paytm");
        categories.add("Phone Pe");
        categories.add("Google Pay");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        TextView btnSubmit=dialogLayout.findViewById(R.id.btnSubmit);

//        final AlertDialog dialog = builder.create();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtMobileNo.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(edtMobileNo.getText().length()!=10){
                    Toast.makeText(getActivity(), "Enter Valid 10-digits Mobile Number ", Toast.LENGTH_SHORT).show();
                    edtMobileNo.requestFocus();
                }
                else if(edtAmount.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Enter Valid Amount!", Toast.LENGTH_SHORT).show();
                    edtAmount.requestFocus();

                }
                else {
                    builder.dismiss();
                    redeem(edtMobileNo.getText().toString(),edtAmount.getText().toString().trim());
                }
            }
        });


        builder.show();
        Window window = builder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void redeem(final String mob,final String amount){


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Payment..");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.redeem,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equals("200")){


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onResume();
                                    }
                                },500);

                                Toast.makeText(getActivity(),""+message,Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(getActivity(),""+message,Toast.LENGTH_LONG).show();

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //  handleResponse(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                params.put("redeem_mobile",mob);
                params.put("payment_method",selectedType);
                params.put("amount",amount+"");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }


    public void place_order(final String amount){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Const.PLCE_ORDER,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String code=jsonObject.getString("code");
                            String message=jsonObject.getString("message");

                            if (code.equals("200")){

                                order_id=jsonObject.getString("order_id");
                                String Total_Amount=jsonObject.getString("Total_Amount");
                                RazorPay_ID=jsonObject.getString("RazorPay_ID");
                                startPayment(order_id,Total_Amount,RazorPay_ID);
                            }
                            else  if (code.equals("404")) {
                                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // NoInternet(listTicket.this);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header= new HashMap<>();
                header.put("token",Const.TOKEN);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params= new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                params.put("amount", amount);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    public void startPayment( String ticket_id, String total_Amount, String razorPay_ID) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        //final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            JSONObject options = new JSONObject();
            options.put("name",  prefs.getString("name", ""));
            options.put("description", "chips payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", total_Amount);
            options.put("order_id", razorPay_ID);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "support@androappstech.com");
            preFill.put("contact",  prefs.getString("mobile", ""));
            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }



//    @Override
//    public void onPaymentSuccess(String razorpayPaymentID) {
//        try {
//            payNow(razorpayPaymentID);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        try {
//            Toast.makeText(getContext(), "Payment failed: " +i + s, Toast
//             .LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentError", e);
//        }
//    }


    public void payNow(final String payment_id){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Const.PY_NOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String code=jsonObject.getString("code");
                            String message=jsonObject.getString("message");

                            if (code.equals("200")){
                                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
                                dialog_payment_success();
                            }
                            else  if (code.equals("404")) {
                                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // NoInternet(listTicket.this);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header= new HashMap<>();
                header.put("token",Const.TOKEN);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params= new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                params.put("order_id", order_id);
                params.put("payment_id", payment_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    private void dialog_payment_success(){

        new SmartDialogBuilder(getContext())
                .setTitle("Your Payment has been done Successfully!")
                .setSubTitle("Your Payment has been done Successfully!")
                .setCancalable(false)

                //.setTitleFont("Do you want to Logout?") //set title font
                // .setSubTitleFont(subTitleFont) //set sub title font
                .setNegativeButtonHide(true) //hide cancel button
                .setPositiveButton("Ok", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                        smartDialog.dismiss();
                       getActivity().onBackPressed();
                    }
                }).setNegativeButton("Cancel", new SmartDialogClickListener() {
            @Override
            public void onClick(SmartDialog smartDialog) {
                // Toast.makeText(context,"Cancel button Click",Toast.LENGTH_SHORT).show();
                smartDialog.dismiss();

            }
        }).build().show();



    }


}