package com.gamegards.allinonev3._AdharBahar.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.gamegards.allinonev3._AdharBahar.Model.GameHistory;
import com.gamegards.allinonev3.R;

import java.util.ArrayList;

public class GamesHistoryAdapter extends RecyclerView.Adapter<GamesHistoryAdapter.myholder> {
    Activity context;
    ArrayList<GameHistory> gameModelArrayList;

    public GamesHistoryAdapter(Activity context, ArrayList<GameHistory> gameModelArrayList) {
        this.context = context;
        this.gameModelArrayList = gameModelArrayList;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gamehistory,parent,false);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, final int position) {
        holder.txtGameId.setText("Game Id "+gameModelArrayList.get(position).getId());
        holder.txtroomid.setText("Room "+gameModelArrayList.get(position).getRoom_id());


        if (gameModelArrayList.get(position).getBet().equals("0")){

            holder.txtBeton.setText("ANADAR");
        }else {
            holder.txtBeton.setText("BAHAR");

        }

        holder.txtGameAmount.setText(""+gameModelArrayList.get(position).getAmount());
        holder.txtTime.setText(""+gameModelArrayList.get(position).getAdded_date());

        if (gameModelArrayList.get(position).getWinning_amount().equals("0.00")){

            holder.btnstatusgame.setText("LOST");
        }else {
            holder.btnstatusgame.setText("Win");

        }

      //  holder.btnstatusgame.setText(""+gameModelArrayList.get(position).getWinning_amount());
//        holder.itemView.findViewById(R.id.btnplay).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, GameActivity.class);
//                intent.putExtra("room_id" ,gameModelArrayList.get(position).getId());
//               context.startActivity(intent);
//              //  context.startActivity(new Intent(context, TestAimationAcitivty.class));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return gameModelArrayList.size();
    }

    class  myholder extends RecyclerView.ViewHolder{
        TextView txtroomid,txtBeton,txtGameAmount,txtGameId,txtTime;
        Button btnstatusgame;
        public myholder(@NonNull View itemView) {
            super(itemView);

            txtroomid = itemView.findViewById(R.id.txtroomid);
            txtBeton = itemView.findViewById(R.id.txtBeton);
            txtGameAmount = itemView.findViewById(R.id.txtGameAmount);
            btnstatusgame = itemView.findViewById(R.id.btnstatusgame);
            txtGameId = itemView.findViewById(R.id.txtGameId);
            txtTime = itemView.findViewById(R.id.txtTime);



        }
    }
}
