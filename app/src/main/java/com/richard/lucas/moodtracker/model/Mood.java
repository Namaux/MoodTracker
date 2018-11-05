package com.richard.lucas.moodtracker.model;

import java.util.Map;

/**
 * Created by lucas on 01/11/2018.
 */
public class Mood {
    private int currentMood;
    private String comment;

    public int getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(int currentMood) {
        this.currentMood = currentMood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addMoodValue(Map<Integer, Integer> mListMoodValue){
        for (int i = 1; i < 7; i++){
            mListMoodValue.put(i, mListMoodValue.get(i + 1));
        }mListMoodValue.put(7, currentMood);
    }

    public void addMoodComment(Map<Integer, String> mListMoodComment) {

        for (int i = 1; i < 7; i++) {
            mListMoodComment.put(i, mListMoodComment.get(i + 1));
        }
        mListMoodComment.put(7, comment);
    }
}
