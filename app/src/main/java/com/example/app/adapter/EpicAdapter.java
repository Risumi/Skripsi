package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EpicAdapter extends RecyclerView.Adapter<EpicAdapter.EpicViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Epic> epicArrayList;
    private ArrayList<Backlog> backlogArrayList;
    private Context _context;
    private Epic current;
    private ClickListener clickListener;


    public EpicAdapter(Context _context, ArrayList<Epic> projectArrayList,ArrayList<Backlog> backlogArrayList) {
        this.mInflater = LayoutInflater.from(_context);
        this.epicArrayList= projectArrayList;
        this._context = _context;
        this.backlogArrayList = backlogArrayList;
    }

    @Override
    public EpicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_epic,parent,false);
        return new EpicViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(EpicViewHolder holder, int position) {
        current = epicArrayList.get(position);
        holder.EpicName.setText(current.getName());
        holder.EpicTask.setText(current.getId());
        float progress = 0;
        float completed = 0 ;
        float count = 0 ;
        for (int i = 0 ; i < backlogArrayList.size();i++){
            if (backlogArrayList.get(i).getEpicName().equalsIgnoreCase(current.getId())){
                count++;
                if (backlogArrayList.get(i).getStatus().equalsIgnoreCase("Completed")||backlogArrayList.get(i).getStatus().equalsIgnoreCase("Done")){
                    completed++;
                }
            }
        }
        try {
            progress = (completed /count) * 100;
//                    Log.d("Percentage",Double.toString(progress));
            if (Float.isNaN(progress)){
                progress = 0;
            }
        }catch (ArithmeticException e){
            progress = 0;
        }
        holder.EpicProgress.setText(Integer.toString((int)progress)+"%");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(epicArrayList.get(position));
            }
        });
    }

    public void setListener (ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return epicArrayList.size();
    }
    class EpicViewHolder extends RecyclerView.ViewHolder {
        TextView EpicName;
        TextView EpicTask;
        TextView EpicProgress;
        EpicAdapter mAdapter;
        CardView cardView;

        public EpicViewHolder(View itemView, EpicAdapter adapter) {
            super(itemView);
            EpicName = itemView.findViewById(R.id.txtName);
            EpicTask = itemView.findViewById(R.id.txtTask);
            EpicProgress = itemView.findViewById(R.id.txtProgress);
            cardView = itemView.findViewById(R.id.cardView);
            this.mAdapter = adapter;
        }

    }
    public interface ClickListener {
        void onItemClicked(Epic epic);
    }
}
