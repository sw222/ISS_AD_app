package com.tianhang.adapp.widget;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tianhang.adapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 下拉刷新
 1.addHeaderView必须在setAdapter之前调用
 2.将paddingTop设置一个headerView高度的负值去隐藏它
 ----------------------------------------------------------------------------------
 getHeight()和getMeasuredHeight()的区别：
 (1)method one
 getMeasuredHeight():获取测量完的高度，只要在onMeasure方法执行完，就可以用
 它获取到宽高，在自定义控件内部多使用这个
 使用view.measure(0,0)方法可以主动通知系统去测量，然后就
 可以直接使用它获取宽高
 ----------------------------------------------------------------------------------
 (2)method two
 getHeight()：必须在onLayout方法执行完后，才能获得宽高
 -----------------------------------------------------------------------------------
 view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int headerViewHeight = headerView.getHeight();
        //直接可以获取宽高
    }
});
 ------------------------------------------------------------------------------------
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    private ImageView iv_arrow;
    private ProgressBar pb_rotate;
    private TextView tv_state,tv_time;
    private View footerView;
    private int footerViewHeight;

    private int downY;
    private View headerView;
    private int headerViewHeight;

    private final int PULL_REFRESH = 0;//下拉刷新的状态
    private final int RELEASE_REFRESH = 1;//松开刷新的状态
    private final int REFRESHING = 2;//正在刷新的状态
    private int currentState = PULL_REFRESH;

    private RotateAnimation upAnimation,downAnimation;

    private boolean isLoadingMore = false;//当前是否正在处于加载更多

    public RefreshListView(Context context) {
        super(context);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setOnScrollListener(this);
        initHeaderView();
        initRotateAnimation();
        initFooterView();
    }

    /**
     * initialize the headerView
     */
    private void initHeaderView(){
        headerView = View.inflate(getContext(), R.layout.layout_header, null);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        headerView.measure(0, 0);
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        addHeaderView(headerView);
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.layout_footer, null);
        footerView.measure(0, 0);//主动通知系统去测量该view;
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                // if it is refreshing, can not move
                if(currentState==REFRESHING){
                    break;
                }

                int deltaY = (int) (ev.getY() - downY);

                int paddingTop = -headerViewHeight + deltaY;
                if(paddingTop>-headerViewHeight && getFirstVisiblePosition()==0){
                    headerView.setPadding(0, paddingTop, 0, 0);

                    if(paddingTop>=0 && currentState==PULL_REFRESH){
                        //从下拉刷新进入松开刷新状态
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    }else if (paddingTop<0 && currentState==RELEASE_REFRESH) {
                        //进入下拉刷新状态
                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }


                    return true;//拦截TouchMove，不让listview处理该次move事件,会造成listview无法滑动
                }

                break;
            case MotionEvent.ACTION_UP:
                if(currentState==PULL_REFRESH){
                    //隐藏headerView
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }else if (currentState==RELEASE_REFRESH) {
                    headerView.setPadding(0, 0, 0, 0);
                    currentState = REFRESHING;
                    refreshHeaderView();

                    if(listener!=null){
                        listener.onPullRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 根据currentState来更新headerView
     */
    private void refreshHeaderView(){
        switch (currentState) {
            case PULL_REFRESH:
                tv_state.setText("下拉刷新");
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state.setText("松开刷新");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();//因为向上的旋转动画有可能没有执行完
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("正在刷新...");
                break;
        }
    }

    /**
     * 初始化旋转动画
     */
    private void initRotateAnimation() {
        upAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }
    /**
     * 完成刷新操作，重置状态,在你获取完数据并更新完adater之后，去在UI线程中调用该方法
     */
    public void completeRefresh(){
        if(isLoadingMore){
            //重置footerView状态
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        }else {
            //重置headerView状态
            headerView.setPadding(0, -headerViewHeight, 0, 0);
            currentState = PULL_REFRESH;
            pb_rotate.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            tv_state.setText("下拉刷新");
            tv_time.setText("最后刷新："+getCurrentTime());
        }
    }

    /**
     * 获取当前系统时间，并格式化
     * @return
     */
    private String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private OnRefreshListener listener;
    public void setOnRefreshListener(OnRefreshListener listener){
        this.listener = listener;
    }



    public interface OnRefreshListener{
        void onPullRefresh();
        void onLoadingMore();
    }

    /**
     * SCROLL_STATE_IDLE:闲置状态，就是手指松开
     * SCROLL_STATE_TOUCH_SCROLL：手指触摸滑动，就是按着来滑动
     * SCROLL_STATE_FLING：快速滑动后松开
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==OnScrollListener.SCROLL_STATE_IDLE
                && getLastVisiblePosition()==(getCount()-1) &&!isLoadingMore){
            isLoadingMore = true;

            footerView.setPadding(0, 0, 0, 0);//显示出footerView
            setSelection(getCount());//让listview最后一条显示出来

            if(listener!=null){
                listener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
