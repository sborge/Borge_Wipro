package com.borge.wipro_challenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.borge.wipro_challenge.model.WeatherItem;
import com.borge.wipro_challenge.model.WeatherResults;
import com.borge.wipro_challenge.remote.SOService;
import com.borge.wipro_challenge.util.ApiUtil;
import com.borge.wipro_challenge.util.ErrorHandlerUtil;
import com.borge.wipro_challenge.util.NetworkUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *   The ResultsActivity uses the zip code submitted by the user as a parameter to retrieve weather
 *      information for that location.
 *   If successful, it will pass that information along to the DisplayActivity.
 *   If unsuccessful, screen will display alert dialog box for invalid zip code or other error.
 *
 */
public class ResultsActivity extends AppCompatActivity {

    private static final String API_KEY = "9ab35a82cc61230df09e1ff731b95d36";
    private static final String TAG = ResultsActivity.class.getName();
    private static final String UNITS = "Imperial";
    private static final int DEFAULT_ITEM = 0;
    private Context context = this;

    public static final String THE_ZIPCODE = "The_ZipCode";

    private String theZipCode;

    private SOService mService;

    @BindView(R.id.progressBar) ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();

        theZipCode = extra.getString(THE_ZIPCODE);

        ActionBar actionBar = this.getSupportActionBar();

        if (theZipCode != null) {
            actionBar.setTitle(getString(R.string.search_title, theZipCode));
        }

        mService = ApiUtil.getSOService();

        getWeatherInfo(theZipCode);
    }


    // Retrieve weather information for given zip code.
    public void getWeatherInfo(final String zipcode) {
        spinner.setVisibility(View.VISIBLE);

        if (NetworkUtil.isOnline(getApplicationContext())) {

            mService.getResults(zipcode, UNITS, API_KEY)
                    .enqueue(new Callback<WeatherResults>() {
                        @Override
                        public void onResponse(Call<WeatherResults> call,
                                               Response<WeatherResults> response) {
                            if (response.isSuccessful()) {
                                ArrayList<WeatherItem> weatherItems = new ArrayList<>();
                                WeatherResults myResults = response.body();
                                for(int i = 0; i < myResults.getList().size(); i++)
                                {
                                    String[] getNoon = myResults.getList().get(i).getDt_txt().split(" ");
                                    if(getNoon[1].equals("12:00:00")) {
                                        WeatherItem tempWeatherItem = new WeatherItem(
                                                myResults.getList().get(i).getWeather().get(DEFAULT_ITEM).getMain(),
                                                myResults.getList().get(i).getWeather().get(DEFAULT_ITEM).getDescription(),
                                                myResults.getList().get(i).getMain().getTemp(),
                                                myResults.getList().get(i).getWind().getSpeed(),
                                                myResults.getList().get(i).getMain().getPressure(),
                                                myResults.getList().get(i).getMain().getHumidity(),
                                                getNoon[0],
                                                myResults.getCity().getName() + ", " +
                                                        myResults.getCity().getCountry());
                                        weatherItems.add(tempWeatherItem);
                                    }
                                }
                                Intent intent = new Intent(context, DisplayActivity.class);

                                intent.putExtra("weatherItems", weatherItems);

                                startActivity(intent);
                                Log.d(TAG, "Posts loaded from API");
                            } else {
                                int statusCode = response.code();

                                setAlertDialog(getString(R.string.dialog_invalid_zipcode_message, theZipCode),
                                               getString(R.string.dialog_invalid_zipcode_title),
                                               getString(R.string.dialog_invalid_zipcode_goback),
                                               null);

                                Log.d(TAG, "Error - Code: " + statusCode);
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResults> call, Throwable t) {

                            setAlertDialog(getString(R.string.dialog_error_message),
                                    getString(R.string.dialog_error_title),
                                    getString(R.string.dialog_error_goback),
                                    getString(R.string.dialog_error_tryagain));

                            Log.d(TAG, "Error loading from API");
                        }
                    });
        } else {
            setAlertDialog(getString(R.string.dialog_network_error_message),
                    getString(R.string.dialog_network_error_title),
                    getString(R.string.dialog_network_error_goback),
                    getString(R.string.dialog_network_error_tryagain));
        }
    }

    // Display alert dialog if any issue occurs during API call.
    public void setAlertDialog(String message, String title, String positive, String negative) {
        spinner.setVisibility(View.GONE);

        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getWeatherInfo(theZipCode);
            }
        };

        ErrorHandlerUtil.showErrorDialog(this, message, title, positive,
                positiveListener, negative, negativeListener);
    }
}
