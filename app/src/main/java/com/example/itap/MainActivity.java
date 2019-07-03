package com.example.itap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import java.util.ArrayList;

/*
    TODO: create SharedPreferences Manager
 */

public class MainActivity extends AppCompatActivity {

    ImageView playSolo, play2Gether, settings, highscores;
    //ArrayList<ScoreEntry> highscoreList = new ArrayList<>(10);
    //instead we are loading the current highscoreList from the sharedPreferences in our onCreate() method
    ArrayList<ScoreEntry> highscoreList;
    SharedPreferencesManager spManager;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize preferenceManager
        mPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
        spManager = new SharedPreferencesManager(mPreferences);

        //load highscoreList
        highscoreList = spManager.loadHighscoreListFromSharedPreferences();

        //initialize elements
        playSolo = findViewById(R.id.playSolo);
        playSolo.setOnClickListener(playSoloListener);

        play2Gether = findViewById(R.id.play2Gether);
        play2Gether.setOnClickListener(play2GetherListener);

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(settingsListener);

        highscores = findViewById(R.id.highscores);
        highscores.setOnClickListener(highscoresListener);

    }

    private View.OnClickListener playSoloListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSoloGameScreen();
        }
    };

    private View.OnClickListener play2GetherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showMultiplayerGameScreen();
        }
    };

    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSettingsScreen();
        }
    };

    private View.OnClickListener highscoresListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showHighscoresScreen();
        }
    };

    private void showSoloGameScreen() {
        Intent intentSolo = new Intent(this, PlaySoloActivity.class);
        //intentSolo.putExtra("highscores", highscoreList);
        //instead we save the current highscores to the sharedPreferences with spManager
        spManager.saveHighscoreListToSharedPreferences(highscoreList);
        startActivityForResult(intentSolo, 1);
    }

    private void showMultiplayerGameScreen() {
        Intent intentMulti = new Intent(this, Play2GetherActivity.class);
        startActivity(intentMulti);
    }

    private void showSettingsScreen() {
        Intent intentSettings = new Intent(this, SettingsActivity.class);
        startActivity(intentSettings);
    }

    private void showHighscoresScreen() {
        Intent intentHighscores = new Intent(this, HighscoresActivity.class);
        //intentHighscores.putExtra("highscores", highscoreList);
        //instead we are saving the current list to the shared preferences of our app with spManager
        spManager.saveHighscoreListToSharedPreferences(highscoreList);
        startActivity(intentHighscores);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                //what happens with result (data)?
                //highscoreList = (ArrayList<ScoreEntry>) data.getExtras().getSerializable("newHighscores");
                //instead we load the new highscoreList from the sharedPreferences with spManager
                spManager.loadHighscoreListFromSharedPreferences();
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                //when there is no result
            }
        }
    }
}
