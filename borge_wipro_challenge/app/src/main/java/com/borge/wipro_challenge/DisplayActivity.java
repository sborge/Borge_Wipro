package com.borge.wipro_challenge;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.borge.wipro_challenge.dayFrags.BaseFragment;
import com.borge.wipro_challenge.model.WeatherItem;

import java.util.ArrayList;


/**
 *   The DisplayActivity handles delegating the displays to different fragments for
 *   the weather information of given zip code.
 *   It receives a WeatherItems ArrayList and passes the appropriate item to each fragment
 *
 */
public class DisplayActivity extends AppCompatActivity {
    private ArrayList<WeatherItem> weatherItems;

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        Bundle extra = getIntent().getExtras();
        weatherItems = extra.getParcelableArrayList("weatherItems");

        ActionBar actionBar = this.getSupportActionBar();

        if (weatherItems != null) {
            actionBar.setTitle(getString(R.string.search_title, weatherItems.get(0).getCityName()));
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 5;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BaseFragment.newInstance(weatherItems.get(0));
                case 1:
                    return BaseFragment.newInstance(weatherItems.get(1));
                case 2:
                    return BaseFragment.newInstance(weatherItems.get(2));
                case 3:
                    return BaseFragment.newInstance(weatherItems.get(3));
                case 4:
                    return BaseFragment.newInstance(weatherItems.get(4));
            }
            return null;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Today";
                case 1:
                    return "Tomorrow";
                case 2:
                    return "Day 3";
                case 3:
                    return "Day 4";
                case 4:
                    return "Day 5";
            }
            return "Today";
        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
