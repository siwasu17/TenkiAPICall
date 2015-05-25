package com.example.yasu.tenkiapicall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String[] POINT_LIST = {
            "012010",
            "020010",
            "040010",
            "060010",
            "080010",
            "110010",
            "130010",
            "150010",
            "170010",
            "190010",
            "210010",
            "230010",
            "250010",
    };

    private List<String> pointList;

    private Adapter adapter;
    private ViewPager viewPager;

    private class Adapter extends FragmentStatePagerAdapter{
        private Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentWeather.newInstance(pointList.get(i));
        }

        @Override
        public int getCount() {
            return pointList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(pointList == null){
            pointList = Arrays.asList(POINT_LIST);
        }

        viewPager = (ViewPager)findViewById(R.id.vp_main);
        adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
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
