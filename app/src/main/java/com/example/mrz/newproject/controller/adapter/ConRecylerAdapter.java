package com.example.mrz.newproject.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.Consume;

import java.util.List;

import static com.example.mrz.newproject.model.dao.GSUserInfoDao.context;

/**
 * Created by 那个谁 on 2017/9/26.
 * 奥特曼打小怪兽
 * 作用：
 */

public class ConRecylerAdapter extends RecyclerView.Adapter<ConRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<Consume> mConsume;

    public ConRecylerAdapter(Context context, List<Consume> consume) {
        mContext = context;
        mConsume = consume;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(context).inflate(R.layout.con_item,parent,false);
        // 实例化viewholder

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_place.setText(mConsume.get(position).getAddress());
        holder.tv_date.setText(mConsume.get(position).getDate());
        holder.tv_money.setText(mConsume.get(position).getBalance());

    }


    @Override
    public int getItemCount() {
        return mConsume.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //地点
        TextView tv_place;
        //时间
        TextView tv_date;
        //消费
        TextView tv_money;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_place = (TextView) itemView.findViewById(R.id.tv_place);
        }
    }
}
