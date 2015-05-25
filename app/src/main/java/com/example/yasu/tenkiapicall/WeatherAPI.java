package com.example.yasu.tenkiapicall;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by yasu on 15/05/24.
 */
public class WeatherAPI {
    private static final String USER_AGENT = "WeatherForecasts Sample";
    private static final String URL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";

    public static WeatherInfo getWeather(Context context,String pointId) throws IOException,JSONException {
        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT,context);
        HttpGet get = new HttpGet(URL + pointId);

        StringBuilder sb = new StringBuilder();
        try{
            HttpResponse response = client.execute(get);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        }finally{
            client.close();
        }

        return new WeatherInfo(new JSONObject(sb.toString()));
    }

}
