package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.tianhang.adapp.base.BasePager;

/**
 * Created by student on 24/8/15.
 */
public class InventoryPager extends BasePager{

    public InventoryPager(Activity activity){
        super(activity);
    }

    @Override
    public void initData() {
        //tvTitle.setText("stockPage");

        TextView textView = new TextView(mActivity);
        textView.setText("Inventory");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        flContent.addView(textView);
    }
}
