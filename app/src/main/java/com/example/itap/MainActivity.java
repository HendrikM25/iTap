package com.example.itap;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageView playSolo, play2Gether, settings, highscores;
    ArrayList<ScoreEntry> highscoreList = new ArrayList<ScoreEntry>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        intentSolo.putExtra("highscores", highscoreList);
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
        intentHighscores.putExtra("highscores", highscoreList);
        startActivity(intentHighscores);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                //what happens with result (data)?
                highscoreList = (ArrayList<ScoreEntry>) data.getExtras().getSerializable("newHighscores");

            }
            if(resultCode == Activity.RESULT_CANCELED) {
                //when there is no result
            }
        }
    }
}
