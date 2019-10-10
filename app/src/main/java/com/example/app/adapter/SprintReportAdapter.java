package com.example.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.Sprint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SprintReportAdapter extends RecyclerView.Adapter<SprintReportAdapter.SprintReportAdapterViewHolder> {

    private ArrayList <Sprint> listSprint;

    public SprintReportAdapter(ArrayList<Sprint> listSprint) {
        this.listSprint = listSprint;
    }

    @NonNull
    @Override
    public SprintReportAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sprint_report, parent, false);
        return new SprintReportAdapter.SprintReportAdapterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SprintReportAdapterViewHolder holder, int position) {
        holder.txtName.setText(listSprint.get(position).getName());
//        String date = formatDate(listSprint.get(position).getBegda())+" - "+formatDate(listSprint.get(position).getEndda());
        String date = listSprint.get(position).getBegda()+" - "+listSprint.get(position).getEndda();
        holder.txtDate.setText(date);
    }


    @Override
    public int getItemCount() {
        return listSprint.size();
    }

    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        return formatDate.format(rawDate);
    }

    class SprintReportAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtDate;

        public SprintReportAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtName = itemView.findViewById(R.id.txtName);
            this.txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
