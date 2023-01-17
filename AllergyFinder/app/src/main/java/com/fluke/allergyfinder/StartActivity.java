package com.fluke.allergyfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;

public class StartActivity extends AppCompatActivity {

    private AppPreferences appPreferences;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        appPreferences = new AppPreferences(StartActivity.this);
        if (appPreferences.getBooleanPrefs(AppPreferences.KEY_SAVE_USER)) {
            if (appPreferences.getStringPrefs(AppPreferences.KEY_TYPE).equals("admin")) {
                Intent intent = new Intent(getApplicationContext(), MainAdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from", "splashscreen");
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from", "splashscreen");
                startActivity(intent);
                finish();
            }

        } else {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000); // ถ้าใช้งานแอปครั้งแรก จะต้องรอ 4 วิ แล้วไปหน้า FirstActivity
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }).start();
        }


    }
}