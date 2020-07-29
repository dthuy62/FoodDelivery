package com.example.ddth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ddth.DetailsActivity;
import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Food;
import com.example.ddth.R;


import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Food> foodList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public FoodAdapter(List<Food> foodList, ItemClickListener itemClickListener){
        this.foodList = foodList;
        this.itemClickListener = itemClickListener;
    }

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.danang_food_row_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {



        Food food = foodList.get(position);
//        holder.imageFavorites.setImageResource(foodList.get(position));
        holder.imageFood.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.txtnameFood.setText(food.getNamefood());
        holder.txtPrice.setText(String.valueOf(food.getPrice()));
        String uri = food.getImg();
        Glide.with(holder.itemView.getContext()).load(uri).into(holder.imageFood);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra("FoodId", foodList.get(position).getId());
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return foodList.size();
    }



    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtnameFood,txtPrice;
        public ImageView imageFood, imageRatting, imageFavorites;
        public CardView cardView;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            txtnameFood = itemView.findViewById(R.id.txt_name_food);
            txtPrice = itemView.findViewById(R.id.txt_price_food);
            imageFood =itemView.findViewById(R.id.image_food);
            imageFavorites = itemView.findViewById(R.id.fav);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

}
