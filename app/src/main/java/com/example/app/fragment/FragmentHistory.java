package com.example.app.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.adapter.ProjectHistoryAdapter;
import com.example.app.model.Project;
import com.example.app.utils.ListenerGraphqlHistory;
import com.example.app.utils.MainViewModel;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentHistory extends Fragment implements ListenerGraphqlHistory {
    final static String projectKey="project";
    private MainViewModel model;
    private Project project;
    private ProgressDialog progressDialog;
    private ProjectHistoryAdapter mAdapter;
    private RecyclerView listHistory;

    public FragmentHistory() {
        // Required empty public constructor
    }

    public static FragmentHistory newInstance(Project project) {
        FragmentHistory fragment = new FragmentHistory();
        Bundle args = new Bundle();
        args.putParcelable(projectKey,project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = getArguments().getParcelable(projectKey);
        }
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        model.instantiateListener(this);
        model.fetchProjectHistory(project.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listHistory = view.findViewById(R.id.list_history);
        listHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProjectHistoryAdapter(new ArrayList<>());
        listHistory.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void startProgressDialog() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
//            }
//        });
    }

    @Override
    public void endProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void setDataToRecyclerView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.setList(model.getListProjectHistory().getValue());
            }
        });
//        Log.d("Tes", ((Integer) model.getListProjectHistory().getValue().get(0).getDateTime().getDayOfMonth()).toString());
//        Log.d("Tes", ((Integer) model.getListProjectHistory().getValue().get(0).getDateTime().getMonthOfYear()).toString());
//        Log.d("Tes", ((Integer) model.getListProjectHistory().getValue().get(0).getDateTime().getYear()).toString());
//        Log.d("Tes", ((Integer) model.getListProjectHistory().getValue().get(0).getDateTime().getHourOfDay()).toString());
//        Log.d("Tes", ((Integer) model.getListProjectHistory().getValue().get(0).getDateTime().getMinuteOfHour()).toString());
    }

    @Override
    public void setToast(String message) {

    }
}
