package com.tianhang.adapp.base.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
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
import com.tianhang.adapp.AddItemActivity;
import com.tianhang.adapp.R;
import com.tianhang.adapp.base.BasePager;
//import com.tianhang.adapp.scan.CaptureActivity;
//import com.tianhang.adapp.scan.CaptureActivity;
import com.tianhang.adapp.widget.Fab;
import com.tianhang.adapp.CaptureActivity;

import java.util.List;

/**
 * Created by student on 1/9/15.
 */
public class PurchasePager extends BasePager implements View.OnClickListener {
   // private Fab fab;
    private MaterialSheetFab materialSheetFab;

    private Spinner spinner;
    private SwipeMenuListView smListView;
    private AppAdapter mAdapter;
    private int statusBarColor;
    private List<String> mAppList;
    String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13"};

    public PurchasePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();

        View view = View.inflate(mActivity, R.layout.pager_purchase, null);
        ViewUtils.inject(this, view); // 把当前的View对象注入到xUtils框架中
        mRootView = view;
        smListView = (SwipeMenuListView)view.findViewById(R.id.listView);
        //fab = (Fab)view.findViewById(R.id.fab);
        setupFab();
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

    }

    @Override
    public void initData() {
        super.initData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, ITEMS);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) mRootView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // change the spinner color
        spinner.getBackground().setColorFilter(mActivity.getResources().getColor(R.color.blue_400), PorterDuff.Mode.SRC_ATOP);
        smListView.setAdapter(new AppAdapter());
    }


    private void delete() {
        // delete app
        Toast.makeText(mActivity,"delete",Toast.LENGTH_SHORT).show();
    }

    private void open() {
        // open app
        Toast.makeText(mActivity,"open",Toast.LENGTH_SHORT).show();
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ITEMS.length;
        }

        @Override
        public String getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity.getApplicationContext(),
                        R.layout.item_list_app, null);
                //new ViewHolder(convertView);
            }else {
                view = convertView;
            }

            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

            tv_name.setText(ITEMS[position]);

            return view;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mRootView.getResources().getDisplayMetrics());
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
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                //setStatusBarColor(mActivity.getResources().getColor(R.color.theme_primary_dark2));
                setStatusBarColor(mActivity.getResources().getColor(R.color.light_blue_A400));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        mRootView.findViewById(R.id.fab_sheet_item_add).setOnClickListener(this);
        mRootView.findViewById(R.id.fab_sheet_item_scan).setOnClickListener(this);
       // mRootView.findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        mRootView.findViewById(R.id.fab_sheet_item_submit).setOnClickListener(this);
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mActivity.getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(mActivity,"press", Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.fab_sheet_item_add:
                Intent intent = new Intent(mActivity, AddItemActivity.class);
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
}
