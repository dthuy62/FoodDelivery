package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView txtnameFood,txtPrice;
    public ImageView imageFood, imageRatting;
    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        txtnameFood = itemView.findViewById(R.id.name_food);
        txtPrice = itemView.findViewById(R.id.txt_price);
        imageFood =itemView.findViewById(R.id.food_image);

    }

}
