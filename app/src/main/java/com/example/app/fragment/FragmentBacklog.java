package com.example.app.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.model.Backlog;
import com.example.app.adapter.BacklogAdapter;
import com.example.app.Listener;
import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.model.Sprint;
import com.example.app.activity.ActivityAddBacklog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog extends Fragment implements Listener, BacklogAdapter.BacklogViewHolder2.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int  EDIT_BACKLOG = 2;
    // TODO: Rename and change types of parameters
    private String PID;
    private String mParam2;


    public FragmentBacklog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSprint.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBacklog newInstance(String param1, String param2) {
        FragmentBacklog fragment = new FragmentBacklog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            PID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        model.getCurrentSprint().observe(this, new Observer<Sprint>() {
            @Override
            public void onChanged(@Nullable Sprint sprint) {
                if (sprint != null){
                    if (model.getListBacklog().getValue().size()==0){
                        tvEmptyListBottom.setVisibility(View.VISIBLE);
                    }
                    rvBottom.setVisibility(View.VISIBLE);
                    tvSprint.setVisibility(View.VISIBLE);
                }else {
                    tvEmptyListBottom.setVisibility(View.GONE);
                    rvBottom.setVisibility(View.GONE);
                    tvSprint.setVisibility(View.GONE);
                }
            }
        });
        model.getListBacklog().observe(this, new Observer<ArrayList<Backlog>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Backlog> backlogs) {
//                synchronized (topListAdapter){
                topListAdapter.notifyDataSetChanged();
                Log.d("Item Count : ", ((Integer) topListAdapter.getItemCount()).toString());
            }
        });
        Log.d("PID", PID);

    }

    RecyclerView rvTop;
    RecyclerView rvBottom;

    TextView tvEmptyListTop;
    TextView tvEmptyListBottom;
    TextView tvSprint;
    private MainViewModel model;
    BacklogAdapter topListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog, container, false);
        rvTop = view.findViewById(R.id.rvTop);
        rvBottom = view.findViewById(R.id.rvBottom);
        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);
        tvSprint= view.findViewById(R.id.textView8);

        if (model.getListBacklog().getValue().size()==0){
            tvEmptyListTop.setVisibility(View.VISIBLE);
        }
        if(model.getCurrentSprint().getValue()!=null){
            if (model.getListBacklogSprint().getValue().size()==0){
                tvEmptyListBottom.setVisibility(View.VISIBLE);
            }
        }else {
            tvEmptyListBottom.setVisibility(View.GONE);
            rvBottom.setVisibility(View.GONE);
            tvSprint.setVisibility(View.GONE);
        }


        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        topListAdapter = new BacklogAdapter(model.getListBacklog().getValue(),this,this);
        rvTop.setAdapter(topListAdapter);
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

        rvBottom.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        BacklogAdapter bottomListAdapter = new BacklogAdapter(model.getListBacklogSprint().getValue(), this,this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        return view;
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void setEmptyListMiddle(boolean visibility) {
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    void log(){
        Log.d("rvTop", ((Integer) rvTop.getVisibility()).toString());
        Log.d("rvBottom",((Integer) rvBottom.getVisibility()).toString());
        Log.d("tvTop", ((Integer) tvEmptyListTop.getVisibility()).toString());
        Log.d("tvBottom",((Integer) tvEmptyListBottom.getVisibility()).toString());
    }

    @Override
    public void onItemClicked(int position) {
        Intent editBacklog =new Intent(getContext(), ActivityAddBacklog.class);
        editBacklog.putExtra("backlog",model.getListBacklog().getValue().get(position));
        Log.d("position", ((Integer) position).toString());
        editBacklog.putExtra("PID", PID);
        editBacklog.putExtra("blsID",model.getListBacklog().getValue().get(position).getId());
        editBacklog.putExtra("position",position);
        editBacklog.putExtra("req code",EDIT_BACKLOG);
        getActivity().startActivityForResult(editBacklog,EDIT_BACKLOG);
    }

    public void AddDataSet(Backlog backlog){
        model.getListBacklog().getValue().add(backlog);
        topListAdapter.notifyDataSetChanged();
        model.mutateBacklog(backlog);
    }

    public void EditDataSet(int position,Backlog backlog){
        model.getListBacklog().getValue().set(position,backlog);
        topListAdapter.notifyDataSetChanged();
        model.mutateBacklog(backlog);
    }

    public void setSprint(Sprint sprint){
        model.setCurrentSprint(sprint);
        model.mutateSprint(sprint);
    }
}
