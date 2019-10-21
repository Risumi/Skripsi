package com.example.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.Sprint;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SprintReportAdapter extends RecyclerView.Adapter<SprintReportAdapter.SprintReportAdapterViewHolder> {

    private ArrayList <Sprint> listSprint;
    private OnItemClickCallback onItemClickCallback;


    public SprintReportAdapter(ArrayList<Sprint> listSprint) {
        this.listSprint = listSprint;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public SprintReportAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sprint_report, parent, false);
        return new SprintReportAdapter.SprintReportAdapterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SprintReportAdapterViewHolder holder, int position) {
        Sprint sprint = listSprint.get(position);
        holder.txtName.setText(sprint.getName());
//        String date = formatDate(sprint.getBegda())+" - "+formatDate(sprint.getEndda());
//        String date = listSprint.get(position).getBegda()+" - "+listSprint.get(position).getEndda();
        holder.txtDate.setText(sprint.getId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listSprint.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return listSprint.size();
    }

    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }

    class SprintReportAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtDate;
        MaterialCardView cardView;

        SprintReportAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtName = itemView.findViewById(R.id.txtName);
            this.txtDate = itemView.findViewById(R.id.txtDate);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Sprint data);
    }

}
