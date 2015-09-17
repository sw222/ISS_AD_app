package com.tianhang.adapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.tianhang.adapp.decoding.Intents;
import com.tianhang.adapp.domain.PurchaseBean;
import com.tianhang.adapp.domain.itemBean;
import com.tianhang.adapp.rest.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScanResultActivity extends AppCompatActivity {

    private itemBean itembean ;
    private SmartImageView myImage;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            //更新UI
            String imageName = itembean.getDescription().trim();

            myImage.setImageUrl(RestClient.getImageAbsoluteUrl(imageName+".jpg"));
            Toast.makeText(ScanResultActivity.this,"Handler"+imageName,Toast.LENGTH_SHORT).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        itembean = new itemBean();
        Intent intent = getIntent();
        String ID = intent.getStringExtra(CaptureActivity.QR_RESULT);

        myImage = (SmartImageView) findViewById(R.id.my_image);

        getOrder(ID);
        //Toast.makeText(this,"ID:"+ID,Toast.LENGTH_SHORT).show();
       // SmartImageView

        //String imageName = itembean.getDescription().trim();
        //SmartImageView myImage = (SmartImageView) this.findViewById(R.id.my_image);
        //myImage.setImageUrl(RestClient.getImageAbsoluteUrl(imageName+".jpg"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan_result, menu);
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

    public void getOrder(String ID){

        RestClient.get("/getItemById/"+ID, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                try {
                    Log.i("bean","--->"+result);
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

                Toast.makeText(ScanResultActivity.this, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
