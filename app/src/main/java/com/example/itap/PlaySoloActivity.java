package com.example.itap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PlaySoloActivity extends AppCompatActivity implements NewHighscoreFragment.OnFragmentInteractionListener{

    ImageView tapTarget;
    TextView score, time;
    int currentScore;
    int timeLeft;
    boolean gameStarted;
    CountDownTimer timer;
    String newHighscorePlayerName = "unknown";
    NewHighscoreFragment nameDialog;

    ArrayList<ScoreEntry> highscores;
    //SaveDataManager saveDataManager = new SaveDataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_solo);

        //load highscore hashmap from internal storage
        //highscores = saveDataManager.loadHighscoreMap();
        loadData();

        //initialize elements
        gameStarted = false;

        tapTarget = findViewById(R.id.tapTarget);
        tapTarget.setOnClickListener(tapTargetListener);

        score = findViewById(R.id.score);

        time = findViewById(R.id.timeLeft);
        timeLeft = 10;

        //reset elements
        setScore(0);
        setTime(timeLeft);
    }

    private View.OnClickListener tapTargetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentScore += 1;
            setScore(currentScore);

            if(!gameStarted) {
                startTimer(timeLeft * 1000, 1000);
                gameStarted = true;
            }
        }
    };

    //implement CountDownTimer
    private void startTimer(int time, int interval) {
        timer = new CountDownTimer(time, interval) {

            public void onTick(long millisUntilFinished) {
                setTime((int) millisUntilFinished/1000);
            }

            public void onFinish() {
                setTime(0);
                tapTarget.setOnClickListener(null);
                Toast.makeText(getApplicationContext(), "Game ended!", Toast.LENGTH_SHORT).show();

                //check for a new highscore, only call openPlayerNameDialog() in that case
                if(highscores.size() < 10) {
                    openPlayerNameDialog();
                } else if(highscores.get(9).getScore() < currentScore) {
                    highscores.remove(9);
                    openPlayerNameDialog();
                }

                //for testing, open fragment
                //remove after checking for new highscore is implemented
                //openPlayerNameDialog();
            }
        }.start();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscores);
        editor.putString("highscores", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("highscores", null);
        Type type = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();
        highscores = gson.fromJson(json, type);

        if (highscores == null) {
            highscores = new ArrayList<>(10);
        }
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        //save highscoreList
        //saveDataManager.saveHighscoreMap(highscores);
        saveData();
        finish();
    }

    private void setScore(int newScore) {
        currentScore = newScore;
        String newText = String.valueOf(newScore);
        score.setText(newText);
    }

    private void setTime(int timeLeft) {
        this.timeLeft = timeLeft;
        String newText = String.valueOf(timeLeft);
        time.setText(newText);
    }

    //open Dialog Fragment
    public void openPlayerNameDialog() {
        NewHighscoreFragment nameDialog = new NewHighscoreFragment();
        nameDialog.setCancelable(false);
        nameDialog.show(getSupportFragmentManager(), "nameDialog");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void createNewHighscore(String name) {
        newHighscorePlayerName = name;
        highscores.add(new ScoreEntry(newHighscorePlayerName, currentScore));
        Collections.sort(highscores, Comparator.comparing(ScoreEntry::getScore));
        Collections.reverse(highscores);
    }
}
