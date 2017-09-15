package com.example.mrz.newproject.controller.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.LoginActivity;
import com.example.mrz.newproject.model.bean.Class;
import com.example.mrz.newproject.uitls.DensityUtils;

import java.util.List;

/**
 * Created by Mr.z on 2017/8/18.
 *
 * 作用：选课recycler数据适配器
 */

public class ElectiveRecyclerAdapter extends RecyclerView.Adapter<ElectiveRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Class> classes;


    public ElectiveRecyclerAdapter(Context context, List<Class> classes){
        this.context = context;
        this.classes = classes;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.class_item,parent,false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.class_name.setText(classes.get(position).getClassName());
        holder.class_teacher.setText(classes.get(position).getClassTeacter());
        String classTime = classes.get(position).getClassTime();
        if(classTime.length() > 25)
            classTime = classTime.substring(0, classTime.indexOf("{")) + classTime.substring(classTime.indexOf("}")+1);
        holder.class_time.setText(classTime);
        holder.class_place.setText("上课地点："+classes.get(position).getClassPlace());
        holder.class_scroe.setText(classes.get(position).getClassScore());
        holder.class_total.setText("总量: "+classes.get(position).getClassTotal());
        holder.class_selected.setText("已选: "+classes.get(position).getClassSelected());
        holder.class_margin.setText("余量: "+classes.get(position).getClassMargin());

        //条目添加点击事件
        holder.cv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //弹出确认选择框
                new AlertDialog.Builder(context)
                        .setTitle("确认选择"+classes.get(position).getClassName()+"?")
                        .setMessage("学号或者密码错误，请检查！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //确认选课弹出选课成功的框
                                if(holder.rl_selected.getVisibility() == View.VISIBLE)
                                {
                                    holder.rl_selected.setVisibility(View.GONE);
                                }else{
                                    holder.rl_selected.setVisibility(View.VISIBLE);
                                }
                                classes.get(position).setFlag(!classes.get(position).isFlag());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        holder.cv_class.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView class_name;
        TextView class_teacher;
        TextView class_time;
        TextView class_place;
        TextView class_scroe;
        TextView class_total;
        TextView class_selected;
        TextView class_margin;
        CardView cv_class;
        RelativeLayout rl_selected;


        public ViewHolder(View itemView) {
            super(itemView);
            cv_class = (CardView) itemView;
            class_name = (TextView) itemView.findViewById(R.id.class_name);
            class_teacher = (TextView) itemView.findViewById(R.id.class_teacher);
            class_time = (TextView) itemView.findViewById(R.id.class_time);
            class_place = (TextView) itemView.findViewById(R.id.class_place);
            class_scroe = (TextView) itemView.findViewById(R.id.class_scroe);
            class_total = (TextView) itemView.findViewById(R.id.class_total);
            class_selected = (TextView) itemView.findViewById(R.id.class_selected);
            class_margin = (TextView) itemView.findViewById(R.id.class_margin);
            rl_selected = (RelativeLayout) cv_class.findViewById(R.id.rl_selected);
        }
    }
}
