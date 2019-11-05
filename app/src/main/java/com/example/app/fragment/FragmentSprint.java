package com.example.app.fragment;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.ItemAdapter;
import com.example.app.model.Backlog;
import com.example.app.model.Sprint;
import com.woxthebox.draglistview.BoardView;
import com.woxthebox.draglistview.DragItemAdapter;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
    private TextView txtSprint;
    private TextView txtRemaining;
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

//                        Toast.makeText(mBoardView.getContext(), test.second, Toast.LENGTH_SHORT).show();
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
                    .appendDays().appendSuffix(" day ", "days ")
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
//                txtRemaining.setText("");
                txtRemaining.setVisibility(View.GONE);
            }
        }else{
            txtSprint.setText("No Active Sprint");
//            txtRemaining.setText("");
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
        final ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.column_item, R.id.item_layout, true);

        final View header = View.inflate(getContext(), R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.text)).setText(title);

        ((TextView) header.findViewById(R.id.item_count)).setText("" + list.size());
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = sCreatedItems++;
                Pair item = new Pair<>(id, "Test " + id);
                mBoardView.addItem(mBoardView.getColumnOfHeader(v), 0, item, true);
                ((TextView) header.findViewById(R.id.item_count)).setText(String.valueOf(mItemArray.size()));
            }
        });
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
    private AlertDialog.Builder builder;

    private void initializeAlertDialog(){
        builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Complete "+model.getCurrentSprint().getValue().getName()+"  ?");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        int lastID = model.getLargestSprintID()+1;
                        model.getListSprint().getValue().remove(model.getCurrentSprint().getValue());
                        model.getCurrentSprint().getValue().setEndda(new Date());
                        model.getCurrentSprint().getValue().setModifieddate(new Date());
                        model.getCurrentSprint().getValue().setModifiedby(model.getUser().getEmail());
                        model.getCurrentSprint().getValue().setStatus("Done");
                        Sprint sprint =model.getCurrentSprint().getValue();
                        Sprint newSprint = new Sprint(
                                model.getCurrentSprint().getValue().getIdProject()+"-S "+lastID,
                                model.getCurrentSprint().getValue().getIdProject(),
                                "Sprint "+lastID,
                                null,
                                null,
                                "",
                                "Not Active","",new Date(),model.getUser().getEmail(),null,null);
                        model.getListSprint().getValue().add(sprint);
                        model.getListSprint().getValue().add(newSprint);
                        model.getCurrentSprint().setValue(newSprint);
                        ArrayList<Backlog> list = new ArrayList<>();
                        for (int i = 0 ;i<mBoardView.getColumnCount();i++){
                            DragItemAdapter dragItemAdapter =mBoardView.getAdapter(i);
                            Log.d("Adapter", ((Integer) i).toString());
                            if (dragItemAdapter.getItemList().size()!=0){
                                for (int a= 0 ;a<dragItemAdapter.getItemList().size();a++){
                                    if (i!=2){
                                        ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setIdSprint(newSprint.getId());
                                    }else {
                                        ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setStatus("Done");
                                    }
                                    ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setModifiedby(model.getUser().getEmail());
                                    ((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second.setModifieddate(new Date());
                                    list.add(((Pair<Long, Backlog>) dragItemAdapter.getItemList().get(a)).second);
                                }
                            }
//                            list.addAll(dragItemAdapter.getItemList());
                            dragItemAdapter.getItemList().clear();
                            dragItemAdapter.notifyDataSetChanged();
                        }
                        model.completeSprint(list,sprint,newSprint);
                        txtSprint.setText("No Active Sprint");
                        txtRemaining.setText("");
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
}
