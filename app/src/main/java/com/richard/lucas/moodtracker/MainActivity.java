package com.richard.lucas.moodtracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageButton;

import com.richard.lucas.moodtracker.R;


public class MainActivity extends AppCompatActivity {

    private GestureDetector mDetector;
    private static final int SWIPE_MIN_DISTANCE = 600;
    private static final int SWIPE_MAX_OFF_PATH = 2000;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private ImageButton imgButton;
    private int currentSlide = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton = (ImageButton) findViewById(R.id.smiley);

        // this is the view we will add the gesture detector to
        View myView = findViewById(R.id.moodTrackerLayout);

        // get the gesture detector
        mDetector = new GestureDetector(this, new MyGestureListener());
        // Add a touch listener to the view
        // The touch listener passes all its events on to the gesture detector
        myView.setOnTouchListener(touchListener);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    imgButton.setImageResource(R.drawable.smiley_super_happy);
                    break;
                case 1 :
                    color = 0xffb8e986;
                    imgButton.setImageResource(R.drawable.smiley_happy);
                    break;
                case 2 :
                    color = 0xff468ad9;
                    imgButton.setImageResource(R.drawable.smiley_normal);
                    break;
                case 3 :
                    color = 0xff9b9b9b;
                    imgButton.setImageResource(R.drawable.smiley_disappointed);
                    break;
                case 4 :
                    color = 0xffde3c50;
                    imgButton.setImageResource(R.drawable.smiley_sad);
                    break;
            }
            myView.setBackgroundColor(color);
            viewSmiley.setBackgroundColor(color);
            viewAddNote.setBackgroundColor(color);
            viewHistory.setBackgroundColor(color);
        }

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

                    if (currentSlide > 0){currentSlide--;}

                    switch (currentSlide){
                        case 0 :
                            changeSlide(0);
                            break;
                        case 1 :
                            changeSlide(1);
                            break;
                        case 2 :
                            changeSlide(2);
                            break;
                        case 3 :
                            changeSlide(3);
                            break;
                        case 4 :
                           changeSlide(4);
                            break;
                    }
                }
                // down swipe
                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (currentSlide < 4){currentSlide++;}

                    switch (currentSlide){
                        case 0 :
                            changeSlide(0);
                            break;
                        case 1 :
                            changeSlide(1);
                            break;
                        case 2 :
                            changeSlide(2);
                            break;
                        case 3 :
                            changeSlide(3);
                            break;
                        case 4 :
                            changeSlide(4);
                            break;
                    }
                }
            } catch (Exception e) {
            }
            return false;
        }
    }
}
