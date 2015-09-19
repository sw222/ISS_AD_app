package com.tianhang.adapp;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.tianhang.adapp.decoding.Intents;
import com.tianhang.adapp.domain.itemBean;
import com.tianhang.adapp.rest.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultActivity extends ActionBarActivity {

    private ArrayList<itemBean> list = new ArrayList<itemBean>();
    private ListView listView;
    private Toolbar mToolbar;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //tool bar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        setTitle();
        // Get the intent, verify the action and get the query

        Intent intent = getIntent();
        String query ="";
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
        // sign
        final String sign = intent.getStringExtra("sign");
        //Toast.makeText(this, sign, Toast.LENGTH_SHORT).show();

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                if(sign.equals("purchase")){
                    i = new Intent(SearchResultActivity.this,ScanResultActivity.class);
                    i.putExtra("RESULT", Integer.toString(list.get(position).getItemId()));
                    startActivity(i);
                }else {
                    i = new Intent(SearchResultActivity.this,AddDiscrepancyActivity.class);
                    i.putExtra("RESULT", Integer.toString(list.get(position).getItemId()));
                    startActivity(i);
                }
            }
        });
        getSearchResult(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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

    public void getSearchResult(String Name){

        RestClient.get("/getItemByName/" + Name, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                try {
                    Log.i("bean", "--->" + result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(j).toString());
                        itemBean itembean = new itemBean();
                        itembean.setDescription(jsonObject.getString("description"));
                        itembean.setBalance(jsonObject.getInt("balance"));
                        itembean.setBinNumber(jsonObject.getString("binNumber"));
                        itembean.setCategoryName(jsonObject.getString("categoryName"));
                        itembean.setCompanyName(jsonObject.getString("companyName"));
                        itembean.setItemId(jsonObject.getInt("itemId"));
                        itembean.setPhotourl(jsonObject.getString("photourl"));
                        itembean.setReorderlevel(jsonObject.getInt("reorderlevel"));
                        itembean.setReorderQty(jsonObject.getInt("reorderQty"));

                        itembean.setStatus(jsonObject.getString("status"));
                        itembean.setUnit(jsonObject.getString("unit"));
                        if(j<=20){
                            list.add(itembean);
                        }

                    }
                    // Log.i("bean", itembean.toString());
                    //在UI线程更新UI
                    //handler.sendEmptyMessage(0);

                } catch (Exception e) {
                    Log.e("JSON Parser", "Error psrsing data" + e.toString());
                }
                //Toast.makeText(mActivity, "result" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Toast.makeText(SearchResultActivity.this, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });
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
                view = View.inflate(SearchResultActivity.this,R.layout.activity_search_result_list_item,null);
            }
            TextView tv_descp = (TextView)view.findViewById(R.id.tv_descp);
            TextView tv_itemID = (TextView)view.findViewById(R.id.tv_itemID);
            TextView tv_company = (TextView)view.findViewById(R.id.tv_company);
            //TextView tv_status = (TextView)view.findViewById(R.id.listview_requisition_item_status);

            // get position
            tv_descp.setText(list.get(position).getDescription());
            tv_itemID.setText(list.get(position).getItemId()+"");
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

    public void setTitle() {
        mTitle = "Search Result";
        getSupportActionBar().setTitle(mTitle);
    }

}
