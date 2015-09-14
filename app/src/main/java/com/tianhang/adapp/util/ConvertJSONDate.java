package com.tianhang.adapp.util;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by student on 14/9/15.
 */
public class ConvertJSONDate {


    public static String convert(String json) {
        String jsonDate = json;
        String date = "";
        try {

            String results = jsonDate.replaceAll("^/Date\\(", "");
            results = results.substring(0, results.indexOf('+'));
            long time = Long.parseLong(results);
            Date myDate = new Date(time);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            date = sdf.format(myDate);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
