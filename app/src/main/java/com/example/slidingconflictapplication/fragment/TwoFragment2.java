package com.example.slidingconflictapplication.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.adapter.ListViewAdapter;
import com.example.slidingconflictapplication.adapter.RecycleViewAdapter;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.bean.ListFragmentBean;
import com.example.slidingconflictapplication.view.ChartLineRelativeView;
import com.example.slidingconflictapplication.view.LineChartView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class TwoFragment2 extends BaseFragment {
    private List<String> mList ;

    private RecyclerView rvData;
    private RecycleViewAdapter recycleAdapter;


    private LineChart chartview;
    private LineData lineData;
    private List<String> xValue = new ArrayList<>();//x轴坐标对应的数据
    private List<Float> yValue = new ArrayList<>();//y轴坐标对应的数据
    private Map<String, Integer> value = new HashMap<>();//折线对应的数据
    private ChartLineRelativeView rl_chart;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_two2;
    }

    @Override
    protected void initView() {
        chartview = (LineChart)getContentView().findViewById(R.id.chartview);
        rvData = getContentView().findViewById(R.id.rv_data);
        if (onSFragmentHeightListener!=null){
            onSFragmentHeightListener.onSFragmentHeightListener(1,getContentView());
        }

    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mList.add(String.valueOf(i));
        }
        recycleAdapter = new RecycleViewAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvData.setLayoutManager(layoutManager);
        rvData.setAdapter(recycleAdapter);
        rvData.setNestedScrollingEnabled(false);
        chartview.setDescription("");
        //权益折线图
        for (int i = 0; i < 20; i++) {
            String time = "2018-07-10 17:28:15".substring(5, 10);
            xValue.add(time);
            float  b   =  (float)1050/10000;
//                        float  b   =  (float)(Math.round((equityHomeBeanList.get(i).getOrderPrice()/10000)*10))/10;
            yValue.add(b);
        }
        lineData = LineChartView.initSingleLineChart(getContext(),chartview,xValue.size(),xValue,yValue);
        LineChartView.initDataStyle(chartview,lineData,getContext());
    }

    private static OnFragmentHeightListener onSFragmentHeightListener;


    public interface OnFragmentHeightListener {
        void onSFragmentHeightListener(int position,View view);
    }

    public void setOnFragmentHeightListener(OnFragmentHeightListener onSFragmentHeightListener) {
        this.onSFragmentHeightListener = onSFragmentHeightListener;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
