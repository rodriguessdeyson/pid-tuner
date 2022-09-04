package com.rad.pidtuner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashOpening extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.layout_opening);

        // New Handler to start the Menu-Activity and close this Splash-Screen after some seconds.
        int splashDisplayLength = 1500;

        // Starts the handle.
        new Handler().postDelayed(() ->
        {
            // Start the activity after the process.
            Intent startActivity = new Intent(SplashOpening.this, MainActivity.class);
            startActivity(startActivity);
            this.finish();
        }, splashDisplayLength);
    }
}
