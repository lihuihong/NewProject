package com.example.mrz.newproject.controller.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.ScoreQueryActivity;

/**
 * Created by Mr.z on 2017/9/19.
 */

public class ScoreRecyclerAdapter extends RecyclerView.Adapter<ScoreRecyclerAdapter.ViewHolder> {

    private Context context;

    public ScoreRecyclerAdapter(Context context){
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_item,parent,false);
        return new ScoreRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView score_item_name;
        TextView score_item_score;



        public ViewHolder(View itemView) {
            super(itemView);
            score_item_name = (TextView) itemView.findViewById(R.id.score_item_name);
            score_item_score = (TextView) itemView.findViewById(R.id.score_item_score);

        }
    }
}
