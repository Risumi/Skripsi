package com.example.app.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.activity.ActivityAddBacklog;
import com.example.app.activity.ActivityStartSprint;
import com.example.app.adapter.ExpandableDraggableAdapter;
import com.example.app.model.Backlog;
import com.example.app.model.Sprint;
import com.example.app.utils.AbstractExpandableDataProvider;
import com.example.app.utils.ExpandableDataProvider;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBacklog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBacklog extends Fragment implements RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;

    private ExpandableDataProvider mDataProvider;
    private ExpandableDraggableAdapter myItemAdapter;
    private final int REQ_START_SPRINT = 5;
    private final int REQ_EDIT_SPRINT_ACTIVE = 6;
    private final int REQ_EDIT_SPRINT_NOT_ACTIVE = 8;
    private final int REQ_EDIT_BACKLOG = 2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private MainViewModel model;

    private String PID;



    public FragmentBacklog() {
        // Required empty public constructor
    }

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
            PID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        mDataProvider = new ExpandableDataProvider(model.getCurrentSprint().getValue(),model.getListBacklog().getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_backlog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.rvDemo);
        mLayoutManager = new LinearLayoutManager(requireContext());

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        mRecyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        mRecyclerViewExpandableItemManager.setOnGroupCollapseListener(this);
        mRecyclerViewExpandableItemManager.setDefaultGroupsExpandedState(true);

        // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);

        // drag & drop manager
        mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
//        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
//                (NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z3));

        //adapter

        myItemAdapter = new ExpandableDraggableAdapter(mRecyclerViewExpandableItemManager, mDataProvider);

        myItemAdapter.setEventListener(new ExpandableDraggableAdapter.EventListener() {

            @Override
            public void onDragFinished(int fromGroupPosition,int toGroupPosition, int toChildPosition) {
                if (fromGroupPosition!=toGroupPosition){
                    Backlog backlog = getDataProvider().getChildItem(toGroupPosition,toChildPosition).getBacklog();
                    if (toGroupPosition==0){
                        backlog.setIdSprint("");
                        model.updateList(backlog,"add");
                    }else {
                        Log.d("ID Sprint",getDataProvider().getGroupItem(toGroupPosition).getSprint().getId());
                        backlog.setIdSprint(getDataProvider().getGroupItem(toGroupPosition).getSprint().getId());
                        model.updateList(backlog,"remove");
                    }
                    backlog.setModifieddate(new Date());
                    backlog.setModifiedby(model.getUser().getEmail());
                    model.editBacklog(backlog);
                }
            }

            @Override
            public void onItemViewClicked(View v) {
                onItemViewClick(v);
            }

            @Override
            public void onMenuClicked(MenuItem m,int GroupPos) {
                switch (m.getItemId()){
                    case R.id.start_sprint:
                        if (!model.getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")){
                            Intent intent = new Intent(getActivity(), ActivityStartSprint.class);
                            intent.putExtra("Sprint",getDataProvider().getGroupItem(GroupPos).getSprint());
                            intent.putExtra("User",model.getUser());
//                            intent.putExtra("indexSprint",model.getListSprint().getValue().indexOf(getDataProvider().getGroupItem(GroupPos).getSprint()));
                            getActivity().startActivityForResult(intent,REQ_START_SPRINT);
                        }else{
                            Toast.makeText(getActivity(),"Can't start sprint, there is an active sprint",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.edit_sprint:
                        if (model.getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")){
                            Intent intent = new Intent(getActivity(), ActivityStartSprint.class);
                            intent.putExtra("Sprint",getDataProvider().getGroupItem(GroupPos).getSprint());
                            intent.putExtra("User",model.getUser());
                            intent.putExtra("Req code",REQ_EDIT_SPRINT_ACTIVE);
                            getActivity().startActivityForResult(intent,REQ_EDIT_SPRINT_ACTIVE);
                        }else{
                            Intent intent = new Intent(getActivity(), ActivityStartSprint.class);
                            intent.putExtra("Sprint",getDataProvider().getGroupItem(GroupPos).getSprint());
                            intent.putExtra("User",model.getUser());
                            intent.putExtra("Req code",REQ_EDIT_SPRINT_NOT_ACTIVE);
                            getActivity().startActivityForResult(intent,REQ_EDIT_SPRINT_NOT_ACTIVE);
                        }
                        break;
                }
            }
        });

        mAdapter = myItemAdapter;

        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);       // wrap for expanding
        mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(mWrappedAdapter);           // wrap for dragging

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Disable the change animation in order to make turning back animation of swiped item works properly.
        // Also need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

        // additional decorations
        //noinspection StatementWithEmptyBody
//        if (supportsViewElevation()) {
//            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
//        } else {
//            mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z1)));
//        }
//        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(requireContext(), R.drawable.list_divider_h), true));


        // NOTE:
        // The initialization order is very important! This order determines the priority of touch event handling.
        //
        // priority: TouchActionGuard > Swipe > DragAndDrop > ExpandableItem
        mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecyclerView);
        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
        mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save current state to support screen rotation, etc...
        if (mRecyclerViewExpandableItemManager != null) {
            outState.putParcelable(
                    SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
                    mRecyclerViewExpandableItemManager.getSavedState());
        }
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager.release();
            mRecyclerViewDragDropManager = null;
        }

        if (mRecyclerViewTouchActionGuardManager != null) {
            mRecyclerViewTouchActionGuardManager.release();
            mRecyclerViewTouchActionGuardManager = null;
        }

        if (mRecyclerViewExpandableItemManager != null) {
            mRecyclerViewExpandableItemManager.release();
            mRecyclerViewExpandableItemManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        mAdapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition);
        }
    }

    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height);
        int topMargin = (int) (getActivity().getResources().getDisplayMetrics().density * 16); // top-spacing: 16dp
        int bottomMargin = topMargin; // bottom-spacing: 16dp

        mRecyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, bottomMargin);
    }

    private void onItemViewClick(View v) {
        final int flatPosition = mRecyclerView.getChildAdapterPosition(v);

        if (flatPosition == RecyclerView.NO_POSITION) {
            return;
        }

        final long expandablePosition = mRecyclerViewExpandableItemManager.getExpandablePosition(flatPosition);
        final int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
        final int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(expandablePosition);

        if (childPosition == RecyclerView.NO_POSITION) {
//            ((ActivityMain) getActivity()).onGroupItemClicked
//            (groupPosition);
        } else {
            Intent editBacklog =new Intent(getContext(), ActivityAddBacklog.class);
            int backlogPos = (0);
            if (groupPosition== backlogPos){
                editBacklog.putExtra("backlog",getDataProvider().getChildItem(backlogPos,childPosition).getBacklog());
                editBacklog.putExtra("index",model.getListBacklog().getValue().indexOf(getDataProvider().getChildItem(backlogPos,childPosition).getBacklog()));

            }else {
                editBacklog.putExtra("backlog",getDataProvider().getChildItem(groupPosition,childPosition).getBacklog());
                editBacklog.putExtra("index",model.getListBacklog().getValue().indexOf(getDataProvider().getChildItem(groupPosition,childPosition).getBacklog()));
            }

            ArrayList<String> spinnerArray = new ArrayList<>();
            ArrayList<String> idEpic = new ArrayList<>();
            for (int i=0;i<model.getListEpic().getValue().size();i++){
                spinnerArray.add(model.getListEpic().getValue().get(i).getName());
                idEpic.add(model.getListEpic().getValue().get(i).getId());
            }
            editBacklog.putStringArrayListExtra("spinner",spinnerArray);
            editBacklog.putStringArrayListExtra("epicID",idEpic);
            ArrayList<String> spinnerArray2 = new ArrayList<>();
            ArrayList<String> emailUser = new ArrayList<>();
            for (int i=0;i<model.getListUser().getValue().size();i++){
                spinnerArray2.add(model.getListUser().getValue().get(i).getName());
                emailUser.add(model.getListUser().getValue().get(i).getEmail());
            }
            editBacklog.putStringArrayListExtra("spinner2",spinnerArray2);
            editBacklog.putStringArrayListExtra("emailUser",emailUser);
            editBacklog.putExtra("PID", PID);

            editBacklog.putExtra("group pos",groupPosition);
            editBacklog.putExtra("child pos",childPosition);
            editBacklog.putExtra("req code", REQ_EDIT_BACKLOG);
            editBacklog.putExtra("User",model.getUser());
            getActivity().startActivityForResult(editBacklog, REQ_EDIT_BACKLOG);
        }
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    public AbstractExpandableDataProvider getDataProvider() {
        return mDataProvider;
    }

    public void notifyGroupItemChanged(int groupPosition) {
        final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForGroup(groupPosition);
        final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);

        mAdapter.notifyItemChanged(flatPosition);
    }

    public void notifyChildItemChanged(int groupPosition, int childPosition) {
        final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, childPosition);
        final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);
        mAdapter.notifyItemChanged(flatPosition);
    }

    public void AddDataSet(Backlog backlog){
        model.createBacklog(backlog);
    }

    public void onAddCompleted(Backlog backlog){
        model.getListBacklog().getValue().add(backlog);
        getDataProvider().insertChildItem(0,backlog);
        mAdapter.notifyDataSetChanged();
    }


    public void EditDataSet(int groupPos,int childPos,Backlog backlog,int index,int index2){
        int backlogPos = (0);
    //    int allBacklogIdx = model.indexBacklog(backlog,model.getListAllBacklog().getValue());
    //    model.getListAllBacklog().getValue().set(allBacklogIdx,backlog);
        Log.d("Group ",Integer.toString(groupPos));
        Log.d("Child ",Integer.toString(childPos));
        Log.d("Index",Integer.toString(index));
        if (groupPos == backlogPos){
            model.getListBacklog().getValue().set(index,backlog);
        }else {
            model.getListBacklog().getValue().set(index,backlog);
            if (index2!=-1){
    //            model.getListFilterBacklogSprint().getValue().set(index2,backlog);
            }

        }
        getDataProvider().editChildItem(groupPos,childPos,backlog);
    //    mRecyclerViewExpandableItemManager.notifyChildItemChanged(groupPos,childPos,backlog);
        notifyChildItemChanged(groupPos,childPos);
        mAdapter.notifyDataSetChanged();
        model.editBacklog(backlog);
    }
    public void RemoveDataSet(int groupPos,int childPos,Backlog backlog){
        model.deleteBacklog(groupPos,childPos,backlog);
//        int backlogPos = (0);
//        if (groupPos != backlogPos){
//            getDataProvider().removeChildItem(groupPos,childPos);
//            mAdapter.notifyDataSetChanged();
//        }else {
//            getDataProvider().removeChildItem(groupPos,childPos);
//            mAdapter.notifyDataSetChanged();
//
//        }
    }

    public void startSprint(Sprint sprint){
        model.editSprint(sprint);
//        model.getListSprint().getValue().set(model.getListSprint().getValue().size()-1,sprint);
//        getDataProvider().insertGroupItem(sprint);
        getDataProvider().editGroupItem(1,sprint);
        mAdapter.notifyDataSetChanged();
        model.setCurrentSprint(sprint);
    }

    public void onDeleteCompleted(int groupPos,int childPos,Backlog backlog) {
        int backlogPos = (0);
        if (groupPos != backlogPos){
            getDataProvider().removeChildItem(groupPos,childPos);
            mAdapter.notifyDataSetChanged();

        }else {
            getDataProvider().removeChildItem(groupPos,childPos);
            mAdapter.notifyDataSetChanged();
        }
    }
}