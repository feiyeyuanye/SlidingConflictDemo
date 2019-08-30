package com.example.slidingconflictapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by xwxwaa on 2018/7/25.
 */

public class ChartLineRelativeView extends RelativeLayout {

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
        int y = (int) e.getX();
        int x = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - yData;
                int deleaX = x - xData;
//                Log.e("TAG","屏幕宽度；"+screenWidth);
                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
                case MotionEvent.ACTION_UP:
                    break;
                    default:
                        break;
        }
        xData = x;
        yData = y;
        return super.dispatchTouchEvent(e);
    }






    public ChartLineRelativeView(Context context) {
        super(context);
    }

    public ChartLineRelativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartLineRelativeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
