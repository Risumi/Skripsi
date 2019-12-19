package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.model.User;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    LayoutInflater mInflater;
    ArrayList<User> userArrayList;
    Context _context;
    User current;
    UserViewHolder.ClickListener listener;

    public UserAdapter(Context _context, ArrayList<User> userArrayList,UserViewHolder.ClickListener listener) {
        this.mInflater = LayoutInflater.from(_context);
        this.userArrayList = userArrayList;
        this._context = _context;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_user,parent,false);
        return new UserViewHolder(itemView,this,listener);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        current = userArrayList.get(position);
        holder.user = userArrayList.get(position);
        holder.UserName.setText(current.getName());
        holder.UserEmail.setText(current.getEmail());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView UserName;
        TextView UserEmail;
        UserAdapter mAdapter;
        Button btnRemove;
        ClickListener listener;
        User user;

        public UserViewHolder(View itemView, UserAdapter adapter,ClickListener listener) {
            super(itemView);
            UserName = itemView.findViewById(R.id.txtName);
            UserEmail = itemView.findViewById(R.id.txtEmail);
            btnRemove = itemView.findViewById(R.id.buttonRemove);
            btnRemove.setOnClickListener(this);
            this.mAdapter = adapter;
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClicked(getPosition(),user,mAdapter);
            }
        }
        public interface ClickListener {
            public void onItemClicked(int position, User user, UserAdapter adapter);
        }
    }
}
