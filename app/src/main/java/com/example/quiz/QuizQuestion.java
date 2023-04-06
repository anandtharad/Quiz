package com.example.quiz;

public class QuizQuestion {
    private String mQuestion;
    private boolean mAnswer;
    private String genre;
    private String difficulty;
    public QuizQuestion(String mQuestion, boolean mAnswer, String genre, String difficulty){
        this.mQuestion=mQuestion;
        this.mAnswer=mAnswer;
        this.genre=genre;
        this.difficulty=difficulty;
    }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getmQuestion() {
        return mQuestion;
    }
    public boolean ismAnswer() {
        return mAnswer;
    }
    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }
    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
