package com.udromero.budget_buddy.recyclerView;

import androidx.annotation.NonNull;

public class AdditionalSubCategoryModel {
    String title;
    String plannedAmount;
    String recurring;
    String updateDelete;

    public AdditionalSubCategoryModel(String title, String plannedAmount, String recurring) {
        this.title = title;
        this.plannedAmount = plannedAmount;
        this.recurring = recurring;
        this.updateDelete = "Update/Delete";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(String plannedAmount) {
        this.plannedAmount = plannedAmount;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public String getUpdateDelete() {
        return updateDelete;
    }

    public void setUpdateDelete(String updateDelete) {
        this.updateDelete = updateDelete;
    }

    @NonNull
    @Override
    public String toString() {
        return "AdditionalSubCategoryModel{" +
                "title='" + title + '\'' +
                ", plannedAmount='" + plannedAmount + '\'' +
                ", recurring='" + recurring + '\'' +
                ", updateDelete='" + updateDelete + '\'' +
                '}';
    }
}
