package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    LayoutInflater mInflater;
    ArrayList<Project> projectArrayList;
    Context _context;
    Project current;


    public ProjectAdapter(Context _context, ArrayList<Project> projectArrayList) {
        this.mInflater = LayoutInflater.from(_context);
        this.projectArrayList = projectArrayList;
        this._context = _context;
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ProjectName;
        TextView ProjectStatus;
        ProjectAdapter mAdapter;
        CardView cardView;

        public ProjectViewHolder(View itemView, ProjectAdapter adapter) {
            super(itemView);
            ProjectName = itemView.findViewById(R.id.txtName);
            ProjectStatus = itemView.findViewById(R.id.txtStatus);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(_context, MainActivity.class);
            _context.startActivity(intent);
        }
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.project_card,parent,false);
        return new ProjectViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        current = projectArrayList.get(position);
//        holder.PokemonImage.setImageResource(current.image);
        holder.ProjectName.setText(current.name);
        holder.ProjectStatus.setText(current.type);
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }
}
