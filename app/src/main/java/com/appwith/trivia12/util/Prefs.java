package com.appwith.trivia12.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;
    private final static String HIGHEST_SCORE="highest_score";
    private final static String SAVE_QUESTION_INDEX="save_question_index";

    public Prefs(Activity ctx) {
        preferences=ctx.getPreferences(Context.MODE_PRIVATE);
    }
    public void saveHighestScore(int score){
        int lastScore=preferences.getInt("highest_score",0);
        if(lastScore<score){
            preferences.edit().putInt(HIGHEST_SCORE,score).apply();
        }
    }
    public int getHighestScore(){

        return preferences.getInt(HIGHEST_SCORE,0);
    }
    public void saveQuestionIndex(int index){
        preferences.edit().putInt(SAVE_QUESTION_INDEX,index).apply();
    }
    public int getQuestionIndex(){
        return preferences.getInt(SAVE_QUESTION_INDEX,0);
    }
}
