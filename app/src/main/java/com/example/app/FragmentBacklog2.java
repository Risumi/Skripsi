package com.example.app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog2 extends Fragment implements Listener ,BacklogAdapter2.BacklogViewHolder2.ClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int  EDIT_BACKLOG = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentBacklog2() {
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
    public static FragmentBacklog2 newInstance(String param1, String param2) {
        FragmentBacklog2 fragment = new FragmentBacklog2();
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
    private BacklogViewModel model;
    BacklogAdapter2 topListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog, container, false);
        model = ViewModelProviders.of(this.getActivity()).get(BacklogViewModel.class);
        rvTop = view.findViewById(R.id.rvTop);
        rvBottom = view.findViewById(R.id.rvBottom);
        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);
        if (model.getListUserStories().getValue().size()==0){
            tvEmptyListTop.setVisibility(View.VISIBLE);
        }
        if (model.getListBacklog().getValue().size()==0){
            tvEmptyListBottom.setVisibility(View.VISIBLE);

        }

        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        topListAdapter = new BacklogAdapter2(model.getListUserStories().getValue(),this,this);
        rvTop.setAdapter(topListAdapter);
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

        rvBottom.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        BacklogAdapter2 bottomListAdapter = new BacklogAdapter2(model.getListBacklog().getValue(), this,this);
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
//        rvMiddle.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    public void AddDataSet(Backlog backlog){
        model.getListUserStories().getValue().add(backlog);
        topListAdapter.notifyDataSetChanged();

    }

    public void EditDataSet(int position,Backlog backlog){
        model.getListUserStories().getValue().set(position,backlog);
//        Log.d("position", ((Integer) position).toString());
        topListAdapter.notifyDataSetChanged();
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
        Intent editBacklog =new Intent(getContext(),ActivityAddBacklog.class);
        editBacklog.putExtra("backlog",model.getListUserStories().getValue().get(position));
        Log.d("position", ((Integer) position).toString());
        editBacklog.putExtra("position",position);
        editBacklog.putExtra("req code",EDIT_BACKLOG);
        getActivity().startActivityForResult(editBacklog,EDIT_BACKLOG);
    }
}
