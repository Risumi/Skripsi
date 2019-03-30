package com.example.app;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprint extends Fragment implements BacklogAdapter.BacklogViewHolder.ClickListener, SprintAdapter.SprintViewHolder.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Backlog>listBacklog, BacklogToDo,BacklogOnProgress,BacklogCompleted;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private SprintAdapter mAdapter;
    private SprintAdapter mAdapter2;
    private SprintAdapter mAdapter3;

    public FragmentSprint() {
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
    public static FragmentSprint newInstance(String param1, String param2) {
        FragmentSprint fragment = new FragmentSprint();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sprint, container, false);

        listBacklog = new ArrayList<>();
        listBacklog.add(new Backlog("Recycler View","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("Recycler View","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("Recycler View","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("Burndown Chart","On Progress", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat chart untuk merepresentasikan backlog ke dalam bentuk chart sesuai dengan kaidah scrum"));
        listBacklog.add(new Backlog("Sprint","To Do", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat automatisasi proses sprint"));
        listBacklog.add(new Backlog("Sprint","To Do", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat automatisasi proses sprint"));
        BacklogToDo = new ArrayList<>();
        BacklogOnProgress = new ArrayList<>();
        BacklogCompleted= new ArrayList<>();
        setBacklog(listBacklog);

        mRecyclerView = view.findViewById(R.id.rvToDo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(view.getContext(), resId);
        mAdapter = new SprintAdapter(view.getContext(), BacklogToDo,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutAnimation(animation);

        mRecyclerView2 = view.findViewById(R.id.rvProgress);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter2 = new SprintAdapter(view.getContext(), BacklogOnProgress,this);
        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView2.setLayoutAnimation(animation);

        mRecyclerView3 = view.findViewById(R.id.rvCompleted);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter3 = new SprintAdapter(view.getContext(), BacklogCompleted,this);
        mRecyclerView3.setAdapter(mAdapter3);
        mRecyclerView3.setLayoutAnimation(animation);


        return view;
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }

    public void setBacklog(ArrayList<Backlog> listBacklog){
        for (int i=0;i<listBacklog.size();i++){
            if (listBacklog.get(i).getStatus().equalsIgnoreCase("To Do")){
                BacklogToDo.add(listBacklog.get(i));
            }else if (listBacklog.get(i).getStatus().equalsIgnoreCase("On Progress")){
                BacklogOnProgress.add(listBacklog.get(i));
            }else if (listBacklog.get(i).getStatus().equalsIgnoreCase("Completed")){
                BacklogCompleted.add(listBacklog.get(i));
            }
        }
    }
}
