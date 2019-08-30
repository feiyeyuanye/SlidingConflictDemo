package com.example.slidingconflictapplication.fragment;

import android.widget.EditText;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.utils.EditTextUtils;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class OneFragment extends BaseFragment {
    private EditText editText;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {
        editText = getContentView().findViewById(R.id.et_text);
    }

    @Override
    protected void initData() {
        EditTextUtils myEditText=new EditTextUtils(getContext(),editText);
        myEditText.addTextWatch();
    }
}
