package com.example.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.activity.ActivityMain;
import com.example.app.model.Backlog;
import com.example.app.model.Progress;
import com.example.app.model.Project;
import com.example.app.model.User;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    LayoutInflater mInflater;
    ArrayList<Project> projectArrayList;
    ArrayList<Progress> progressArrayList;
    Context _context;
    Project current;
    User user;


    public ProjectAdapter(Context _context, ArrayList<Project> projectArrayList,ArrayList<Progress> progressArrayList,User user) {
        this.mInflater = LayoutInflater.from(_context);
        this.projectArrayList = projectArrayList;
        this._context = _context;
        this.progressArrayList = progressArrayList;
        this.user = user;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_project,parent,false);
        return new ProjectViewHolder(itemView,this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        current = projectArrayList.get(position);
        holder.ProjectName.setText(current.getName());
        holder.ProjectStatus.setText(current.getId());
        String desc = current.getDescription();
        float progress = 0;
        for (int i = 0 ; i < progressArrayList.size();i++){
            if (progressArrayList.get(i).getIdProject().equalsIgnoreCase(current.getId())){
//                Log.d("Completed",Float.toString(progressArrayList.get(i).getCompleted()));
//                Log.d("Count",Float.toString(progressArrayList.get(i).getCount()));
                try {
                    progress = (progressArrayList.get(i).getCompleted() / progressArrayList.get(i).getCount()) * 100;
//                    Log.d("Percentage",Double.toString(progress));
                    if (Float.isNaN(progress)){
                        progress = 0;
                    }
                }catch (ArithmeticException e){
                    progress = 0;
                }
            }
        }
        holder.ProjectProgress.setText(Integer.toString((int)progress)+"%");
        if (desc.length()>45){
            desc = desc.substring(0,45)+"...";
        }
        holder.ProjectDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }
    class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ProjectName;
        TextView ProjectStatus;
        TextView ProjectDesc;
        TextView ProjectProgress;
        ProjectAdapter mAdapter;
        CardView cardView;

        public ProjectViewHolder(View itemView, ProjectAdapter adapter) {
            super(itemView);
            ProjectName = itemView.findViewById(R.id.txtName);
            ProjectStatus = itemView.findViewById(R.id.txtStatus);
            ProjectDesc = itemView.findViewById(R.id.txtDescription);
            ProjectProgress = itemView.findViewById(R.id.txtProgress);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(_context, ActivityMain.class);
            intent.putExtra("PName",projectArrayList.get(getAdapterPosition()).getName());
            intent.putExtra("PID",projectArrayList.get(getAdapterPosition()).getId());
            intent.putExtra("project",projectArrayList.get(getAdapterPosition()));
            intent.putExtra("User",user);
            _context.startActivity(intent);
        }
    }
}
