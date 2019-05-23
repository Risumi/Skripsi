package com.example.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.model.Epic;
import com.example.app.adapter.EpicAdapter;
import com.example.app.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEpic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEpic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<Epic> listEpic;

    public FragmentEpic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEpic.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEpic newInstance(String param1, String param2) {
        FragmentEpic fragment = new FragmentEpic();
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
        View view = inflater.inflate(R.layout.fragment_epic, container, false);
        listEpic = new ArrayList<>();
        for (int i = 0 ; i< 5;i++){
            listEpic.add(new Epic(("Epic "+(Integer) (i+1)),(Integer) (i+2)+"Task ",(Integer) (i+2)+" Tasks"));
        }
        mRecyclerView=view.findViewById(R.id.rvTop);
        mAdapter = new EpicAdapter(this.getActivity(), listEpic);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return view;
    }
}