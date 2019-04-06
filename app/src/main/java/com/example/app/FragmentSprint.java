package com.example.app;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprint extends Fragment implements Listener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
    ArrayList<Backlog> listBacklog, listBacklog2,listBacklog3;

    RecyclerView rvTop;
    RecyclerView rvBottom;
    RecyclerView rvMiddle;
    TextView tvEmptyListTop;
    TextView tvEmptyListBottom;
    TextView tvEmptyListMiddle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sprint, container, false);

        listBacklog = new ArrayList<>();
        listBacklog.add(new Backlog("1","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("2","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("3","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));

        listBacklog2 = new ArrayList<>();
        listBacklog2.add(new Backlog("1","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog2.add(new Backlog("2","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog2.add(new Backlog("3","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));

        listBacklog3 = new ArrayList<>();
        listBacklog3.add(new Backlog("1","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog3.add(new Backlog("2","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog3.add(new Backlog("3","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));

        rvTop = view.findViewById(R.id.rvToDo);
        rvBottom = view.findViewById(R.id.rvProgress);
        rvMiddle = view.findViewById(R.id.rvCompleted);
        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
        tvEmptyListMiddle = view.findViewById(R.id.tvEmptyListMiddle);
        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
        tvEmptyListMiddle.setVisibility(View.GONE);

        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter topListAdapter = new SprintAdapter(listBacklog,this);
        rvTop.setAdapter(topListAdapter);
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

        rvMiddle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter middleListAdapter = new SprintAdapter(listBacklog2, this);
        rvMiddle.setAdapter(middleListAdapter);
        tvEmptyListMiddle.setOnDragListener(middleListAdapter.getDragInstance());
        rvMiddle.setOnDragListener(middleListAdapter.getDragInstance());

        rvBottom.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter bottomListAdapter = new SprintAdapter(listBacklog3, this);
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
        tvEmptyListMiddle.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvMiddle.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    void log(){
        Log.d("rvTop", ((Integer) rvTop.getVisibility()).toString());
        Log.d("rvMiddle",((Integer) rvMiddle.getVisibility()).toString());
        Log.d("rvBottom",((Integer) rvBottom.getVisibility()).toString());
        Log.d("tvTop", ((Integer) tvEmptyListTop.getVisibility()).toString());
        Log.d("tvMiddle",((Integer) tvEmptyListMiddle.getVisibility()).toString());
        Log.d("tvBottom",((Integer) tvEmptyListBottom.getVisibility()).toString());
    }
}
