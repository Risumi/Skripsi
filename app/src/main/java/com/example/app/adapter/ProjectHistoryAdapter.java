package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.ProjectHistory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProjectHistoryAdapter extends
        RecyclerView.Adapter<ProjectHistoryAdapter.ViewHolder> {

    private static final String TAG = ProjectHistoryAdapter.class.getSimpleName();

    private Context context;
    private List<ProjectHistory> list;

    public ProjectHistoryAdapter(List<ProjectHistory> list) {
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProjectHistory item = list.get(position);
        holder.message.setText(item.getMessage());
        DateTime time = item.getDateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
        holder.time.setText(time.toString(fmt));
        fmt = DateTimeFormat.forPattern("dd MMMM yyyy");
        holder.date.setText(time.toString(fmt));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ProjectHistory> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            message= itemView.findViewById(R.id.history_message);
            time = itemView.findViewById(R.id.history_time);

        }
    }
}