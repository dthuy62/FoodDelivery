package com.example.ddth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ddth.Adapter.FoodAdapter;
import com.example.ddth.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private String search = "";
    private RecyclerView recyclerSearch;
    private DatabaseReference ref;
    private RecyclerView.LayoutManager layoutManager;
    private FoodAdapter foodAdapter;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerSearch = findViewById(R.id.recyclerViewSearch);
        txt_result = findViewById(R.id.result);


        layoutManager = new LinearLayoutManager(this);
        recyclerSearch.setLayoutManager(layoutManager);
        if(getIntent() != null)
            search = getIntent().getStringExtra("search");
            txt_result.setText(search);

        loadSearch();
    }

    private void loadSearch() {
        Query query = FirebaseDatabase.getInstance().getReference("Food").orderByChild("Namefood").equalTo(search);
        Log.d("Thôi thôi","mày đửng  "+query.toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Food> foodArrayList = new ArrayList<>();
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    Food food = sn.getValue(Food.class);
                    Log.d("a","a "+food.getNamefood());
                    foodArrayList.add(food);

                }
                foodAdapter = new FoodAdapter(SearchActivity.this, foodArrayList);
                foodAdapter.notifyDataSetChanged();
                recyclerSearch.setAdapter(foodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}