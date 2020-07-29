package com.example.ddth.ViewHolder;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.R;

public class ShowCommentViewHolder extends RecyclerView.ViewHolder {
    public TextView txtUserPhone, txtComment;
    public RatingBar ratingBar;
    public ShowCommentViewHolder(@NonNull View itemView) {
        super(itemView);
        txtUserPhone = itemView.findViewById(R.id.txtPhone);
        txtComment = itemView.findViewById(R.id.show_comment);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
