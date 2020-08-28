package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView txtOrderId, txtOrderStt, txtOrderPhone, txtOrderAddress;
    public Button btnEdit, btnRemove;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderId = itemView.findViewById(R.id.txt_order_id);
        txtOrderStt = itemView.findViewById(R.id.txt_shipper_name);
        txtOrderPhone = itemView.findViewById(R.id.txt_order_phone);
        txtOrderAddress = itemView.findViewById(R.id.txt_shipper_phone);
        btnEdit = itemView.findViewById(R.id.edit_order);
        btnRemove = itemView.findViewById(R.id.delete_order);

    }

}
