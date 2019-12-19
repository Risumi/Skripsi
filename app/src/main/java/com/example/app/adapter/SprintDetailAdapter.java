package com.example.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.Backlog;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class SprintDetailAdapter extends RecyclerView.Adapter<SprintDetailAdapter.backlogViewHolder> {

    ArrayList<Backlog> backlogList;

    public SprintDetailAdapter(ArrayList<Backlog> backlogList) {
        this.backlogList = backlogList;
    }


    @Override
    public backlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sprint, parent, false);
        return new backlogViewHolder(v);
    }

    public ArrayList<Backlog> getList() {
        return backlogList;
    }

    @Override
    public void onBindViewHolder(backlogViewHolder sprintViewHolder, int i) {
        sprintViewHolder.name.setText(backlogList.get(i).getName());
        sprintViewHolder.id.setText(backlogList.get(i).getId());
    }


    @Override
    public int getItemCount() {
        try {
            return backlogList.size();
        }catch (Exception e){
            return 0;
        }

    }


    class backlogViewHolder extends RecyclerView.ViewHolder{
        TextView name, id;
        public backlogViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            id = itemView.findViewById(R.id.txtID);
        }
    }

}