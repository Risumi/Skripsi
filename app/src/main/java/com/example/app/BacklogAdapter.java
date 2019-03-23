package com.example.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BacklogAdapter extends RecyclerView.Adapter<BacklogAdapter.BacklogViewHolder>{

    LayoutInflater mInflater;
    ArrayList<Backlog> backlogArrayList;
    Context _context;
    Backlog current;

    public BacklogAdapter(Context _context, ArrayList<Backlog> backlogArrayList) {
        this.mInflater = LayoutInflater.from(_context);
        this.backlogArrayList = backlogArrayList;
        this._context = _context;
    }

    @NonNull
    @Override
    public BacklogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_project,parent,false);
        return new BacklogViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(BacklogViewHolder holder, int position) {
        current = backlogArrayList.get(position);
        holder.BacklogName.setText(current.getName());
        holder.BacklogStatus.setText(current.getStatus());
//        holder.BacklogDate.setText(current.getBegda());
        holder.BacklogDescription.setText(current.getDescription());
    }

    @Override
    public int getItemCount() {
        return backlogArrayList.size();
    }

    class BacklogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView BacklogName;
        TextView BacklogStatus;
        TextView BacklogDescription;
        TextView BacklogDate;
        BacklogAdapter mAdapter;
        CardView cardView;
        public BacklogViewHolder(View itemView, BacklogAdapter adapter) {
            super(itemView);
            BacklogName = itemView.findViewById(R.id.txtName);
            BacklogStatus = itemView.findViewById(R.id.txtStatus);
            BacklogDescription = itemView.findViewById(R.id.txtDescription);
            BacklogDate = itemView.findViewById(R.id.txtName);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
