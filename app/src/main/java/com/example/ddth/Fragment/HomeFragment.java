package com.example.ddth.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Adapter.FoodAdapter;
import com.example.ddth.CartActivity;
import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Category;
import com.example.ddth.Model.Food;
import com.example.ddth.R;
import com.example.ddth.SearchActivity;
import com.example.ddth.ViewHolder.CategoryViewHolder;
import com.example.ddth.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ItemClickListener {
    // search

    private RecyclerView recyclerView, recyclerViewFood;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference category;
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> Categoryadapter;
    private FoodAdapter foodAdapter;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    private FloatingActionButton fab;
    private ArrayList<Food> foodArrayListDetail = new ArrayList<>();
    private String categoryId = "";
    private EditText editTextSearch;
    private ImageView ImageSearch;
    private String search = "";
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
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CartActivity.class);
            startActivity(intent);
        });

        editTextSearch = view.findViewById(R.id.edit_search);
        ImageSearch = view.findViewById(R.id.search_button);
        ImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SearchActivity.class);
                search = editTextSearch.getText().toString();
                intent.putExtra("search", search);
                startActivity(intent);
                Log.d("úi xời","nàm thao  "+search);
            }
        });
//        editTextSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                search = editTextSearch.getText().toString();
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
//                ref.orderByChild("Namefood").equals(search);
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        ArrayList<Food> arrayList = new ArrayList<>();
//                        for (DataSnapshot sn : snapshot.getChildren())
//                        {
//                             Food food = sn.getValue(Food.class);
//                             arrayList.add(food);
//                        }
//                         foodAdapter = new FoodAdapter(requireContext(), arrayList);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
////                startActivity(new Intent(requireContext(), SearchActivity.class));
//            }
//        });
        recyclerViewFood = view.findViewById(R.id.danang_recyclerView);

//        editTextSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                search = s.toString();
//                if (!search.trim().isEmpty()) {
//                    updateList(search);
//                }
//            }
//        });

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(requireContext()));

//        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("Food"), Food.class)
//                .build();
//
//        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
//
//                holder.txtnameFood.setText(model.getNamefood());
//                holder.txtPrice.setText(String.valueOf(model.getPrice()));
//                Glide.with(requireActivity()).load(model.getImg()).into(holder.imageFood);
//                holder.imageFood.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
//                holder.cardView.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
//                holder.setItemClickListener((view1, position1, isLongClick) -> {
//                    Intent intent = new Intent(requireContext(), DetailsActivity.class);
//                    intent.putExtra("FoodId", adapter.getRef(position1).getKey());
//                    Log.e("FoodId", ""+adapter.getRef(position1).getKey());
//                    startActivity(intent);
//                });
//            }
//
//
//            @NonNull
//            @Override
//            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.danang_food_row_item, parent, false));
//            }
//        };
        updateList("");
//        foodAdapter.startListening();

    }

//    private void loadSuggest() {
//            foodList.orderByChild("IdCategory".equals(categoryId))
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot sn : snapshot.getChildren())
//                            {
//                                Food item = sn.getValue(Food.class);
//                                suggestList.add(item.getNamefood());
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//    }

    private void updateList(String search) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Food> foodArrayList = new ArrayList<>();
                for (DataSnapshot sn : snapshot.getChildren()) {


                    Food food = sn.getValue(Food.class);


                        foodArrayList.add(food);

                }
                foodAdapter = new FoodAdapter(requireContext(), foodArrayList);
                foodAdapter.notifyDataSetChanged();
                recyclerViewFood.setAdapter(foodAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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


                    if (model.getId().equals("01")) {
//                        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
//                                .setQuery(FirebaseDatabase.getInstance().getReference("Food"), Food.class)
//                                .build();

//                        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
//                            @Override
//                            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
//
//                                holder.txtnameFood.setText(model.getNamefood());
//                                holder.txtPrice.setText(Integer.toString(model.getPrice()));
//                                Glide.with(getActivity()).load(model.getImg()).into(holder.imageFood);
//
//                                holder.cardView.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
//                                holder.imageFood.setAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_transition_animation));
//
//                                holder.setItemClickListener((view1, position2, isLongClick1) -> {
//                                    Intent intent = new Intent(requireContext(), DetailsActivity.class);
//                                    intent.putExtra("FoodId",adapter.getRef(position2).getKey());
//                                    startActivity(intent);
//
//                                });
//                            }
//
//                            @NonNull
//                            @Override
//                            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                                return new FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.danang_food_row_item, parent, false));
//                            }
//                        };
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Food> foodArrayList = new ArrayList<>();
                                for (DataSnapshot sn : snapshot.getChildren()) {
                                    Food food = sn.getValue(Food.class);

                                    foodArrayList.add(food);
                                }

                                foodAdapter = new FoodAdapter(requireContext(), foodArrayList);
                                foodAdapter.notifyDataSetChanged();
                                recyclerViewFood.setAdapter(foodAdapter);

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
//                        foodAdapter.startListening();

                    } else {
                        Log.i("TAG", "onClick" + model.getId());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Food> foodArrayList = new ArrayList<>();
                                for (DataSnapshot sn : snapshot.getChildren()) {
                                    if (sn.child("IdCategory").getValue().equals(model.getId())) {

                                        Food food = sn.getValue(Food.class);
                                        Log.d("IdCategory", food.getIdCategory() + "");
                                        foodArrayList.add(food);
                                    }

                                }


                                foodAdapter = new FoodAdapter(getContext(), foodArrayList);
                                recyclerViewFood.setAdapter(foodAdapter);
                                foodAdapter.notifyDataSetChanged();
                                Log.d("sizeAdapter", foodAdapter.getItemCount() + "");

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
        Categoryadapter.notifyDataSetChanged();
        category.keepSynced(true);
    }



    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }
}