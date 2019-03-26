package com.example.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BacklogAdapter extends SelectableAdapter<BacklogAdapter.BacklogViewHolder>{

    LayoutInflater mInflater;
    ArrayList<Backlog> backlogArrayList;
    Context _context;
    Backlog current;
    private BacklogViewHolder.ClickListener clickListener;

    public BacklogAdapter(Context _context, ArrayList<Backlog> backlogArrayList, BacklogViewHolder.ClickListener clickListener) {
        this.mInflater = LayoutInflater.from(_context);
        this.backlogArrayList = backlogArrayList;
        this._context = _context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BacklogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = mInflater.inflate(R.layout.card_project,parent,false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_project, parent, false);
        return new BacklogViewHolder(v,this,clickListener);
    }

    @Override
    public void onBindViewHolder(BacklogViewHolder holder, int position) {
        current = backlogArrayList.get(position);
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String endda= formatDate.format(current.getEndda().getTime());
        holder.BacklogName.setText(current.getName());
        holder.BacklogStatus.setText(current.getStatus());
        holder.BacklogDescription.setText(current.getDescription());
        holder.BacklogDate.setText(endda);
        String desc = current.getDescription();
        if (desc.length()>40){
            desc = desc.substring(0,40)+"...";
        }
        holder.BacklogDescription.setText(desc);
//        holder.selectedOverlay.setVisibility(isSelected(position)?View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return backlogArrayList.size();
    }

    public void removeItem(int position) {
        backlogArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            backlogArrayList.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    static class BacklogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView BacklogName;
        TextView BacklogStatus;
        TextView BacklogDescription;
        TextView BacklogDate;
        BacklogAdapter mAdapter;
        CardView cardView;
        View selectedOverlay;
        private ClickListener listener;

        public BacklogViewHolder(View itemView, BacklogAdapter adapter,ClickListener listener) {
            super(itemView);
            BacklogName = itemView.findViewById(R.id.txtName);
            BacklogStatus = itemView.findViewById(R.id.txtStatus);
            BacklogDescription = itemView.findViewById(R.id.txtDescription);
            BacklogDate = itemView.findViewById(R.id.txtDate);
            cardView = itemView.findViewById(R.id.cardView);
//            cardView.setOnClickListener(this);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getPosition());
            }

            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);
            public boolean onItemLongClicked(int position);
        }
    }
}
