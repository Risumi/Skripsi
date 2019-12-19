package com.example.app.adapter;

import android.content.ClipData;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.Backlog;
import com.example.app.utils.Listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class BacklogAdapter extends RecyclerView.Adapter<BacklogAdapter.BacklogViewHolder2> implements View.OnLongClickListener {
    ArrayList<Backlog> backlogList;
    Listener listener;
    private BacklogViewHolder2.ClickListener clickListener;

    public BacklogAdapter(ArrayList<Backlog> backlogList, Listener listener, BacklogViewHolder2.ClickListener clickListener) {
        this.backlogList = backlogList;
        this.listener = listener;
        this.clickListener = clickListener;
    }


    @Override
    public BacklogAdapter.BacklogViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_backlog, parent, false);
        return new BacklogAdapter.BacklogViewHolder2(v,clickListener);
    }

    public ArrayList<Backlog> getList() {
        return backlogList;
    }

    @Override
    public void onBindViewHolder(BacklogAdapter.BacklogViewHolder2 backlogViewHolder2, int i) {
        backlogViewHolder2.BacklogName.setText(backlogList.get(i).getName());
        backlogViewHolder2.BacklogStatus.setText(backlogList.get(i).getId());
//        backlogViewHolder2.BacklogDate.setText(formatDate(backlogList.get(i).getEndda()));
        String desc = backlogList.get(i).getDescription();
        if (desc.length()>40){
            desc = desc.substring(0,40)+"...";
        }
//        backlogViewHolder2.BacklogDescription.setText(desc);

        backlogViewHolder2.backlog = backlogList.get(i);
        backlogViewHolder2.backlogAdapter = this;

    }

    @Override
    public int getItemCount() {
        return backlogList.size();
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

    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }

    public static class BacklogViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView BacklogName;
        TextView BacklogStatus;
        private ClickListener listener;
        Backlog backlog;
        BacklogAdapter backlogAdapter;

        public BacklogViewHolder2(View itemView,ClickListener listener) {
            super(itemView);
            BacklogName = itemView.findViewById(R.id.txtName);
            BacklogStatus = itemView.findViewById(R.id.txtStatus);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClicked(getPosition(),backlog,backlogAdapter);
            }
        }

        public interface ClickListener {
            public void onItemClicked(int position,Backlog backlog,BacklogAdapter adapter);
        }
    }




}
