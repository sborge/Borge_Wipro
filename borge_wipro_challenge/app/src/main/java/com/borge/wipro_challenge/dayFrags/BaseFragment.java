package com.borge.wipro_challenge.dayFrags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.borge.wipro_challenge.R;
import com.borge.wipro_challenge.model.WeatherItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    private WeatherItem weatherItem;

    @BindView(R.id.main_condition) TextView mainCondition;
    @BindView(R.id.description_condition) TextView descriptionCondition;
    @BindView(R.id.temperature) TextView temperature;
    @BindView(R.id.wind_speed) TextView windSpeed;
    @BindView(R.id.dt_txt) TextView dt_txt;
    @BindView(R.id.pressure) TextView pressure;
    @BindView(R.id.humidity) TextView humidity;
    @BindView(R.id.city_name) TextView cityName;

    @BindView(R.id.weather_details_layout)
    FrameLayout weatherDetailsLayout;

    // newInstance constructor for creating fragment with arguments
    public static BaseFragment newInstance(WeatherItem weatherItem) {
        BaseFragment fragmentBase = new BaseFragment();
        Bundle args = new Bundle();
        args.putParcelable("weatherItem", weatherItem);
        fragmentBase.setArguments(args);
        return fragmentBase;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherItem = getArguments().getParcelable("weatherItem");

    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, view);
        if(weatherItem != null)
            initializeViews(weatherItem);
        return view;
    }

    // If successful, display weather information for given zip code.
    public void initializeViews(WeatherItem weatherItem) {
        mainCondition.setText(weatherItem.getMainCondition());
        descriptionCondition.setText(weatherItem.getDescriptionCondition());

        String temp = String.valueOf(weatherItem.getTemperature());
        String s = temp + "\u00b0F";
        SpannableString spannable = new SpannableString(s);
        spannable.setSpan(new RelativeSizeSpan(2f), 0, temp.length(), 0); // set size
        temperature.setText(spannable);

        windSpeed.setText(getString(R.string.results_wind_speed, String.valueOf(weatherItem.getWindSpeed())));

        pressure.setText(getString(R.string.results_pressure, String.valueOf(weatherItem.getPressure())));

        dt_txt.setText(getString(R.string.results_dt_txt, String.valueOf(weatherItem.getDt_txt())));

        String theHumidity = String.valueOf(weatherItem.getHumidity()) + "%";
        humidity.setText(theHumidity);

        cityName.setText(getString(R.string.results_city_name, String.valueOf(weatherItem.getCityName())));

        weatherDetailsLayout.setVisibility(View.VISIBLE);
    }
}
