package com.example.app;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBacklog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentBacklog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBacklog.
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog, container, false);
        mRecyclerView = view.findViewById(R.id.rv_backlog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(view.getContext(), resId);
        ArrayList<Backlog> listBacklog = new ArrayList<>();
        listBacklog.add(new Backlog("Recycler View","Complete", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"",""));
        mAdapter = new BacklogAdapter(view.getContext(), listBacklog);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutAnimation(animation);
        return view;
    }
}
