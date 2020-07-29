package com.example.ddth.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ddth.Model.Users;
import com.example.ddth.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActitvity extends AppCompatActivity {
    private TextInputEditText txt_email, txt_password;
    private Button register;

    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Users users;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_email = findViewById(R.id.gmail);
        txt_password = findViewById(R.id.password);
        register = findViewById(R.id.btn_register);
    }
}
