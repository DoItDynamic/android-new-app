package com.smartstudio.sajmovi.eu.function;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Boris on 13.6.2015..
 */
public class HandleCountryData {
    String MyCountry;

    public HandleCountryData(Activity activity){

        SharedPreferences settings = activity.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
        /*Check if Country data exists, if not, write default value TRUE*/
        if(!settings.contains("country1"))
                settings.edit().putBoolean("country1", true).apply();
        if(!settings.contains("country2"))
                settings.edit().putBoolean("country2", true).apply();
        if(!settings.contains("country3"))
                settings.edit().putBoolean("country3", true).apply();
        if(!settings.contains("country4"))
                settings.edit().putBoolean("country4", true).apply();
        if(!settings.contains("country5"))
                settings.edit().putBoolean("country5", true).apply();
        if(!settings.contains("country6"))
                settings.edit().putBoolean("country6", true).apply();
    }
}
