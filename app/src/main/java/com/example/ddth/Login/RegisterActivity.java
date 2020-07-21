package com.example.ddth.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ddth.MainActivity;
import com.example.ddth.Model.Users;
import com.example.ddth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText txt_email, txt_password;
    private Button register;

    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



            txt_email = findViewById(R.id.gmail);
            txt_password = findViewById(R.id.password);
            register = findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog.setMessage("Đang đăng ký...");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();

                // validate
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    // set error
                      txt_email.setError("Email không hợp lệ");
                      txt_email.setFocusable(true);
                }
                else if(password.length()<6){
                    txt_password.setError("Mật khẩu quá ngắn");
                    txt_password.setFocusable(true);
                }
                else {
                    registerUser(email, password);
                }
            }
        });



    }
    private void registerUser(String email, String password){
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,  new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){



                        progressDialog.dismiss();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // get user email and id from auth
                        String email = user.getEmail();
                        String uid = user.getUid();
                        // using Hashmap
                        Users users = new Users(
                                email,
                                "",
                                user.getDisplayName(),
                                "",
                                user.getUid()
                        );

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("Users");
                        databaseReference.child(uid).setValue(users);

                        Toast.makeText(RegisterActivity.this, "Đang đăng ký...\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();

                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Lỗi xác thực",Toast.LENGTH_SHORT).show();
                    }

                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    }
