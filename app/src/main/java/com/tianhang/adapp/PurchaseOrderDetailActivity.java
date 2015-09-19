package com.tianhang.adapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
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

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tianhang.adapp.domain.PurchaseBean;
import com.tianhang.adapp.domain.PurchaseItemBean;
import com.tianhang.adapp.rest.RestClient;
import com.tianhang.adapp.util.ConvertJSONDate;
import com.tianhang.adapp.widget.Fab;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PurchaseOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<PurchaseItemBean> list = new ArrayList<PurchaseItemBean>();
    private JSONArray jsonArray;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_detail);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter());

        getPurchaseOrderList(Integer.toString(getIntent().getIntExtra("purchaseID", 0)));

        View view = View.inflate(this,R.layout.order_header_title_list,null);
        listView.addHeaderView(view);
        overridePendingTransition(R.animator.right_to_left, R.animator.left_to_right);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        setTitle();
        setupFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase_order_detail, menu);
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(PurchaseOrderDetailActivity.this,
                        R.layout.activity_purchase_order_detail_item, null);
                //new ViewHolder(convertView);
            }else {
                view = convertView;
            }
            //expectedDeliveryDate/purchaseDate/status/purchaserId

            TextView tv_itemcode = (TextView)view.findViewById(R.id.tv_itemcode);
            TextView tv_description = (TextView)view.findViewById(R.id.tv_description);
            TextView tv_quantity = (TextView)view.findViewById(R.id.tv_quantity);
            TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
            TextView tv_amount = (TextView)view.findViewById(R.id.tv_amount);

            tv_itemcode.setText(list.get(position).getItemcode()+"");
            tv_description.setText(list.get(position).getDescription()+"");
            tv_quantity.setText(list.get(position).getQuantity()+"");
            tv_price.setText(list.get(position).getPrice()+"");
            tv_amount.setText(list.get(position).getAmount()+"");
            return view;
        }
    }

    public void getPurchaseOrderList(String PurchaseId){

        RestClient.get("/getPurchaseOrderDetailbyId/" + PurchaseId, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                //JSONObject jsonObject = new JSONObject();

                try {
                    jsonArray = new JSONArray(result);
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject jObject = new JSONObject(jsonArray.get(j).toString());

                        PurchaseItemBean purchaseOrderBean = new PurchaseItemBean();
                        purchaseOrderBean.setAmount(jObject.getInt("Amount"));
                        purchaseOrderBean.setDescription(jObject.getString("Description"));
                        purchaseOrderBean.setItemcode(jObject.getInt("Itemcode"));
                        purchaseOrderBean.setPrice(jObject.getInt("price"));
                        purchaseOrderBean.setQuantity(jObject.getInt("Quantity"));
                        // Log.i("tianhang", purchaseBean.toString());
                        list.add(purchaseOrderBean);
                        // Log.i("bean", purchaseBeanList.size()+"");

                    }
                } catch (Exception e) {
                    Log.e("JSON Parser", "Error psrsing data" + e.toString());
                }
                //Toast.makeText(mActivity, "result" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Toast.makeText(PurchaseOrderDetailActivity.this, "request network failed !", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.back_enter, R.animator.back_exit);
    }
    public void setTitle() {
        mTitle = "Order Detail";
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * Sets up the Floating action button.
     */
    private void setupFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        // change fab color
        fab.setRippleColor(this.getResources().getColor(R.color.light_blue_A400));
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_accent);

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
                setStatusBarColor(getResources().getColor(R.color.light_blue_A400));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        findViewById(R.id.fab_sheet_item_add).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_scan).setOnClickListener(this);
        // mRootView.findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_submit).setOnClickListener(this);
    }
    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return this.getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(mActivity,"press", Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.fab_sheet_item_add:
                Intent intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("sign","purchase");
                startActivity(intent);
                break;
            case R.id.fab_sheet_item_scan:
                Intent intent2 = new Intent(this, CaptureActivity.class);
                startActivity(intent2);
                break;
            case R.id.fab_sheet_item_submit:
                Intent intent3 = new Intent(this,ScanResultActivity.class);
                intent3.putExtra(CaptureActivity.QR_RESULT,"1");

                startActivity(intent3);
                break;
            default: ;
        }
        materialSheetFab.hideSheet();
    }
}
