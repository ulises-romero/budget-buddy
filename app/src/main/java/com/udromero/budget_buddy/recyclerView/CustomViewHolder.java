package com.udromero.budget_buddy.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder{

    public TextView textTitle, textPlannedAmount, textRecurring;
    public Button buttonModify, buttonDelete;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
