package com.tianhang.adapp.base.impl.detail.retrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tianhang.adapp.R;
import com.tianhang.adapp.RequisitionDetailActivity;
import com.tianhang.adapp.RetriveByDepaActivity;
import com.tianhang.adapp.base.BaseDetailPager;
import com.tianhang.adapp.domain.RequisitionDetailBean;
import com.tianhang.adapp.rest.RestClient;
import com.tianhang.adapp.util.ConvertJSONDate;
import com.tianhang.adapp.widget.RefreshListView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by student on 31/8/15.
 */
public class RetrivePgerDetailByDepa extends BaseDetailPager {

    private RefreshListView refreshListView;
    private ArrayList<String> list = new ArrayList<String>();
    private MyAdapter adapter;
    JSONArray jsonArray;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            //更新UI
            adapter.notifyDataSetChanged();
            refreshListView.completeRefresh();
        };
    };

    public RetrivePgerDetailByDepa(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_retrive_detail_by_depa, null);
        ViewUtils.inject(this, view); // 把当前的View对象注入到xUtils框架中
        refreshListView = (RefreshListView)view.findViewById(R.id.refreshListView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDepartmentNameList();
        // addHeadView must be operated before set adapter
       // View headView = View.inflate(mActivity,R.layout.retrive_header_title_list,null);
        //refreshListView.addHeaderView(headView);

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
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mActivity,"Depatment",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mActivity, RetriveByDepaActivity.class);
                intent.putExtra("departmentID",getDepartmentIDFromRequest(position-1));
               Toast.makeText(mActivity, Integer.toString(position), Toast.LENGTH_LONG).show();
                mActivity.startActivity(intent);
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
            TextView textview = new TextView(mActivity);
            textview.setPadding(20,20,20,20);
            textview.setTextSize(18);
            textview.setText(list.get(position));
            return textview;
        }
    }

    public void getDepartmentNameList(){
        RestClient.get("/getDepartmentNameList", null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonArray = new JSONArray(result);
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject jObject = new JSONObject(jsonArray.get(j).toString());
                        list.add(jObject.getString("deptName"));
                       // list.add(requisitionDetailBean);
                        //Log.i("bean25", ConvertJSONDate.convert(jObject.getString("requestDate")));

                    }
                } catch (Exception e) {
                    Log.e("JSON Parser", "Error psrsing data" + e.toString());
                }
                //Toast.makeText(mActivity, "result" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Toast.makeText(mActivity, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public String getDepartmentIDFromRequest(int position){

        String departmentID = "";
        try {
            JSONObject jObject = new JSONObject(jsonArray.get(position).toString());
            departmentID = jObject.getString("departmentId");

        } catch (Exception e) {
            Log.e("JSON Parser", "Error psrsing data" + e.toString());
        }
        return departmentID ;
    }
}
