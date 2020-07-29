package com.example.ddth.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Model.Order;
import com.example.ddth.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    List<Order> myOrders;
    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_layout, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        Order order = myOrders.get(position);
        holder.name.setText(String.format("Ten :  %s",order.getProductName()));
        holder.quantity.setText(Integer.parseInt("Số lượng : %d",order.getQuantity()));
        holder.price.setText(Integer.parseInt("Giá : %d",order.getPrice()));
        holder.discount.setText(String.format("Mã giảm giá : %s", order.getDiscount()));
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView name, quantity, price, discount;

        public OrderDetailViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.txt_order_name);
            quantity = itemView.findViewById(R.id.txt_order_quantity);
            price = itemView.findViewById(R.id.txt_order_price);
            discount = itemView.findViewById(R.id.txt_order_discount);
        }
    }
}
