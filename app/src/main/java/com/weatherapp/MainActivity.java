package com.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Activity activity;
    StoreUserData storeUserData;
    ListView listView;
    ImageView settings;
    String locations[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        storeUserData = new StoreUserData(activity);

        listView = (ListView) findViewById(R.id.listView);
        settings = (ImageView) findViewById(R.id.back);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                startActivity(new Intent(activity, SettingActivity.class));
            }
        });
        locations = storeUserData.getString(Constants.LOCATIONS).split(";");
        if (locations.length == 0) {
            Toast.makeText(MainActivity.this, "Please add locations from Settings.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<CharSequence> aa = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, locations);
            listView.setAdapter(aa);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Utils.isConnectedToInternet(activity)) {
                    startActivity(new Intent(activity, LocationDetails.class).putExtra("location", locations[i]));
                } else {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                playSound();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        locations = storeUserData.getString(Constants.LOCATIONS).split(";");
        if (locations.length == 0) {
            Toast.makeText(MainActivity.this, "Please add locations from Settings.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<CharSequence> aa = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, locations);
            listView.setAdapter(aa);
        }
    }

    void playSound() {
        MediaPlayer mp = null;
        mp = MediaPlayer.create(activity, R.raw.click);
        mp.start();
    }

}
