package com.example.ddth.Interface;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener  {
    void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
