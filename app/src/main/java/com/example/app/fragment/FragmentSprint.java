package com.example.app.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.ListenerSprint;
import com.example.app.model.Backlog;
import com.example.app.Listener;
import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.SprintAdapter;
import com.example.app.model.Sprint;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprint extends Fragment implements ListenerSprint, View.OnClickListener {
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
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }
    ArrayList<Backlog> listBacklog, listBacklog2,listBacklog3;

    RecyclerView rvTop;
    RecyclerView rvBottom;
    RecyclerView rvMiddle;
    TextView tvEmptyListTop;
    TextView tvEmptyListBottom;
    TextView tvEmptyListMiddle;
    TextView tvSprint;
    TextView tvRemaining;
    Button btnSprint;
    private MainViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sprint, container, false);

        btnSprint = view.findViewById(R.id.button2);
        btnSprint.setOnClickListener(this);
        tvSprint = view.findViewById(R.id.textView7);
        tvRemaining = view.findViewById(R.id.tvRemaining);
        DateTime now = new DateTime(new Date());
        DateTime end = new DateTime(model.getCurrentSprint().getValue().getEndda());
        Period diff = new Period(now, end);

        tvRemaining.setText(((Integer) diff.getDays()).toString()+" days remaining");
        if (model.getCurrentSprint().getValue()!=null){
            btnSprint.setVisibility(View.VISIBLE);
            tvSprint.setText(model.getCurrentSprint().getValue().getName());
            Date date = new Date();
//            Date date1 = new Date(1999,01,01);
//            if (model.getCurrentSprint().getValue().getBegda().equals(date1)){
//                btnSprint.setText("Standby");
//            }
//            if (date.before(model.getCurrentSprint().getValue().getEndda())){
//                btnSprint.setText("Running");
//            }else if (date.after(model.getCurrentSprint().getValue().getEndda())){
//                btnSprint.setText("Finished");
//            }
        }else {
            btnSprint.setVisibility(View.GONE);
        }

        rvTop = view.findViewById(R.id.rvTop);
        rvBottom = view.findViewById(R.id.rvBottom);
        rvMiddle = view.findViewById(R.id.rvMiddle);
        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
        tvEmptyListMiddle = view.findViewById(R.id.tvEmptyListMiddle);
        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);

        if (model.getToDoBacklog().getValue().size()==0){
            tvEmptyListTop.setVisibility(View.VISIBLE);
        }
        if (model.getOnProgressBacklog().getValue().size()==0){
            tvEmptyListMiddle.setVisibility(View.VISIBLE);
        }
        if (model.getCompletedBacklog().getValue().size()==0){
            tvEmptyListBottom.setVisibility(View.VISIBLE);
        }

        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter topListAdapter = new SprintAdapter(model.getToDoBacklog().getValue(),model.getListEpic().getValue(),this);
        rvTop.setAdapter(topListAdapter);
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

        rvMiddle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter middleListAdapter = new SprintAdapter(model.getOnProgressBacklog().getValue(),model.getListEpic().getValue(), this);
        rvMiddle.setAdapter(middleListAdapter);
        tvEmptyListMiddle.setOnDragListener(middleListAdapter.getDragInstance());
        rvMiddle.setOnDragListener(middleListAdapter.getDragInstance());

        rvBottom.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        SprintAdapter bottomListAdapter = new SprintAdapter(model.getCompletedBacklog().getValue(),model.getListEpic().getValue(), this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());

        return view;
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
//        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void setEmptyListMiddle(boolean visibility) {
        tvEmptyListMiddle.setVisibility(visibility ? View.VISIBLE : View.GONE);
//        rvMiddle.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
//        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void setStatus(Backlog backlog, String status) {
        if (status.equalsIgnoreCase("Top")){
            backlog.setStatus("To Do");
            model.editBacklog(backlog);
        }else if (status.equalsIgnoreCase("Middle")){
            backlog.setStatus("On Progress");
            model.editBacklog(backlog);
        }else if (status.equalsIgnoreCase("Bottom")){
            backlog.setStatus("Completed");
            model.editBacklog(backlog);
        }
    }

    void log(){
        Log.d("rvTop", ((Integer) rvTop.getVisibility()).toString());
        Log.d("rvMiddle",((Integer) rvMiddle.getVisibility()).toString());
        Log.d("rvBottom",((Integer) rvBottom.getVisibility()).toString());
        Log.d("tvTop", ((Integer) tvEmptyListTop.getVisibility()).toString());
        Log.d("tvMiddle",((Integer) tvEmptyListMiddle.getVisibility()).toString());
        Log.d("tvBottom",((Integer) tvEmptyListBottom.getVisibility()).toString());
    }

    @Override
    public void onClick(View view) {
        if (view == btnSprint){
            initializeAlertDialog();
            builder.show();
        }
    }

    AlertDialog.Builder builder;

    void initializeAlertDialog(){
        builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Complete "+model.getCurrentSprint().getValue().getName()+"  ?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        model.getCurrentSprint().getValue().setEndda(new Date());
                        model.getCurrentSprint().getValue().setModifieddate(new Date());
                        model.getCurrentSprint().getValue().setModifiedby(model.getUser().getEmail());
                        model.editSprint(model.getCurrentSprint().getValue());
                        model.getCurrentSprint().setValue(null);
                        model.getListBacklogSprint().getValue().clear();
                        tvSprint.setText("Sprint");
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
    }

}