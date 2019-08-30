package com.example.slidingconflictapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.slidingconflictapplication.fragment.OneFragment;
import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.fragment.ThrFragment;
import com.example.slidingconflictapplication.fragment.TwoFragment;
import com.example.slidingconflictapplication.utils.AndroidWorkaround;
import com.example.slidingconflictapplication.utils.StatusBarModify;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpContent;
    private TextView onePage,twoPage,threePage;

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThrFragment thrFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 页面顶部透明
        StatusBarModify.transportStatus(this);
        // 页面顶部，如果显示操作栏，会被顶起
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }

        initView();
        initData();
    }

    private void initData() {
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        thrFragment = new ThrFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.commit();
        onePage.setSelected(true);

        Bundle bundle = new Bundle();
        bundle.putString("id", getIntent().getStringExtra("id"));
        oneFragment.setArguments(bundle);
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(thrFragment);

        vpContent.setOffscreenPageLimit(2);
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        onePage.setSelected(true);
                        twoPage.setSelected(false);
                        threePage.setSelected(false);
                        break;
                    }
                    case 1: {
                        onePage.setSelected(false);
                        twoPage.setSelected(true);
                        threePage.setSelected(false);
                        break;
                    }
                    case 2: {
                        onePage.setSelected(false);
                        twoPage.setSelected(false);
                        threePage.setSelected(true);
                        break;
                    }
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        vpContent = findViewById(R.id.vp_content);
        onePage = findViewById(R.id.tv_one);
        twoPage = findViewById(R.id.tv_two);
        threePage = findViewById(R.id.tv_thr);
    }
    /**
     * 物理返回键
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
