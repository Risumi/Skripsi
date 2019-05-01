package com.example.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.activity.ActivityEpic;
import com.example.app.model.Epic;

import java.util.ArrayList;

public class EpicAdapter extends RecyclerView.Adapter<EpicAdapter.EpicViewHolder> {

    LayoutInflater mInflater;
    ArrayList<Epic> projectArrayList;
    Context _context;
    Epic current;


    public EpicAdapter(Context _context, ArrayList<Epic> projectArrayList) {
        this.mInflater = LayoutInflater.from(_context);
        this.projectArrayList = projectArrayList;
        this._context = _context;
    }

    @Override
    public EpicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_epic,parent,false);
        return new EpicViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(EpicViewHolder holder, int position) {
        current = projectArrayList.get(position);
        holder.ProjectName.setText(current.getName());
        holder.ProjectStatus.setText(current.getId());
        String desc = current.getDescription();
        if (desc.length()>45){
            desc = desc.substring(0,45)+"...";
        }
        holder.ProjectDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }
    class EpicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ProjectName;
        TextView ProjectStatus;
        TextView ProjectDesc;
        EpicAdapter mAdapter;
        CardView cardView;

        public EpicViewHolder(View itemView, EpicAdapter adapter) {
            super(itemView);
            ProjectName = itemView.findViewById(R.id.txtName);
            ProjectStatus = itemView.findViewById(R.id.txtStatus);
            ProjectDesc = itemView.findViewById(R.id.txtDescription);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(_context, ActivityEpic.class);
            intent.putExtra("PID",projectArrayList.get(getAdapterPosition()).getId());
            _context.startActivity(intent);
        }
    }
}
