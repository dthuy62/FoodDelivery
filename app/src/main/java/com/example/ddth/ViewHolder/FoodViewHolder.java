package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtnameFood,txtPrice;
    public ImageView imageFood, imageRatting, imageFavorites;

    public CardView cardView;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

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
