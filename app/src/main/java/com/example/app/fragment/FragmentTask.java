package com.example.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.Listener;
import com.example.app.R;
import com.example.app.adapter.BacklogAdapter;
import com.example.app.model.Backlog;
import com.example.app.model.Sprint;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTask extends Fragment implements Listener, BacklogAdapter.BacklogViewHolder2.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Backlog> mParam1;
    private String mParam2;


    public FragmentTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTask.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTask newInstance(ArrayList<Backlog> param1, String param2) {
        FragmentTask fragment = new FragmentTask();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1=new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rvTop;
    BacklogAdapter topListAdapter;
//    ArrayList <Backlog> backlogArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_task, container, false);
        rvTop = view.findViewById(R.id.rvTop);

//        backlogArrayList = new ArrayList<>();
//        for (int i = 1 ;i<6;i++){
//            backlogArrayList.add(new Backlog("Task "+i,"Status",new Date(),new Date(),"","Deskripsi","","","",""));
//        }
        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        topListAdapter = new BacklogAdapter(mParam1,this,this);
        rvTop.setAdapter(topListAdapter);

        return view;
    }

    @Override
    public void setEmptyListTop(boolean visibility) {

    }

    @Override
    public void setEmptyListBottom(boolean visibility) {

    }

    @Override
    public void updateSprint(Backlog backlog, String todo) {

    }

    @Override
    public void onItemClicked(int position,Backlog backlog,BacklogAdapter adapter) {

    }
}
