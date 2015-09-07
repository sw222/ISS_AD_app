package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.tianhang.adapp.base.BasePager;

/**
 * Created by student on 24/8/15.
 */
public class ReceivePager extends BasePager{

    public ReceivePager(Activity activity){
        super(activity);
    }

    @Override
    public void initData() {
        //tvTitle.setText("StatisticsPager");

        TextView textView = new TextView(mActivity);
        textView.setText("Receive");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        flContent.addView(textView);
    }
}
