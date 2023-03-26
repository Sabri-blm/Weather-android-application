package com.example.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportActionModeWrapper;

public class SplachScren extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        //so that the splash screen goes full screen (up to the notification bar in your phone)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // we create a handler
        Handler h = new Handler();

        // we create a runnable to put in the handler
        Runnable intro = new Runnable() {
            @Override
            public void run() {
                // now we create an intent to take us to the main activity
                Intent intent = new Intent(SplachScren.this, MainActivity.class);
                startActivity(intent);
                // and we put finish so that we can't back to the splash screen once we are in the app
                finish();
            }
        };

        // we execute the handler
        h.postDelayed(intro, 3000);

    }
}
