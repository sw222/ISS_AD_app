package com.tianhang.adapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.tianhang.adapp.domain.itemBean;
import com.tianhang.adapp.rest.RestClient;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddDiscrepancyActivity extends AppCompatActivity {

    private itemBean itembean ;
    private SmartImageView myImage;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    //
    private TextView tv_description;
    private TextView tv_balance;
    private TextView tv_bin;
    private TextView tv_company;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            //更新UI
            String imageName = itembean.getDescription().trim();
            String [] arr = imageName.split(" ");
            String newName = "";
            for(int i=0;i<arr.length;i++){
                if(i != arr.length-1)
                    newName += (arr[i]+"%20");
                else{
                    newName += (arr[i]);
                }
            }
            myImage.setImageUrl(RestClient.getImageAbsoluteUrl("/"+newName+".jpg"));
            tv_description.setText(itembean.getDescription());
            tv_balance.setText("Balance : "+itembean.getBalance());
            tv_bin.setText("Bin : "+itembean.getBinNumber());
            tv_company.setText(itembean.getCompanyName());
            // myImage.setImageUrl("http://10.10.1.200/LogicUniversityWCF/images/2015%20cute%20stationery.jpg");
            //Toast.makeText(ScanResultActivity.this,RestClient.getImageAbsoluteUrl("/"+imageName+".jpg"),Toast.LENGTH_SHORT).show();
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discrepancy);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle();

        myImage = (SmartImageView) findViewById(R.id.my_image);
        //tv
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_balance = (TextView)findViewById(R.id.tv_balance);
        tv_bin = (TextView)findViewById(R.id.tv_bin);
        tv_company = (TextView)findViewById(R.id.tv_company);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_discrepancy, menu);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.back_enter, R.animator.back_exit);
    }

    public void setTitle() {
        mTitle = "Stationary";
        getSupportActionBar().setTitle(mTitle);
    }

    public void getDiscrepancy(String ID){

        RestClient.get("/getItemById/" + ID, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                try {
                    Log.i("bean", "--->" + result);
                    JSONObject jsonObject = new JSONObject(result);
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

                    Log.i("bean", itembean.toString());
                    //在UI线程更新UI
                    handler.sendEmptyMessage(0);

                } catch (Exception e) {
                    Log.e("JSON Parser", "Error psrsing data" + e.toString());
                }
                //Toast.makeText(mActivity, "result" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Toast.makeText(AddDiscrepancyActivity.this, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
