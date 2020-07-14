package com.example.ddth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ddth.Login.LoginActivity;
import com.example.ddth.Login.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRegister();
            }
        });
    }
    public void openActivityLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openActivityRegister(){
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}