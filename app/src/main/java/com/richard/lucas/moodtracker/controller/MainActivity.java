package com.richard.lucas.moodtracker.controller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.richard.lucas.moodtracker.R;
import com.richard.lucas.moodtracker.model.Mood;

import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.richard.lucas.moodtracker.R.drawable.color1;


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

    private int currentMood = 1; // 0:superHappy 1:happy 2:normal 3:disappointed 4:sad

    private Mood mMood;

    private Map<Integer, Integer> mListMoodValue = new HashMap<>();
    private Map<Integer, String> mListMoodComment = new HashMap<>();

    private SharedPreferences mPreferences;

    private String saveDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date = new Date();
        SimpleDateFormat DATE = new SimpleDateFormat("MM dd");
        SimpleDateFormat HOUR = new SimpleDateFormat("HH");
        final String Date = DATE.format(date);
        String Hour = HOUR.format(date);

        mMood = new Mood();

        mPreferences = getPreferences(MODE_PRIVATE);

        mImgButton = (ImageButton) findViewById(R.id.smiley);
        mAddNoteButton = (ImageButton) findViewById(R.id.addNote);
        mHistoryButton = (ImageButton) findViewById(R.id.history);
        mCommentInput = (EditText) findViewById(R.id.boxComment);
        mImgCurrentMood = (ImageView) findViewById(R.id.currentSmiley);

        // this is the view we will add the gesture detector to
        View myView = findViewById(R.id.moodTrackerLayout);

        // get the gesture detector
        mDetector = new GestureDetector(this, new MyGestureListener());
        // Add a touch listener to the view
        // The touch listener passes all its events on to the gesture detector
        myView.setOnTouchListener(touchListener);

        mMood.setCurrentMood(mPreferences.getInt("currentMood", 5));
        mMood.setComment(mPreferences.getString("currentMoodComment", ""));

        changeCurrentSmiley(mPreferences.getInt("currentMood",1));

        mImgButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                mMood.setCurrentMood(currentMood);
                mPreferences.edit().putInt("currentMood",mMood.getCurrentMood()).apply();

                mMood.setComment(mPreferences.getString("currentMoodComment", ""));

                if (mMood.getComment().compareTo("") == 0){
                    mMood.setComment("");
                    mPreferences.edit().putString("currentMoodComment",mMood.getComment()).apply();
                }

                saveDate = Date;
                mPreferences.edit().putString("saveDate",saveDate).apply();
                changeCurrentSmiley(mPreferences.getInt("currentMood",1));
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
                                String comment = mCommentInput.getText().toString();
                                mMood.setComment(comment);
                                mPreferences.edit().putString("currentMoodComment",mMood.getComment()).apply();
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

                Bundle bundle = new Bundle();

                bundle.putInt("moodValue1", mPreferences.getInt("moodValue1", 5));
                bundle.putInt("moodValue2", mPreferences.getInt("moodValue2", 5));
                bundle.putInt("moodValue3", mPreferences.getInt("moodValue3", 5));
                bundle.putInt("moodValue4", mPreferences.getInt("moodValue4", 5));
                bundle.putInt("moodValue5", mPreferences.getInt("moodValue5", 5));
                bundle.putInt("moodValue6", mPreferences.getInt("moodValue6", 5));
                bundle.putInt("moodValue7", mPreferences.getInt("moodValue7", 5));

                bundle.putString("moodComment1", mPreferences.getString("moodComment1", ""));
                bundle.putString("moodComment2", mPreferences.getString("moodComment2", ""));
                bundle.putString("moodComment3", mPreferences.getString("moodComment3", ""));
                bundle.putString("moodComment4", mPreferences.getString("moodComment4", ""));
                bundle.putString("moodComment5", mPreferences.getString("moodComment5", ""));
                bundle.putString("moodComment6", mPreferences.getString("moodComment6", ""));
                bundle.putString("moodComment7", mPreferences.getString("moodComment7", ""));

                recordActivity.putExtras(bundle);
                startActivity(recordActivity);
            }
        });
    }
    // This touch listener passes everything on to the gesture detector.
    // That saves us the trouble of interpreting the raw touch events
    // ourselves.
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return mDetector.onTouchEvent(event);
        }
    };
    // In the SimpleOnGestureListener subclass you should override
    // onDown and any other gesture that you want to detect.
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
    @SuppressLint({"CutPasteId", "WrongViewCast"})
    public void changeSlide(int slide){
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

    public void changeCurrentSmiley(int currentMood){
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

    public void createListMood(){
        mListMoodValue.put(1, mPreferences.getInt("moodValue1", 5));
        mListMoodValue.put(2, mPreferences.getInt("moodValue2", 5));
        mListMoodValue.put(3, mPreferences.getInt("moodValue3", 5));
        mListMoodValue.put(4, mPreferences.getInt("moodValue4", 5));
        mListMoodValue.put(5, mPreferences.getInt("moodValue5", 5));
        mListMoodValue.put(6, mPreferences.getInt("moodValue6", 5));
        mListMoodValue.put(7, mPreferences.getInt("moodValue7", 5));

        mListMoodComment.put(1, mPreferences.getString("moodComment1", ""));
        mListMoodComment.put(2, mPreferences.getString("moodComment2", ""));
        mListMoodComment.put(3, mPreferences.getString("moodComment3", ""));
        mListMoodComment.put(4, mPreferences.getString("moodComment4", ""));
        mListMoodComment.put(5, mPreferences.getString("moodComment5", ""));
        mListMoodComment.put(6, mPreferences.getString("moodComment6", ""));
        mListMoodComment.put(7, mPreferences.getString("moodComment7", ""));

    }

    public void addPreferences(){
        mPreferences.edit().putInt("moodValue1", mListMoodValue.get(1)).apply();
        mPreferences.edit().putInt("moodValue2", mListMoodValue.get(2)).apply();
        mPreferences.edit().putInt("moodValue3", mListMoodValue.get(3)).apply();
        mPreferences.edit().putInt("moodValue4", mListMoodValue.get(4)).apply();
        mPreferences.edit().putInt("moodValue5", mListMoodValue.get(5)).apply();
        mPreferences.edit().putInt("moodValue6", mListMoodValue.get(6)).apply();
        mPreferences.edit().putInt("moodValue7", mListMoodValue.get(7)).apply();

        mPreferences.edit().putString("moodComment1", mListMoodComment.get(1)).apply();
        mPreferences.edit().putString("moodComment2", mListMoodComment.get(2)).apply();
        mPreferences.edit().putString("moodComment3", mListMoodComment.get(3)).apply();
        mPreferences.edit().putString("moodComment4", mListMoodComment.get(4)).apply();
        mPreferences.edit().putString("moodComment5", mListMoodComment.get(5)).apply();
        mPreferences.edit().putString("moodComment6", mListMoodComment.get(6)).apply();
        mPreferences.edit().putString("moodComment7", mListMoodComment.get(7)).apply();
    }

    public void saveMoodAtMidnight(){
        Date date = new Date();
        SimpleDateFormat DATE = new SimpleDateFormat("MM dd");
        final String Date = DATE.format(date);

        if (Date.compareTo(mPreferences.getString("saveDate","")) != 0){
            createListMood();
            mMood.addMoodValue(mListMoodValue);
            mMood.addMoodComment(mListMoodComment);
            addPreferences();

            mPreferences.edit().putString("currentMoodComment", "").apply();
            mPreferences.edit().putInt("currentMood", 1).apply();
            changeCurrentSmiley(mPreferences.getInt("currentMood",1));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Date date = new Date();
        SimpleDateFormat DATE = new SimpleDateFormat("MM dd");
        final String Date = DATE.format(date);

        System.out.println("MainActivity::onStart()");
        if (Date.compareTo(mPreferences.getString("saveDate","")) != 0){
            saveMoodAtMidnight();
            mPreferences.edit().putString("saveDate", Date).apply();
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
