package com.example.itap;

public class ScoreEntry {
    private int score;
    private String name;

    public ScoreEntry() {
        score = 0;
        name = null;
    }

    public ScoreEntry(String name, int score) {
        this.score = score;
        this.name = name;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}
