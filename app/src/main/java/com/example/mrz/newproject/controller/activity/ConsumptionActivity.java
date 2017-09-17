package com.example.mrz.newproject.controller.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mrz.newproject.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//消费情况查询页面
public class ConsumptionActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    @BindView(R.id.pie_chart)
    PieChart mPieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        ButterKnife.bind(this);
        //初始化圆饼图
        intiView();
    }

    private void intiView() {
        /**
         * 是否使用百分比
         */
        mPieChart.setUsePercentValues(true);
        mPieChart.setEnabled(false);
        /**
         * 圆环距离屏幕的距离
         */
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        /**
         * 设置圆环中间的文字
         */
        mPieChart.setCenterText("缴费记录");

        /**
         * 是否显示圆环中间的洞
         */
        mPieChart.setDrawHoleEnabled(true);
        /**
         * 设置中间洞的颜色
         */
        mPieChart.setHoleColor(Color.WHITE);

        /**
         * 设置圆环透明度及半径
         */
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        /**
         * 设置圆环中间洞的半径
         */
        mPieChart.setHoleRadius(40f);
        mPieChart.setTransparentCircleRadius(40f);
        // 设置周围文字大小
        mPieChart.setDescriptionTextSize(10);
        // 设置圆盘中间文字大小
        mPieChart.setCenterTextSize(20);
        // 设置周围字体颜色
        // mPieChart.setValueTextColor(Color.BLACK);
        // 饼图又下角的说明文字
        mPieChart.setDescription("重庆电子工程职业学院");
        // 饼图右下角说明文字大小
        mPieChart.setDescriptionTextSize(10);

        //设置比例图
        Legend mLegend = mPieChart.getLegend();
        //右边垂直显示
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        mLegend.setFormSize(12f);//比例块字体大小
        mLegend.setXEntrySpace(20f);//设置距离饼图的距离，防止与饼图重合
        mLegend.setYEntrySpace(8f);
        //设置比例块换行...
        mLegend.setWordWrapEnabled(true);
        mLegend.setOrientation(Legend.LegendOrientation.VERTICAL);

        mLegend.setTextColor(getResources().getColor(android.R.color.black));
        //设置比例块形状 原点
        mLegend.setForm(Legend.LegendForm.CIRCLE);


        /**
         * 是否显示洞中间文本
         */
        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        /**
         *触摸是否可以旋转以及松手后旋转的度数
         */
        //mPieChart.setRotationAngle(20);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(40, "优秀"));
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(30, "及格"));
        entries.add(new PieEntry(10, "不及格"));

        //设置数据
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);


    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }


    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
