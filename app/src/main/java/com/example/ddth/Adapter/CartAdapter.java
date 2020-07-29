package com.example.ddth.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ddth.CartActivity;
import com.example.ddth.Common.Common;
import com.example.ddth.Database.Database;
import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Order;
import com.example.ddth.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Order> listData = new ArrayList<>();
    private CartActivity cart;
    private ItemClickListener itemClickListener;
    public static final String TAG = "CartAdapter";

    public CartAdapter(List<Order> listData, CartActivity cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View view = inflater.inflate(R.layout.item_shopping_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.BLUE);
//        holder.imageFoodOrder.setImageDrawable(drawable);
        holder.btn_quantity.setNumber(""+listData.get(position).getQuantity());
        holder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = listData.get(position);
                order.setQuantity(Integer.valueOf(newValue));
                new Database(cart).updateCart(order);

                // update text total
                int total = 0;
                List<Order> orderList = new Database(cart).getCarts();
                for(Order item:orderList)
                total += ((order.getPrice())) * ((item.getQuantity()));

                cart.txtTotalPrice.setText(total + "");
            }
        });
//        Glide.with(holder.itemView.getContext()).load(listData.get(position)).into(holder.imageFoodOrder);

        int price = 0;
//           Locale locale = new Locale("vi","VN");
//           NumberFormat format = NumberFormat.getCurrencyInstance(locale);
             price = ((listData.get(position).getPrice()))*((listData.get(position).getQuantity()));
            Log.d(TAG, "onBindViewHolder: " + listData.get(position).getPrice());
            Log.d(TAG, "onBindViewHolder: " + listData.get(position).getQuantity());
            holder.txtPriceFoodOrder.setText(price+"");

        holder.txtNameFoodOrder.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public TextView txtNameFoodOrder, txtPriceFoodOrder;
        public ImageView imageFoodOrder;
        public ElegantNumberButton btn_quantity;

        public void setTxtNameFoodOrder(TextView txtNameFoodOrder) {
            this.txtNameFoodOrder = txtNameFoodOrder;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFoodOrder = itemView.findViewById(R.id.txt_name_food_order);
            txtPriceFoodOrder = itemView.findViewById(R.id.txt_price_food_order);
            btn_quantity = itemView.findViewById(R.id.btn_quantity);

//            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("gì ai biết gì đâu");
            menu.add(0,0,getAdapterPosition(), Common.DELETE);
        }
    }
}
