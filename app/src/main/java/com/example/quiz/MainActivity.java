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

public class MainActivity extends AppCompatActivity {

    private TextView mTextQuestion;
    private Button btnTrue;
    private Button btnFalse;
    private ProgressBar mProgressBar;
    private TextView mStats;
    private int userScore;
    private QuizModel[] questionCollection=new QuizModel[]{
        new QuizModel(R.string.q1, true),
        new QuizModel(R.string.q2, false),
        new QuizModel(R.string.q3, true),
        new QuizModel(R.string.q4, true),
        new QuizModel(R.string.q5, false),
        new QuizModel(R.string.q6, true),
        new QuizModel(R.string.q7, false),
        new QuizModel(R.string.q8, true),
        new QuizModel(R.string.q9, true),
        new QuizModel(R.string.q10, true),
    };
    final int USER_PROGRESS=(int)Math.ceil(100.0/(double) questionCollection.length); //increments progress bar by USER_PROGRESS each time answered
    private int mQuestionIndex;
    private int mQuizQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTextQuestion=(TextView)findViewById(R.id.question);
        btnTrue=(Button) findViewById(R.id.trueButton);
        btnFalse=(Button) findViewById(R.id.falseButton);
        mProgressBar=(ProgressBar) findViewById(R.id.progressBar);
        mStats=(TextView)findViewById(R.id.remainingQuestion);

        if(savedInstanceState!=null){
            userScore=savedInstanceState.getInt("SCORE");
            mQuestionIndex=savedInstanceState.getInt("CurrentIndex");
            mStats.setText(userScore+"");
        }
        else{
            userScore=0;
            mQuestionIndex=0;
        }

        QuizModel q1=questionCollection[mQuizQuestion];
        mQuizQuestion=q1.getmQuestion();
        mTextQuestion.setText(mQuizQuestion);


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
    private void changeQuestionOnButtonClick(){
        mQuestionIndex=(mQuestionIndex+1)%questionCollection.length;

        if(mQuestionIndex == 0){
            AlertDialog.Builder quizAlert= new AlertDialog.Builder(MainActivity.this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The Quiz is finished");
            quizAlert.setMessage("Your score is: "+userScore+"/"+questionCollection.length);
            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }

        mQuizQuestion=questionCollection[mQuestionIndex].getmQuestion();
        mTextQuestion.setText(mQuizQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        mStats.setText(Integer.toString(userScore));
    }

    private void evaluateAnswer(boolean userAnswer){
        boolean correctAnswer=questionCollection[mQuestionIndex].ismAnswer();
        if(correctAnswer==userAnswer){
            Toast.makeText(getApplicationContext(), R.string.CorrectAnswer, Toast.LENGTH_SHORT).show();
            userScore=userScore+1;
        }
        else{
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