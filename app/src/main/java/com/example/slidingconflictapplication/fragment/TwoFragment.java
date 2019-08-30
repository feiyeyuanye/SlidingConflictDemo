package com.example.slidingconflictapplication.fragment;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.slidingconflictapplication.R;
import com.example.slidingconflictapplication.base.BaseFragment;
import com.example.slidingconflictapplication.bean.ThoFragmentBean;
import com.example.slidingconflictapplication.view.ChildViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/8/28.
 */

public class TwoFragment extends BaseFragment implements View.OnClickListener, TwoFragment2.OnFragmentHeightListener, TwoFragment1.OnFragmentHeightListener, TwoFragment3.OnFragmentHeightListener {

    private TextView onePage,twoPage,thrPage;
    private TwoFragment1 twoFragment1;
    private TwoFragment2 twoFragment2;
    private TwoFragment3 twoFragment3;
    private NestedScrollView nestscrollview;
    private String[] titles = { "1", "2", "3" };
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh=false;
    // 自定义指示器
    private LinearLayout ll_table;
    private View indicator;
    private int width;
    private int pandding =50;
    private ViewPager vp;
    private ChildViewPager vp_content;
    private List<Fragment> mListFragment = new ArrayList<Fragment>();
    private String[] colorBg = {"#EFA454","#D3B77F","#6F6F6F" };
    private int colorblack= Color.parseColor("#000000");
    private int pagerInder=0;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {
        onePage = getContentView().findViewById(R.id.tv_onepage);
        twoPage = getContentView().findViewById(R.id.tv_twopage);
        thrPage = getContentView().findViewById(R.id.tv_thrpage);
        nestscrollview = getContentView().findViewById(R.id.nestscrollview);
        ll_table = getContentView().findViewById(R.id.ll_table);
        indicator = getContentView().findViewById(R.id.indicator);
        vp = getContentView().findViewById(R.id.vp_silver);
        vp_content = getContentView().findViewById(R.id.vp_content);
        swipeRefreshLayout = getContentView().findViewById(R.id.srl_refresh);

        onePage.setOnClickListener(this);
        twoPage.setOnClickListener(this);
        thrPage.setOnClickListener(this);
        twoFragment1=new TwoFragment1();
        twoFragment2=new TwoFragment2();
        twoFragment3=new TwoFragment3();
        twoFragment1.setOnFragmentHeightListener(this);
        twoFragment2.setOnFragmentHeightListener(this);
        twoFragment3.setOnFragmentHeightListener(this);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nestscrollview.fling(0);
            nestscrollview.smoothScrollTo(0, 0);

        }
    };
    @Override
    protected void initData() {
        // 首先要将它做成 1/3大小
        ll_table.post(new Runnable() {
            @Override
            public void run() {
//                windowWidth = getResources().getDisplayMetrics().widthPixels;
                width = ll_table.getWidth() / 3;
                RelativeLayout.LayoutParams param =new RelativeLayout.LayoutParams(width-pandding*2,4);
                param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_LEFT,R.id.ll_table);
                param.setMargins(pandding, 0, 0, 0);
                indicator.setLayoutParams(param);
                indicator.setBackgroundColor(Color.parseColor("#EFA454"));
            }
        });
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp_content.resetHeight(position);
                vp.setCurrentItem(position);
                pagerInder=position;
                // 使用动画来执行

                handler.sendEmptyMessageDelayed(1,300);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // 使用动画来执行
                vp_content.setCurrentItem(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                        Log.e("TAG", arg0 + "-----   " + arg1 + "---------    " + arg2);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width
//                        - pandding * 2, 2);
//                indicator.setLayoutParams(params);
//                        width = ll_table.getWidth() / 3;
//                Log.e("TAG", "run2: width = " + width);

                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width-pandding*2, 4);
                param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_LEFT,R.id.ll_table);
//                    if (arg0 == 0&& arg1 == 0){
                param.setMargins((int) (width * (arg1 + arg0)+pandding), 0,
                        0, 0);
//                    }else {
//                        param.setMargins((int) (width * (arg1 + arg0))+pandding, 0,
//                                0, 0);
//                    }

                indicator.setLayoutParams(param);
                // 线的渐变
                ArgbEvaluator evaluator = new ArgbEvaluator();
                int color = (int)evaluator.evaluate(arg1,Color.parseColor(colorBg[arg0 % colorBg.length]),
                        Color.parseColor(colorBg[(arg0 + 1) % colorBg.length]));
                indicator.setBackgroundColor(color);
                // 字体的渐变
                // 挖掘->黑色
                int colorExcavateToBlack = (int)evaluator.evaluate(arg1, Color.parseColor(colorBg[0]),
                        colorblack);
                // 黑色->挖掘
                int colorBlackToExcavate = (int)evaluator.evaluate(arg1,colorblack,
                        Color.parseColor(colorBg[0]));

                // 交易->黑色
                int colorDealToBlack = (int)evaluator.evaluate(arg1, Color.parseColor(colorBg[1]),
                        colorblack);
                // 黑色->交易
                int colorBlackToDeal = (int)evaluator.evaluate(arg1,colorblack,
                        Color.parseColor(colorBg[1]));

                // ERTC->黑色
                int colorERTCToBlack = (int)evaluator.evaluate(arg1, Color.parseColor(colorBg[2]),
                        colorblack);
                // 黑色->ERTC
                int colorBlackToERTC= (int)evaluator.evaluate(arg1,colorblack,
                        Color.parseColor(colorBg[2]));
                if (arg0 == 0 ){
                    onePage.setTextColor(colorExcavateToBlack);
                    twoPage.setTextColor(colorBlackToDeal);
                }else if(arg0 == 1){
                    twoPage.setTextColor(colorDealToBlack);
                    thrPage.setTextColor(colorBlackToERTC);
                }else if(arg0 == 2){
                    twoPage.setTextColor(colorBlackToDeal);
                    thrPage.setTextColor(colorERTCToBlack);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        handler.sendEmptyMessageDelayed(1,300);
        nestscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
//                    Log.e("TAG", "Scroll DOWN;"+scrollY+"oldScrollY"+oldScrollY);
                }
                if (scrollY < oldScrollY) {
//                    Log.e("TAG", "Scroll UP");
                }

                if (scrollY == 0) {
//                    Log.e("TAG", "TOP SCROLL");

                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Log.e("TAG", "BOTTOM SCROLL");
                    if (vp_content.getCurrentItem() == 2){
//                        onScrollToLoadMoreListener.onScrollToLoadMoreListener();

                    }
                }
            }
        });
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉多少距离之后开始刷新数据
        swipeRefreshLayout.setDistanceToTriggerSync(150);
        // 设置进度条背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.getResources().getColor(android.R.color.white));
        // 设置刷新动画的颜色，可以设置1或者更多.
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_green_light));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isRefresh=true;
                showRecyclerView();
               }
        });
        showRecyclerView();
    }
    private void showRecyclerView() {
        vp.setOffscreenPageLimit(2);
        vp.setPageMargin(10);
        vp.setAdapter(adapter);
        vp_content.setOffscreenPageLimit(2);
        Bundle bundle = new Bundle();
        ThoFragmentBean thoFragmentBean = new ThoFragmentBean();
        bundle.putSerializable("bean", thoFragmentBean);
        twoFragment1.setArguments(bundle);

        if (mListFragment.size()>0){
            mListFragment.clear();
            mListFragment.add(twoFragment1);
            mListFragment.add(twoFragment2);
            mListFragment.add(twoFragment3);
            if (myFragmentPagerAdapter!=null){
                myFragmentPagerAdapter.setFragments(mListFragment);
            }
        }else{
            // 第一次加载
            mListFragment.add(twoFragment1);
            mListFragment.add(twoFragment2);
            mListFragment.add(twoFragment3);
            myFragmentPagerAdapter=new MyFragmentPagerAdapter(getChildFragmentManager(),mListFragment);
            vp_content.setAdapter(myFragmentPagerAdapter);
        }

        vp.setCurrentItem(pagerInder);
        if (isRefresh){
            swipeRefreshLayout.setRefreshing(false);
            isRefresh=false;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_onepage:
                vp.setCurrentItem(0);
                break;
            case R.id.tv_twopage:
                vp.setCurrentItem(1);
                break;
            case R.id.tv_thrpage:
                vp.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSFragmentHeightListener(int position,View view) {
        vp_content.setObjectForPosition(view,position);
    }

    class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragmentList;
        private FragmentManager fm;
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentList = list;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int arg0) {

            return mListFragment.get(arg0);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void setFragments(List<Fragment> fragments) {
            if (this.fragmentList != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragmentList) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragmentList = fragments;
            this.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mListFragment.size();
        }

    }

    public PagerAdapter adapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=View.inflate(getContext(),R.layout.item_twofragment,null);
            view.setTag(position);
            RelativeLayout rl_backgroundcolor= (RelativeLayout) view.findViewById(R.id.rl_backgroundcolor);
            // 挖掘
;           TextView tvOne = view.findViewById(R.id.tv_oneText);
            // 交易
            TextView tvTwo = view.findViewById(R.id.tv_twoText);
            // ERTC
            TextView tvThr = view.findViewById(R.id.tv_thrText);
            if (position==0){
                rl_backgroundcolor.setBackgroundResource(R.color.colorAccent);
                tvOne.setText("1");
            }else if (position==1){
                rl_backgroundcolor.setBackgroundResource(R.color.colorPrimary);
                tvTwo.setText("2");
            }else if (position ==2 ){
                rl_backgroundcolor.setBackgroundResource(R.color.colorPrimaryDark);
                tvThr.setText("3");
            }
            container.addView(view);
            this.notifyDataSetChanged();
            return view;
        };

        @Override
        public void destroyItem(android.view.ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        // 获取每一页的 title
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        };

    };

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
