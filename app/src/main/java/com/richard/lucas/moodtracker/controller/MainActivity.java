package com.richard.lucas.moodtracker.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ImageButton;
import com.richard.lucas.moodtracker.R;
import com.richard.lucas.moodtracker.model.AlarmReceiver;
import com.richard.lucas.moodtracker.model.Mood;
import com.richard.lucas.moodtracker.model.PlaySound;
import com.richard.lucas.moodtracker.model.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private GestureDetector mDetector;

    private static final int SWIPE_MIN_DISTANCE = 600;
    private static final int SWIPE_MAX_OFF_PATH = 2000;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;

    private ImageButton mImgButton;
    private ImageButton mAddNoteButton;
    private ImageButton mHistoryButton;
    private EditText mCommentInput;
    private ImageView mImgCurrentMood;
    public static Context contextOfApplication;

    private int currentMood = 1; // 0:superHappy 1:happy 2:normal 3:disappointed 4:sad

    private Mood mMood;

    private Map<Integer, Integer> mListMoodValue = new HashMap<>();
    private Map<Integer, String> mListMoodComment = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity::onCreate");
        setContentView(R.layout.activity_main);
        SharedPref.init(getApplicationContext());
        View myView = findViewById(R.id.moodTrackerLayout);
        myView.setOnTouchListener(touchListener);

        mImgButton = (ImageButton) findViewById(R.id.smiley);
        mAddNoteButton = (ImageButton) findViewById(R.id.addNote);
        mHistoryButton = (ImageButton) findViewById(R.id.history);
        mCommentInput = (EditText) findViewById(R.id.boxComment);
        mImgCurrentMood = (ImageView) findViewById(R.id.currentSmiley);
        contextOfApplication = getApplicationContext();

        mDetector = new GestureDetector(this, new MyGestureListener());
        mMood = new Mood();

        changeCurrentSmiley(SharedPref.read(SharedPref.CurrentMood, 1));
        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert();
                SharedPref.write(SharedPref.CurrentMood, currentMood);

                if (SharedPref.read(SharedPref.CurrentMoodComment, "null").compareTo("null") == 0){
                    SharedPref.write(SharedPref.CurrentMoodComment, "null");
                }
                changeCurrentSmiley(SharedPref.read(SharedPref.CurrentMood, 1));
                PlaySound.sound(currentMood);
                SharedPref.write(SharedPref.NewMood, true);
            }
        });
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setView(R.layout.comment)

                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCommentInput = (EditText) ((AlertDialog) dialog).findViewById(R.id.boxComment);
                                SharedPref.write(SharedPref.CurrentMoodComment, mCommentInput.getText().toString());
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordActivity = new Intent(MainActivity.this, HistoryActivity.class);
                SharedPref.createListMood(mListMoodValue, mListMoodComment);

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
            }
        });
    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mDetector.onTouchEvent(event);
        }
    };
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("TAG","onDown: ");
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH){
                    return false;
                }
                // up swipe
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (currentMood > 0){currentMood--;}
                    changeSlide(currentMood);
                }
                // down swipe
                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (currentMood < 4){currentMood++;}
                    changeSlide(currentMood);
                }
            } catch (Exception e) {
            }
            return false;
        }
    }
    private void changeSlide(int slide){
        View myView = findViewById(R.id.moodTrackerLayout);

        View viewSmiley = findViewById(R.id.smiley);
        View viewAddNote = findViewById(R.id.addNote);
        View viewHistory = findViewById(R.id.history);

        int color = 0xffb8e986;

        switch (slide){
            case 0 :
                color = 0xfff9ec4f;
                mImgButton.setImageResource(R.drawable.smiley_super_happy);
                break;
            case 1 :
                color = 0xffb8e986;
                mImgButton.setImageResource(R.drawable.smiley_happy);
                break;
            case 2 :
                color = 0xff468ad9;
                mImgButton.setImageResource(R.drawable.smiley_normal);
                break;
            case 3 :
                color = 0xff9b9b9b;
                mImgButton.setImageResource(R.drawable.smiley_disappointed);
                break;
            case 4 :
                color = 0xffde3c50;
                mImgButton.setImageResource(R.drawable.smiley_sad);
                break;
        }
        myView.setBackgroundColor(color);
        viewSmiley.setBackgroundColor(color);
        viewAddNote.setBackgroundColor(color);
        viewHistory.setBackgroundColor(color);
    }
    private void changeCurrentSmiley(int currentMood){
        switch (currentMood){
            case 0 :
                mImgCurrentMood.setImageResource(R.drawable.smiley_super_happy);
                break;
            case 1 :
                mImgCurrentMood.setImageResource(R.drawable.smiley_happy);
                break;
            case 2 :
                mImgCurrentMood.setImageResource(R.drawable.smiley_normal);
                break;
            case 3 :
                mImgCurrentMood.setImageResource(R.drawable.smiley_disappointed);
                break;
            case 4 :
                mImgCurrentMood.setImageResource(R.drawable.smiley_sad);
                break;
        }
    }
    private void saveMoodAtMidnight(){
            mMood.setCurrentMood(SharedPref.read(SharedPref.CurrentMood, 1));
            mMood.setComment(SharedPref.read(SharedPref.CurrentMoodComment, "null"));

            SharedPref.createListMood(mListMoodValue, mListMoodComment);
            mMood.addMoodValue(mListMoodValue);
            mMood.addMoodComment(mListMoodComment);
            SharedPref.addPreferences(mListMoodValue, mListMoodComment);

            SharedPref.write(SharedPref.CurrentMoodComment, "null");
            SharedPref.write(SharedPref.CurrentMood, 1);
            SharedPref.write(SharedPref.NewMood, false);
            SharedPref.write(SharedPref.Midnight, false);

            currentMood =1;
            changeSlide(1);
            changeCurrentSmiley(1);
    }
    private void startAlert(){
        Date date = new Date();
        SimpleDateFormat HOUR = new SimpleDateFormat("HH");
        SimpleDateFormat MIN = new SimpleDateFormat("mm");
        String Hour = HOUR.format(date);
        String Min = MIN.format(date);

        int hour = Integer.parseInt(Hour);
        int min = Integer.parseInt(Min);
        int millisBeforeMidnight = ((23 - hour) * 3600000) + ((60 - min) * 60000);

        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 234324243, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (millisBeforeMidnight), pendingIntent);
    }
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");

        if (SharedPref.read(SharedPref.Midnight, false) == true){
            if (SharedPref.read(SharedPref.NewMood, false) == true){saveMoodAtMidnight();}
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
