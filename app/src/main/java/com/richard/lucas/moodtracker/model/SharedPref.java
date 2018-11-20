package com.richard.lucas.moodtracker.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.richard.lucas.moodtracker.controller.HistoryActivity;
import com.richard.lucas.moodtracker.controller.MainActivity;

import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by lucas on 20/11/2018.
 */
public class SharedPref
{
    private static SharedPreferences mSharedPref;
    public static final String CurrentMood = "currentMood";
    public static final String CurrentMoodComment = "currentMoodComment";
    public static final String NewMood = "newMood";
    public static final String Midnight = "midnight";

    private SharedPref()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }

    public static void createListMood(Map<Integer, Integer> mListMoodValue, Map<Integer, String> mListMoodComment){
        mListMoodValue.put(1, read("moodValue1", 5));
        mListMoodValue.put(2, read("moodValue2", 5));
        mListMoodValue.put(3, read("moodValue3", 5));
        mListMoodValue.put(4, read("moodValue4", 5));
        mListMoodValue.put(5, read("moodValue5", 5));
        mListMoodValue.put(6, read("moodValue6", 5));
        mListMoodValue.put(7, read("moodValue7", 5));

        mListMoodComment.put(1, read("moodComment1", "null"));
        mListMoodComment.put(2, read("moodComment2", "null"));
        mListMoodComment.put(3, read("moodComment3", "null"));
        mListMoodComment.put(4, read("moodComment4", "null"));
        mListMoodComment.put(5, read("moodComment5", "null"));
        mListMoodComment.put(6, read("moodComment6", "null"));
        mListMoodComment.put(7, read("moodComment7", "null"));

    }

    public static void addPreferences(Map<Integer, Integer> mListMoodValue, Map<Integer, String> mListMoodComment){
        write("moodValue1", mListMoodValue.get(1));
        write("moodValue2", mListMoodValue.get(2));
        write("moodValue3", mListMoodValue.get(3));
        write("moodValue4", mListMoodValue.get(4));
        write("moodValue5", mListMoodValue.get(5));
        write("moodValue6", mListMoodValue.get(6));
        write("moodValue7", mListMoodValue.get(7));

        write("moodComment1", mListMoodComment.get(1));
        write("moodComment2", mListMoodComment.get(2));
        write("moodComment3", mListMoodComment.get(3));
        write("moodComment4", mListMoodComment.get(4));
        write("moodComment5", mListMoodComment.get(5));
        write("moodComment6", mListMoodComment.get(6));
        write("moodComment7", mListMoodComment.get(7));
    }

    /*public static void createBundle(Map<Integer, Integer> mListMoodValue, Map<Integer, String> mListMoodComment){

        Intent recordActivity = new Intent(MainActivity.class, HistoryActivity.class);


        Bundle bundle = new Bundle();

        bundle.putInt("moodValue1", mListMoodValue.get(1));
        bundle.putInt("moodValue2", mListMoodValue.get(2));
        bundle.putInt("moodValue3", mListMoodValue.get(3));
        bundle.putInt("moodValue4", mListMoodValue.get(4));
        bundle.putInt("moodValue5", mListMoodValue.get(5));
        bundle.putInt("moodValue6", mListMoodValue.get(6));
        bundle.putInt("moodValue7", mListMoodValue.get(7));


        bundle.putString("moodComment1", mListMoodComment.get(1));
        bundle.putString("moodComment2", mListMoodComment.get(2));
        bundle.putString("moodComment3", mListMoodComment.get(3));
        bundle.putString("moodComment4", mListMoodComment.get(4));
        bundle.putString("moodComment5", mListMoodComment.get(5));
        bundle.putString("moodComment6", mListMoodComment.get(6));
        bundle.putString("moodComment7", mListMoodComment.get(7));

        recordActivity.putExtras(bundle);
        startActivity(recordActivity);
    }*/
}
