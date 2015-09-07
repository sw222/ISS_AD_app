package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BasePager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by student on 24/8/15.
 */
public class HomePager extends BasePager {

    private GridView gridView;
    private ArrayList<HashMap<String, Object>> lstImageItem;

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();
        //override
        mRootView = View.inflate(mActivity, R.layout.pager_home, null);
        gridView = (GridView)mRootView.findViewById(R.id.gridView);


    }

    @Override
    public void initData() {
        //生成动态数组，并且转入数据
        lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.home_press);//添加图像资源的ID
            map.put("ItemText", "NO." + String.valueOf(i));//按序号做ItemText
            lstImageItem.add(map);
        }
        // add plus
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemText", "");//按序号做ItemText
        map.put("ItemImage", R.drawable.plus);//添加图像资源的ID
        lstImageItem.add(map);
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(mActivity, //没什么解释
                lstImageItem,//数据来源
                R.layout.gridview_item,//night_item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.ItemImage, R.id.ItemText});
        //添加并且显示
        gridView.setAdapter(saImageItems);
        //添加消息处理
        gridView.setOnItemClickListener(new ItemClickListener());

    }

    //当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
            //在本例中arg2=arg3
            HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
           // setTitle((String) item.get("ItemText"));
            if(arg2 == lstImageItem.size()-1)
            Toast.makeText(mActivity,arg2+"-->"+arg3,Toast.LENGTH_SHORT).show();
           // else
           // Toast.makeText(mActivity,item.get("item").toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
