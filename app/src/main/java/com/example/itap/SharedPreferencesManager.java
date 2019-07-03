package com.example.itap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesManager extends AppCompatActivity {

    SharedPreferences mPreferences;

    SharedPreferencesManager(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
    }

    public void saveHighscoreListToSharedPreferences(ArrayList<ScoreEntry> highscores) {
        //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscores);
        editor.putString("highscore list", json);
        editor.apply();
    }

    public ArrayList<ScoreEntry> loadHighscoreListFromSharedPreferences() {
        ArrayList<ScoreEntry> highscoreList;
        //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("highscore list", null);
        Type type = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();
        highscoreList = gson.fromJson(json, type);

        if(highscoreList == null) {
            highscoreList = new ArrayList<>(10);
        }

        return highscoreList;
    }

    public void deleteHighscoreListFromSharedPreferences() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove("highscore list");
    }

}
