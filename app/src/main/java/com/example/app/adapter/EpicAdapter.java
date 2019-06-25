package com.example.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        holder.EpicName.setText(current.getName());
        holder.EpicTask.setText(current.getId());
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }
    class EpicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView EpicName;
        TextView EpicTask;
        EpicAdapter mAdapter;
        CardView cardView;

        public EpicViewHolder(View itemView, EpicAdapter adapter) {
            super(itemView);
            EpicName = itemView.findViewById(R.id.txtName);
            EpicTask = itemView.findViewById(R.id.txtTask);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(_context, ActivityEpic.class);
            intent.putExtra("epicID",projectArrayList.get(getAdapterPosition()).getId());
            intent.putExtra("epic",projectArrayList.get(getAdapterPosition()));
            Log.d("epicID",projectArrayList.get(getAdapterPosition()).toString());
            Log.d("epicID",projectArrayList.get(getAdapterPosition()).getId());
            _context.startActivity(intent);
        }
    }
}
