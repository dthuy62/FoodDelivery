package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtCategoryName;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryViewHolder(@NonNull View itemView)
    {
        super(itemView);
        txtCategoryName = itemView.findViewById(R.id.txt_nameCate);
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
