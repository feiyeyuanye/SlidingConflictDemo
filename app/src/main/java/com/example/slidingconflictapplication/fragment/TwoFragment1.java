package com.example.slidingconflictapplication.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.adapter.ListViewAdapter;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.bean.ListFragmentBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class TwoFragment1 extends BaseFragment implements View.OnClickListener {
    private TextView add;
    private ListView mLv;
    private List<ListFragmentBean> mList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_two1;
    }

    @Override
    protected void initView() {
        if (onSFragmentHeightListener!=null){
            onSFragmentHeightListener.onSFragmentHeightListener(0,getContentView());
        }
        add = getContentView().findViewById(R.id.tv_two1_add);
        mLv = getContentView().findViewById(R.id.lv_two1);

        add.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        mList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new ListFragmentBean());
        }
        ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.item_list_thr, mList);
        mLv.setAdapter(adapter);
        setListViewHeightByItem(mLv);
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
    private static OnFragmentHeightListener onSFragmentHeightListener;

    @Override
    public void onClick(View v) {
        mList.add(new ListFragmentBean());
        ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.item_list_thr, mList);
        mLv.setAdapter(adapter);
        setListViewHeightByItem(mLv);
    }

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
