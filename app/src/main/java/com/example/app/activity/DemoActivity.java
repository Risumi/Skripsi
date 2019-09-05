package com.example.app.activity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapter.ItemAdapter;
import com.example.app.model.Backlog;
import com.woxthebox.draglistview.BoardView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DemoActivity extends AppCompatActivity {

    BoardView mBoardView;
    private static int sCreatedItems = 0;
    private int mColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mBoardView = findViewById(R.id.board_view);
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
                if (fromColumn != toColumn || fromRow != toRow) {
//                    Toast.makeText(mBoardView.getContext(), "End - column: " + toColumn + " row: " + toRow, Toast.LENGTH_SHORT).show();

                    try {
                        if (mBoardView.getAdapter(toColumn).getItemList().get(toRow) instanceof Pair){
                            Pair<Long, String> test = ((Pair<Long, String>) mBoardView.getAdapter(toColumn).getItemList().get(toRow));
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
//        addColumn("To Do");
//        addColumn("In Progress");
//        addColumn("Completed");
    }
//    private void addColumn(String title, ArrayList<Backlog> list) {
//        final ArrayList<Pair<Long, String>> mItemArray = new ArrayList<>();
//        int addItems = 15;
//        final int column = mColumns;
//        final ItemAdapter listAdapter = new ItemAdapter(m, R.layout.column_item, R.id.item_layout, true);
//
//        final View header = View.inflate(this, R.layout.column_header, null);
//        ((TextView) header.findViewById(R.id.text)).setText(title);
//
//        ((TextView) header.findViewById(R.id.item_count)).setText("" + addItems);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                long id = sCreatedItems++;
//                Pair item = new Pair<>(id, "Test " + id);
//                mBoardView.addItem(mBoardView.getColumnOfHeader(v), 0, item, true);
//                ((TextView) header.findViewById(R.id.item_count)).setText(String.valueOf(mItemArray.size()));
//            }
//        });
//        mBoardView.addColumn(listAdapter, header, null, false, new LinearLayoutManager(this));
//        mColumns++;
//    }
}
