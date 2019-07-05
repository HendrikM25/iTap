package com.example.itap;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Play2GetherActivity extends AppCompatActivity implements NewHighscoreFragment.OnFragmentInteractionListener{

    TextView scoreView, timeView;
    Button passButton;
    ImageView tapTarget, p1Indicator, p2Inidicator;

    boolean gameStarted;
    boolean player1Active;
    boolean player1Played;
    int currentScore;
    int p1Score;
    int p2Score;
    int timeLeft;

    CountDownTimer timer;

    ArrayList<ScoreEntry> highscores;
    String newHighscorePlayerName = "unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2_gether);

        //load highscores
        loadData();

        //initialize elements
        gameStarted = false;
        player1Active = true;
        player1Played = false;
        currentScore = 0;
        timeLeft = 10;
        p1Score = 0;
        p2Score = 0;

        scoreView = findViewById(R.id.scoreView);
        timeView = findViewById(R.id.timeView);

        setTime(timeLeft);

        passButton = findViewById(R.id.passButton);

        tapTarget = findViewById(R.id.tapTarget);
        p1Indicator = findViewById(R.id.player1Indicator);
        p2Inidicator = findViewById(R.id.player2Indicator);

        p1Indicator.setImageResource(R.drawable.number_one_orange);

        //add onClickListener
        tapTarget.setOnClickListener(tapTargetListener);
        passButton.setOnClickListener(passButtonListener);
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

    private View.OnClickListener passButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(player1Active  && player1Played) {
                p2Inidicator.setImageResource(R.drawable.number_two_orange);
                p1Indicator.setImageResource(R.drawable.number_one);
                player1Active = false;
                gameStarted = false;

                setScore(0);
                currentScore = 0;
                timeLeft = 10;
                setTime(timeLeft);

                tapTarget.setOnClickListener(tapTargetListener);
            }
        }
    };

    private void startTimer(int time, int interval) {
        timer = new CountDownTimer(time, interval) {

            public void onTick(long millisUntilFinished) {
                setTime((int) millisUntilFinished/1000);
            }

            public void onFinish() {
                setTime(0);
                tapTarget.setOnClickListener(null);
                Toast.makeText(getApplicationContext(), "Game ended!", Toast.LENGTH_SHORT).show();

                if(player1Active) {
                    p1Score = currentScore;
                    player1Played = true;
                } else if(!player1Active) {
                    p2Score = currentScore;
                    if(p1Score > p2Score) {
                        Toast.makeText(getApplicationContext(), "Player 1 won!", Toast.LENGTH_LONG).show();
                    }
                    else if(p2Score > p1Score) {
                        Toast.makeText(getApplicationContext(), "Player 2 won!", Toast.LENGTH_LONG).show();
                    }
                    else if(p1Score == p2Score) {
                        Toast.makeText(getApplicationContext(), "Draw!", Toast.LENGTH_LONG).show();
                    }
                }

                //check for a new highscore, only call openPlayerNameDialog() in that case
                if(highscores.size() < 10) {
                    openPlayerNameDialog();
                } else if(highscores.get(9).getScore() < currentScore) {
                    highscores.remove(9);
                    openPlayerNameDialog();
                }
            }
        }.start();
    }

    private void setScore(int newScore) {
        currentScore = newScore;
        String newText = String.valueOf(newScore);
        scoreView.setText(newText);
    }

    private void setTime(int timeLeft) {
        this.timeLeft = timeLeft;
        String newText = String.valueOf(timeLeft);
        timeView.setText(newText);
    }

    //open Dialog Fragment
    public void openPlayerNameDialog() {
        NewHighscoreFragment nameDialog = new NewHighscoreFragment();
        nameDialog.setCancelable(false);
        nameDialog.show(getSupportFragmentManager(), "nameDialog");
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        saveData();
        finish();
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


}
