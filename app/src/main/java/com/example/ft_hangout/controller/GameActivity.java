package com.example.ft_hangout.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ft_hangout.Model.Question;
import com.example.ft_hangout.Model.QuestionBank;
import com.example.ft_hangout.R;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;
    private int    mAnswerIndex;
    private int     mNbOfQuestions;
    private int     mScore;
    private QuestionBank questionBank;
    private Handler handler = new Handler();
    private Boolean mEnableTouchEvents;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNbOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        questionBank = this.generateQuestions();

        if (savedInstanceState != null)
        {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNbOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }
        else
        {
            mScore = 0;
            mNbOfQuestions = questionBank.getSize();
        }

        mQuestion = (TextView) findViewById(R.id.activity_game_question);
        mQuestion.setOnClickListener(this);
        mAnswer1 = (Button) findViewById(R.id.activity_game_answer1);
        mAnswer1.setTag(0);
        mAnswer1.setOnClickListener(this);
        mAnswer2 = (Button) findViewById(R.id.activity_game_answer2);
        mAnswer2.setTag(1);
        mAnswer2.setOnClickListener(this);
        mAnswer3 = (Button) findViewById(R.id.activity_game_answer3);
        mAnswer3.setTag(2);
        mAnswer3.setOnClickListener(this);
        mAnswer4 = (Button) findViewById(R.id.activity_game_answer4);
        mAnswer4.setTag(3);
        mAnswer4.setOnClickListener(this);

        displayQuestion(questionBank.getQuestion());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void displayQuestion(final Question question)
    {
        mEnableTouchEvents = true;
        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mAnswer4.setText(question.getChoiceList().get(3));
        mAnswer4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mAnswerIndex = question.getAnwserIndex();
    }

    public QuestionBank generateQuestions(){
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
    }
    public void showRightAnswer(int index)
    {
        switch (index)
        {
            case 0:
                mAnswer1.setBackgroundColor(Color.parseColor("#0BBD4E"));
                break;
            case 1:
                mAnswer2.setBackgroundColor(Color.parseColor("#0BBD4E"));
                break;
            case 2:
                mAnswer3.setBackgroundColor(Color.parseColor("#0BBD4E"));
                break;
            case 3:
                mAnswer4.setBackgroundColor(Color.parseColor("#0BBD4E"));
                break;
        }
    }
    @Override
    public void onClick(View v) {
        mEnableTouchEvents = false;
        if (mAnswerIndex == (int)v.getTag())
        {
            v.setBackgroundColor(Color.parseColor("#0BBD4E"));
            mScore++;
        }
        else
        {
            v.setBackgroundColor(Color.parseColor("#FF0000"));
            showRightAnswer(mAnswerIndex);
        }
        if (--mNbOfQuestions != 0)
            handler.postDelayed(new Runnable()
                {@Override public void run() {displayQuestion(questionBank.getQuestion());}
                }, 700);
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }
}

