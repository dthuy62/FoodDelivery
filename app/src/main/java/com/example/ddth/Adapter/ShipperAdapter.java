package com.example.ddth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Shippers;
import com.example.ddth.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShipperAdapter extends RecyclerView.Adapter<ShipperAdapter.ShipperViewHolder> {
    private List<Shippers> shippersList;
    private Context context;


    public ShipperAdapter(List<Shippers> shippersList, Context context)
    {
        this.shippersList = shippersList;
        this.context = context;
    }
    @NonNull
    @Override
    public ShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shipper_layout, parent, false);
        return new ShipperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperViewHolder holder, int position) {
        holder.shipper_name.setText(shippersList.get(position).getName());
        holder.shipper_phone.setText(shippersList.get(position).getName());
       holder.btnEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showEditDialog(holder.getAdapterPosition());
           }
       });
       holder.btnRemove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               removeShipper(holder.getAdapterPosition());
           }
       });
    }

    private void removeShipper(int adapterPosition) {

    }

    private void showEditDialog(int adapterPosition) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
    public class ShipperViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

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
}
