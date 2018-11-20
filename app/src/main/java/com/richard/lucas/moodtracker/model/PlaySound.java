package com.richard.lucas.moodtracker.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.richard.lucas.moodtracker.R;
import com.richard.lucas.moodtracker.controller.MainActivity;

/**
 * Created by lucas on 20/11/2018.
 */
public class PlaySound {

    static Context applicationContext = MainActivity.getContextOfApplication();

    final static MediaPlayer supperHappySoundMP = MediaPlayer.create(applicationContext, R.raw.super_happy);
    final static MediaPlayer happySoundMP = MediaPlayer.create(applicationContext, R.raw.happy);
    final static MediaPlayer normalSoundMP = MediaPlayer.create(applicationContext, R.raw.normal);
    final static MediaPlayer disapointedSoundMP = MediaPlayer.create(applicationContext, R.raw.disappointed);
    final static MediaPlayer sadSoundMP = MediaPlayer.create(applicationContext, R.raw.sad);

    private PlaySound(){

    }

    public static void sound(int currentMood){
        switch (currentMood){
            case 0 :
                supperHappySoundMP.start();
                break;
            case 1 :
                happySoundMP.start();
                break;
            case 2 :
                normalSoundMP.start();
                break;
            case 3 :
                disapointedSoundMP.start();
                break;
            case 4 :
                sadSoundMP.start();
                break;
        }
    }
}

