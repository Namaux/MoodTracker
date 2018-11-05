package com.richard.lucas.moodtracker.controller;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.richard.lucas.moodtracker.R;

import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private TextView mTextCase1;
    private TextView mTextCase2;
    private TextView mTextCase3;
    private TextView mTextCase4;
    private TextView mTextCase5;
    private TextView mTextCase6;
    private TextView mTextCase7;

    private ImageButton mBtnCase1;
    private ImageButton mBtnCase2;
    private ImageButton mBtnCase3;
    private ImageButton mBtnCase4;
    private ImageButton mBtnCase5;
    private ImageButton mBtnCase6;
    private ImageButton mBtnCase7;

    private Map<Integer, Integer> mListMoodValue = new HashMap<>();
    private Map<Integer, String> mListMoodComment = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        mTextCase1 = (TextView) findViewById(R.id.historyCase1Text);
        mTextCase2 = (TextView) findViewById(R.id.historyCase2Text);
        mTextCase3 = (TextView) findViewById(R.id.historyCase3Text);
        mTextCase4 = (TextView) findViewById(R.id.historyCase4Text);
        mTextCase5 = (TextView) findViewById(R.id.historyCase5Text);
        mTextCase6 = (TextView) findViewById(R.id.historyCase6Text);
        mTextCase7 = (TextView) findViewById(R.id.historyCase7Text);

        mBtnCase1 = (ImageButton) findViewById(R.id.historyCase1Btn);
        mBtnCase2 = (ImageButton) findViewById(R.id.historyCase2Btn);
        mBtnCase3 = (ImageButton) findViewById(R.id.historyCase3Btn);
        mBtnCase4 = (ImageButton) findViewById(R.id.historyCase4Btn);
        mBtnCase5 = (ImageButton) findViewById(R.id.historyCase5Btn);
        mBtnCase6 = (ImageButton) findViewById(R.id.historyCase6Btn);
        mBtnCase7 = (ImageButton) findViewById(R.id.historyCase7Btn);

        createListMood();
        displayHistory(mListMoodValue);

        mBtnCase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(1), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(2), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(3), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(4), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(5), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(6), Toast.LENGTH_SHORT).show();
            }
        });

        mBtnCase7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mListMoodComment.get(7), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createListMood() {
        Bundle bundle = getIntent().getExtras();

        mListMoodValue.put(1,(bundle.getInt("moodValue1", 5)));
        mListMoodValue.put(2,(bundle.getInt("moodValue2", 5)));
        mListMoodValue.put(3,(bundle.getInt("moodValue3", 5)));
        mListMoodValue.put(4,(bundle.getInt("moodValue4", 5)));
        mListMoodValue.put(5,(bundle.getInt("moodValue5", 5)));
        mListMoodValue.put(6,(bundle.getInt("moodValue6", 5)));
        mListMoodValue.put(7,(bundle.getInt("moodValue7", 5)));

        mListMoodComment.put(1,(bundle.getString("moodComment1", "")));
        mListMoodComment.put(2,(bundle.getString("moodComment2", "")));
        mListMoodComment.put(3,(bundle.getString("moodComment3", "")));
        mListMoodComment.put(4,(bundle.getString("moodComment4", "")));
        mListMoodComment.put(5,(bundle.getString("moodComment5", "")));
        mListMoodComment.put(6,(bundle.getString("moodComment6", "")));
        mListMoodComment.put(7,(bundle.getString("moodComment7", "")));
    }

    public void displayHistory(Map<Integer, Integer> mListMoodValue){
        View viewHistoryCase1 = (View) findViewById(R.id.historyCase1);
        View viewHistoryCase2 = (View) findViewById(R.id.historyCase2);
        View viewHistoryCase3 = (View) findViewById(R.id.historyCase3);
        View viewHistoryCase4 = (View) findViewById(R.id.historyCase4);
        View viewHistoryCase5 = (View) findViewById(R.id.historyCase5);
        View viewHistoryCase6 = (View) findViewById(R.id.historyCase6);
        View viewHistoryCase7 = (View) findViewById(R.id.historyCase7);

        View viewBtnCase1 = findViewById(R.id.historyCase1Btn);
        View viewBtnCase2 = findViewById(R.id.historyCase2Btn);
        View viewBtnCase3 = findViewById(R.id.historyCase3Btn);
        View viewBtnCase4 = findViewById(R.id.historyCase4Btn);
        View viewBtnCase5 = findViewById(R.id.historyCase5Btn);
        View viewBtnCase6 = findViewById(R.id.historyCase6Btn);
        View viewBtnCase7 = findViewById(R.id.historyCase7Btn);

        designCase(viewHistoryCase1, viewBtnCase1, mListMoodValue.get(1), mListMoodComment.get(1), mBtnCase1, mTextCase1);
        designCase(viewHistoryCase2, viewBtnCase2, mListMoodValue.get(2), mListMoodComment.get(2), mBtnCase2, mTextCase2);
        designCase(viewHistoryCase3, viewBtnCase3, mListMoodValue.get(3), mListMoodComment.get(3), mBtnCase3, mTextCase3);
        designCase(viewHistoryCase4, viewBtnCase4, mListMoodValue.get(4), mListMoodComment.get(4), mBtnCase4, mTextCase4);
        designCase(viewHistoryCase5, viewBtnCase5, mListMoodValue.get(5), mListMoodComment.get(5), mBtnCase5, mTextCase5);
        designCase(viewHistoryCase6, viewBtnCase6, mListMoodValue.get(6), mListMoodComment.get(6), mBtnCase6, mTextCase6);
        designCase(viewHistoryCase7, viewBtnCase7, mListMoodValue.get(7), mListMoodComment.get(7), mBtnCase7, mTextCase7);
    }

    public void designCase (View viewHistoryCase, View viewBtnCase, int moodValue, String moodComment, ImageButton mBtn, TextView mText){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;

        boolean comment = false;

        if(moodComment.compareTo("") != 0){comment = true;}

        switch (moodValue){
            case 0:
                viewHistoryCase.setBackground(getResources().getDrawable(R.drawable.color0,null));
                getResources().getDrawable(R.drawable.color0);
                viewBtnCase.setBackgroundColor(0xfff9ec4f);
                if (comment == true){mBtn.setImageResource(R.drawable.ic_comment_black_48px);}
                ViewGroup.LayoutParams params0=viewHistoryCase.getLayoutParams();
                params0.width=widthScreen;
                viewHistoryCase.setLayoutParams(params0);
                break;
            case 1:
                viewHistoryCase.setBackground(getResources().getDrawable(R.drawable.color1,null));
                getResources().getDrawable(R.drawable.color1);
                viewBtnCase.setBackgroundColor(0xffb8e986);
                if (comment == true){mBtn.setImageResource(R.drawable.ic_comment_black_48px);}
                ViewGroup.LayoutParams params1=viewHistoryCase.getLayoutParams();
                params1.width= 4 * widthScreen/5;
                viewHistoryCase.setLayoutParams(params1);
                break;
            case 2:
                viewHistoryCase.setBackground(getResources().getDrawable(R.drawable.color2,null));
                getResources().getDrawable(R.drawable.color2);
                viewBtnCase.setBackgroundColor(0x40bdb7);
                if (comment == true){mBtn.setImageResource(R.drawable.ic_comment_black_48px);}
                ViewGroup.LayoutParams params2=viewHistoryCase.getLayoutParams();
                params2.width=3 * widthScreen/5;
                viewHistoryCase.setLayoutParams(params2);
                break;
            case 3:
                viewHistoryCase.setBackground(getResources().getDrawable(R.drawable.color3,null));
                getResources().getDrawable(R.drawable.color3);
                viewBtnCase.setBackgroundColor(0xff9b9b9b);
                if (comment == true){mBtn.setImageResource(R.drawable.ic_comment_black_48px);}
                ViewGroup.LayoutParams params3=viewHistoryCase.getLayoutParams();
                params3.width=2 * widthScreen/5;
                viewHistoryCase.setLayoutParams(params3);
                break;
            case 4:
                viewHistoryCase.setBackground(getResources().getDrawable(R.drawable.color4,null));
                getResources().getDrawable(R.drawable.color4);
                viewBtnCase.setBackgroundColor(0xffde3c50);
                if (comment == true){mBtn.setImageResource(R.drawable.ic_comment_black_48px);}
                ViewGroup.LayoutParams params4=viewHistoryCase.getLayoutParams();
                params4.width=widthScreen/5;
                viewHistoryCase.setLayoutParams(params4);
                break;
            case 5:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("HistoryActivity::onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("HistoryActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("HistoryActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("HistoryActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("HistoryActivity::onDestroy()");
    }
}



