package com.example.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.utils.MainViewModel;
import com.example.app.R;
import com.example.app.activity.ActivityDetailSprint;
import com.example.app.adapter.SprintReportAdapter;
import com.example.app.model.Sprint;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprintReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprintReport extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainViewModel model;

    public FragmentSprintReport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSprintReport.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSprintReport newInstance(String param1, String param2) {
        FragmentSprintReport fragment = new FragmentSprintReport();
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
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_demo, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvSprintReport);
        SprintReportAdapter sprintReportAdapter = new SprintReportAdapter(model.getListSprintDone());
        sprintReportAdapter.setOnItemClickCallback(new SprintReportAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Sprint data) {
                Intent intent = new Intent(getContext(),ActivityDetailSprint.class);
                intent.putExtra("sprint",data);
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(sprintReportAdapter);


        return view;
    }

}
