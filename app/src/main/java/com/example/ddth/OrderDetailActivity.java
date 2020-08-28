package com.example.ddth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView order_id, order_phone, order_address, order_total;
    String order_id_value = "";
    RecyclerView lstFoods;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);
        order_id = findViewById(R.id.txt_order_id);
        order_phone = findViewById(R.id.txt_order_phone);
        order_address = findViewById(R.id.txt_shipper_phone);
        order_total  = findViewById(R.id.total);
    }
}
