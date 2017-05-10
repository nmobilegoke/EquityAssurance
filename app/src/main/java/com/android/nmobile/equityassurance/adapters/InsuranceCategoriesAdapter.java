package com.android.nmobile.equityassurance.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.dto.InsuranceCategorie;

import java.util.ArrayList;

/**
 * @author Dev Rupesh Saxena
 */

public class InsuranceCategoriesAdapter extends RecyclerView.Adapter<InsuranceCategoriesAdapter.ViewHolder> {
    private ArrayList<InsuranceCategorie> itemsData;
    private Context context;

    public InsuranceCategoriesAdapter(ArrayList<InsuranceCategorie> itemsData, Context ctx) {
        this.itemsData = itemsData;
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        InsuranceCategorie obj = itemsData.get(position);

        holder.txtViewTitle.setText(obj.getTitle());
    }


    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id._title);
        }

    }
}
