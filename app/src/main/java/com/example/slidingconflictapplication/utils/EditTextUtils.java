package com.example.slidingconflictapplication.utils;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by xwxwaa on 2018/7/29.
 */

public class EditTextUtils {
    private  Context mContext;
    private  EditText editText;
    public EditTextUtils(Context context, EditText editText) {
        this.mContext=context;
        this.editText=editText;
    }
    public  void setOnFocus(){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
    }
    public void addTextWatchSetHintSize(){
        editText.addTextChangedListener(new TextWatcher() {
            boolean hint;
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(s.length() == 0) {
                    // no text, hint is visible
                    hint = true;
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                } else {
                    // no hint, text is visible
                    hint = false;
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                }


                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
                onEditTextListener.onEditTextListener(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
    }
    public  void addTextWatch(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public  void setType(){
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER|EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
    }
    public  boolean resultText(){
        String etText=editText.getText().toString();
        if (!TextUtils.isEmpty(etText)) {
                int length = etText.length() - etText.replace(".", "").length();
                if (length>1){
                    Toast.makeText(mContext, "金额输入有误,小数点只能有一位！", Toast.LENGTH_SHORT).show();
                }else{
                    if (etText.substring(etText.length()-1,etText.length()).equals(".")){
                        Toast.makeText(mContext, "金额输入有误，最后一位不能是小数点！", Toast.LENGTH_SHORT).show();
                    }else{
                        double money=Double.parseDouble(etText);
                        if(money == 0.0||money<0){
                            Toast.makeText(mContext, "不能小于或等于0!", Toast.LENGTH_SHORT).show();
                        }else {
                            return  true;
                        }
                    }
                }
        }else {
            Toast.makeText(mContext, "请输入!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private static OnEditTextListener onEditTextListener;

    public interface OnEditTextListener {
        void onEditTextListener(String s);
    }

    public void setOnEditTextListener(OnEditTextListener onEditTextListener) {
        this.onEditTextListener = onEditTextListener;
    }
}
