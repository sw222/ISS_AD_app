package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BaseDetailPager;
import com.tianhang.adapp.base.BasePager;
import com.tianhang.adapp.base.impl.detail.requisition.RequisitionPgerDetailByAll;
import com.tianhang.adapp.base.impl.detail.requisition.RequisitionPgerDetailByComplete;
import com.tianhang.adapp.base.impl.detail.requisition.RequisitionPgerDetailByPending;
import com.tianhang.adapp.base.impl.detail.requisition.RequisitionPgerDetailByWait;
import com.tianhang.adapp.base.impl.detail.retrive.RetrivePgerDetailByDepa;
import com.tianhang.adapp.base.impl.detail.retrive.RetrivePgerDetailByItem;
import com.tianhang.adapp.domain.NewsBean.NewsMenuBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * 侧边栏, 新闻详情页面
 *
 * @author Kevin
 *
 */
public class RequisitionPager extends BasePager implements
        OnPageChangeListener {

    //private static final String TAG = NewsMenuDetailPager.class.getSimpleName();

    @ViewInject(R.id.tpi_requisition)
    private TabPageIndicator mIndicator;

    @ViewInject(R.id.vp_requisition)
    private ViewPager mViewPager;

    private ArrayList<String> tabItems;// 页签对应的数据
    private ArrayList<BaseDetailPager> DetailPagerList;// 页签对应的页面列表

    private NewsMenuAdapter mAdapter;

    public RequisitionPager(Activity activity) {
        super(activity);
        //mNewsTabDataList = newsMenuBean.children;

    }
    public RequisitionPager(Activity activity, NewsMenuBean newsMenuBean) {
        super(activity);
        //mNewsTabDataList = newsMenuBean.children;

    }

    @Override
    public void initViews() {
        Log.i("tianhang", "requisition--->initView--->start");
        super.initViews();
        View view = View.inflate(mActivity, R.layout.pager_requisition, null);
        ViewUtils.inject(this, view); // 把当前的View对象注入到xUtils框架中
        mRootView = view;
        Log.i("tianhang", "requisition--->initView--->end");
    }

    @Override
    public void initData() {
        Log.i("tianhang", "requisition--->initData--->start");
        DetailPagerList = new ArrayList<BaseDetailPager>();

        // 初始化页签页面列表
		/*
		for (NewsMenuTab newsTab : mNewsTabDataList) {
			TabDetailPager pager = new TabDetailPager(mActivity, newsTab);
			mNewsTabPagerList.add(pager);
		}*/

        DetailPagerList.add(new RequisitionPgerDetailByAll(mActivity));
        DetailPagerList.add(new RequisitionPgerDetailByPending(mActivity));
        DetailPagerList.add(new RequisitionPgerDetailByWait(mActivity));
        DetailPagerList.add(new RequisitionPgerDetailByComplete(mActivity));

        tabItems = new ArrayList<String>();
        tabItems.add("All");
        tabItems.add("Pending");
        tabItems.add("Wait");
        tabItems.add("Complete");


        mAdapter = new NewsMenuAdapter();
        mViewPager.setAdapter(mAdapter);

        // 注意: 如果ViewPagerIndicator和ViewPager搭配使用时,
        // 设置ViewPager滑动监听的方法必须在ViewPagerIndicator中执行!!!!
        // mViewPager.setOnPageChangeListener(this);

        mIndicator.setViewPager(mViewPager);// 将指针和ViewPager关联起来
        mIndicator.setOnPageChangeListener(this);// 设置ViewPager的滑动监听
        mIndicator.setVisibility(View.VISIBLE);// 设置ViewPager的滑动监听
    }

    /**
     * ViewPager的数据适配器
     *
     * @author Kevin
     *
     */
    class NewsMenuAdapter extends PagerAdapter {

        /**
         * 返回页面标题, 用于在指针中显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems.get(position);
        }

        @Override
        public int getCount() {
            return tabItems.size();
            //return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //TabDetailPager tabDetailPager = mNewsTabPagerList.get(position);
            //container.addView(tabDetailPager.mRootView);
            //tabDetailPager.initData();// 初始化数据
            //return tabDetailPager.mRootView;
            //Toast.makeText(mActivity,"_"+position,Toast.LENGTH_SHORT).show();
            BaseDetailPager baseDetailPager = DetailPagerList.get(position);
            container.addView(baseDetailPager.mRootView);
            baseDetailPager.initData();
            return baseDetailPager.mRootView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

	/*
	@OnClick(R.id.iv_news_tab_next)
	public void nextTab(View view) {
		//Toast.makeText(mActivity,"_"+position,Toast.LENGTH_SHORT).show();
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
	}*/

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
		/*
		Log.d(TAG, "onPageSelected=" + arg0);
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if (arg0 == 0) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 设置侧边栏不可用
		} */

    }
}
