package com.example.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportActionModeWrapper;

public class SplachScren extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        Handler h = new Handler();

        Runnable intro = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplachScren.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        h.postDelayed(intro, 3000);

    }
}
