package com.example.yasu.tenkiapicall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfo {

    public final Location location;
    public final List<Forecast> forecastList = new ArrayList();

    public WeatherInfo(JSONObject jsonObject) throws JSONException{
        JSONObject locationObject = jsonObject.getJSONObject("location");
        location = new Location(locationObject);

        JSONArray forecastArray = jsonObject.getJSONArray("forecasts");

        int len = forecastArray.length();
        for(int i = 0;i < len;i++){
            JSONObject forecastJson = forecastArray.getJSONObject(i);
            Forecast forecast = new Forecast(forecastJson);
            forecastList.add(forecast);
        }
    }

    public class Location {
        public final String area;
        public final String prefecture;
        public final String city;

        private Location(JSONObject jsonObject) throws JSONException{
            area = jsonObject.getString("area");
            prefecture = jsonObject.getString("prefecture");
            city = jsonObject.getString("city");
        }
    }

    public class Forecast {
        public final String date;
        public final String dateLabel;
        public final String telop;
        public final Temparature temperature;
        public final Image image;

        private Forecast(JSONObject jsonObject) throws JSONException{
            date = jsonObject.getString("date");
            dateLabel = jsonObject.getString("dateLabel");
            telop = jsonObject.getString("telop");
            image = new Image(jsonObject.getJSONObject("image"));
            temperature = new Temparature(jsonObject.getJSONObject("temperature"));

        }
    }

    public class Image{
        public final int height;
        public final int width;
        public final String title;
        public final String link;
        public final String url;

        public Image(JSONObject jsonObject) throws JSONException{
            title = jsonObject.getString("title");
            if(jsonObject.has("link")){
                link = jsonObject.getString("link");
            }else{
                link = null;
            }
            url = jsonObject.getString("url");
            width = jsonObject.getInt("width");
            height = jsonObject.getInt("height");

        }
    }

    public class Temparature{
        public final Temp min;
        public final Temp max;

        public Temparature(JSONObject jsonObject) throws JSONException {
            if (!jsonObject.isNull("min")) {
                min = new Temp(jsonObject.getJSONObject("min"));
            } else {
                min = new Temp(null);
            }

            if (!jsonObject.isNull("max")) {
                max = new Temp(jsonObject.getJSONObject("max"));
            } else {
                max = new Temp(null);
            }
        }

        @Override
        public String toString() {
            String minTemp = "-";
            if(min.celsius != null){
                minTemp = min.celsius;
            }
            String maxTemp = "-";
            if(max.celsius != null){
                maxTemp = max.celsius;
            }

            return minTemp + "℃" + " / " + maxTemp + "℃";
        }
    }

    public class Temp{
        public final String celsius;
        public final String fahrenheit;

        public Temp(JSONObject jsonObject) throws JSONException{
            if(jsonObject == null){
                celsius = null;
                fahrenheit = null;
                return;
            }
            celsius = jsonObject.getString("celsius");
            fahrenheit = jsonObject.getString("fahrenheit");
        }
    }
}
