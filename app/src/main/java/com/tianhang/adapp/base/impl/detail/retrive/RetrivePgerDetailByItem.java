package com.tianhang.adapp.base.impl.detail.retrive;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BaseDetailPager;
import com.tianhang.adapp.widget.RefreshListView;

import java.util.ArrayList;

/**
 * Created by student on 31/8/15.
 */
public class RetrivePgerDetailByItem extends BaseDetailPager {

    private RefreshListView refreshListView;
    private ArrayList<String> list = new ArrayList<String>();
    private MyAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            //更新UI
            adapter.notifyDataSetChanged();
            refreshListView.completeRefresh();
        };
    };

    public RetrivePgerDetailByItem(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_retrive_detail_by_item, null);
        ViewUtils.inject(this, view); // 把当前的View对象注入到xUtils框架中
        refreshListView = (RefreshListView)view.findViewById(R.id.refreshListView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        for(int i =0;i<30;i++){
            list.add("listview data ->"+i);
        }
        // addHeadView must be operated before set adapter
        View headView = View.inflate(mActivity,R.layout.retrive_header_title_list,null);
        refreshListView.addHeaderView(headView);

        adapter = new MyAdapter();
        refreshListView.setAdapter(adapter);

        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                //需要联网请求服务器的数据，然后更新UI
                requestDataFromServer(false);
            }

            @Override
            public void onLoadingMore() {
                requestDataFromServer(true);
            }
        });

    }

    /**
     * 模拟向服务器请求数据
     */
    private void requestDataFromServer(final boolean isLoadingMore){
        new Thread(){
            public void run() {
                SystemClock.sleep(3000);//模拟请求服务器的一个时间长度

                if(isLoadingMore){
                    list.add("加载更多的数据-1");
                    list.add("加载更多的数据-2");
                    list.add("加载更多的数据-3");
                }else {
                    list.add(0, "下拉刷新的数据");
                }

                //在UI线程更新UI
                handler.sendEmptyMessage(0);
            };
        }.start();
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView != null){
                view = convertView;
            }else {
                view = View.inflate(mActivity,R.layout.pager_retrive_item,null);
            }
            TextView tv_code = (TextView)view.findViewById(R.id.listview_retrive_item_code);
            TextView tv_name = (TextView)view.findViewById(R.id.listview_retrive_item_name);
            TextView tv_amount = (TextView)view.findViewById(R.id.listview_retrive_item_amount);
            TextView tv_unit = (TextView)view.findViewById(R.id.listview_retrive_item_unit);
            TextView tv_bin = (TextView)view.findViewById(R.id.listview_retrive_item_bin);


            // get position
            tv_code.setText("00001");
            tv_name.setText("pen");
            tv_amount.setText("10");
            tv_unit.setText("12");
            tv_bin.setText("A-2");
            return view;
        }
    }

}
