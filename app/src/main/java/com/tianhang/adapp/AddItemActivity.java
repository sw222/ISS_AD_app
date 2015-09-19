package com.tianhang.adapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.tianhang.adapp.domain.itemBean;
import com.tianhang.adapp.rest.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddItemActivity extends ActionBarActivity implements  SearchView.OnQueryTextListener{
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ListView listView;
    private ArrayList<itemBean> list = new ArrayList<itemBean>();

    private  String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle();
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter());

        Intent intent = getIntent();

        sign = intent.getStringExtra("sign");
       // Toast.makeText(this,intent.getStringExtra("sign"),Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        //SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();


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

        searchView.setQueryHint("ID/Name");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTitle() {
        mTitle = "Search Stationery";
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this,SearchResultActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY,query);
        //Toast.makeText(this,query,Toast.LENGTH_SHORT).show();

        intent.putExtra("sign",sign);
        startActivity(intent);
       // getOrder(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
                view = View.inflate(AddItemActivity.this,R.layout.activity_add_item_listview_item,null);
            }
            TextView tv_descp = (TextView)view.findViewById(R.id.tv_descp);
            TextView tv_itemID = (TextView)view.findViewById(R.id.tv_itemID);
            TextView tv_company = (TextView)view.findViewById(R.id.tv_company);
            //TextView tv_status = (TextView)view.findViewById(R.id.listview_requisition_item_status);

            // get position
            tv_descp.setText(list.get(position).getDescription());
            tv_itemID.setText(list.get(position).getItemId());
            tv_company.setText(list.get(position).getCompanyName());
           // tv_status.setText(list.get(position).getStatus());


            String imageName = list.get(position).getDescription().trim();
            String [] arr = imageName.split(" ");
            String newName = "";
            for(int i=0;i<arr.length;i++){
                if(i != arr.length-1)
                    newName += (arr[i]+"%20");
                else{
                    newName += (arr[i]);
                }
            }
            SmartImageView myImage = (SmartImageView)view.findViewById(R.id.my_image);
            myImage.setImageUrl(RestClient.getImageAbsoluteUrl("/" + newName + ".jpg"));

            //handler.sendEmptyMessage(0);
            return view;
        }
    }
}
