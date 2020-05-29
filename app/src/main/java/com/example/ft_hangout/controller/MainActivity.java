package com.example.ft_hangout.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ft_hangout.Model.User;
import com.example.ft_hangout.R;

public class MainActivity extends AppCompatActivity {
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mDeleteButton;
    private User mUser = new User();
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private int mScore;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK)
            mScore += data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
        editor.putInt("score", mScore);
        editor.apply();
        ft_setup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity::onCreate()");

        mPreferences = getPreferences(MODE_PRIVATE);
        editor = mPreferences.edit();

        mScore = mPreferences.getInt("score", 0);
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mDeleteButton = (Button) findViewById(R.id.activity_main_delete_btn);

        ft_setup();
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().commit();
                mScore = 0;
                mNameInput.setText("");
                mGreetingText.setText("Hello to Quizzland!");
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setFirstName(mNameInput.getText().toString());
                editor.putString("firstname", mUser.getFirstName());
                editor.apply();
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });


        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void ft_setup()
    {
        mUser.setFirstName(mPreferences.getString("firstname", null));
        if (mUser.getFirstName() != null)
        {
            mGreetingText.setText("Welcome back " + mUser.getFirstName()+
                    "!\n Your total score was " + mScore + ", can you do better?");
            mNameInput.setText(mUser.getFirstName());
            mNameInput.requestFocus();
            mPlayButton.setEnabled(true);
        }
        else
            mPlayButton.setEnabled(false);
    };
}
