package com.example.app.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapter.SprintAdapter;
import com.example.app.model.Backlog;
import com.example.app.model.Sprint;
import com.example.app.utils.MainViewModel;
import com.woxthebox.draglistview.BoardView;
import com.woxthebox.draglistview.DragItemAdapter;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import graphql.CompleteSprintMutation;
import type.BacklogInput;
import type.SprintInput;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BoardView mBoardView;
    private static int sCreatedItems = 0;
    private int mColumns;
    private MainViewModel model;
    private TextView txtSprint;
    private TextView txtRemaining;
    private AlertDialog.Builder builder;

    public FragmentSprint() {
        // Required empty public constructor
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sprint, container, false);
        setHasOptionsMenu(true);

        txtSprint = view.findViewById(R.id.txtSprint);

        txtRemaining = view.findViewById(R.id.txtRemaining);


        mBoardView = view.findViewById(R.id.board_view);
        mBoardView.setSnapToColumnsWhenScrolling(true);
        mBoardView.setSnapToColumnWhenDragging(true);
        mBoardView.setSnapDragItemToTouch(true);
        mBoardView.setDragEnabled(true);
        mBoardView.setSnapToColumnInLandscape(false);
        mBoardView.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER);
        mBoardView.setColumnWidth((int) (getResources().getDisplayMetrics().widthPixels * 0.333));

        mBoardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) {
//                Toast.makeText(mBoardView.getContext(), "Start - column: " + column + " row: " + row, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
                if (fromColumn != toColumn) {
                    try {
                        if (mBoardView.getAdapter(toColumn).getItemList().get(toRow) instanceof Pair){
                            Backlog test = ((Pair<Long, Backlog>) mBoardView.getAdapter(toColumn).getItemList().get(toRow)).second;
                            switch (toColumn){
                                case 0:
                                    test.setStatus("To Do");
                                    break;
                                case 1:
                                    test.setStatus("On Progress");
                                    break;
                                case 2:
                                    test.setStatus("Completed");
                                    break;
                            }
                            test.setModifieddate(new Date());
                            test.setModifiedby(model.getUser().getEmail());
                            model.editBacklog(test);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onItemChangedPosition(int oldColumn, int oldRow, int newColumn, int newRow) {
//                Toast.makeText(mBoardView.getContext(), "Position changed - column: " + newColumn + " row: " + newRow, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChangedColumn(int oldColumn, int newColumn) {
                TextView itemCount1 = mBoardView.getHeaderView(oldColumn).findViewById(R.id.item_count);
                itemCount1.setText(String.valueOf(mBoardView.getAdapter(oldColumn).getItemCount()));
                TextView itemCount2 = mBoardView.getHeaderView(newColumn).findViewById(R.id.item_count);
                itemCount2.setText(String.valueOf(mBoardView.getAdapter(newColumn).getItemCount()));
            }

            @Override
            public void onFocusedColumnChanged(int oldColumn, int newColumn) {
                //Toast.makeText(getContext(), "Focused column changed from " + oldColumn + " to " + newColumn, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragStarted(int position) {
                //Toast.makeText(getContext(), "Column drag started from " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragChangedPosition(int oldPosition, int newPosition) {
                //Toast.makeText(getContext(), "Column changed from " + oldPosition + " to " + newPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragEnded(int position) {
                //Toast.makeText(getContext(), "Column drag ended at " + position, Toast.LENGTH_SHORT).show();
            }
        });


        DateTime now = new DateTime(new Date());
        DateTime end  ;
        try {
            end = new DateTime(model.getCurrentSprint().getValue().getEndda());
//            Log.d("Now", ((Integer) now.getDayOfMonth()).toString());
//            Log.d("End",((Integer) end.getDayOfMonth()).toString());
            Period period = new Period(now, end, PeriodType.dayTime());
            PeriodFormatter formatter = new PeriodFormatterBuilder()
//                    .printZeroNever()
                    .appendDays().appendSuffix(" day ", " days ")
//                    .appendHours().appendSuffix(" hour ", " hours ")
//                    .appendMinutes().appendSuffix(" minute ", " minutes ")
//                    .appendSeconds().appendSuffix(" second ", " seconds ")
                    .toFormatter();
//            Log.d("Period",((Integer) diff.getDays()).toString());
            txtRemaining.setText(formatter.print(period)+" remaining");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if (model.getCurrentSprint().getValue().getName()!=null){
            if (model.getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")){
                txtSprint.setText(Objects.requireNonNull(model.getCurrentSprint().getValue()).getName());
                txtRemaining.setVisibility(View.VISIBLE);
            }else {
                txtSprint.setText("No Active Sprint");
                txtRemaining.setVisibility(View.GONE);
            }
        }else{
            txtSprint.setText("No Active Sprint");
            txtRemaining.setVisibility(View.GONE);
        }

        addColumn("To Do",model.getToDoBacklog().getValue());
        addColumn("On Progress",model.getOnProgressBacklog().getValue());
        addColumn("Completed",model.getCompletedBacklog().getValue());
        return view;
    }

    private void addColumn(String title, ArrayList<Backlog> list) {
        final ArrayList<Pair<Long, Backlog>> mItemArray = new ArrayList<>();

        final int column = mColumns;
        for (int i = 0; i < list.size(); i++) {
            long id = sCreatedItems++;
            mItemArray.add(new Pair<>(id, list.get(i)));
        }
        final SprintAdapter listAdapter = new SprintAdapter(mItemArray, R.layout.column_item, R.id.item_layout, true);

        final View header = View.inflate(getContext(), R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.text)).setText(title);

        ((TextView) header.findViewById(R.id.item_count)).setText("" + list.size());
        mBoardView.addColumn(listAdapter, header, null, false, new LinearLayoutManager(getContext()));
        mColumns++;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sprint_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.complete_sprint) {
//            model.editSprint(model.getCurrentSprint().getValue());
            if (model.getCurrentSprint().getValue().getName()!=null){
                initializeAlertDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeAlertDialog(){
        builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Complete "+model.getCurrentSprint().getValue().getName()+"  ?");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        model.completeSprint(completeSprint());
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public Backlog setStatusBacklog(int fromColumn, int toColumn,Backlog task){
        if (fromColumn != toColumn) {
            switch (toColumn){
                case 0:
                    task.setStatus("To Do");
                    break;
                case 1:
                    task.setStatus("On Progress");
                    break;
                case 2:
                    task.setStatus("Completed");
                    break;
            }
        }
        return task;
    }

    public CompleteSprintMutation completeSprint(){

        //Set objek sprint
        int lastID = model.getLargestSprintID()+1;
        model.getListSprint().getValue().remove(model.getCurrentSprint().getValue());
        model.getCurrentSprint().getValue().setEndda(new Date());
        model.getCurrentSprint().getValue().setModifieddate(new Date());
        model.getCurrentSprint().getValue().setModifiedby(model.getUser().getEmail());
        model.getCurrentSprint().getValue().setStatus("Done");
        Sprint sprint =model.getCurrentSprint().getValue();

        //Set objek sprint baru
        Sprint newSprint = new Sprint(
                model.getCurrentSprint().getValue().getIdProject()+"-S "+lastID,
                model.getCurrentSprint().getValue().getIdProject(),
                "Sprint "+lastID,
                null,
                null,
                "",
                "Not Active","",new Date(),model.getUser().getEmail(),null,null);

        //Set sprint
        model.getListSprint().getValue().add(sprint);
        model.getListSprint().getValue().add(newSprint);
        model.getCurrentSprint().setValue(newSprint);

        //Set backlog
        ArrayList<Backlog> list = new ArrayList<>();
        for (int i = 0 ;i<mBoardView.getColumnCount();i++){
            DragItemAdapter dragItemAdapter =mBoardView.getAdapter(i);
            Log.d("Adapter", ((Integer) i).toString());
            if (dragItemAdapter.getItemList().size()!=0){
                for (int a= 0 ;a<dragItemAdapter.getItemList().size();a++){
                    Log.d("Tes", ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.getId()+" "+((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.getStatus());
                    if (i!=2){
                        list.add(((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second);
                        ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setIdSprint(newSprint.getId());
                    }else {
                        ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setStatus("Done");
                    }
                    ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setModifiedby(model.getUser().getEmail());
                    ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setModifieddate(new Date());
                    list.add(((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second);
                }
            }
            dragItemAdapter.getItemList().clear();
            dragItemAdapter.notifyDataSetChanged();
        }

        //api call
        CompleteSprintMutation sprintMutation = wrapCompleteSprint(list,sprint,newSprint);
        txtSprint.setText("No Active Sprint");
        txtRemaining.setText("");
        return sprintMutation;

    }

    public void setList(ArrayList<Backlog> list, int i){
        DragItemAdapter dragItemAdapter =mBoardView.getAdapter(i);
        final ArrayList<Pair<Long, Backlog>> mItemArray = new ArrayList<>();

        for (int j = 0; j < list.size(); j++) {
            long id = sCreatedItems++;
            mItemArray.add(new Pair<>(id, list.get(j)));
        }

        dragItemAdapter.setItemList(mItemArray);
    }

    public  void setmColumns(){

    }

    public CompleteSprintMutation wrapCompleteSprint(ArrayList<Backlog> listB, Sprint sprint, Sprint newSprint){

        List<BacklogInput> backlogInputs= new ArrayList<>();
        for (int i = 0 ; i< listB.size();i++){
            Backlog backlog = listB.get(i);
            backlogInputs.add(BacklogInput.builder()
                    .idBacklog(backlog.getId())
                    .idSprint(backlog.getIdSprint())
                    .status(backlog.getStatus())
                    .date(new Date())
                    .build());
        }

        SprintInput newSprintInput  = SprintInput.builder()
                .id(newSprint.getId())
                .idProject(newSprint.getIdProject())
                .name(newSprint.getName())
                .goal(newSprint.getSprintGoal())
                .status(newSprint.getStatus())
                .createddate(new Date())
                .createdby(model.getUser().getEmail())
                .build();

        SprintInput sprintInput  = SprintInput.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .begindate(sprint.getBegda())
                .enddate(sprint.getEndda())
                .goal(sprint.getSprintGoal())
                .status(sprint.getStatus())
                .retrospective(" ")
                .modifieddate(new Date())
                .modifiedby(model.getUser().getEmail())
                .build();

        CompleteSprintMutation completeSprintMutation= CompleteSprintMutation.builder()
                .newSprint(newSprintInput)
                .sprint(sprintInput)
                .backlog(backlogInputs)
                .modifiedby(model.getUser().getEmail())
                .oldSprint(sprint.getId())
                .build();

        return  completeSprintMutation;
    }
}
