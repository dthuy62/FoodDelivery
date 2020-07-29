package com.example.ddth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Interface.ItemClickListener;
import com.example.ddth.Model.Ratting;
import com.example.ddth.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Ratting> rattingList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public CommentAdapter(Context context, List<Ratting> rattingList) {
        this.context = context;
        this.rattingList = rattingList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Ratting ratting = rattingList.get(position);
        holder.ratingBar.setRating(Float.parseFloat(ratting.getRatevalue()));
        holder.txtComment.setText(ratting.getComment());
        holder.txtUserPhone.setText(ratting.getUserphone());
    }

    @Override
    public int getItemCount() {
        return rattingList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView txtUserPhone, txtComment;
        public RatingBar ratingBar;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserPhone = itemView.findViewById(R.id.txtPhone);
            txtComment = itemView.findViewById(R.id.txtComment);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}
