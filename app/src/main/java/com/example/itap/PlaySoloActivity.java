package com.example.itap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlaySoloActivity extends AppCompatActivity implements NewHighscoreDialogFragment.OnFragmentInteractionListener {

    ImageView tapTarget;
    TextView score, time;
    int currentScore;
    int timeLeft;
    boolean gameStarted, timerIsRunning, newHighscoreAchieved;
    CountDownTimer timer;
    ArrayList<ScoreEntry> highscores = new ArrayList<>(10);
    private NewHighscoreDialogFragment.OnFragmentInteractionListener fragmentListener;
    String playerNameNewHighscore = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_solo);

        //initialize elements
        gameStarted = false;

        highscores = (ArrayList) getIntent().getSerializableExtra("highscores");

        tapTarget = findViewById(R.id.tapTarget);
        tapTarget.setOnClickListener(tapTargetListener);

        score = findViewById(R.id.score);

        time = findViewById(R.id.timeLeft);
        timeLeft = 10;

        //reset elements
        setScore(0);
        setTime(timeLeft);
    }

    //actual game method
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

    //check for new highscore
    private void checkForNewHighscore() {
        //if current highscore list is empty we insert the current score
        if(getLowestHighscoreIndex(highscores) == -1) {
            openPlayerNameDialog();
        }
        //if highscore list has got less than 10 entries we add the current score and sort the list.
        else if(getLowestHighscoreIndex(highscores) == -2) {
            openPlayerNameDialog();
            String playerName = playerNameNewHighscore;
            highscores.add(new ScoreEntry(playerName, currentScore));
            Collections.sort(highscores, Comparator.comparing(ScoreEntry::getScore));
            newHighscoreAchieved = true;
        }
        //if not, we want to replace the lowest highscore with the new one and sort the list. (with insertNewHighscore)
        else {
            int lowestHighscoreIndex = getLowestHighscoreIndex(highscores);
            int lowestHighscore = highscores.get(lowestHighscoreIndex).getScore();

            if(currentScore > lowestHighscore) {
                //get name of player
                openPlayerNameDialog();
                String playerName = playerNameNewHighscore;
                insertNewHighscore(playerName, lowestHighscoreIndex);
                newHighscoreAchieved = true;
            }
        }
    }

    private void insertNewHighscore(String playerName, int lowestHighscoreindex) {
        highscores.remove(lowestHighscoreindex);
        highscores.add(new ScoreEntry(playerName, currentScore));

        Collections.sort(highscores, Comparator.comparing(ScoreEntry::getScore));
    }

    private int getLowestHighscoreIndex(ArrayList<ScoreEntry> scoreList) {
        if(!scoreList.isEmpty()) {
            if(scoreList.size() == 10) {
                int lowestHighscore = scoreList.get(0).getScore();
                int lowestHighscoreIndex = 0;

                for(int i = 0; i < scoreList.size(); i++) {
                    if(scoreList.get(i).getScore() < lowestHighscore) {
                        lowestHighscore = scoreList.get(i).getScore();
                        lowestHighscoreIndex = i;
                    }
                }

                return lowestHighscoreIndex;
            } else {
                //returns -2 if there are less than 10 highscores
                return -2;
            }
        } else {
            //returns -1 if there is no highscore yet
            return -1;
        }
    }


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

                //check for a new highscore and add it to highscore data
                checkForNewHighscore();

            }
        }.start();
        timerIsRunning = true;
    }

    @Override
    public void onBackPressed() {
        Log.i("highscore", highscores.toString());
        Log.i("bla", "bla");
        if(timerIsRunning) {
            timer.cancel();
        }
        if(newHighscoreAchieved) {
            endActivity(highscores);
        } else {
            endActivity();
        }
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

    //if we got a new highscore
    private void endActivity(ArrayList highscoreList) {
        Intent backIntent = new Intent();
        backIntent.putExtra("newHighscores", highscoreList);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    //if we don't have a new highscore
    private void endActivity() {
        Intent backIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, backIntent);
        finish();
    }

    //open Dialog Fragment
    public void openPlayerNameDialog() {
        NewHighscoreDialogFragment nameDialog = new NewHighscoreDialogFragment();
        nameDialog.show(getSupportFragmentManager(), "nameDialog");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getPlayerName(String name) {
        playerNameNewHighscore = name;
        String playerName = playerNameNewHighscore;
        highscores.add(new ScoreEntry(playerName, currentScore));
        newHighscoreAchieved = true;
    }


}
