package com.example.ddth.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Category;
import com.example.ddth.Model.Food;
import com.example.ddth.R;
import com.example.ddth.ViewHolder.CategoryViewHolder;
import com.example.ddth.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView, recyclerViewFood;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference category;
    private String categoryId = "";
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> Categoryadapter;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> Foodadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.category);
        recyclerViewFood = view.findViewById(R.id.danang_recyclerView);
        loadFood(categoryId);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCategory();
//      loadFood(categoryId);
    }

    private void loadCategory() {
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Categories");

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class).build();

        Categoryadapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position, @NonNull final Category model) {
                holder.txtCategoryName.setText(model.getName());
//                Log.d("Debug", "onBindViewHolder: " + model.getName());

                holder.setItemClickListener((view, position1, isLongClick) -> {
                    // get categoryID send to fragment
                    categoryId = model.getId();
                    Log.i("TAG", "onClick: " + categoryId);
                    loadFood(categoryId);
                });

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_item, parent, false);
                return new CategoryViewHolder(view);
            }
        };
        recyclerView.setAdapter(Categoryadapter);
        Categoryadapter.startListening();
        category.keepSynced(true);
    }

    private void loadFood(String categoryId) {
        database = FirebaseDatabase.getInstance();
        Query foodList;
        if (categoryId != null){
            foodList = database.getReference("Food").equalTo(categoryId, "IdCategory");
        }
        else
        { foodList = database.getReference("Food");}
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFood.setLayoutManager(layoutManager);
        foodList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("cac", "onDataChange: " + dataSnapshot.getValue(Food.class).getIdCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foodList, Food.class).build();
        Foodadapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.danang_food_row_item, parent, false);
                return new  FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.txtnameFood.setText(model.getNamefood());
                holder.txtPrice.setText(model.getPrice());
                Glide.with(getActivity()).load(model.getImg()).into(holder.imageFood);
                final Food local = model;
                Log.e("Loi", "NameFood: " + model.getNamefood());
            }
        };
        recyclerViewFood.setAdapter(Foodadapter);
        Foodadapter.startListening();
        Foodadapter.notifyDataSetChanged();
        foodList.keepSynced(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadCategory();
        loadFood(categoryId);
    }
}