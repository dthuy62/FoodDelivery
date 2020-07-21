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
import com.example.ddth.Model.DanangFood;
import com.example.ddth.R;

import java.util.List;

public class DanangFoodAdapter extends RecyclerView.Adapter<DanangFoodAdapter.DanangFoodViewHolder> {

    Context context;
    List<DanangFood> danangFoodList;

    public DanangFoodAdapter(Context context, List<DanangFood> danangFoodList){
        this.context = context;
        this.danangFoodList = danangFoodList;
    }

    @NonNull
    @Override
    public DanangFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.danang_food_row_item, parent, false);
        return new DanangFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanangFoodViewHolder holder, int position) {
    holder.imageView.setImageResource(danangFoodList.get(position).getImgUrl());
    holder.restaurent.setText(danangFoodList.get(position).getRestaurent_name());
    holder.name.setText(danangFoodList.get(position).getName());
    holder.rating.setText(danangFoodList.get(position).getRating());
    holder.price.setText(danangFoodList.get(position).getPrice());

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
        return danangFoodList.size();
    }
    public static final class DanangFoodViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView restaurent, name, rating, price;
        ImageView img_rating;
        public DanangFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_food_order);
            price  = itemView.findViewById(R.id.txt_price_food_order);
            restaurent = itemView.findViewById(R.id.res_name);
            name = itemView.findViewById(R.id.txt_name_food_order);
            rating = itemView.findViewById(R.id.txt_rating);
            img_rating = itemView.findViewById(R.id.img_rating);
        }
    }
}
