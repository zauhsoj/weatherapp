package com.weatherapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LocationDetails extends AppCompatActivity {
    private TextView text;
    private ImageView back;
    private TextView weather;
    Activity activity;
    StoreUserData storeUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        findViews();
        String location = getIntent().getStringExtra("location");
        if (location != null)
            new GetWeather(location).execute();
    }

    private void findViews() {
        activity = this;
        storeUserData = new StoreUserData(activity);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                playSound();
            }
        });
    }

    class GetWeather extends AsyncTask<String, Void, String> {
        String w;

        public GetWeather(String w) {
            this.w = w;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.showProgress(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap map = new HashMap();
            map.put("q", w);
            map.put("appid", Constants.API_KEY);
            map.put("units", storeUserData.getString(Constants.UNITS).length() == 0 ? "imperial" : storeUserData.getString(Constants.UNITS));
            return API.call("http://api.openweathermap.org/data/2.5/find?", map, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Utils.dismissProgress();
            String message = "";
            try {
                JSONObject object = new JSONObject(s);
                if (object.has("list")) {
                    object = object.getJSONArray("list").getJSONObject(0);
                }

                ((TextView) findViewById(R.id.name)).setText(w);
//                message += "Name: " + w;
                //  message += "\n\n";
                //  message += "Coordinates: " + object.getJSONObject("coord").getString("lat") + "," + object.getJSONObject("coord").getString("lon");
//                message += "\n\n";
                ((TextView) findViewById(R.id.weather_)).setText(object.getJSONArray("weather").getJSONObject(0).getString("main") + " - " + object.getJSONArray("weather").getJSONObject(0).getString("description"));
//                message += "Weather: " + object.getJSONArray("weather").getJSONObject(0).getString("main") + " - " + object.getJSONArray("weather").getJSONObject(0).getString("description");
//                message += "\n\n";
                ((TextView) findViewById(R.id.temprature)).setText(object.getJSONObject("main").getString("temp") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C"));

//                message += "Temperature: " + object.getJSONObject("main").getString("temp") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C");
//                message += "\n\n";

                ((TextView) findViewById(R.id.min_temprature)).setText(object.getJSONObject("main").getString("temp_min") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C"));
                ((TextView) findViewById(R.id.max_temprature)).setText(object.getJSONObject("main").getString("temp_max") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C"));
//                message += "Min. Temperature: " + object.getJSONObject("main").getString("temp_min") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C");
//                message += "\n\n";
//                message += "Max. Temperature: " + object.getJSONObject("main").getString("temp_max") + " " + (storeUserData.getString(Constants.UNITS).equals("imperial") ? "F" : "C");
//                message += "\n\n";

                ((TextView) findViewById(R.id.pressure)).setText(object.getJSONObject("main").getString("pressure") + " hPa");
                ((TextView) findViewById(R.id.humidity)).setText(object.getJSONObject("main").getString("humidity") + " %");

//                message += "Pressure: " + object.getJSONObject("main").getString("pressure") + " hPa";
//                message += "\n\n";
//                message += "Humidity: " + object.getJSONObject("main").getString("humidity") + " %";
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    void playSound() {
        MediaPlayer mp = null;
        mp = MediaPlayer.create(activity, R.raw.click);
        mp.start();
    }

}
