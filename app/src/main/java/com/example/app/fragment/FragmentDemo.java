package com.example.app.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.ItemAdapter;
import com.example.app.model.Backlog;
import com.woxthebox.draglistview.BoardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDemo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDemo extends Fragment {
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


    public FragmentDemo() {
        // Required empty public constructor
    }

    public static FragmentDemo newInstance(String param1, String param2) {
        FragmentDemo fragment = new FragmentDemo();
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
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        TextView txtSprint = view.findViewById(R.id.txtSprint);
        TextView txtRemaining = view.findViewById(R.id.txtRemaining);
        txtSprint.setText(Objects.requireNonNull(model.getCurrentSprint().getValue()).getName());

        mBoardView = view.findViewById(R.id.board_view);
        mBoardView.setSnapToColumnsWhenScrolling(true);
        mBoardView.setSnapToColumnWhenDragging(true);
        mBoardView.setSnapDragItemToTouch(true);
        mBoardView.setDragEnabled(true);
        mBoardView.setSnapToColumnInLandscape(false);
        mBoardView.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER);
        mBoardView.setColumnWidth((int) (getResources().getDisplayMetrics().widthPixels * 0.9));

        mBoardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) {
//                Toast.makeText(mBoardView.getContext(), "Start - column: " + column + " row: " + row, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
                if (fromColumn != toColumn) {
//                    Toast.makeText(mBoardView.getContext(), "End - column: " + toColumn + " row: " + toRow, Toast.LENGTH_SHORT).show();

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
        addColumn("To Do",model.getToDoBacklog().getValue());
        addColumn("In Progress",model.getOnProgressBacklog().getValue());
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

}
