package com.example.slidingconflictapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by mling on 2017/7/5.
 */

public class StatusBarModify {

    //判断是否有虚拟键
    public static boolean isHaveNavigationBar(Context context) {

        boolean isHave = false;
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                isHave = false;
            } else if ("0".equals(navBarOverride)) {
                isHave = true;
            }
        } catch (Exception e) {
            Log.w("TAG", e);
        }


        return isHave;
    }

    //返回为true的话就不给他设置，反之设置，具体代码如下：
    public static void transportStatus(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (!isHaveNavigationBar(context)){
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

        }
    }

    //获取状态栏的高度
    public static int getStatusBarHeight(Context context){
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    //给响应的状态栏的顶部空白设置测量出的高度默认为20dp
    public static void setStatusBarHeight(Context context, View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (getStatusBarHeight(context) > 0){
            layoutParams.height = getStatusBarHeight(context);
            Log.e("height",getStatusBarHeight(context)+"--");
        }else {
            layoutParams.height = (int) dp2px(20,context);
            Log.e("height",(int) dp2px(20,context)+"--");
        }

        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        view.setLayoutParams(layoutParams);
    }


    public static float dp2px(float dp,Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
}
