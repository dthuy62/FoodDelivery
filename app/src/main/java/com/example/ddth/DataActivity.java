package com.example.ddth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddth.Login.LoginActivity;
import com.example.ddth.Model.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataActivity extends AppCompatActivity {
    private Button btnAdd, btnOpen;
    private EditText nameCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        nameCat = findViewById(R.id.txt_nameCat);
        btnAdd = findViewById(R.id.btnadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_category = nameCat.getText().toString();
                if (txt_category.isEmpty()) {
                    Toast.makeText(DataActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Categories").push();
                    reference.setValue(new Category(reference.getKey(), txt_category));
                }
            }
        });
        btnOpen = findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open();
            }
        });

    }

    public void Open() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}