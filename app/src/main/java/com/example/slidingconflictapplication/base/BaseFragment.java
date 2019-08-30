package com.example.slidingconflictapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private View layoutView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(setLayoutResourceID(),container,false);
        mContext = getContext();
        initView();
        initData();
        return layoutView;
    }

    /**
     * 用于返回Fragment设置ContentView的布局文件资源ID
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void initView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void initData();


    public View getContentView() {
        return layoutView;
    }

    public Context getMContext() {
        return mContext;
    }
}
