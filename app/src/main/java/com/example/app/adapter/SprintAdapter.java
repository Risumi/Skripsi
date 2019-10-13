package com.example.app.adapter;

import android.content.ClipData;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.app.utils.ListenerSprint;
import com.example.app.R;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;


public class SprintAdapter extends RecyclerView.Adapter<SprintAdapter.SprintViewHolder> {

    ArrayList<Backlog> backlogList;

    public SprintAdapter(ArrayList<Backlog> backlogList) {
        this.backlogList = backlogList;
    }


    @Override
    public SprintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sprint, parent, false);
        return new SprintViewHolder(v);
    }

    public ArrayList<Backlog> getList() {
        return backlogList;
    }

    @Override
    public void onBindViewHolder(SprintViewHolder sprintViewHolder, int i) {
        sprintViewHolder.name.setText(backlogList.get(i).getName());
        sprintViewHolder.id.setText(backlogList.get(i).getId());
        sprintViewHolder.date.setText(formatDate(backlogList.get(i).getModifieddate()));
    }


    @Override
    public int getItemCount() {
        try {
            return backlogList.size();
        }catch (Exception e){
            return 0;
        }

    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    v.startDragAndDrop(data, shadowBuilder, v, 0);
//                } else {
//                    v.startDrag(data, shadowBuilder, v, 0);
//                }
//                return true;
//        }
//        return false;
//    }

    public void updateList(ArrayList<Backlog> list) {
        this.backlogList = list;
    }


    class SprintViewHolder extends RecyclerView.ViewHolder{
        TextView name, id,date;
        FrameLayout fl;
        public SprintViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            id = itemView.findViewById(R.id.txtID);
            date = itemView.findViewById(R.id.txtDate);
            fl = itemView.findViewById(R.id.FM1);
        }
    }
    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }
}
