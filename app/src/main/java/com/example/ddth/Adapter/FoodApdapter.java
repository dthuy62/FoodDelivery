package com.example.ddth.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ddth.Model.Food;
import com.example.ddth.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FoodApdapter extends RecyclerView.Adapter<FoodApdapter.FoodViewHolder> {
    public ArrayList<Food> foodList;
    public Activity context;


    public FoodApdapter(ArrayList<Food> foodList, Activity context) {
        this.foodList = foodList;
        this.context = context;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodApdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.danang_food_row_item, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);
        return foodViewHolder;
    }





    @Override
    public void onBindViewHolder(@NonNull FoodApdapter.FoodViewHolder holder, int position) {
        holder.txtnameFood.setText(foodList.get(position).getNamefood());
        holder.txtPrice.setText(foodList.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return
                foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView txtnameFood,txtPrice;
        private ImageView imageFood;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnameFood = itemView.findViewById(R.id.name_food);
            txtPrice = itemView.findViewById(R.id.food_price);
            imageFood = itemView.findViewById(R.id.food_image);
        }
    }
}
