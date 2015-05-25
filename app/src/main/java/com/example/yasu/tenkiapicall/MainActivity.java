package com.example.yasu.tenkiapicall;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private TextView location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;
//    private Handler handler;

    private class GetWeatherTask extends WeatherAPITask{
        private GetWeatherTask(Context context) {
            super(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(WeatherInfo data) {
            super.onPostExecute(data);

            progress.setVisibility(View.GONE);

            if(data != null){
                location.setText(data.location.area + " " +
                        data.location.prefecture + " " + data.location.city);

                //予報一覧を表示
                for(WeatherInfo.Forecast forecast : data.forecastList){
                    //location.append("\n");
                    //location.append(forecast.dateLabel + " " + forecast.telop);

                    View row = View.inflate(MainActivity.this, R.layout.forecast_row, null);

                    TextView date = (TextView)row.findViewById(R.id.tv_date);
                    date.setText(forecast.dateLabel);

                    TextView telop = (TextView)row.findViewById(R.id.tv_telop);
                    telop.setText(forecast.telop);

                    TextView temp = (TextView)row.findViewById(R.id.tv_tempreture);
                    temp.setText(forecast.temperature.toString());

                    ImageView imageView = (ImageView)row.findViewById(R.id.iv_weather);
                    imageView.setTag(forecast.image.url);
                    //画像読み込み処理の実行
                    ImageLoaderTask task = new ImageLoaderTask(MainActivity.this);
                    task.execute(imageView);

                    forecastLayout.addView(row);

                }

            }else if(exception != null){
                Toast.makeText(MainActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (TextView) findViewById(R.id.tv_location);

        forecastLayout = (LinearLayout) findViewById(R.id.ll_forecasts);

        progress = (ProgressBar) findViewById(R.id.progress);

        new GetWeatherTask(this).execute("130010");


/*
        handler = new Handler();
        location = (TextView) findViewById(R.id.tv_main);
        Thread thread  = new Thread(){
            @Override
            public void run() {
                try {
                    final String data = WeatherAPI.getWeather(MainActivity.this, "400040");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            location.setText(data);
                        }
                    });

                }catch(final IOException e){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        thread.start();
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
