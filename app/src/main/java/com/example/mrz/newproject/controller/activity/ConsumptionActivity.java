package com.example.mrz.newproject.controller.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.ConRecylerAdapter;
import com.example.mrz.newproject.model.bean.Consume;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//消费情况查询页面
public class ConsumptionActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    @BindView(R.id.pie_chart)
    PieChart mPieChart;

    @BindView(R.id.rl_data)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_con)
    TextView mTv_money;

    //标题
    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_more)
    ImageView toolbar_more;
    @BindView(R.id.tv_error)
    TextView mTv_err;

    private List<Consume> mConsumesList;
    //消费
    private int mCount;
    //余额
    private int mBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        ButterKnife.bind(this);
        initToolBar();
        initData();
        //初始化圆饼图
        intiView();

    }

    private void initData() {

        //获取传递过来的数据
        mConsumesList = (List<Consume>) getIntent().getSerializableExtra("consumes");

        //用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置数据适配器
        mRecyclerView.setAdapter(new ConRecylerAdapter(this, mConsumesList));

    }

    private void intiView() {
        /**
         * 是否使用百分比
         */
        //mPieChart.setUsePercentValues(true);
        mPieChart.setEnabled(false);
        /**
         * 圆环距离屏幕的距离
         */
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        /**
         * 设置圆环中间的文字
         */
        //mPieChart.setCenterText("");

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
        mPieChart.setDrawCenterText(false);

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

        if (mConsumesList.size()==1 || mConsumesList.size()>1) {
            for (int i = 0; i < mConsumesList.size(); i++) {
                BigDecimal bd = new BigDecimal(mConsumesList.get(i).getPrice());
                int max = bd.intValue();
                if (max < 0) {
                    mCount += max;
                } else {
                    mBalance += max;
                }
                mTv_money.setText(mCount + "");
                //模拟数据
                ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
                if (mConsumesList.get(mConsumesList.size() - 1).getAccountNumber() != null) {
                    entries.add(new PieEntry(Float.parseFloat((mConsumesList.get(mConsumesList.size() - 1).getAccountNumber())), "余额"));
                } else {
                    entries.add(new PieEntry(Float.parseFloat((mConsumesList.get(mConsumesList.size() - 1).getBalance())), "余额"));
                }
                entries.add(new PieEntry(mCount, "消费"));
                if (mBalance != 0) {
                    entries.add(new PieEntry(mBalance, "充值"));
                } else {
                    //entries.add(new PieEntry(0, "充值"));
                }
                //设置数据
                setData(entries);
            }
        } else {
            mTv_err.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        mPieChart.animateY(800, Easing.EasingOption.EaseInCirc);
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
        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        /*for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/
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

    //初始化标题栏
    private void initToolBar() {
        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_title.setText("本月消费");
        toolbar_more.setVisibility(View.GONE);

    }

}
