package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tianhang.adapp.AddItemActivity;
import com.tianhang.adapp.CaptureActivity;
import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BasePager;
import com.tianhang.adapp.domain.RequisitionBean;
import com.tianhang.adapp.domain.discrepancyBean;
import com.tianhang.adapp.rest.RestClient;
import com.tianhang.adapp.util.ConvertJSONDate;
import com.tianhang.adapp.widget.Fab;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 24/8/15.
 */
public class DiscrepancyPager extends BasePager implements View.OnClickListener {
    private Spinner spinner;
    // private Fab fab;
    private MaterialSheetFab materialSheetFab;
    private SwipeMenuListView smListView;
    private ArrayList<discrepancyBean> list = new ArrayList<discrepancyBean>();
    private JSONArray jsonArray;
   // private AppAdapter mAdapter;
   // private int statusBarColor;
   // private List<String> mAppList;
    String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13"};


    public DiscrepancyPager(Activity activity){
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();
        View view = View.inflate(mActivity, R.layout.pager_discrepancy, null);
        mRootView = view;
        smListView = (SwipeMenuListView)view.findViewById(R.id.listView);

        setupFab();

        getDiscrepancyListHistory();

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        mActivity.getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        mActivity.getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        smListView.setMenuCreator(creator);

        // step 2. listener item click event
        smListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // String item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open();
                        break;
                    case 1:
                        // delete
                        delete();
                        //mAppList.remove(position);
                        //mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });


        // set SwipeListener
        smListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        smListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(mActivity.getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        //smListView.setAdapter(new AppAdapter());
    }

    @Override
    public void initData() {
        //tvTitle.setText("StatisticsPager");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, ITEMS);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner = (Spinner) mRootView.findViewById(R.id.spinner_stationary);
        //spinner.setAdapter(adapter);
        //spinner.setAdapter(adapter);
        // change the spinner color
        //spinner.getBackground().setColorFilter(mActivity.getResources().getColor(R.color.blue_400), PorterDuff.Mode.SRC_ATOP);

        smListView.setAdapter(new AppAdapter());
    }

    /**
     * Sets up the Floating action button.
     */
    private void setupFab() {

        Fab fab = (Fab) mRootView.findViewById(R.id.fab);
        // change fab color
        fab.setRippleColor(mActivity.getResources().getColor(R.color.light_blue_A400));
        View sheetView = mRootView.findViewById(R.id.fab_sheet);
        View overlay = mRootView.findViewById(R.id.overlay);
        int sheetColor = mRootView.getResources().getColor(R.color.background_card);
        int fabColor = mRootView.getResources().getColor(R.color.theme_accent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        //materialSheetFab.
        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                // statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                //setStatusBarColor(mActivity.getResources().getColor(R.color.theme_primary_dark2));
                // setStatusBarColor(mActivity.getResources().getColor(R.color.light_blue_A400));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                // setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        mRootView.findViewById(R.id.fab_sheet_item_add).setOnClickListener(this);
        mRootView.findViewById(R.id.fab_sheet_item_scan).setOnClickListener(this);
        // mRootView.findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        mRootView.findViewById(R.id.fab_sheet_item_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(mActivity,"press", Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.fab_sheet_item_add:
                Intent intent = new Intent(mActivity, AddItemActivity.class);
                intent.putExtra("sign","discrepancy");
                mActivity.startActivity(intent);
                break;
            case R.id.fab_sheet_item_scan:
                Intent intent2 = new Intent(mActivity, CaptureActivity.class);
                mActivity.startActivity(intent2);
                break;
            default: ;
        }
        materialSheetFab.hideSheet();
    }

    private void delete() {
        // delete app
        Toast.makeText(mActivity,"delete",Toast.LENGTH_SHORT).show();
    }

    private void open() {
        // open app
        Toast.makeText(mActivity,"open",Toast.LENGTH_SHORT).show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mRootView.getResources().getDisplayMetrics());
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            //return mAppList.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            //return position;
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity.getApplicationContext(),
                        R.layout.pager_discrepancy_list_item, null);
            }else {
                view = convertView;
            }

            TextView tv_discrepancyDate = (TextView) view.findViewById(R.id.tv_discrepancyDate);
            TextView tv_discrepancyID = (TextView) view.findViewById(R.id.tv_discrepancyID);
            TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
            TextView tv_discrepancyRemark = (TextView) view.findViewById(R.id.tv_discrepancyRemark);

            tv_discrepancyDate.setText(ConvertJSONDate.convert(list.get(position).getReportDate()));
            tv_discrepancyID.setText(list.get(position).getDiscrepancyId()+"");
            tv_status.setText(list.get(position).getStatus());
            tv_discrepancyRemark.setText(list.get(position).getRemark());
           // requisitionBean.setRequestDate(ConvertJSONDate.convert(jObject.getString("requestDate")));

            return view;
        }
    }

    public void getDiscrepancyListHistory(){

        RestClient.get("/discrepancyListHistory", null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
               // JSONObject jsonObject = new JSONObject();

                try {
                    jsonArray = new JSONArray(result);
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject jObject = new JSONObject(jsonArray.get(j).toString());
                        discrepancyBean bean = new discrepancyBean();
                        bean.setRemark(jObject.getString("Remark"));
                        bean.setReportDate(jObject.getString("reportDate"));
                        bean.setDiscrepancyId(jObject.getInt("discrepancyId"));
                        bean.setStatus(jObject.getString("status"));
                        list.add(bean);
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

}
