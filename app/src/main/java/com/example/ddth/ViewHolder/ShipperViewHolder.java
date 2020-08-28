package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.R;

public class ShipperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView shipper_name, shipper_phone;
    public Button btnEdit, btnRemove;
    private ItemClickListener itemClickListener;
    public ShipperViewHolder(@NonNull View itemView) {
        super(itemView);
        shipper_name = itemView.findViewById(R.id.txt_shipper_name);
        shipper_phone = itemView.findViewById(R.id.txt_shipper_phone);

        btnEdit = itemView.findViewById(R.id.update);
        btnRemove = itemView.findViewById(R.id.delete);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
