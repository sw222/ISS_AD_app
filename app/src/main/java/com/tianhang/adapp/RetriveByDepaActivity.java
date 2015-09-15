package com.tianhang.adapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class RetriveByDepaActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private ListView listView;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.right_to_left, R.animator.left_to_right);

        setContentView(R.layout.activity_retrive_by_depa);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        listView = (ListView)findViewById(R.id.retrive_depa_lv);
        View headView = View.inflate(this,R.layout.retrive_header_title_list,null);
        listView.addHeaderView(headView);
        setTitle();

        Intent intent = getIntent();
        String ID = intent.getStringExtra("departmentID");
        Toast.makeText(this, ID, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retrive_by_depa, menu);
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
        overridePendingTransition(R.animator.back_enter,R.animator.back_exit);
    }

    public void setTitle() {
        mTitle = "Department Requisition";
        getSupportActionBar().setTitle(mTitle);
    }
}
