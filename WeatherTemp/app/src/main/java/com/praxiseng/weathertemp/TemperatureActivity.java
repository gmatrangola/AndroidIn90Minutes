package com.praxiseng.weathertemp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity {


    private TextView temperature;
    private Button refreshButton;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temperature =  (TextView) findViewById(R.id.temperature);

        refreshButton = (Button) findViewById(R.id.refresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherIntentService.startActionWeahter(TemperatureActivity.this);
                temperature.setText("Sent");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                temperature.setText(intent.getDoubleExtra(WeatherIntentService.TEMP_EXTRA, 0) + "\u00b0" + "F");
            }
        };
        registerReceiver(receiver, new IntentFilter(WeatherIntentService.TEMP_RESPONSE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);

    }
}
