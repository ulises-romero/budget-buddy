package com.udromero.budget_buddy.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udromero.budget_buddy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdditionalSubCategoryAdapter extends RecyclerView.Adapter<AdditionalSubCategoryAdapter.MyViewHolder> {

    private ArrayList<AdditionalSubCategoryModel> mSubCategoryList;
    private List<String> titles = new ArrayList<>();
    private RecylcerViewClickListener listener;

    public ArrayList<AdditionalSubCategoryModel> getSubCategoryList() {
        return mSubCategoryList;
    }

    public AdditionalSubCategoryAdapter(ArrayList<AdditionalSubCategoryModel> subCategoryList, RecylcerViewClickListener listener) {
        mSubCategoryList = subCategoryList;
        this.listener = listener;
    }

    public void clearData(){
        mSubCategoryList.clear();
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<AdditionalSubCategoryModel> newList){
        mSubCategoryList = newList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, plannedAmount, recurring, updateDelete;

        public MyViewHolder(final View view){
            super(view);
            title = view.findViewById(R.id.title);
            plannedAmount = view.findViewById(R.id.plannedAmount);
            recurring = view.findViewById(R.id.recurring);
            updateDelete = view.findViewById(R.id.update);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public AdditionalSubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_other_sub_categories, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionalSubCategoryAdapter.MyViewHolder holder, int position) {
        String title = mSubCategoryList.get(position).getTitle();
        holder.title.setText(title);

        String plannedAmount = mSubCategoryList.get(position).getPlannedAmount();
        holder.plannedAmount.setText(plannedAmount);

        String recurring = mSubCategoryList.get(position).getRecurring();
        if(recurring.equals("[r]")){
            holder.recurring.setText("Y");
        } else {
            holder.recurring.setText("");
        }


        holder.updateDelete.setText("Select #" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mSubCategoryList.size();
    }

    public interface RecylcerViewClickListener{
        void onClick(View view, int position);
    }
}
