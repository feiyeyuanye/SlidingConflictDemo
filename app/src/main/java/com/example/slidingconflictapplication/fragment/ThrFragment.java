package com.example.slidingconflictapplication.fragment;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.adapter.ListViewAdapter;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.bean.ListFragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class ThrFragment extends BaseFragment {

    private Button btnAdd;
    private ListView mListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh=false;
    private List<ListFragmentBean> mList;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_thr;
    }

    @Override
    protected void initView() {
        btnAdd = getContentView().findViewById(R.id.btn_add);
        mListView = getContentView().findViewById(R.id.ll_data);
        swipeRefreshLayout = getContentView().findViewById(R.id.srl_refresh);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(new ListFragmentBean());
                ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.item_list_thr, mList);
                mListView.setAdapter(adapter);
                setListViewHeightByItem(mListView);
            }
        });
    }

    @Override
    protected void initData() {
        // 文字颜色渐变
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, btnAdd.getPaint().getTextSize(),getResources().getColor(R.color.colorPrimary) ,getResources().getColor(R.color.colorPrimaryDark) , Shader.TileMode.CLAMP);
        btnAdd.getPaint().setShader(mLinearGradient);
        btnAdd.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉多少距离之后开始刷新数据
        swipeRefreshLayout.setDistanceToTriggerSync(150);
        // 设置进度条背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.getResources().getColor(android.R.color.white));
        // 设置刷新动画的颜色，可以设置1或者更多.
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_green_light));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isRefresh=true;
                initListView();
            }
        });
        initListView();
    }

    private void initListView(){
        mList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            mList.add(new ListFragmentBean());
        }
        ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.item_list_thr, mList);
        mListView.setAdapter(adapter);
        setListViewHeightByItem(mListView);

        swipeRefreshLayout.setRefreshing(false);
        isRefresh=false;
    }

    private void setListViewHeightByItem(ListView listView) {
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View item = listAdapter.getView(i, null, listView);
            //item的布局要求是linearLayout，否则measure(0,0)会报错。
            item.measure(0, 0);
            //计算出所有item高度的总和
            totalHeight += item.getMeasuredHeight();
        }
        //获取ListView的LayoutParams,只需要修改高度就可以。
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //修改ListView高度为item总高度和所有分割线的高度总和。
        //这里的分隔线是指ListView自带的divider
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //将修改过的参数，重新设置给ListView
        listView.setLayoutParams(params);
    }
}
