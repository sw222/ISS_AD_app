package com.tianhang.adapp;

import android.app.Notification;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tianhang.adapp.base.BasePager;
import com.tianhang.adapp.base.impl.BasicPager;
import com.tianhang.adapp.base.impl.DiscrepancyPager;
import com.tianhang.adapp.base.impl.HomePager;
import com.tianhang.adapp.base.impl.InventoryPager;
import com.tianhang.adapp.base.impl.PurchasePager;
import com.tianhang.adapp.base.impl.ReceivePager;
import com.tianhang.adapp.base.impl.RequisitionPager;
import com.tianhang.adapp.base.impl.RetrivePager;
import com.tianhang.adapp.widget.PagerSlidingTabStrip;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements  SearchView.OnQueryTextListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
   // private ShareActionProvider mShareActionProvider;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ArrayList<BasePager> mPagerList;
    // left menu titles
    private String[] mPlanetTitles;
    // left menu name list
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    // menu icons
    private final int [] menuIcons =   {R.drawable.category ,
    R.drawable.requisition,
    R.drawable.retrive,
    R.drawable.cart,
    R.drawable.receive,
    R.drawable.store,
    R.drawable.question};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();
        initData();
        initLeftMenu();
    }

    private void initViews() {

        mTitle = mDrawerTitle = getTitle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // mToolbar.set
        // toolbar.setLogo(R.drawable.ic_launcher);
        //mToolbar.setTitle("Rocko");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
		/* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        getSupportActionBar().setTitle("Home");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);
		/* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过下面的两个回调方法来处理 */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.flag_noty:
                        //Toast.makeText(MainActivity.this, "flag_noty", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		/* findView */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
       // mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
       //    R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
       // mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);


    }
    public void initData() {
        // default check home page

        // 初始化5个子页面
        mPagerList = new ArrayList<BasePager>();

        mPagerList.add(new HomePager(this));
        mPagerList.add(new RequisitionPager(this));
        mPagerList.add(new RetrivePager(this));
        mPagerList.add(new PurchasePager(this));
        mPagerList.add(new ReceivePager(this));
        mPagerList.add(new InventoryPager(this));
        mPagerList.add(new DiscrepancyPager(this));
        //mPagerList.add(new BasicPager(this));

        mViewPager.setAdapter(new ContentAdapter());


        // add change listener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();// 获取当前被选中的页面, 初始化该页面数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerList.get(0).initData();// 初始化首页数据
    }

    public void initLeftMenu(){
        //menuIcons_array

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
               // android.R.layout.simple_list_item_1, mPlanetTitles));
        mDrawerList.setAdapter(new LeftMenuAdapter());
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
           // selectItem(position);
            //Toast.makeText(MainActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(position, false);
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
           // mDrawerList.setItemC
            setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
    private class LeftMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mPlanetTitles.length;
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
                view = View.inflate(MainActivity.this,R.layout.left_menu_item,null);
            }
            TextView tv = (TextView)view.findViewById(R.id.left_menu_item_tv);
            ImageView iv = (ImageView)view.findViewById(R.id.left_menu_item_img);
            tv.setText(mPlanetTitles[position]);
            iv.setImageResource(menuIcons[position]);
            return view;
        }
    }

    private class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            // pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        // Add SearchWidget.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        // change the text hint color
        // traverse the view to the widget containing the hint text
        LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
         // set the hint text color
        autoComplete.setHintTextColor(Color.WHITE);
        // set the text color
        autoComplete.setTextColor(Color.WHITE);

        searchView.setQueryHint("Search Stationery");
        // intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        // mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // switch (item.getItemId()) {
        // case R.id.action_settings:
        // Toast.makeText(MainActivity.this, "action_settings", 0).show();
        // break;
        // case R.id.action_share:
        // Toast.makeText(MainActivity.this, "action_share", 0).show();
        // break;
        // default:
        // break;
        // }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    // implement SearchView.OnQueryTextListene
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(this,"text sunbmit !",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,SearchResultActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Toast.makeText(this,"text change !",Toast.LENGTH_SHORT).show();
        return false;
    }


}
