package com.example.ddth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.DetailsActivity;
import com.example.ddth.Model.PopularFood;
import com.example.ddth.R;

import java.util.List;

public class PopularFoodApdater extends RecyclerView.Adapter<PopularFoodApdater.PopularFoodViewHolder> {

    Context context;
    List<PopularFood> popularFoodList;

    public PopularFoodApdater(Context context, List<PopularFood> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.popular_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {
        holder.foodImage.setImageResource(popularFoodList.get(position).getImgUrl());
        holder.name.setText(popularFoodList.get(position).getName());
        holder.price.setText(popularFoodList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return popularFoodList.size();
    }

    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView price, name;
        public PopularFoodViewHolder(@NonNull View itemView)
        {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.food_price);
            name = itemView.findViewById(R.id.food_name);
        }
    }
}
