package com.example.ddth.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddth.Adapter.FoodAdapter;
import com.example.ddth.Adapter.OrdersAdapter;
import com.example.ddth.Common.Common;
import com.example.ddth.Model.Food;
import com.example.ddth.Model.Requests;
import com.example.ddth.Model.Users;
import com.example.ddth.R;
import com.example.ddth.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class OrderFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private Users users;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerAdapter<Requests, OrderViewHolder> adapter;

    private OrdersAdapter ordersAdapter;
    private List<Requests> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_order, container, false);


        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Requests");

        recyclerView = view.findViewById(R.id.listOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadViewOrder();

//        String uid;
//        FirebaseAuth auth;
//        auth = FirebaseAuth.getInstance();
//        uid = auth.getCurrentUser().getUid();
//
//        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users users = snapshot.getValue(Users.class);
//                loadOrder(users.getPhone());
//
//
////                Log.d("Phone","alo"+users.getPhone());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//            }
//        });
//
//


    }

    private void loadViewOrder(){
        Query ref = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("cac","alo alo  " +FirebaseAuth.getInstance().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Requests> arrayList = new ArrayList<>();
                for (DataSnapshot sn : snapshot.getChildren())
                {
                    Requests requests = sn.getValue(Requests.class);
                    arrayList.add(requests);
                }
                ordersAdapter = new OrdersAdapter(arrayList,getContext());

                ordersAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(ordersAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    private void loadOrder(String phone) {

        FirebaseRecyclerOptions<Requests> options = new FirebaseRecyclerOptions.Builder<Requests>()
                .setQuery(reference.orderByChild("phone").equalTo(phone), Requests.class).build();


        adapter = new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Requests model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStt.setText(ConvertCodeToStatus(model.getStatus()));
                holder.txtOrderAddress.setText(model.getAddress());
                holder.txtOrderPhone.setText(model.getPhone());
                Log.d("","Model"+model.toString());


            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status,parent,false));
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    private String ConvertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Đã đặt hàng";
        else if (status.equals("1"))
            return "Đang giao";
        else
            return "Đã giao";
    }
}
