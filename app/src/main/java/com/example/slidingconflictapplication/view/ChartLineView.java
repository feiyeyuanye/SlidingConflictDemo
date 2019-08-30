package com.example.slidingconflictapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xwxwaa on 2018/7/24.
 */

public class ChartLineView extends LinearLayout {
    private int screenWidth;//屏幕宽度
    private int xData;
    private int yData;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);//view测量时获取屏幕宽度
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        int x = (int) e.getRawX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xData = x;
                yData = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - yData;
                int deleaX = x - xData;
//                Log.e("TAG","屏幕宽度；"+screenWidth);
//                Log.e("TAG","横向滑动距离；"+x);
//                Log.e("TAG","横向Down；"+xData);
                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    // 横向距离大于纵向距离
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
                case MotionEvent.ACTION_UP:
                    break;
                    default:
                        break;
        }
        return super.dispatchTouchEvent(e);
    }




    public ChartLineView(Context context) {
        super(context);
    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
