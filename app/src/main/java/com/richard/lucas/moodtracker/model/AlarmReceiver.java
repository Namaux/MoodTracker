package com.richard.lucas.moodtracker.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.richard.lucas.moodtracker.controller.MainActivity;

/**
 * Created by lucas on 12/11/2018.
 */
public class AlarmReceiver extends BroadcastReceiver {
    //private SharedPreferences Preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPref.init(context);

        //Preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Toast.makeText(context, "Mood save !", Toast.LENGTH_SHORT).show();
        //Preferences.edit().putBoolean("midnight", true).apply();
        SharedPref.write(SharedPref.Midnight, true);
    }
}
