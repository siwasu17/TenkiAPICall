package com.example.yasu.tenkiapicall;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentWeather extends Fragment{
    private static final String KEY_CITY_CODE = "key_city_code";

    public static FragmentWeather newInstance(String cityCode){
        FragmentWeather fragment = new FragmentWeather();
        Bundle args = new Bundle();
        args.putString(KEY_CITY_CODE,cityCode);
        fragment.setArguments(args);

        return fragment;
    }

    private TextView location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,null);

        location = (TextView) view.findViewById(R.id.tv_location);

        forecastLayout = (LinearLayout) view.findViewById(R.id.ll_forecasts);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        new GetWeatherTask(getActivity()).execute(getArguments().getString(KEY_CITY_CODE));

        return view;
    }

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

                    View row = View.inflate(getActivity(), R.layout.forecast_row, null);

                    TextView date = (TextView)row.findViewById(R.id.tv_date);
                    date.setText(forecast.dateLabel);

                    TextView telop = (TextView)row.findViewById(R.id.tv_telop);
                    telop.setText(forecast.telop);

                    TextView temp = (TextView)row.findViewById(R.id.tv_tempreture);
                    temp.setText(forecast.temperature.toString());

                    ImageView imageView = (ImageView)row.findViewById(R.id.iv_weather);
                    imageView.setTag(forecast.image.url);
                    //画像読み込み処理の実行
                    ImageLoaderTask task = new ImageLoaderTask(getActivity());
                    task.execute(imageView);

                    forecastLayout.addView(row);

                }

            }else if(exception != null){
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
