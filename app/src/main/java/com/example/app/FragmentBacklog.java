package com.example.app;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBacklog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog extends Fragment implements BacklogAdapter.BacklogViewHolder.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Backlog>listBacklog;

    private RecyclerView mRecyclerView;
    private BacklogAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
//    private ActivityMain activity ;

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
//            activity = ((ActivityMain)this.getActivity());
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
        listBacklog = new ArrayList<>();
        listBacklog.add(new Backlog("Recycler View","Complete", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        listBacklog.add(new Backlog("Burndown Chart","In Progress", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat chart untuk merepresentasikan backlog ke dalam bentuk chart sesuai dengan kaidah scrum"));
        listBacklog.add(new Backlog("Sprint","In Progress", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat automatisasi proses sprint"));
        mAdapter = new BacklogAdapter(view.getContext(), listBacklog,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutAnimation(animation);
        return view;
    }

    public void dataSet(Backlog backlog){
        listBacklog.add(backlog);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
//            activity.getSupportActionBar().hide();
            actionMode = ((AppCompatActivity)this.getActivity()).startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);
        return true;    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate (R.menu.selected_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    // TODO: actually remove items
                    Log.d(TAG, "menu_remove");
                    mAdapter.removeItems(mAdapter.getSelectedItems());
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            actionMode = null;
//            activity.getSupportActionBar().show();
        }
    }
}
