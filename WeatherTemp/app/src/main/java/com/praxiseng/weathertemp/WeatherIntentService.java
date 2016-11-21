package com.praxiseng.weathertemp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WeatherIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TEMPERATURE = "com.praxiseng.weathertemp.action.TEMP";
    public static final String TEMP_RESPONSE = "com.praxiseng.weather.intent.response";
    public static final String TEMP_EXTRA = "com.praxiseng.weather.temp.extra";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionWeahter(Context context) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_TEMPERATURE);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionWeather(Context context, String param1, String param2) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_TEMPERATURE);
        context.startService(intent);
    }

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TEMPERATURE.equals(action)) {
                handleActionWeather();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWeather() {
        try {
            String json = IOUtils.toString(new URL("http://api.openweathermap.org/data/2.5/weather?zip=21044,us&units=imperial&appid=4d36b5f1fce463fe1647b8b9711bf707"), "UTF-8");
            Log.d("IntentService", "json = " + json);
            JSONObject jsonWeather = new JSONObject(json);
            JSONObject main = jsonWeather.getJSONObject("main");
            double temp = main.getDouble("temp");
            Intent tempResponseIntent = new Intent(TEMP_RESPONSE);
            tempResponseIntent.putExtra(TEMP_EXTRA, temp);
            sendBroadcast(tempResponseIntent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
