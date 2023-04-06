package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url="https://opentdb.com/api.php?amount=10&category=11&type=boolean";
    private RequestQueue mRequestQueue;
    private TextView mTextQuestion;
    private TextView genre;
    private TextView difficulty;
    private Button btnTrue;
    private Button btnFalse;
    private ProgressBar mProgressBar;
    private TextView mStats;
    private int userScore;
    private TextView totalQuestions;
    final int USER_PROGRESS = (int) Math.ceil(100.0 / (double) 10); //increments progress bar by USER_PROGRESS each time answered
    private int mQuestionIndex;
    private String mQuizQuestion;
    private List<QuizQuestion> questionCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue= VolleySingleton.getInstance().getRequestQueue();
        questionCollection=new ArrayList<QuizQuestion>();
        JsonObjectRequest filmsJsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results=response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject question=results.getJSONObject(i);
                        String questionText=question.getString("question");
                        boolean answer=question.getBoolean("correct_answer");
                        String genre=question.getString("category");
                        String difficulty=question.getString("difficulty");
                        QuizQuestion myQuestion=new QuizQuestion(questionText, answer, genre, difficulty);
                        questionCollection.add(myQuestion);
                    }
                }catch (JSONException e){
                    Log.e("error", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(filmsJsonObjectRequest);


        genre = (TextView) findViewById(R.id.genre);
        difficulty = (TextView) findViewById(R.id.difficulty);
        mTextQuestion = (TextView) findViewById(R.id.question);
        btnTrue = (Button) findViewById(R.id.trueButton);
        btnFalse = (Button) findViewById(R.id.falseButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mStats = (TextView) findViewById(R.id.remainingQuestion);
        totalQuestions = (TextView) findViewById(R.id.totalQuestion);
        totalQuestions.setText("/"+questionCollection.size());

        if (savedInstanceState != null) {
            userScore = savedInstanceState.getInt("SCORE");
            mQuestionIndex = savedInstanceState.getInt("CurrentIndex");
            mStats.setText(userScore + "");
        } else {
            userScore = 0;
            mQuestionIndex = 0;
        }
//        mQuizQuestion = questionCollection.get(mQuestionIndex).getmQuestion();
//        mTextQuestion.setText(mQuizQuestion);
//        mProgressBar.incrementProgressBy(USER_PROGRESS);
//        mStats.setText(Integer.toString(userScore));
//        difficulty.setText("Difficulty: "+questionCollection.get(mQuestionIndex).getDifficulty());
//        genre.setText("Genre: "+questionCollection.get(mQuestionIndex).getGenre());




        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAnswer(true);
                changeQuestionOnButtonClick();
            }
        });
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAnswer(false);
                changeQuestionOnButtonClick();
            }
        });

    }

    private void changeQuestionOnButtonClick() {
        mQuestionIndex = (mQuestionIndex + 1) % questionCollection.size();

        if (mQuestionIndex == 0) {
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(MainActivity.this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The Quiz is finished");
            quizAlert.setMessage("Your score is: " + userScore + "/" + questionCollection.size());
            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }

        mQuizQuestion = questionCollection.get(mQuestionIndex).getmQuestion();
        mTextQuestion.setText(mQuizQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        mStats.setText(Integer.toString(userScore));
        difficulty.setText("Difficulty: "+questionCollection.get(mQuestionIndex).getDifficulty());
        genre.setText("Genre: "+questionCollection.get(mQuestionIndex).getGenre());
    }

    private void evaluateAnswer(boolean userAnswer) {
        boolean correctAnswer = questionCollection.get(mQuestionIndex).ismAnswer();
        if (correctAnswer == userAnswer) {
            Toast.makeText(getApplicationContext(), R.string.CorrectAnswer, Toast.LENGTH_SHORT).show();
            userScore = userScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.WrongAnswer, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SCORE", userScore);
        outState.putInt("CurrentIndex", mQuestionIndex);
    }
}