package com.example.ddth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Requests;
import com.example.ddth.Model.Users;
import com.example.ddth.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Requests> requestsList = new ArrayList<>();
    private Context context;
    private ItemClickListener itemClickListener;


    public OrdersAdapter(List<Requests> requestsList, Context context) {
        this.requestsList = requestsList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_order_status, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

//        holder.txtOrderId.setText(requestsList.get(position).getKey());
        holder.txtOrderStt.setText(ConvertCodeToStatus(requestsList.get(position).getStatus()));
        holder.txtOrderAddress.setText(requestsList.get(position).getAddress());
        holder.txtOrderPhone.setText(requestsList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtOrderId, txtOrderStt, txtOrderPhone, txtOrderAddress;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txt_order_id);
            txtOrderPhone = itemView.findViewById(R.id.txt_order_phone);
            txtOrderStt = itemView.findViewById(R.id.txt_order_status);
            txtOrderAddress = itemView.findViewById(R.id.txt_order_address);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private String ConvertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Đã đặt hàng";
        else if (status.equals("1"))
            return "Đang giao";
        else
            return "Đã giao";
    }
//    private void getPhone(){
//        String uid;
//        auth = FirebaseAuth.getInstance();
//        uid = auth.getCurrentUser().getUid();
//
//        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users users = snapshot.getValue(Users.class);
//                users.getPhone();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
