package com.example.ddth.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ddth.Adapter.ShipperAdapter;
import com.example.ddth.Common.Common;
import com.example.ddth.Model.Shippers;
import com.example.ddth.R;
import com.example.ddth.ViewHolder.ShipperViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ManageShipperActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private FirebaseDatabase database;
    private DatabaseReference shippersReference;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private ShipperAdapter shipperAdapter;
    private FirebaseRecyclerAdapter<Shippers, ShipperViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shipper);

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateShipperLayout();
            }
        });

        recyclerView = findViewById(R.id.recycler_shippers);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        shippersReference = database.getReference(Common.SHIPPERS_TABLE);

        loadAllShipper();
    }

    private void loadAllShipper() {
        FirebaseRecyclerOptions<Shippers> allShipper = new FirebaseRecyclerOptions.Builder<Shippers>()
                .setQuery(shippersReference, Shippers.class).build();
       adapter  = new FirebaseRecyclerAdapter<Shippers, ShipperViewHolder>(allShipper) {
            @Override
            protected void onBindViewHolder(@NonNull ShipperViewHolder holder, int position, @NonNull Shippers model) {
                holder.shipper_name.setText(model.getName());
                holder.shipper_phone.setText(model.getPhone());

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditDialog(adapter.getRef(position).getKey(), model);
                    }
                });
                holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeShipper(adapter.getRef(position).getKey());
                    }
                });
            }

            @NonNull
            @Override
            public ShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipper_layout, parent, false);
                return new ShipperViewHolder(view);
            }
        };
       recyclerView.setAdapter(adapter);
       adapter.startListening();
       adapter.notifyDataSetChanged();
       shippersReference.keepSynced(true);
    }

    private void removeShipper(String key) {
        shippersReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ManageShipperActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ManageShipperActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void showEditDialog(String key, Shippers model) {
        AlertDialog.Builder create_shipper_dialog = new AlertDialog.Builder(ManageShipperActivity.this);
        create_shipper_dialog.setTitle("Cập nhật shipper mới");
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_shipper_layout, null);
        TextInputEditText edtName = view.findViewById(R.id.name_shipper);
        TextInputEditText edtPhone = view.findViewById(R.id.phone_shipper);
        TextInputEditText edtPass = view.findViewById(R.id.pass_shipper);

        // set data
        edtName.setText(model.getName());
        edtPass.setText(model.getPassword());
        edtPhone.setText(model.getPhone());

        create_shipper_dialog.setView(view);

        create_shipper_dialog.setPositiveButton("CẬP NHẬT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Map<String, Object> update = new HashMap<>();
                update.put("name",edtName.getText().toString());
                update.put("phone", edtPhone.getText().toString());
                update.put("password", edtPass.getText().toString());

                shippersReference.child(edtPhone.getText().toString()).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManageShipperActivity.this, " Shipper đã cập nhật !", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageShipperActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        create_shipper_dialog.setNegativeButton("HỦY BỎ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        create_shipper_dialog.show();
        adapter.notifyDataSetChanged();
    }

    private void showCreateShipperLayout() {
        AlertDialog.Builder create_shipper_dialog = new AlertDialog.Builder(ManageShipperActivity.this);
        create_shipper_dialog.setTitle("Tạo shipper mới");
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_shipper_layout, null);
        TextInputEditText edtName = view.findViewById(R.id.name_shipper);
        TextInputEditText edtPhone = view.findViewById(R.id.phone_shipper);
        TextInputEditText edtPass = view.findViewById(R.id.pass_shipper);

        create_shipper_dialog.setView(view);

        create_shipper_dialog.setPositiveButton("TẠO MỚI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Shippers shippers = new Shippers();
                shippers.setName(edtName.getText().toString());
                shippers.setPhone(edtPhone.getText().toString());
                shippers.setPassword(edtPass.getText().toString());

                shippersReference.child(edtPhone.getText().toString()).setValue(shippers).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManageShipperActivity.this, " Shipper create!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageShipperActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        create_shipper_dialog.setNegativeButton("HỦY BỎ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        create_shipper_dialog.show();
    }
}