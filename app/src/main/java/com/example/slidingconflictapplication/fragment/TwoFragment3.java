package com.example.slidingconflictapplication.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.adapter.RecycleViewAdapter;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.bean.ListFragmentBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class TwoFragment3 extends BaseFragment {
    private List<String> mList ;

    private RecyclerView rvData;
    private RecycleViewAdapter recycleAdapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_two3;
    }

    @Override
    protected void initView() {
        rvData = getContentView().findViewById(R.id.rv_data);
        if (onSFragmentHeightListener!=null){
            onSFragmentHeightListener.onSFragmentHeightListener(2,getContentView());
        }
    }
    @Override
    protected void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add(String.valueOf(i));
        }
        recycleAdapter = new RecycleViewAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvData.setLayoutManager(layoutManager);
        rvData.setAdapter(recycleAdapter);
        rvData.setNestedScrollingEnabled(false);
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
