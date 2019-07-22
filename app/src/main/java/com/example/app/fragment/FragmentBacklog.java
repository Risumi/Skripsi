package com.example.app.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ListenerAdapter;
import com.example.app.activity.ActivityStartSprint;
import com.example.app.model.Backlog;
import com.example.app.adapter.BacklogAdapter;
import com.example.app.Listener;
import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.model.Sprint;
import com.example.app.activity.ActivityAddBacklog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog extends Fragment implements Listener, BacklogAdapter.BacklogViewHolder2.ClickListener , AdapterView.OnItemSelectedListener, ListenerAdapter,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int  EDIT_BACKLOG = 2;
    // TODO: Rename and change types of parameters
    private String PID;
    private String mParam2;


    public FragmentBacklog() {
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
    public static FragmentBacklog newInstance(String param1, String param2)

    {
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
            PID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        model.getCurrentSprint().observe(this, new Observer<Sprint>() {
            @Override
            public void onChanged(@Nullable Sprint sprint) {
                if (sprint != null){
                    if (model.getListBacklog().getValue().size()==0){
                        tvEmptyListBottom.setVisibility(View.VISIBLE);
                    }
                    rvBottom.setVisibility(View.VISIBLE);
                    tvSprint.setVisibility(View.VISIBLE);
                }else {
                    tvEmptyListBottom.setVisibility(View.GONE);
                    rvBottom.setVisibility(View.GONE);
                    tvSprint.setVisibility(View.GONE);
                }
            }
        });
        model.getListBacklog().observe(this, new Observer<ArrayList<Backlog>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Backlog> backlogs) {
//                synchronized (topListAdapter){
                topListAdapter.notifyDataSetChanged();
                Log.d("Item Count : ", ((Integer) topListAdapter.getItemCount()).toString());
            }
        });
        model.instantiateListenerAdapter(this);

    }

    RecyclerView rvTop;
    RecyclerView rvBottom;

    TextView tvEmptyListTop;
    TextView tvEmptyListBottom;
    TextView tvSprint;
    private MainViewModel model;
    BacklogAdapter topListAdapter;
    BacklogAdapter bottomListAdapter;
    Button btnStartSprint;
    Spinner spinner,spinner2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog, container, false);
        rvTop = view.findViewById(R.id.rvTop);
        rvBottom = view.findViewById(R.id.rvBottom);
        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);
        tvSprint= view.findViewById(R.id.textView8);
        btnStartSprint = view.findViewById(R.id.button6);
        btnStartSprint.setOnClickListener(this);

        if (model.getListBacklog().getValue().size()==0){
            tvEmptyListTop.setVisibility(View.VISIBLE);
        }
        if(model.getCurrentSprint().getValue()!=null){
            if (model.getListBacklogSprint().getValue().size()==0){
                tvEmptyListBottom.setVisibility(View.VISIBLE);
            }
        }else {
            tvEmptyListBottom.setVisibility(View.GONE);
            rvBottom.setVisibility(View.GONE);
            tvSprint.setVisibility(View.GONE);
        }

        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        topListAdapter = new BacklogAdapter(model.getListBacklog().getValue(),this,this);
        rvTop.setAdapter(topListAdapter);
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

        rvBottom.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        bottomListAdapter = new BacklogAdapter(model.getListBacklogSprint().getValue(), this,this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());

        spinner = view.findViewById(R.id.spinner2);
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("All");
        spinnerArray.add("---");
        for (int i=0;i<model.getListEpic().getValue().size();i++){
            spinnerArray.add(model.getListEpic().getValue().get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,spinnerArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = view.findViewById(R.id.spinner5);
        List<String> spinnerArray2 = new ArrayList<>();
        int selection=0;
        for (int i=0;i<model.getListSprint().getValue().size();i++){
            if (model.getCurrentSprint().getValue().getId()==model.getListSprint().getValue().get(i).getId()){
                spinnerArray2.add(model.getListSprint().getValue().get(i).getId()+" (Active)");
                selection = i;
            }else {
                spinnerArray2.add(model.getListSprint().getValue().get(i).getId());
            }
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,spinnerArray2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        spinner2.setSelection(selection);
        return view;
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
        log();
    }

    @Override
    public void updateSprint(Backlog backlog,String todo) {
        if (todo.equalsIgnoreCase("update")){
            backlog.setIdSprint(model.getCurrentSprint().getValue().getId());
            model.editBacklog(backlog);
            model.updateList(backlog,"remove");
        }else if (todo.equalsIgnoreCase("remove")){
            backlog.setIdSprint("");
            model.editBacklog(backlog);
            model.updateList(backlog,"add");
        }
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
    public void onItemClicked(int position,Backlog backlog,BacklogAdapter adapter) {
        Intent editBacklog =new Intent(getContext(), ActivityAddBacklog.class);
        if (adapter==topListAdapter){
            Log.d("adapter","top");
            editBacklog.putExtra("adapter", "top");
            editBacklog.putExtra("backlog",model.getListBacklog().getValue().get(position));
            editBacklog.putExtra("blsID",model.getListBacklog().getValue().get(position).getId());
            editBacklog.putExtra("epicName",model.getEpicName(model.getListBacklog().getValue().get(position).getEpicName()));
            Log.d("Epic Name ",model.getEpicName(model.getListBacklog().getValue().get(position).getId()));
        }else if (adapter == bottomListAdapter){
            Log.d("adapter","bot");
            editBacklog.putExtra("adapter", "bot");
            editBacklog.putExtra("backlog",model.getListBacklogSprint().getValue().get(position));
            editBacklog.putExtra("blsID",model.getListBacklogSprint().getValue().get(position).getId());
            editBacklog.putExtra("epicName",model.getEpicName(model.getListBacklogSprint().getValue().get(position).getEpicName()));
//            Log.d("Epic Name ",model.getEpicName(model.getListBacklog().getValue().get(position).getId()));
        }

        Log.d("position", ((Integer) position).toString());
        ArrayList<String> spinnerArray = new ArrayList<>();
        ArrayList<String> idEpic = new ArrayList<>();
        spinnerArray.add("---");
        for (int i=0;i<model.getListEpic().getValue().size();i++){
            spinnerArray.add(model.getListEpic().getValue().get(i).getName());
            idEpic.add(model.getListEpic().getValue().get(i).getId());
        }
        editBacklog.putStringArrayListExtra("spinner",spinnerArray);
        editBacklog.putStringArrayListExtra("epicID",idEpic);
        editBacklog.putExtra("PID", PID);
        editBacklog.putExtra("position",position);
        editBacklog.putExtra("req code",EDIT_BACKLOG);
        editBacklog.putExtra("User",model.getUser());
        getActivity().startActivityForResult(editBacklog,EDIT_BACKLOG);
    }

    public void AddDataSet(Backlog backlog){
        model.getListBacklog().getValue().add(backlog);
        model.getListFilterBacklog().getValue().add(backlog);
        topListAdapter.notifyDataSetChanged();
        model.createBacklog(backlog);
    }

    public void EditDataSet(int position,Backlog backlog,String adapter){
        if (adapter.equalsIgnoreCase("top")){
            model.getListBacklog().getValue().set(position,backlog);
            model.getListFilterBacklog().getValue().set(position,backlog);
            topListAdapter.notifyDataSetChanged();
            model.editBacklog(backlog);
        }else {
            model.getListBacklogSprint().getValue().set(position,backlog);
            bottomListAdapter.notifyDataSetChanged();
            model.editBacklog(backlog);
        }
    }

    public void RemoveDataSet(int position,Backlog backlog,String adapter){
        if (adapter.equalsIgnoreCase("top")){
            model.getListBacklog().getValue().remove(position);
            model.getListFilterBacklog().getValue().remove(position);
            topListAdapter.notifyDataSetChanged();
            model.deleteBacklog(backlog);
        }else {
            model.getListBacklogSprint().getValue().remove(position);
            bottomListAdapter.notifyDataSetChanged();
            model.deleteBacklog(backlog);
        }
    }

    public void setSprint(Sprint sprint){
        model.createSprint(sprint);
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId()==spinner.getId()){
            Log.d("Index ke : ",Integer.toString(i));
            Log.d("Key :",adapterView.getAdapter().getItem(i).toString());
            if (adapterView.getAdapter().getItem(i).toString().equalsIgnoreCase("all")){
                model.filterBacklog("","all");
            }else{
                model.filterBacklog(model.getIDEpic(adapterView.getAdapter().getItem(i).toString()),"");
            }
            topListAdapter.notifyDataSetChanged();
        }else if (adapterView.getId()==spinner2.getId()){
            Log.d("Spinner 2","True");
            selectedSprint= model.getListSprint().getValue().get(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void notifyAdapter() {
        topListAdapter.notifyDataSetChanged();
        bottomListAdapter.notifyDataSetChanged();
    }
    Sprint selectedSprint;
    final int REQ_START_SPRINT = 5;
    @Override
    public void onClick(View view) {
        Date now = new Date();
        if (view == btnStartSprint){
            if (now.after(model.getCurrentSprint().getValue().getEndda())){
                Intent intent = new Intent(getActivity(),ActivityStartSprint.class);
                intent.putExtra("Sprint",selectedSprint);
                getActivity().startActivityForResult(intent,REQ_START_SPRINT);
            }else{
                Toast.makeText(this.getActivity(),"Can't start sprint, there is an active sprint",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
