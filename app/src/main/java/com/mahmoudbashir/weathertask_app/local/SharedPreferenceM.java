package com.mahmoudbashir.weathertask_app.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceM {
    Context context;
    private static final String SHARED_PREF_USER = "WEATHER_APP";

    private static SharedPreferenceM sharedPrefranceManager;

    private SharedPreferenceM(Context context) {
        this.context = context;
    }

    public synchronized static SharedPreferenceM getInastance(Context context) {
        if (sharedPrefranceManager == null) {
            sharedPrefranceManager = new SharedPreferenceM(context);
        }
        return sharedPrefranceManager;
    }
    public void save_city_name(String cityName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("city_name", cityName);
        editor.apply();
    }
    public String getCityName(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("city_name", "london");
    }
}