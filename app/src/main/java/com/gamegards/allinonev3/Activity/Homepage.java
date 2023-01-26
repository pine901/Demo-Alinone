package com.gamegards.allinonev3.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gamegards.allinonev3.Adapter.UserWinnerAdapter;
import com.gamegards.allinonev3.Adapter.WelcomeRewardAdapter;
import com.gamegards.allinonev3.Fragments.ActiveTables_BF;
import com.gamegards.allinonev3.Fragments.UserInformation_BT;
import com.gamegards.allinonev3.Interface.ApiRequest;
import com.gamegards.allinonev3.MyAccountDetails.MyWinningAcitivity;
import com.gamegards.allinonev3.RedeemCoins.RedeemActivity;
import com.gamegards.allinonev3.RedeemCoins.RedeemModel;
import com.gamegards.allinonev3.MyAccountDetails.UserRedeemHistoryAdapter;
import com.gamegards.allinonev3.SampleClasses.CommonFunctions;
import com.gamegards.allinonev3.Utils.SharePref;
import com.gamegards.allinonev3.Utils.Variables;
import com.gamegards.allinonev3._AdharBahar.AndharBahar_Home_A;
import com.gamegards.allinonev3.SampleClasses.Const;
import com.gamegards.allinonev3.Interface.Callback;
import com.gamegards.allinonev3.R;
import com.gamegards.allinonev3._TeenPatti.CustomsiedTablev3;
import com.gamegards.allinonev3._TeenPatti.PrivateTablev2;
import com.gamegards.allinonev3.Utils.Animations;
import com.gamegards.allinonev3.Utils.FileUtils;
import com.gamegards.allinonev3.Utils.Funtions;
import com.gamegards.allinonev3.model.MyWinnigmodel;
import com.gamegards.allinonev3.model.WelcomeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.gamegards.allinonev3.Utils.Funtions.SetBackgroundImageAsDisplaySize;
import static com.gamegards.allinonev3.Utils.Funtions.convertDpToPixel;

public class Homepage extends AppCompatActivity {

    Animation animBounce, animBlink;
    private static final String MY_PREFS_NAME = "Login_data";
    ImageView imgLogout, imgshare, imaprofile;
    ImageView imgpublicGame, imgPrivategame, ImgCustomePage, ImgVariationGane,iv_andher;
    private String user_id, name, mobile, profile_pic, referral_code, wallet, game_played, bank_detail, adhar_card, upi;
    private TextView txtName, txtwallet, txtproname;
    Button btnbuychips;
    LinearLayout lnrbuychips, lnrinvite, lnrmail, lnrearnchips, lnrsetting,lnrvideo;
    Typeface helvatikaboldround, helvatikanormal;
    public String token = "";
    private String game_for_private,app_version;
    SeekBar sBar;
    SeekBar sBarpri;
    ImageView imgCreatetable,imgCreatetablecustom,imgclosetoppri,imgclosetop;
    int pval = 1;
    int pvalpri = 1;
    Button btnCreatetable;
    Button btnCreatetablepri;
    TextView txtStart, txtLimit,txtwalletchips,
            txtwalletchipspri, txtBootamount, txtPotLimit, txtNumberofBlind,
            txtMaximumbetvalue;
    TextView txtStartpri, txtLimitpri, txtBootamountpri, txtPotLimitpri, txtNumberofBlindpri, txtMaximumbetvaluepri;
    RelativeLayout rltimageptofile;
    int version = 0 ;

    RelativeLayout rootView ;


    String base_64 = "";
    ProgressDialog progressDialog;
    Activity context = Homepage.this;

    String REFERRAL_AMOUNT = "referral_amount";
    String JOINING_AMOUNT = "joining_amount";

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

    Random random = new Random();

    private void emitBubbles() {
        // It will create a thread and attach it to
        // the main thread
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                int size = random.nextInt( 250);
//                bubbleEmitter.emitBubble(size);
//                bubbleEmitter.setColors(android.R.color.transparent,
//                        android.R.color.holo_blue_light,
//                        android.R.color.holo_blue_bright);
//                emitBubbles();
            }
        }, random.nextInt(500));
    }


//    BubbleEmitterView bubbleEmitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

//        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

//        bubbleEmitter = findViewById(R.id.bubbleEmitter);

        SharePref.getInstance().init(this);

        imgLogout = findViewById(R.id.imgLogout);


        lnrbuychips = findViewById(R.id.lnrbuychips);
        lnrmail = findViewById(R.id.lnrmail);
        lnrearnchips = findViewById(R.id.lnrearnchips);
        lnrinvite = findViewById(R.id.lnrinvite);
        lnrsetting = findViewById(R.id.lnrsetting);
        lnrvideo = findViewById(R.id.lnrvideo);


        imaprofile = findViewById(R.id.imaprofile);


        emitBubbles();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");




        FrameLayout home_container = findViewById(R.id.home_container);
        home_container.setVisibility(View.VISIBLE);


        rootView = findViewById(R.id.rlt_animation_layout);
        RelativeLayout relativeLayout = findViewById(R.id.rlt_parent);
//        SetBackgroundImageAsDisplaySize(this,relativeLayout,R.drawable.splash);


//        BonusView();


        ((ImageView) findViewById(R.id.imgMywining)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyWinningAcitivity.class);
                startActivity(intent);
            }
        });

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        helvatikaboldround = Typeface.createFromAsset(getAssets(),
                "fonts/helvetica-rounded-bold-5871d05ead8de.otf");

        helvatikanormal = Typeface.createFromAsset(getAssets(),
                "fonts/Helvetica.ttf");

        rltimageptofile = findViewById(R.id.rltimageptofile);

        rltimageptofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserInformation_BT userInformation_bt = new UserInformation_BT(new Callback() {
                    @Override
                    public void Responce(String resp, String type, Bundle bundle) {
                        UserProfile();
                    }
                });
                userInformation_bt.setCancelable(false);
                userInformation_bt.show(getSupportFragmentManager(),userInformation_bt.getTag());
//                DialogUserInfo();
            }
        });

        imgpublicGame = (ImageView) findViewById(R.id.imgpublicGame);
        imgPrivategame = (ImageView) findViewById(R.id.imgPrivategame);
        ImgCustomePage = (ImageView) findViewById(R.id.ImgCustomePage);
        ImgVariationGane = (ImageView) findViewById(R.id.ImgVariationGane);
        iv_andher = (ImageView) findViewById(R.id.iv_andher);
        txtName = findViewById(R.id.txtName);
        txtName.setTypeface(helvatikaboldround);
        txtwallet = findViewById(R.id.txtwallet);
        txtwallet.setTypeface(helvatikanormal);
        txtproname = findViewById(R.id.txtproname);
        txtproname.setTypeface(helvatikaboldround);
        TextView txtMail = findViewById(R.id.txtMail);
        TextView txtEarn = findViewById(R.id.txtEarn);
        TextView txtInvite = findViewById(R.id.txtInvite);
        TextView txtBuyChips = findViewById(R.id.txtBuyChips);




        // load the animation
        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        imgpublicGame.startAnimation(animBlink);
        imgpublicGame.startAnimation(animBounce);


        imgPrivategame.startAnimation(animBlink);
        imgPrivategame.startAnimation(animBounce);


        ImgCustomePage.startAnimation(animBlink);
        ImgCustomePage.startAnimation(animBounce);
        ImgCustomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialoagonBack();
            }
        });


        ImgVariationGane.startAnimation(animBlink);
        ImgVariationGane.startAnimation(animBounce);
        clickTask();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(Homepage.this, token, Toast.LENGTH_SHORT).show();
                        UserProfile();
                    }
                });


        rotation_animation(((ImageView) findViewById(R.id.imgsetting)),R.anim.rotationback_animation);

         View shine = findViewById(R.id.shine);
         v_flesh = findViewById(R.id.v_flesh);

        shineAnimation(shine);
        BlinkAnimation(v_flesh);

        findViewById(R.id.lnr_redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RedeemActivity.class));

//                LoadFragment(new WalletFragment());
            }
        });

        findViewById(R.id.lnrhistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MyWinningAcitivity.class));
//                showRedeemDailog();
            }
        });


        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile();
            }
        });
    }

    private void BonusView(){

        if(SharePref.getInstance().getBoolean(SharePref.isBonusShow))
            lnrmail.setVisibility(View.VISIBLE);
        else
            lnrmail.setVisibility(View.GONE);

            Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String datevalue = prefs.getString("cur_date4", "12/06/2020");



        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate1 = df1.format(c);
        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), datevalue,formattedDate1);


        if (dateDifference > 0) {
            // catalog_outdated = 1;

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(c);

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("cur_date4", formattedDate);
            editor.apply();

            if(SharePref.getInstance().getBoolean(SharePref.isBonusShow))
                showDailyWinCoins();

        }else {

            System.out.println("");


        }

        lnrmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getApplicationContext(), MaiUserListingActivity.class);
//                startActivity(intent);
//                Toast.makeText(Homepage.this, "Coming Soon",
//                        Toast.LENGTH_LONG).show();
                showDailyWinCoins();

            }
        });
    }

    private void LoadFragment(Fragment fragment)
    {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.home_container,fragment).
                addToBackStack(null).
                commit();
    }


    View v_flesh;

    private void BlinkAnimation(final View view) {
        view.setVisibility(View.VISIBLE);
        final Animation animationUtils = AnimationUtils.loadAnimation(context,R.anim.blink);
        animationUtils.setDuration(1000);
        animationUtils.setRepeatCount(1);
        animationUtils.setStartOffset(700);
        view.startAnimation(animationUtils);

        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void shineAnimation(final View view) {
        final Animation animationUtils = AnimationUtils.loadAnimation(context,R.anim.left_to_righ);
        animationUtils.setDuration(1500);
        view.startAnimation(animationUtils);

        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(animationUtils);
                BlinkAnimation(v_flesh);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    if(Funtions.check_permissions(Homepage.this))
                        openCameraIntent();
                }

                else if (options[item].equals("Choose from Gallery"))
                {

                    if(Funtions.check_permissions(Homepage.this)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    // below three method is related with taking the picture from camera
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        getPackageName()+".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, 1);
            }
        }
    }

    String imageFilePath;
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public  String getPath(Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    File image_file;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                Matrix matrix = new Matrix();
                try {
                    ExifInterface exif = new ExifInterface(imageFilePath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                image_file=new File(imageFilePath);
                Uri selectedImage =(Uri.fromFile(image_file));

                InputStream imageStream = null;
                try {
                    imageStream =getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);

                Bitmap  resized = Bitmap.createScaledBitmap(rotatedBitmap,(int)(rotatedBitmap.getWidth()*0.7), (int)(rotatedBitmap.getHeight()*0.7), true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 20, baos);



                    base_64=Funtions.Bitmap_to_base64(this,resized);

                    if(image_file!=null);
                    Glide.with(this).load(resized).into(img_diaProfile);

                UserUpdateProfile(edtUsername.getText().toString().trim(), edtUserbank.getText().toString().trim(), edtUserupi.getText().toString().trim(), edtUseradhar.getText().toString().trim());


            }

            else if (requestCode == 2) {
                Uri selectedImage = data.getData();

                progressDialog.show();
                new ImageSendingAsync().execute(selectedImage);

            }

        }

    }

    public class ImageSendingAsync extends AsyncTask<Uri, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Uri... params) {
            Uri selectedImage = params[0];

            try {
                image_file= FileUtils.getFileFromUri(Homepage.this,selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            InputStream imageStream = null;
            try {
                imageStream =getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

            String path=getPath(selectedImage);
            Matrix matrix = new Matrix();
            ExifInterface exif = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                try {
                    exif = new ExifInterface(path);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);

            Bitmap  resized = Bitmap.createScaledBitmap(rotatedBitmap,(int)(rotatedBitmap.getWidth()*0.5), (int)(rotatedBitmap.getHeight()*0.5), true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 20, baos);

            return resized;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();


                base_64=Funtions.Bitmap_to_base64(Homepage.this,bitmap);

                if(image_file!=null)
                    Glide.with(Homepage.this).load(bitmap).into(img_diaProfile);
            UserUpdateProfile(edtUsername.getText().toString().trim(),
                    edtUserbank.getText().toString().trim(),
                    edtUserupi.getText().toString().trim(),
                    edtUseradhar.getText().toString().trim());


            super.onPostExecute(bitmap);
        }
    }



    LinearLayout lnr_2player,lnr_5player;
    TextView tv_2player,tv_5player;
    int selected_type = 5;
    public void Dialog_SelectPlayer() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_select_palyer);
         lnr_2player = dialog.findViewById(R.id.lnr_2player);
         lnr_5player = dialog.findViewById(R.id.lnr_5player);
       tv_2player =  (TextView) dialog.findViewById(R.id.tv_2player);
       tv_5player =  (TextView) dialog.findViewById(R.id.tv_5player);

        Button btn_player = dialog.findViewById(R.id.btn_play);

        ChangeTextviewColorChange(5);

        lnr_2player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextviewColorChange(2);
            }
        });

        lnr_5player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextviewColorChange(5);
            }
        });

        btn_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if(selected_type == 2)
                {
                    LoadTableFragments(ActiveTables_BF.RUMMY2);
//                    Intent intent = new Intent(Homepage.this, Rummy2Player.class);
//                    startActivity(intent);
                }
                else {
                    LoadTableFragments(ActiveTables_BF.RUMMY5);

//                    Intent intent = new Intent(Homepage.this, Rummy5Player.class);
//                    startActivity(intent);
                }


            }
        });

        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

    private void LoadTableFragments(String TAG){
        ActiveTables_BF activeTables_bf = new ActiveTables_BF(TAG);
        activeTables_bf.show(getSupportFragmentManager(),activeTables_bf.getTag());
    }

    private void ChangeTextviewColorChange(int colortype){

        selected_type = colortype;

        if(colortype == 2)
        {
            tv_2player.setTextColor(getResources().getColor(R.color.white));
            lnr_2player.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            tv_5player.setTextColor(getResources().getColor(R.color.black));
            lnr_5player.setBackgroundColor(getResources().getColor(R.color.white));

        }
        else {
            tv_2player.setTextColor(getResources().getColor(R.color.black));
            lnr_2player.setBackgroundColor(getResources().getColor(R.color.white));

            tv_5player.setTextColor(getResources().getColor(R.color.white));
            lnr_5player.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }


    }



    private void rotation_animation(View view,int animation){

        Animation circle =  Funtions.AnimationListner(this, animation, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

            }
        });
        view.setAnimation(circle);
        circle.startNow();

    }

    int Counts = 100;
    int postion = -100;
    private void CenterAnimationView(String imagename,View imgcards,int Home_Page_Animation){


        int AnimationSpeed = Counts + Home_Page_Animation;
        Counts += 300;

        final View fromView, toView;
        rootView.setVisibility(View.VISIBLE);
//        rootView.removeAllViews();
//        View ivpickcard = findViewById(R.id.view_center);

        fromView = rootView;
        toView = imgcards;


        int fromLoc[] = new int[2];
        fromView.getLocationOnScreen(fromLoc);
        float startX = fromLoc[0];
        float startY = fromLoc[1];

        int toLoc[] = new int[2];
        toView.getLocationOnScreen(toLoc);
        float destX = toLoc[0];
        float destY = toLoc[1];

        final ImageView sticker = new ImageView(this);

        int stickerId = Funtions.GetResourcePath(imagename,this);

        int card_width = (int) getResources().getDimension(R.dimen.home_card_width);
        int card_hieght = (int) getResources().getDimension(R.dimen.home_card_height);

        Picasso.with(this).load(stickerId).into(sticker);

        sticker.setLayoutParams(new ViewGroup.LayoutParams(card_width, card_hieght));
        rootView.addView(sticker);


        Animations anim = new Animations();
        Animation a = anim.fromAtoB(0, 0, convertDpToPixel(postion,this), 0, null, AnimationSpeed, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {
                fromView.setVisibility(View.VISIBLE);
                toView.setVisibility(View.VISIBLE);
                sticker.setVisibility(View.GONE);
            }
        });
        sticker.setAnimation(a);
        a.startNow();

        postion += 100;

    }

    ImageView img_diaProfile;
    EditText edtUsername,edtUserbank,edtUserupi,edtUseradhar;
    public void DialogUserInfo() {

        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_user);
        dialog.setTitle("Title...");

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        ((View) dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        img_diaProfile = dialog.findViewById(R.id.img_diaProfile);
        TextView txt_diaName = dialog.findViewById(R.id.txt_diaName);
        TextView txt_diaPhone = dialog.findViewById(R.id.txt_diaPhone);
        TextView txt_bank = dialog.findViewById(R.id.txt_bank);
        TextView txt_adhar = dialog.findViewById(R.id.txt_adhar);
        TextView txt_upi = dialog.findViewById(R.id.txt_upi);
        edtUsername = dialog.findViewById(R.id.edtUsername);

        edtUserbank = dialog.findViewById(R.id.edtUserbank);
        edtUserupi = dialog.findViewById(R.id.edtUserupi);
        edtUseradhar = dialog.findViewById(R.id.edtUseradhar);

        final LinearLayout lnrUserinfo = dialog.findViewById(R.id.lnr_userinfo);
        final LinearLayout lnr_updateuser = dialog.findViewById(R.id.lnr_updateuser);

        edtUsername.setText("" + name);
        edtUserbank.setText("" + bank_detail);
        edtUserupi.setText("" + upi);
        edtUseradhar.setText("" + adhar_card);

        txt_diaName.setText("Name: " + name);
        txt_diaPhone.setText("Ph.No.:" + mobile);
        txt_bank.setText("Bank Details:" + bank_detail);
        txt_adhar.setText("Addhar No.: " + adhar_card);
        txt_upi.setText("UPI:" + upi);
        Picasso.with(Homepage.this).load(Const.IMGAE_PATH + profile_pic).into(img_diaProfile);

        img_diaProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        ((View) dialog.findViewById(R.id.img_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lnrUserinfo.setVisibility(View.GONE);
                lnr_updateuser.setVisibility(View.VISIBLE);

            }
        });

        ((ImageView) dialog.findViewById(R.id.imgsub)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!edtUsername.getText().toString().trim().equals("")) {
                    lnrUserinfo.setVisibility(View.VISIBLE);
                    lnr_updateuser.setVisibility(View.GONE);

                    UserUpdateProfile(edtUsername.getText().toString().trim(), edtUserbank.getText().toString().trim(), edtUserupi.getText().toString().trim(), edtUseradhar.getText().toString().trim());

                    dialog.dismiss();
                } else {
                    Toast.makeText(Homepage.this, "Input field in empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dialog.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        UserProfile();
        GameLeave();
    }

    public void clickTask() {
        imgpublicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaySaund(R.raw.buttontouchsound);


               LoadTableFragments(ActiveTables_BF.TEENPATTI);

//                Intent intent = new Intent(Homepage.this, PublicTable.class);
//                startActivity(intent);

            }
        });


        lnrearnchips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWinnerDailog();
            }
        });

        ImgVariationGane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Homepage.this, "Coming Soon",
//                        Toast.LENGTH_LONG).show();

//                LoadTableFragments(ActiveTables_BF.RUMMY5);
                Dialog_SelectPlayer();
                         }
        });

        iv_andher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, AndharBahar_Home_A.class));

                         }
        });




        imgPrivategame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialoag();
                float gamecount = 0;
                if(game_played != null && !game_played.equals(""))
                {
                   gamecount  = Float.parseFloat(game_played);
                }
                float game_for_privatetemp = Float.parseFloat(game_for_private);
                if (gamecount > game_for_privatetemp ){

                    showDialoagPrivettable();

                }else {

                    Toast.makeText(Homepage.this, "To Unblock Private Table you have to Play at least "+game_for_privatetemp+
                                    " Games.",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        lnrsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDialogSetting();
            }
        });

        lnrvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homepage.this, "Coming Soon",
                        Toast.LENGTH_LONG).show();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new AlertDialog.Builder(Homepage.this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Logout")
//                        .setMessage("Do you want to Logout?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                                editor.putString("user_id", "");
//                                editor.putString("name", "");
//                                editor.putString("mobile", "");
//                                editor.putString("token", "");
//                                editor.apply();
//                                Intent intent = new Intent(Homepage.this, LoginScreen.class);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();


                new SmartDialogBuilder(Homepage.this)
                        .setTitle("Do you want to Logout?")
                        //.setSubTitle("This is the alert dialog to showing alert to user")
                        .setCancalable(true)
                        //.setTitleFont("Do you want to Logout?") //set title font
                       // .setSubTitleFont(subTitleFont) //set sub title font
                        .setNegativeButtonHide(true) //hide cancel button
                        .setPositiveButton("Logout", new SmartDialogClickListener() {
                            @Override
                            public void onClick(SmartDialog smartDialog) {
                               // Toast.makeText(context,"Ok button Click",Toast.LENGTH_SHORT)
                                // .show();
                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("user_id", "");
                                editor.putString("name", "");
                                editor.putString("mobile", "");
                                editor.putString("token", "");
                                editor.apply();
                                Intent intent = new Intent(Homepage.this, LoginScreen.class);
                                startActivity(intent);
                                finish();

                                smartDialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                       // Toast.makeText(context,"Cancel button Click",Toast.LENGTH_SHORT).show();
                        smartDialog.dismiss();

                    }
                }).build().show();

//                new SweetAlertDialog(Homepage.this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Are you sure?")
//                        .setContentText("Won't be able to recover this file!")
//                        .setConfirmText("Yes,delete it!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
            }
        });

        lnrinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDialogInvite();

            }
        });
        lnrbuychips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, BuyChipsList.class);
                intent.putExtra("homepage","homepage");
                startActivity(intent);
            }
        });

    }


    private void UserProfile() {

        Funtions.showLoader(context,false,false);

        HashMap<String, String> params = new HashMap<String, String>();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        params.put("id", prefs.getString("user_id", ""));
        params.put("fcm", token);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        params.put("app_version", version + "");
        params.put("token", prefs.getString("token", ""));

        ApiRequest.Call_Api(context, Const.PROFILE, params, new Callback() {
            @Override
            public void Responce(String resp, String type, Bundle bundle) {

                if(resp != null)
                {
                    ParseResponse(resp);
                }

            }
        });

    }

    private void ParseResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.getString("code");
            if (code.equalsIgnoreCase("200")) {
                JSONObject jsonObject0 = jsonObject.getJSONArray("user_data").getJSONObject(0);
                user_id = jsonObject0.getString("id");
                name = jsonObject0.optString("name");
                mobile = jsonObject0.optString("mobile");
                profile_pic = jsonObject0.optString("profile_pic");
                referral_code = jsonObject0.optString("referral_code");
                wallet = jsonObject0.optString("wallet");
               String winning_wallet = jsonObject0.optString("winning_wallet");
                game_played = jsonObject0.optString("game_played");
                bank_detail = jsonObject0.optString("bank_detail");
                upi = jsonObject0.optString("upi");
                adhar_card = jsonObject0.optString("adhar_card");

                // txtName.setText("Welcome Back "+name);

                if(Funtions.checkStringisValid(wallet))
                {
                    float f_wallet = Float.parseFloat(wallet);
//                    long numberamount =  Float.parseFloat(f_wallet);
//                    long numberamount = (long) f_wallet;
//                    txtwallet.setText("" + NumberFormat.getNumberInstance(Locale.US).format(f_wallet));
                    txtwallet.setText("" + f_wallet);
                }


                txtproname.setText(name);
                Picasso.with(Homepage.this).load(Const.IMGAE_PATH + profile_pic).into(imaprofile);


                String setting = jsonObject.optString("setting");
                JSONObject jsonObjectSetting = new JSONObject(setting);
                game_for_private = jsonObjectSetting.optString("game_for_private");
                app_version = jsonObjectSetting.optString("app_version");
                String  referral_amount = jsonObjectSetting.optString("referral_amount");
                String  joining_amount = jsonObjectSetting.optString("joining_amount");
                 String whats_no = jsonObjectSetting.optString("whats_no");

                 // bonus = 0=no , 1=yes
                 // payment_gateway = 0=payment , 1=whatsapp
                 // symbol = 0=coin , 1=rupees,2 = dollar

                 String symbol = jsonObjectSetting.optString("symbol");
                 String payment_gateway = jsonObjectSetting.optString("payment_gateway");
                 String bonus = jsonObjectSetting.optString("bonus");
                String razor_api_key = jsonObjectSetting.optString("razor_api_key");
                String share_text = jsonObjectSetting.optString("share_text");

                if(Funtions.checkStringisValid(bonus) && !bonus.equalsIgnoreCase("0"))
                    SharePref.getInstance().putBoolean(SharePref.isBonusShow,true);
                else
                    SharePref.getInstance().putBoolean(SharePref.isBonusShow,false);

                if(Funtions.checkStringisValid(payment_gateway) && !payment_gateway.equalsIgnoreCase("0"))
                    SharePref.getInstance().putBoolean(SharePref.isPaymentGateShow,false);
                else
                    SharePref.getInstance().putBoolean(SharePref.isPaymentGateShow,true);


                if(Funtions.checkStringisValid(symbol) && symbol.equalsIgnoreCase("0"))
                    Variables.CURRENCY_SYMBOL = Variables.COINS;
                else if(Funtions.checkStringisValid(symbol) && symbol.equalsIgnoreCase("1"))
                {
                    Variables.CURRENCY_SYMBOL = Variables.RUPEES;
                }
                else if(Funtions.checkStringisValid(symbol) && symbol.equalsIgnoreCase("2"))
                {
                    Variables.CURRENCY_SYMBOL = Variables.DOLLAR;
                }



                BonusView();

                SharePref.getInstance().putString(SharePref.SYMBOL_TYPE,Variables.CURRENCY_SYMBOL);
                SharePref.getInstance().putString(SharePref.RAZOR_PAY_KEY,razor_api_key);
                SharePref.getInstance().putString(SharePref.SHARE_APP_TEXT,share_text);

                ((ImageView) findViewById(R.id.imgicon)).setImageDrawable(
                        Variables.CURRENCY_SYMBOL.equalsIgnoreCase(Variables.RUPEES) ? Funtions.getDrawable(context,R.drawable.ic_rupee_circle) :
                        Variables.CURRENCY_SYMBOL.equalsIgnoreCase(Variables.DOLLAR) ? Funtions.getDrawable(context,R.drawable.ic_dollar) :
                                Funtions.getDrawable(context,R.drawable.ic_rupee));

                try {

//                    int app_versionint = Integer.parseInt(app_version);
//
//                    //if (version > app_versionint){
//                if (app_versionint > version) {
//
//                    showAppUpdateDialog("Update");
//
//                } else {
//
//
//                }
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }





                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("name", name);
                editor.putString("profile_pic", profile_pic);
                editor.putString("bank_detail", bank_detail);
                editor.putString("upi", upi);
                editor.putString("adhar_card", adhar_card);
                editor.putString("mobile", mobile);
                editor.putString("referal_code", referral_code);
                editor.putString("img_name", profile_pic);
                editor.putString("winning_wallet", winning_wallet);
                editor.putString("wallet", wallet);
                editor.putString("game_for_private", game_for_private);
                editor.putString("app_version", app_version);
                editor.putString("whats_no", whats_no);
                editor.putString(REFERRAL_AMOUNT, referral_amount);
                editor.putString(JOINING_AMOUNT, joining_amount);
                editor.apply();


            } else if (code.equals("411")) {

                Intent intent = new Intent(Homepage.this, LoginScreen.class);
                startActivity(intent);
                finishAffinity();
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("name", "");
                editor.putString("referal_code", "");
                editor.putString("img_name", "");
                editor.putString("game_for_private", "");
                editor.putString("app_version", "");
                editor.apply();

                Toast.makeText(Homepage.this, "You are Logged in from another " +
                                "device.",
                        Toast.LENGTH_LONG).show();


            } else {

                if (jsonObject.has("message")) {
                    String message = jsonObject.getString("message");
                    Toast.makeText(Homepage.this, message,
                            Toast.LENGTH_LONG).show();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Funtions.dismissLoader();

    }

    private void UserUpdateProfile(final String username,
                                         final String user_bank,
                                         final String user_upi,
                                         final String user_adhar) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_UPDATE,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        Log.d("DATA_CHECK", "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");

                            if (code.equalsIgnoreCase("200")) {

                                CommonFunctions.showAlertDialog(context,"Alert message","Profile Updated Successfully!",true,"Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }, null,null);



                            } else {
                                if (jsonObject.has("message")) {

                                    String message = jsonObject.getString("message");


                                    Toast.makeText(context, message,
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        UserProfile();
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
                SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("bank_detail", user_bank);
                params.put("upi", user_upi);
                params.put("adhar_card", user_adhar);
                params.put("name", username);


                params.put("token", prefs.getString("token", ""));
                Log.d("paremter_java", "getParams: " + params);
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



    public void showDialoag() {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final EditText edtEnterTableId = (EditText) dialog.findViewById(R.id.edtEnterTableId);

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        Button btnCreatNewTable = (Button) dialog.findViewById(R.id.btnCreatNewTable);
        // if button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(Homepage.this, PrivateTablev2.class);
                intent.putExtra("table_id", edtEnterTableId.getText().toString());
                intent.putExtra("type", "1");
                startActivity(intent);
                dialog.dismiss();

            }
        });
        btnCreatNewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, PrivateTablev2.class);
                intent.putExtra("table_id", "");
                intent.putExtra("type", "2");
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void PlaySaund(int sound) {

        final MediaPlayer mp = MediaPlayer.create(this,
                sound);
        mp.start();
    }

    public void showDialoagPrivettable() {

        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.custom_dialog_custon_boot);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        sBarpri = (SeekBar) dialog.findViewById(R.id.seekBar1);
        sBarpri.setProgress(0);
        sBarpri.incrementProgressBy(10);
        sBarpri.setMax(100);
        txtStartpri = (TextView) dialog.findViewById(R.id.txtStart);
        txtLimitpri = (TextView) dialog.findViewById(R.id.txtLimit);
        txtwalletchipspri = (TextView) dialog.findViewById(R.id.txtwalletchips);
        float numberamount = Float.parseFloat(wallet);
        txtwalletchipspri.setText("" + NumberFormat.getNumberInstance(Locale.US).format(numberamount));

        // txtwalletchipspri.setText(wallet);
        txtBootamountpri = (TextView) dialog.findViewById(R.id.txtBootamount);
        txtPotLimitpri = (TextView) dialog.findViewById(R.id.txtPotLimit);
        txtNumberofBlindpri = (TextView) dialog.findViewById(R.id.txtNumberofBlind);
        txtMaximumbetvaluepri = (TextView) dialog.findViewById(R.id.txtMaximumbetvalue);
        imgclosetoppri = (ImageView) dialog.findViewById(R.id.imgclosetop);
        imgclosetoppri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imgCreatetable = (ImageView) dialog.findViewById(R.id.imgCreatetable);
        imgCreatetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, PrivateTablev2.class);
                intent.putExtra("bootvalue", pvalpri + "");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        // tView.setText(sBar.getProgress() + "/" + sBar.getMax());
        sBarpri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                if (progress == 1) {

                    pvalpri = 50;

                } else if (progress == 2) {
                    pvalpri = 100;
                } else if (progress == 3) {

                    pvalpri = 500;
                } else if (progress == 4) {

                    pvalpri = 1000;

                } else if (progress == 5) {

                    pvalpri = 5000;

                } else if (progress == 6) {

                    pvalpri = 10000;
                } else if (progress == 7) {

                    pvalpri = 25000;
                } else if (progress == 8) {

                    pvalpri = 50000;
                } else if (progress == 9) {

                    pvalpri = 100000;
                } else if (progress == 10) {

                    pvalpri = 250000;
                }

                //progress = progress * 10;
                // pvalpri = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtBootamountpri.setText("Boot amount : " + kvalue(pvalpri) + "");

                int valueforpot = pvalpri * 1024;
                int valueformaxi = pvalpri * 128;

                //long valueforpotlong= valueforpot;

                txtPotLimitpri.setText("Pot limit : " + kvalue(valueforpot) + "");
                txtMaximumbetvaluepri.setText("Maximumbet balue : " + kvalue(valueformaxi) + "");
                txtNumberofBlindpri.setText("Number of Blinds : 4");
                //tView.setText(pval + "/" + seekBar.getMax());
            }
        });


        dialog.show();
    }

    public void showDialoagonBack() {

        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.custom_dialog_custon_boot);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        sBar = (SeekBar) dialog.findViewById(R.id.seekBar1);
        sBar.setProgress(0);
        sBar.incrementProgressBy(10);
        sBar.setMax(100);
        txtStart = (TextView) dialog.findViewById(R.id.txtStart);
        txtLimit = (TextView) dialog.findViewById(R.id.txtLimit);
        txtwalletchips = (TextView) dialog.findViewById(R.id.txtwalletchips);
        float numberamount = Float.parseFloat(wallet);
        txtwalletchips.setText("" + NumberFormat.getNumberInstance(Locale.US).format(numberamount));
        txtBootamount = (TextView) dialog.findViewById(R.id.txtBootamount);
        txtPotLimit = (TextView) dialog.findViewById(R.id.txtPotLimit);
        txtNumberofBlind = (TextView) dialog.findViewById(R.id.txtNumberofBlind);
        txtMaximumbetvalue = (TextView) dialog.findViewById(R.id.txtMaximumbetvalue);
        imgclosetop = (ImageView) dialog.findViewById(R.id.imgclosetop);
        imgclosetop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imgCreatetablecustom = (ImageView) dialog.findViewById(R.id.imgCreatetable);
        imgCreatetablecustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Homepage.this, CustomisedTablev2.class);
                Intent intent = new Intent(Homepage.this, CustomsiedTablev3.class);
                intent.putExtra("bootvalue", pval + "");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        // tView.setText(sBar.getProgress() + "/" + sBar.getMax());
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
                progress = progress / 10;
                if (progress == 1) {

                    pval = 50;

                } else if (progress == 2) {
                    pval = 100;
                } else if (progress == 3) {

                    pval = 500;
                } else if (progress == 4) {

                    pval = 1000;

                } else if (progress == 5) {

                    pval = 5000;

                } else if (progress == 6) {

                    pval = 10000;
                } else if (progress == 7) {

                    pval = 25000;
                } else if (progress == 8) {

                    pval = 50000;
                } else if (progress == 9) {

                    pval = 100000;
                } else if (progress == 10) {

                    pval = 250000;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtBootamount.setText("Boot amount : " + kvalue(pval) + "");

                int valueforpot = pval * 1024;
                int valueformaxi = pval * 128;
                txtPotLimit.setText("Pot limit : " + kvalue(valueforpot) + "");
                txtMaximumbetvalue.setText("Maximumbet balue : " + kvalue(valueformaxi) + "");
                txtNumberofBlind.setText("Number of Blinds : 4");
                //tView.setText(pval + "/" + seekBar.getMax());
            }
        });


        dialog.show();
    }

    public void showRedeemDailog() {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_gift);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((ImageView) dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtnotfound = dialog.findViewById(R.id.txtnotfound);
        TextView txttitle = dialog.findViewById(R.id.txtheader);

        txttitle.setText("Redeem History");

        RecyclerView recyclerView_gifts = dialog.findViewById(R.id.recylerview_gifts);
        recyclerView_gifts.setLayoutManager(new LinearLayoutManager(Homepage.this));

        UsersRedeemList(recyclerView_gifts, dialog);

        dialog.show();
    }

    private void UsersRedeemList(final RecyclerView recyclerView, final Dialog dialog) {


        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        final ArrayList<RedeemModel> redeemModelArrayList = new ArrayList();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_Redeem_History_LIST,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
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


                                    redeemModelArrayList.add(model);
                                }

                                UserRedeemHistoryAdapter userWinnerAdapter = new UserRedeemHistoryAdapter(Homepage.this, redeemModelArrayList);
                                recyclerView.setAdapter(userWinnerAdapter);
                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(Homepage.this, message,
                                            Toast.LENGTH_LONG).show();
                                    txtnotfound.setVisibility(View.VISIBLE);

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtnotfound.setVisibility(View.VISIBLE);

                        }

                        rlt_progress.setVisibility(View.GONE);


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                txtnotfound.setVisibility(View.VISIBLE);
                rlt_progress.setVisibility(View.GONE);

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


    private void GameLeave() {

//        final ProgressDialog progressDialog = new ProgressDialog(Homepage.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Logging in..");
//        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.GAME_TABLE_LEAVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        System.out.println("" + response);
                        // finish();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    public void showDialogInvite() {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.custom_dialog_invite);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView imgclose = (ImageView) dialog.findViewById(R.id.imgclosetop);
        TextView txtshare = (TextView) dialog.findViewById(R.id.txtshare);

        TextView txtchipsbelow = (TextView) dialog.findViewById(R.id.txtchipsbelow);
        TextView tvInviteCoins = (TextView) dialog.findViewById(R.id.tvInviteCoins);

        tvInviteCoins.setText(Variables.CURRENCY_SYMBOL+SharePref.getInstance().getString(SharePref.referral_amount));
        txtchipsbelow.setText(Variables.CURRENCY_SYMBOL+SharePref.getInstance().getString(SharePref.referral_amount));

        TextView txtReferalcode = (TextView) dialog.findViewById(R.id.txtReferalcode);
        txtReferalcode.setText(referral_code);
        TextView txtAnd = (TextView) dialog.findViewById(R.id.txtAnd);
        ImageView imgfb = (ImageView) dialog.findViewById(R.id.imgfb);
        ImageView imgwhats = (ImageView) dialog.findViewById(R.id.imgwhats);
        ImageView imgmail = (ImageView) dialog.findViewById(R.id.imgmail);
        imgmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.google.android.gm");



                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getReferralMessage());
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
            }
        });

        imgwhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");

                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getReferralMessage());
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
            }
        });

        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.facebook.katana");

                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getReferralMessage());

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
            }
        });
        TextView txtyourfrind = (TextView) dialog.findViewById(R.id.txtyourfrind);
        TextView txtFooter = (TextView) dialog.findViewById(R.id.txtFooter);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });


        dialog.show();
    }

    private String getReferralMessage() {
        final String appPackageName = getPackageName();
        String applink = "https://play.google.com/store/apps/details?id=" + appPackageName;

        String shareMessage = "You have been invited to use " +Funtions.getString(context,R.string.app_name)+" "+
                "app.";

        String apiMessgae = SharePref.getInstance().getString(SharePref.SHARE_APP_TEXT);

        if(Funtions.checkisStringValid(apiMessgae))
            shareMessage  = apiMessgae;

        String referral_message = shareMessage+ " Use the referral code    " +
                referral_code + " Download the App now. "
                + "Link:- "+applink;

        return referral_message;
    }

    public void showDialogSetting() {
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.custom_dialog_setting_home);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View imgclose = (View) dialog.findViewById(R.id.imgclosetop);
//
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });


        (dialog.findViewById(R.id.btnPrivacy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTermDialog("Privacy Policy");

            }
        });

        (dialog.findViewById(R.id.btnHowtoplay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showHelpDialog();

                dialog.dismiss();
            }
        });

        (dialog.findViewById(R.id.btnTermscond)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTermDialog("Term and Conditions");

            }
        });



        (dialog.findViewById(R.id.btnHelpandsupport)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpDialog();

                dialog.dismiss();
            }
        });

        (dialog.findViewById(R.id.lnrlogoutdia)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("user_id", "");
                editor.putString("name", "");
                editor.putString("mobile", "");
                editor.putString("token", "");
                editor.apply();
                Intent intent = new Intent(Homepage.this, LoginScreen.class);
                startActivity(intent);
                finish();

                dialog.dismiss();
            }
        });

        dialog.show();

        Switch switchd = (Switch) dialog.findViewById(R.id.switch1);
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = prefs.getString("issoundon", "1");

        if (value.equals("0")) {

            switchd.setChecked(true);

        } else {

            switchd.setChecked(false);
        }

        switchd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("issoundon", "0");
                    editor.apply();


                    // Toast.makeText(PublicTable.this, "On", Toast.LENGTH_LONG).show();

                } else {
                    // Toast.makeText(PublicTable.this, "Off", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("issoundon", "1");
                    editor.apply();

                }
            }
        });


        LinearLayout lnr_language = dialog.findViewById(R.id.lnr_language);
        lnr_language.removeAllViews();

        AddViewToLanguage(lnr_language,"English");

    }

    private void AddViewToLanguage(ViewGroup viewGroup,String text){

        View view = Funtions.CreateDynamicViews(R.layout.item_language,viewGroup,context);

        TextView textView = view.findViewById(R.id.tv_language);
        textView.setText(""+text);
        textView.setTag(text);


    }

    public interface itemClick{
        void OnDailyClick(String id);
    }
    TextView txtnotfound;


    public void showDailyWinCoins() {

        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_dailyreward);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RecyclerView Reward_rec;

        txtnotfound = dialog.findViewById(R.id.txtnotfound);

        RelativeLayout relativeLayout = dialog.findViewById(R.id.rlt_parent);
        SetBackgroundImageAsDisplaySize(this,relativeLayout,R.drawable.bghome);


        imgclosetop = dialog.findViewById(R.id.imgclosetop);
        imgclosetop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ((View)dialog.findViewById(R.id.btnCollect)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CollectWelcomeBonus(dialog);

            }
        });


        Reward_rec = dialog.findViewById(R.id.reward_rec);

        itemClick itemClick = new itemClick() {
            @Override
            public void OnDailyClick(String id) {

//                CollectWelcomeBonus();
            }
        };

        ArrayList<Integer> CoinsList = new ArrayList<>();
        CoinsList.add(R.drawable.day1);
        CoinsList.add(R.drawable.day2);
        CoinsList.add(R.drawable.day3);
        CoinsList.add(R.drawable.day4);
        CoinsList.add(R.drawable.day5);
        CoinsList.add(R.drawable.day5);
        CoinsList.add(R.drawable.day5);
        CoinsList.add(R.drawable.day5);
        CoinsList.add(R.drawable.day5);
        CoinsList.add(R.drawable.day5);

        GetUserWelcomeBonus(Reward_rec,itemClick,dialog,CoinsList);

        Reward_rec.setLayoutManager(new LinearLayoutManager(Homepage.this,RecyclerView.HORIZONTAL,false));



        dialog.show();
    }


    public void showTermDialog(String tag) {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_termandcondition);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((ImageView)dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtnotfound = dialog.findViewById(R.id.txtnotfound);
        TextView txtheader = dialog.findViewById(R.id.txtheader);
        txtheader.setText(""+tag);

        WebView webView = dialog.findViewById(R.id.webview);

        webView.setBackgroundColor(Color.TRANSPARENT);


        UserTermsAndCondition(webView,dialog,tag);

        dialog.show();
    }

    public void showAppUpdateDialog(String tag) {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_app_update);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
//        ((ImageView)dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });


        TextView txtheader = dialog.findViewById(R.id.txtheader);
        RelativeLayout rltUpdate = dialog.findViewById(R.id.rltUpdate);
        txtheader.setText(""+tag);
        rltUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void showHelpDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_rulesplay);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((ImageView)dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void showWinnerDailog() {
        // custom dialog
        final Dialog dialog = new Dialog(Homepage.this,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_winnerlist);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((ImageView)dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtnotfound = dialog.findViewById(R.id.txtnotfound);
        TextView txttitle = dialog.findViewById(R.id.txtheader);

        txttitle.setText("Leader Board");

        RecyclerView recyclerView_gifts = dialog.findViewById(R.id.recylerview_gifts);
        recyclerView_gifts.setLayoutManager(new LinearLayoutManager(Homepage.this));

        UsersWinningList(recyclerView_gifts,dialog);

        dialog.show();
    }

    private void UsersWinningList(final RecyclerView recyclerView, final Dialog dialog) {


        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        final ArrayList<MyWinnigmodel> winnigmodelArrayList = new ArrayList();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.USER_WINNING_LIST,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");


                            if (code.equalsIgnoreCase("200")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("leaderboard");

                                for (int i = 0; i < jsonArray.length() ; i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    MyWinnigmodel model = new MyWinnigmodel();
                                    model.setId(jsonObject1.getString("winner_id"));
                                    model.setUserimage(jsonObject1.getString("profile_pic"));
                                    model.setWinner_id(jsonObject1.getString("winner_id"));
                                    model.setTotalwin(jsonObject1.getString("Total_Win"));
                                    model.setName(jsonObject1.getString("name"));


                                    winnigmodelArrayList.add(model);
                                }

                                UserWinnerAdapter userWinnerAdapter = new UserWinnerAdapter(Homepage.this,winnigmodelArrayList);
                                recyclerView.setAdapter(userWinnerAdapter);
                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(Homepage.this, message,
                                            Toast.LENGTH_LONG).show();
                                    txtnotfound.setVisibility(View.VISIBLE);

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtnotfound.setVisibility(View.VISIBLE);

                        }

                        rlt_progress.setVisibility(View.GONE);



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                txtnotfound.setVisibility(View.VISIBLE);
                rlt_progress.setVisibility(View.GONE);

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


    private void GetUserWelcomeBonus(final RecyclerView reward_rec, final itemClick itemClick, Dialog dialog, final ArrayList<Integer> coinsList) {


        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        final ArrayList<WelcomeModel> welcomeList = new ArrayList();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.WELCOME_BONUS,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equalsIgnoreCase("200")) {

                                txtnotfound.setVisibility(View.GONE);

                                JSONArray welcome_bonusArray = jsonObject.getJSONArray("welcome_bonus");

                                for (int i = 0; i < welcome_bonusArray.length() ; i++) {
                                    JSONObject welcome_bonusObject = welcome_bonusArray.getJSONObject(i);

                                    WelcomeModel model= new WelcomeModel();
                                    model.setCoins(welcome_bonusObject.getString("coin"));
                                    model.setId(welcome_bonusObject.getString("id"));
                                    model.setGame_played(welcome_bonusObject.getString("game_played"));
                                    model.setCollected_days(jsonObject.getString("collected_days"));
                                    model.setDay(welcome_bonusObject.getString("id"));
                                    model.setImgcoins(coinsList.get(i));

                                    welcomeList.add(model);
                                }

                                WelcomeRewardAdapter  welcomeRewardAdapter = new WelcomeRewardAdapter(Homepage.this,welcomeList,itemClick);
                                reward_rec.setAdapter(welcomeRewardAdapter);

                            } else {
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(Homepage.this, message,
                                            Toast.LENGTH_LONG).show();
                                }

                                txtnotfound.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtnotfound.setVisibility(View.VISIBLE);

                        }

                        rlt_progress.setVisibility(View.GONE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                txtnotfound.setVisibility(View.VISIBLE);
                rlt_progress.setVisibility(View.GONE);


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


    private void CollectWelcomeBonus(Dialog dialog) {

        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.COLLECT_BONUS,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            String coins = "0";
                            if (jsonObject.has("coin"))
                                coins  = jsonObject.getString("coin");

                            if (code.equalsIgnoreCase("200")) {

                                WelcomeRewardAdapter.showWinDialog(Homepage.this,coins);

                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(Homepage.this, message,
                                            Toast.LENGTH_LONG).show();


                                }


                            }

                            rlt_progress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            rlt_progress.setVisibility(View.GONE);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                rlt_progress.setVisibility(View.GONE);

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

    private void UserTermsAndCondition(final WebView webview, final Dialog dialog, final String tag) {


        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.TERMS_CONDITION,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");


                            if (code.equalsIgnoreCase("200")) {


                                JSONObject jsonObject1 = jsonObject.getJSONObject("setting");

                                String data = "";

                                if(tag.equals("Privacy Policy"))
                                {
                                    data = jsonObject1.getString("privacy_policy");
                                }
                                else {
                                    data = jsonObject1.getString("terms");
                                }


                                if(data.equals(""))
                                {
                                    txtnotfound.setVisibility(View.VISIBLE);
                                }
                                else {
                                    txtnotfound.setVisibility(View.GONE);
                                }


                                data = data.replaceAll("&#39;","'");

                                String szMessage = "<font face= \"trebuchet\" size=3 color=\"#fff\"><b>"
                                        + data
                                        + "</b></font>";


                                webview.getSettings().setJavaScriptEnabled(true);
                                webview.loadDataWithBaseURL("",szMessage, "text/html", "UTF-8","");


                            } else {
                                if (jsonObject.has("message")) {

                                    Toast.makeText(Homepage.this, message,
                                            Toast.LENGTH_LONG).show();
                                    txtnotfound.setVisibility(View.VISIBLE);

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtnotfound.setVisibility(View.VISIBLE);

                        }

                        rlt_progress.setVisibility(View.GONE);



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                txtnotfound.setVisibility(View.VISIBLE);
                rlt_progress.setVisibility(View.GONE);

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


    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public String kvalue (int number){

        String numberString = "";
        if (Math.abs(number / 1000000) > 1) {
            numberString = (number / 1000000) + "m";

        } else if (Math.abs(number / 1000) > 1) {
            numberString = (number / 1000) + "k";

        } else {
            numberString = number+"";

        }
        return numberString;
    }

}


