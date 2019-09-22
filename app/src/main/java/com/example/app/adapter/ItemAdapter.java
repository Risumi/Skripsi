package com.example.app.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.model.Backlog;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ItemAdapter extends DragItemAdapter<Pair<Long, Backlog>, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;

    public ItemAdapter(ArrayList<Pair<Long, Backlog>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setItemList(list);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Backlog backlog= mItemList.get(position).second;
        holder.txtName.setText(backlog.getName());
        holder.txtID.setText(backlog.getId());
        holder.itemView.setTag(mItemList.get(position));
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView txtName, txtID;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            txtName = itemView.findViewById(R.id.txtName);
            txtID = itemView.findViewById(R.id.txtID);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
