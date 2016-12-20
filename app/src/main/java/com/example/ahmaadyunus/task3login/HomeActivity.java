package com.example.ahmaadyunus.task3login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    public static SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getPreferences(Context.MODE_PRIVATE);
        if (prefs.getBoolean("auth", true)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_home);
        Button logout = (Button)findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putBoolean("auth", true).commit();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
