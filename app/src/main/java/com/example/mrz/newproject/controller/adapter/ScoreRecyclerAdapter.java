package com.example.mrz.newproject.controller.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.ScoreQueryActivity;
import com.example.mrz.newproject.model.bean.ScoreBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mr.z on 2017/9/19.
 */

public class ScoreRecyclerAdapter extends RecyclerView.Adapter<ScoreRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ScoreBean> scores;

    public ScoreRecyclerAdapter(Context context, List<ScoreBean> scores){
        this.context = context;
        this.scores = scores;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_item,parent,false);
        return new ScoreRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.score_item_name.setText(scores.get(position).getScoreName());
        holder.score_item_score.setText(scores.get(position).getScoreResult());

        holder.score_item_1.setText(scores.get(position).getScoreId());
        holder.score_item_2.setText(scores.get(position).getScoreNature());
        holder.score_item_3.setText(scores.get(position).getScoreCredit());
        holder.score_item_4.setText(scores.get(position).getScoreGarde());
        holder.score_item_5.setText(scores.get(position).getScoreSchool());

        holder.score_item_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.score_item_more.getVisibility() == View.VISIBLE){
                    holder.score_item_more.setVisibility(view.GONE);
                }else{
                    holder.score_item_more.setVisibility(view.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView score_item_name;
        TextView score_item_score;

        TextView score_item_1;
        TextView score_item_2;
        TextView score_item_3;
        TextView score_item_4;
        TextView score_item_5;

        LinearLayout score_item_more;
        RelativeLayout score_item_onclick;

        public ViewHolder(View itemView) {
            super(itemView);
            score_item_name = (TextView) itemView.findViewById(R.id.score_item_name);
            score_item_score = (TextView) itemView.findViewById(R.id.score_item_score);
            score_item_1 = (TextView) itemView.findViewById(R.id.score_item_1);
            score_item_2 = (TextView) itemView.findViewById(R.id.score_item_2);
            score_item_3 = (TextView) itemView.findViewById(R.id.score_item_3);
            score_item_4 = (TextView) itemView.findViewById(R.id.score_item_4);
            score_item_5 = (TextView) itemView.findViewById(R.id.score_item_5);

            score_item_more = (LinearLayout) itemView.findViewById(R.id.score_item_more);
            score_item_onclick = (RelativeLayout) itemView.findViewById(R.id.score_item_onclick);

        }
    }
}
