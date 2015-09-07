package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BasePager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by student on 24/8/15.
 */
public class ReceivePager extends BasePager{
    private final String PATH = mActivity.getString(R.string.path);
    private Button btn_get;
    public ReceivePager(Activity activity){
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();
        View view = View.inflate(mActivity, R.layout.pager_receive, null);
        mRootView = view;
        ViewUtils.inject(this, view); // 把当前的View对象注入到xUtils框架中
        mRootView = view;
        btn_get = (Button)mRootView.findViewById(R.id.id_get);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //httpGet();
                httpJsonGet();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

    }

    public void httpGet(){
        String path = PATH+"/customer/haha";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(path, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Toast.makeText(mActivity, "result" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Toast.makeText(mActivity, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void httpJsonGet() {
        String path = PATH+"/customer/haha";
        AsyncHttpClient client = new AsyncHttpClient();

            client.get(path,null,new JsonHttpResponseHandler(){
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(mActivity, "request network failed !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    //JSONObject firstEvent = response.get(0);
                    //String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    //System.out.println(tweetText);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    String address;
                    try {
                        address = response.get("Address").toString();
                    }catch (JSONException e){
                        //throw new JSONException("json exception !");
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(mActivity, "json-->"+address, Toast.LENGTH_SHORT).show();
                }
            });

    }
}
