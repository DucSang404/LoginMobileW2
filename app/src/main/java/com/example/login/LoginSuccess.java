package com.example.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSuccess extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);

        String username = getIntent().getStringExtra("username");

        TextView textView = findViewById(R.id.tvLoginSuccess);
        textView.setText("Hello, " + username);
    }
}

