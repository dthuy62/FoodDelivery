package com.example.ddth.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddth.Common.Common;
import com.example.ddth.Model.Requests;
import com.example.ddth.R;
import com.example.ddth.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class ManageOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Requests, OrderViewHolder> adapter;

    private FirebaseDatabase database;
    private DatabaseReference request;

    private MaterialSpinner spinner,shipperSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        recyclerView = findViewById(R.id.recycler_manage_order);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders();

    }

    private void loadOrders() {
        FirebaseRecyclerOptions<Requests> options = new FirebaseRecyclerOptions.Builder<Requests>()
                .setQuery(request, Requests.class).build();

        adapter = new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(options)
        {

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_order, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Requests model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStt.setText(ConvertCodeToStatus(model.getStatus()));
                holder.txtOrderAddress.setText(model.getAddress());
                holder.txtOrderPhone.setText(model.getPhone());
                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog(adapter.getRef(position).getKey(),adapter.getItem(position));
                    }
                });
                holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(adapter.getRef(position).getKey());
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
        request.keepSynced(true);
    }

    private String ConvertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Đã đặt hàng";
        else if (status.equals("1"))
            return "Đã giao";
        else
            return "Đang giao";
    }

    private void deleteOrder(String key)
    {
        request.child(key).removeValue();
        adapter.notifyDataSetChanged();
    }

    private void showUpdateDialog(String key, final Requests item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ManageOrderActivity.this);
        builder.setTitle("Cập nhật đơn hàng");

        LayoutInflater inflater = this.getLayoutInflater();
        final  View view = inflater.inflate(R.layout.update_order_layout, null);
        spinner  = view.findViewById(R.id.statusSpinner);
        spinner.setItems("Đã đặt hàng", "Đã giao", "Đang giao");


        shipperSpinner = view.findViewById(R.id.shipperSpinner);
        // get All shipper
        List<String> shipperList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Common.SHIPPERS_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren())
                {
                    shipperList.add(sn.getKey());
                    shipperSpinner.setItems(shipperList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        builder.setView(view);

        final String localKey = key;
        builder.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                if(item.getStatus().equals("2"))
                {
                    FirebaseDatabase.getInstance().getReference(Common.ORDER_NEED_SHIPPER_TABLE)
                            .child(shipperSpinner.getItems().get(shipperSpinner.getSelectedIndex()).toString())
                            .child(localKey)
                            .setValue(item);

                    request.child(localKey).setValue(item);
                    Log.d("Local", "onClick: "+localKey); // localkey == key cua Table Requests
                    adapter.notifyDataSetChanged();
                    sendOrderRequestToShipper(shipperSpinner.getItems().get(shipperSpinner.getSelectedIndex()).toString(), item);
                }
                else
                {
                    request.child(localKey).setValue(item);
                    adapter.notifyDataSetChanged();

                }

            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void sendOrderRequestToShipper(String shipperPhone, Requests item) {
    }
}