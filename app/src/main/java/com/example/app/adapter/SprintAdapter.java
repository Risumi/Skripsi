package com.example.app.adapter;

import android.content.ClipData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.app.Listener;
import com.example.app.ListenerSprint;
import com.example.app.R;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;
import com.example.app.model.Sprint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SprintAdapter extends RecyclerView.Adapter<SprintAdapter.SprintViewHolder> implements  View.OnLongClickListener{

    ArrayList<Backlog> backlogList;
    ArrayList<Epic> epicList;
    ListenerSprint listener;

    public SprintAdapter(ArrayList<Backlog> backlogList,ArrayList<Epic> epicList, ListenerSprint listener) {
        this.backlogList = backlogList;
        this.listener = listener;
        this.epicList = epicList;
    }

    @NonNull
    @Override
    public SprintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sprint, parent, false);
        return new SprintViewHolder(v);
    }

    public ArrayList<Backlog> getList() {
        return backlogList;
    }

    @Override
    public void onBindViewHolder(@NonNull SprintViewHolder sprintViewHolder, int i) {
        sprintViewHolder.name.setText(backlogList.get(i).getName());
        sprintViewHolder.date.setText(formatDate(backlogList.get(i).getEndda()));
        sprintViewHolder.fl.setTag(i);
        if (backlogList.get(i).getIdEpic()==null){
            sprintViewHolder.epic.setText("---");
        }else if (backlogList.get(i).getIdEpic().equals("")){
            sprintViewHolder.epic.setText("---");
        }else{
            sprintViewHolder.epic.setText(getNameEpic(backlogList.get(i).getIdEpic()));
        }

//        sprintViewHolder.fl.setOnTouchListener(this);
        sprintViewHolder.fl.setOnLongClickListener(this);
    }

    String getNameEpic(String id){
        String name = "";
        for (int i = 0; i<epicList.size();i++){
            if (id.equalsIgnoreCase(epicList.get(i).getId())){
                name=epicList.get(i).getName();
            }
        }
        return name;
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

    public DragListenerSprint getDragInstance() {
        if (listener != null) {
            return new DragListenerSprint(listener);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

    public void updateList(ArrayList<Backlog> list) {
        this.backlogList = list;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
        return true;
    }

    class SprintViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, epic;
        FrameLayout fl;
        public SprintViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            date = itemView.findViewById(R.id.txtDate);
            epic = itemView.findViewById(R.id.txtEpic);
            fl = itemView.findViewById(R.id.FM1);
        }
    }
    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }
}
