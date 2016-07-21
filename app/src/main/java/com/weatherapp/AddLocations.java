package com.weatherapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocations extends AppCompatActivity implements View.OnClickListener {
    private EditText edittext;
    private Button add;
    Activity activity;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locations);
        findViews();
    }


    private void findViews() {
        activity = this;
        storeUserData = new StoreUserData(activity);
        edittext = (EditText) findViewById(R.id.edittext);
        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            // Handle clicks for add
            if (edittext.getText().toString().length() > 0) {
                String current = storeUserData.getString(Constants.LOCATIONS);
                if (current.length() > 0)
                    current = current + ";" + edittext.getText().toString();
                else
                    current = edittext.getText().toString();
                storeUserData.setString(Constants.LOCATIONS, current);
                Toast.makeText(activity, "Location Added.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(activity, "Please enter location name.", Toast.LENGTH_SHORT).show();
            }
            playadd();
        }
    }
    void playadd() {
        MediaPlayer mp = null;
        mp = MediaPlayer.create(activity,R.raw.add);
        mp.start();
    }
}
