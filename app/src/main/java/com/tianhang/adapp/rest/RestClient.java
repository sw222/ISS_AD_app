package com.tianhang.adapp.rest;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tianhang.adapp.R;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.File;

/**
 * Created by student on 7/9/15.
 */
public class RestClient {
    // base url
    //private static final String BASE_URL = "http://10.10.1.200/WcfService/Service.svc";
    private static final String BASE_URL = "http://10.10.1.200/LogicUniversityWCF/Service.svc";
    private static final String Image_BASE_URL = "http://10.10.1.200/LogicUniversityWCF/images";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context,String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "text/json");
        client.addHeader("content-type", "application/json");
        client.post(context, getAbsoluteUrl(url), params, responseHandler);

    }
    public static void JosnPost(Context context,String url,HttpEntity entity,AsyncHttpResponseHandler responseHandler){
        client.post(context, getAbsoluteUrl(url), entity, "application/json",
                responseHandler);
    }
    public static void getImageFromURL(String url,AsyncHttpResponseHandler responseHandler){
        client.get(getImageAbsoluteUrl(url),responseHandler);

    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    public static String getImageAbsoluteUrl(String relativeUrl) {
        return Image_BASE_URL + relativeUrl;
    }
}
