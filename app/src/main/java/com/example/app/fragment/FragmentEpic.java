package com.example.app.fragment;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.utils.MainViewModel;
import com.example.app.R;
import com.example.app.activity.ActivityEpic;
import com.example.app.adapter.EpicAdapter;
import com.example.app.model.Epic;
import com.example.app.model.Progress;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
    private EpicAdapter mAdapter;
    ArrayList<Epic> listEpic;
    private MainViewModel model;

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
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        model.getListEpic().observe(this, new Observer<ArrayList<Epic>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Epic> epics) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    final int REQ_EPIC = 7;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_epic, container, false);
        mRecyclerView=view.findViewById(R.id.rvTop);
        mAdapter = new EpicAdapter(this.getActivity(), model.getListEpic().getValue(),model.getListBacklog().getValue());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter.setListener(new EpicAdapter.ClickListener() {
            @Override
            public void onItemClicked(Epic epic) {
                Intent intent = new Intent(getContext(), ActivityEpic.class);
                intent.putExtra("epicID",epic.getId());
                intent.putExtra("epic",epic);
                intent.putExtra("User",model.getUser());
                getActivity().startActivityForResult(intent, REQ_EPIC);
            }
        });
        return view;
    }

    public void AddDataSet(Epic epic){
        model.createEpic(epic);
    }
    public void notifyAdapter(){
        mAdapter.notifyDataSetChanged();
    }

    public void deleteEpic(Epic epic){
        model.deleteEpic(epic,model.getUser().getEmail());
    }

    public void onDeleteCompleted(Epic epic) {
        model.removeEpic(epic);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }

    public void onCreateCompleted(Epic epic){
        model.getListEpic().getValue().add(epic);
        Progress progress = new Progress(epic.getId(),0,0);
        model.getListEpicProgress().getValue().add(progress);
        mAdapter.notifyDataSetChanged();

    }

    public void editEpic(Epic epic) {
        model.editEpicValue(epic);
        mAdapter.notifyDataSetChanged();
    }
}