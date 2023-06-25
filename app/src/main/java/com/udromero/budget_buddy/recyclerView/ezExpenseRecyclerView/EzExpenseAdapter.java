package com.udromero.budget_buddy.recyclerView.ezExpenseRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udromero.budget_buddy.R;

import java.util.ArrayList;

public class EzExpenseAdapter extends RecyclerView.Adapter<EzExpenseAdapter.MyViewHolder> {

    private ArrayList<EzExpenseModel> mExpensesList;
    private RecylcerViewClickListener listener;

    public EzExpenseAdapter(ArrayList<EzExpenseModel> expensesList, RecylcerViewClickListener listener) {
        mExpensesList = expensesList;
        this.listener = listener;
    }

    public ArrayList<EzExpenseModel> getExpensesList() {
        return mExpensesList;
    }

    public void clearData(){
        mExpensesList.clear();
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<EzExpenseModel> newList){
        mExpensesList = newList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date, description, category, plannedAmount;

        public MyViewHolder(final View view){
            super(view);
            date = view.findViewById(R.id.date);
            description = view.findViewById(R.id.description);
            category = view.findViewById(R.id.category);
            plannedAmount = view.findViewById(R.id.plannedAmount);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public EzExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expense_history, parent, false);
        return new EzExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EzExpenseAdapter.MyViewHolder holder, int position) {
        String date = mExpensesList.get(position).getDate();
        if(date.equals(" ")){
            holder.date.setBackgroundResource(android.R.color.white);
        } else {
            holder.date.setText(date);
            return;
        }

        String desc = mExpensesList.get(position).getDescription();
        holder.description.setText(desc);

        String category = mExpensesList.get(position).getCategory();
        String subCat = mExpensesList.get(position).getSubCategory();
        holder.category.setText(category + " - " + subCat);

        String plannedAmount = mExpensesList.get(position).getPlannedAmount();
        holder.plannedAmount.setText(plannedAmount);
    }

    @Override
    public int getItemCount() {
        return mExpensesList.size();
    }

    public interface RecylcerViewClickListener{
        void onClick(View view, int position);
    }
}
