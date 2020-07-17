package com.example.ddth.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ddth.Adapter.FoodAdapter;
import com.example.ddth.DetailsActivity;
import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Category;
import com.example.ddth.Model.Food;
import com.example.ddth.R;
import com.example.ddth.ViewHolder.CategoryViewHolder;
import com.example.ddth.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements ItemClickListener {
    private RecyclerView recyclerView, recyclerViewFood;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference category;
    private TextView txtTitle;
        private FirebaseRecyclerAdapter<Category, CategoryViewHolder> Categoryadapter;
        private FoodAdapter foodAdapter;
        private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    private ArrayList<Food> foodArrayListDetail = new ArrayList<>();
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_home, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            recyclerView = view.findViewById(R.id.category);
            loadCategory();




            recyclerViewFood = view.findViewById(R.id.danang_recyclerView);
            recyclerViewFood.setLayoutManager(new LinearLayoutManager(requireContext()));

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Food"), Food.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {

                holder.txtnameFood.setText(model.getNamefood());
                holder.txtPrice.setText(model.getPrice());
                Glide.with(getActivity()).load(model.getImg()).into(holder.imageFood);
                holder.imageFood.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
                holder.cardView.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
                holder.setItemClickListener((view1, position1, isLongClick) -> {
                    Intent intent = new Intent(requireContext(), DetailsActivity.class);
                    intent.putExtra("FoodId", adapter.getRef(position1).getKey());
                    Log.e("FoodId", ""+adapter.getRef(position1).getKey());
                    startActivity(intent);
                });
            }


            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.danang_food_row_item, parent, false));
            }
        };
        adapter.startListening();
        recyclerViewFood.setAdapter(adapter);
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
                Log.d("Debug", "onBindViewHolder: " + model.getName());

                // set onclick item category
                holder.setItemClickListener((view, position1, isLongClick) -> {

                    if(model.getId().equals("01")){
                        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                                .setQuery(FirebaseDatabase.getInstance().getReference("Food"), Food.class)
                                .build();

                        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {

                                holder.txtnameFood.setText(model.getNamefood());
                                holder.txtPrice.setText(model.getPrice());
                                Glide.with(getActivity()).load(model.getImg()).into(holder.imageFood);

                                holder.cardView.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
                                holder.imageFood.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));

                                holder.setItemClickListener((view1, position2, isLongClick1) -> {
                                    Intent intent = new Intent(requireContext(), DetailsActivity.class);
                                    intent.putExtra("FoodId",adapter.getRef(position2).getKey());
                                    startActivity(intent);

                                });
                            }

                            @NonNull
                            @Override
                            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                return new FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.danang_food_row_item, parent, false));
                            }
                        };
                        adapter.startListening();
                        recyclerViewFood.setAdapter(adapter);
                    }
                    else
                        {
                        Log.i("TAG","onClick" +model.getId());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Food> foodArrayList = new ArrayList<>();
                                for(DataSnapshot sn: snapshot.getChildren())
                                {
                                    if(sn.child("IdCategory").getValue().equals(model.getId())) {

                                        Food food = sn.getValue(Food.class);
                                        Log.d("IdCategory", food.getIdCategory() + "");
                                        foodArrayList.add(food);
                                    }

                                }



                                foodAdapter = new FoodAdapter(getContext(),foodArrayList);
                                recyclerViewFood.setAdapter(foodAdapter);
                                foodAdapter.notifyDataSetChanged();
                                Log.d("sizeAdapter",foodAdapter.getItemCount()+"");

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

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

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }
}