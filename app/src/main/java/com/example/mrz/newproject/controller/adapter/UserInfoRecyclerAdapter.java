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
import com.example.mrz.newproject.model.bean.Class;
import com.example.mrz.newproject.model.bean.UserInfoKVP;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.z on 2017/9/14.
 */

public class UserInfoRecyclerAdapter  extends RecyclerView.Adapter<UserInfoRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<UserInfoKVP> infos;

    public UserInfoRecyclerAdapter(Context context, List<UserInfoKVP> infos){
        this.context = context;
        this.infos = infos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userinfo_item,parent,false);
        return new UserInfoRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.userinfo_key.setText(infos.get(position).getKey());
        holder.userinfo_value.setText(infos.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView userinfo_key;
        TextView userinfo_value;


        public ViewHolder(View itemView) {
            super(itemView);
            userinfo_key = (TextView) itemView.findViewById(R.id.userinfo_key);
            userinfo_value = (TextView) itemView.findViewById(R.id.userinfo_value);

        }
    }
}
