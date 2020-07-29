package com.example.ddth;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Adapter.CommentAdapter;
import com.example.ddth.Common.Common;
import com.example.ddth.Model.Ratting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference ratingTbl;
    private List<Ratting> ratting = new ArrayList<>();

    private String foodId = "";
    private CommentAdapter commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);
        database = FirebaseDatabase.getInstance();
        ratingTbl = database.getReference("Rating");
        recyclerView = findViewById(R.id.recyclerComment);

        layoutManager = new LinearLayoutManager(CommentActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null)
            foodId = getIntent().getStringExtra(Common.INTENT_FOOD_ID);
        Log.d("bố mày", "nhịn mày"+foodId);
        loadComment ();

    }



    private void loadComment() {
        Query ref = FirebaseDatabase.getInstance().getReference("Rating").orderByChild("foodId").equalTo(foodId);
        Log.d("alo", "alo" + foodId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Ratting> arrayList = new ArrayList<>();
                for (DataSnapshot sn : snapshot.getChildren()) {
                    Ratting ratting = sn.getValue(Ratting.class);
                    Log.d("ca", "xao" + ratting.getFoodId());
                    Log.d("bmlr","bomaylatrum"+ratting.getUserphone());
                    arrayList.add(ratting);
                }
                commentAdapter = new CommentAdapter(CommentActivity.this, arrayList);
                commentAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}