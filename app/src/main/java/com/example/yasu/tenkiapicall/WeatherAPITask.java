package com.example.yasu.tenkiapicall;

import android.content.Context;
import android.os.AsyncTask;


public class WeatherAPITask extends AsyncTask<String,Void,WeatherInfo> {
    private final Context context;
    Exception exception;

    public WeatherAPITask(Context context) {
        this.context = context;
    }

    @Override
    protected WeatherInfo doInBackground(String... params) {
        try {
            return WeatherAPI.getWeather(context, params[0]);
        }catch(Exception e){
            exception = e;
        }
        return null;
    }
}
