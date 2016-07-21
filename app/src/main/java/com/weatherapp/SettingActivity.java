package com.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text;
    private ImageView back;
    private RadioGroup radio;
    private RadioButton rbCelcius;
    private RadioButton rbFahrenheit;
    private Button btnAddLocation;
    private ListView listView;
    Activity activity;
    StoreUserData storeUserData;
    String locations[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locations = storeUserData.getString(Constants.LOCATIONS).split(";");
        if (locations.length != 0) {
            ArrayAdapter<CharSequence> aa = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, locations);
            listView.setAdapter(aa);
        }
    }

    private void findViews() {
        activity = this;
        storeUserData = new StoreUserData(activity);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.back);
        radio = (RadioGroup) findViewById(R.id.radio);
        rbCelcius = (RadioButton) findViewById(R.id.rb_celcius);
        rbFahrenheit = (RadioButton) findViewById(R.id.rb_fahrenheit);
        btnAddLocation = (Button) findViewById(R.id.btn_add_location);
        listView = (ListView) findViewById(R.id.listView);

        rbCelcius.setOnClickListener(this);
        rbFahrenheit.setOnClickListener(this);
        btnAddLocation.setOnClickListener(this);
        if (storeUserData.getString(Constants.UNITS).equals("imperial")) {
            rbFahrenheit.setChecked(true);
        } else if (storeUserData.getString(Constants.UNITS).equals("metric")) {
            rbCelcius.setChecked(true);
        } else {
            rbFahrenheit.setChecked(true);
        }

        rbFahrenheit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    storeUserData.setString(Constants.UNITS, "imperial");

            }
        });
        rbCelcius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    storeUserData.setString(Constants.UNITS, "metric");

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String loc = storeUserData.getString(Constants.LOCATIONS);
                Log.i("loc1", loc);
                loc = loc.replace(locations[i], "").replace(";;", ";");
                Log.i("loc2", loc);
                if (loc.startsWith(";")) {
                    loc = loc.substring(1, loc.length() - 1);
                    Log.i("loc3", loc);
                }
                if (loc.endsWith(";")) {
                    loc = loc.substring(0, loc.length() - 1);
                    Log.i("loc4", loc);
                }
                Log.i("loc5", loc);
                storeUserData.setString(Constants.LOCATIONS, loc);
                locations = storeUserData.getString(Constants.LOCATIONS).split(";");
                if (locations.length != 0) {
                    ArrayAdapter<CharSequence> aa = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_list_item_1, locations);
                    listView.setAdapter(aa);
                }
                Toast.makeText(SettingActivity.this, "Location deleted.", Toast.LENGTH_SHORT).show();

                playSound();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SettingActivity.this, "Long tap to remove location.", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                playSound();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddLocation) {
            playSound();
            startActivity(new Intent(activity, AddLocations.class));
        }
    }

    void playSound() {
        MediaPlayer mp = null;
        mp = MediaPlayer.create(activity, R.raw.click);
        mp.start();
    }
}
