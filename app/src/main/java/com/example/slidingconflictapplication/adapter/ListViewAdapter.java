package com.example.slidingconflictapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.bean.ListFragmentBean;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    private final int resourceId;

    public ListViewAdapter(Context context, int textViewResourceId, List<ListFragmentBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListFragmentBean thrFragmentBean = (ListFragmentBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView str= (TextView) view.findViewById(R.id.tv_text);

        if (thrFragmentBean!=null){
            str.setText(String.valueOf(position));
        }
       return view;
    }
}