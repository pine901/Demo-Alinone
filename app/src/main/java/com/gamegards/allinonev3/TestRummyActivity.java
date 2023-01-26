package com.gamegards.allinonev3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gamegards.allinonev3.model.CardModel;

import java.util.ArrayList;

public class TestRummyActivity extends AppCompatActivity implements Animation.AnimationListener {

    RecyclerView rec_cardlist  ;
    CardListAdapter cardListAdapter;
    ArrayList<CardModel> cardModelArrayList = new ArrayList<>();
    LinearLayout lnr_cardlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rummy);

        Initialization();
        API_CALL_getCardList();
    }

    private void API_CALL_getCardList() {

        GetNextCartValue();

        cardModelArrayList.clear();
        cardModelArrayList.add(new CardModel(R.drawable.bla));
        cardModelArrayList.add(new CardModel(R.drawable.bl2));
        cardModelArrayList.add(new CardModel(R.drawable.bl3));
        cardModelArrayList.add(new CardModel(R.drawable.bl4));
        cardModelArrayList.add(new CardModel(R.drawable.bl5));
        cardModelArrayList.add(new CardModel(R.drawable.bl6));
        cardModelArrayList.add(new CardModel(R.drawable.bl7));
        cardModelArrayList.add(new CardModel(R.drawable.bl8));
        cardModelArrayList.add(new CardModel(R.drawable.bl9));
        cardModelArrayList.add(new CardModel(R.drawable.bl10));
        cardModelArrayList.add(new CardModel(R.drawable.blk));
        cardModelArrayList.add(new CardModel(R.drawable.blq));
        cardModelArrayList.add(new CardModel(R.drawable.blj));

        for (int i = 0; i < cardModelArrayList.size() ; i++) {

            addCategoryInView(""+cardModelArrayList.get(i).CardItem,""+i,cardModelArrayList.get(i).CardItem);

        }

//        cardListAdapter.notifyDataSetChanged();

    }

    private void addCategoryInView(String name, final String cat_id, int emoji) {
        View view = LayoutInflater.from(TestRummyActivity.this).inflate(R.layout.item_card, null);
//        TextView txtview = view.findViewById(R.id.txt_cat);
//        txtview.setText(name + "");
        View imgemoji = view.findViewById(R.id.imgcard);

        if(!cat_id.equals(""+(cardModelArrayList.size() - 1)))
            setMargins(imgemoji,0,0,-100,0);


//        Glide.with(TestRummyActivity.this)
//                .load(emoji)
//                .into(imgemoji);

        imgemoji.setBackground(getResources().getDrawable(emoji));

        view.setTag(name + "");
        imgemoji.setTag(name + "");

//        TranslateLayout((ImageView) imgemoji, Integer.parseInt(cat_id));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CardModel model = cardModelArrayList.get(Integer.parseInt(cat_id));
                model.isSelected = !model.isSelected;
                cardModelArrayList.set(Integer.parseInt(cat_id),model);

                openCategory(view.getTag()+"");
            }
        });

        Log.d("101010", "title :" + cat_id + " " + view.getHeight() + " " + view.getWidth());
        lnr_cardlist.addView(view);

    }

    private void TranslateLayout(ImageView imageView,int position){

        final TranslateAnimation animationt = new TranslateAnimation(0,
                convertDpToPixel(GetNextCardvalueList.get(position),this), 0, convertDpToPixel(25,this));
        animationt.setDuration(1000);
        animationt.setFillAfter(true);
        animationt.setAnimationListener(this);

        imageView.startAnimation(animationt);


    }

    ArrayList<Float> GetNextCardvalueList = new ArrayList<>();
    private void GetNextCartValue(){

        GetNextCardvalueList.clear();

        float NewValue = 200;
        GetNextCardvalueList.add(NewValue);
        for (int i = 0; i < 13 ; i++) {

            NewValue =  NewValue - 35;

            GetNextCardvalueList.add(NewValue);
        }

    }


    String type = "",category_id;
    public void openCategory(String category) {

        type = category.toLowerCase();
        setSelectedType(category);

    }

    private void setSelectedType(String type) {

        try {
            for (int i = 0; i < lnr_cardlist.getChildCount(); i++) {

                View view = lnr_cardlist.getChildAt(i);
//                TextView txtview = view.findViewById(R.id.txt_cat);
//                View dividerview = view.findViewById(R.id.view);
                View imgcard = view.findViewById(R.id.imgcard);

                CardModel model = cardModelArrayList.get(i);

                if (imgcard.getTag().toString().equalsIgnoreCase(type) && model.isSelected) {
//                    Toast.makeText(TestRummyActivity.this,""+type,Toast.LENGTH_SHORT).show();

                    if(i !=cardModelArrayList.size() - 1)
                        setMargins(imgcard,0,0,-100,50);
                    else
                        setMargins(imgcard,0,0,0,50);


                } else {

                    if(i !=cardModelArrayList.size() - 1)
                        setMargins(imgcard,0,0,-100,0);
                    else
                        setMargins(imgcard,0,0,0,0);



                }

            }
        }
        catch(ArrayIndexOutOfBoundsException exception) {
//            handleTheExceptionSomehow(exception);
            Log.e("tag","Index out of bound : "+exception.getMessage());
        }


    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }



    Animation animFadein,animMove;

    private void Initialization() {

        rec_cardlist = findViewById(R.id.rec_cardlist);
        lnr_cardlist = findViewById(R.id.lnr_cardlist);
        rec_cardlist.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        cardListAdapter = new CardListAdapter(cardModelArrayList);
        rec_cardlist.setAdapter(cardListAdapter);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.sequential);

        animFadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.myholder>
    {


        ArrayList<CardModel> cardModelArrayList;
        public CardListAdapter(ArrayList<CardModel> cardModelArrayList) {
            this.cardModelArrayList = cardModelArrayList;
        }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
            return new myholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myholder holder, int position) {

            final CardModel model = cardModelArrayList.get(position);
            holder.imgcard.setBackground(getResources().getDrawable(model.CardItem));


            holder.imgcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!model.isSelected)
                        model.isSelected = true;

                    notifyDataSetChanged();

                }
            });

            if(!model.isSelected || position != cardModelArrayList.size() - 1)
                setMargins(holder.imgcard,0,0,-100,0);
            else
                setMargins(holder.imgcard,0,-50,-100,0);


        }

        private void setMargins (View view, int left, int top, int right, int bottom) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                p.setMargins(left, top, right, bottom);
                view.requestLayout();
            }
        }

        @Override
        public int getItemCount() {
            return cardModelArrayList.size();
        }

        class myholder extends RecyclerView.ViewHolder{

            RelativeLayout imgcard;

            public myholder(@NonNull View itemView) {
                super(itemView);

                imgcard = itemView.findViewById(R.id.imgcard);

            }
        }
    }
}