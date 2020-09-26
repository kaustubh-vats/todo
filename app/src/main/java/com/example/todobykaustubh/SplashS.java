package com.example.todobykaustubh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SplashS extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2000;
    TextView txt;
    TextView txt2;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_s);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent MainIntend = new Intent(SplashS.this, MainActivity.class);
                startActivity(MainIntend);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
    @Override
    protected void onStart() {
        super.onStart();
        txt = findViewById(R.id.textView6);
        imageView = findViewById(R.id.imageView3);
        txt2 = findViewById(R.id.textView7);
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            txt.setText("Good Morning");
            Toast.makeText(this, "Let's Plan Your day", Toast.LENGTH_LONG).show();
        } else if (hour < 13) {
            txt.setText("Guten Tag");
        } else if (hour < 16) {
            txt.setText("Good Afternoon");
        } else if (hour < 21) {
            txt.setText("Good Evening");
        } else {
            txt.setText("Good Night");
            Toast.makeText(this, "Plan Your next day", Toast.LENGTH_LONG).show();
        }
        SharedPreferences getshared = getSharedPreferences("info", MODE_PRIVATE);
        String username = getshared.getString("username", null);
        String image = getshared.getString("image", null);
        if (username != null) {
            txt2.setText(username);
        }
        if (image != null){
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), decodedByte);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        SharedPreferences getsharedswitch = getSharedPreferences("nightMode",MODE_PRIVATE);
        int anInt=getsharedswitch.getInt("is night",-1);
        int nightModeFlags=getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        PowerManager powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPreferences shrd = getSharedPreferences("nightMode",MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putInt("is night",-1);
            editor.apply();
        }
        else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP && powerManager.isPowerSaveMode())
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPreferences shrd = getSharedPreferences("nightMode",MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putInt("is night",-1);
            editor.apply();
        }
        else if(anInt==1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(anInt==0){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}