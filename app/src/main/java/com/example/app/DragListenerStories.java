package com.example.app;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;

public class DragListenerStories implements View.OnDragListener {

    private boolean isDropped = false;
    private Listener listener;

    DragListenerStories(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:

                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.FM1;
                final int tvEmptyListTop = R.id.tvEmptyListTop;
                final int tvEmptyListMiddle = R.id.tvEmptyListMiddle;
                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.rvTop;
                final int rvMiddle = R.id.rvMiddle;
                final int rvBottom = R.id.rvBottom;

//                Log.d("Drop",v.toString());
                switch (viewId) {
                    case flItem:
                    case tvEmptyListTop:
                    case tvEmptyListMiddle:
                    case tvEmptyListBottom:
                    case rvTop:
                    case rvMiddle:
                    case rvBottom:

                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case rvTop:
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
                            case tvEmptyListMiddle:
                            case rvMiddle:
                                target = (RecyclerView) v.getRootView().findViewById(rvMiddle);
                                break;
                            case tvEmptyListBottom:
                            case rvBottom:
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();
                            BacklogAdapter adapterSource = (BacklogAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            Backlog list = adapterSource.getList().get(positionSource);
                            ArrayList<Backlog> listSource = adapterSource.getList();
                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            BacklogAdapter adapterTarget = (BacklogAdapter) target.getAdapter();
                            ArrayList<Backlog> customListTarget = adapterTarget.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list);
                            } else {
                                customListTarget.add(list);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

                            if(sourceId == rvTop && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                                Log.d("setEmptyListTop","True");
                            }
                            if(viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false);
                                Log.d("setEmptyListTop","False");
                            }
                            if(sourceId == rvMiddle && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListMiddle(true);
                                Log.d("setEmptyListMiddle","True");
                            }
                            if(viewId == tvEmptyListMiddle) {
                                listener.setEmptyListMiddle(false);
                                Log.d("setEmptyListMiddle","False");
                            }
                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                                Log.d("setEmptyListBottom","True");
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                                Log.d("setEmptyListBottom","False");
                            }
                        }
                        break;
                }
                break;
        }
        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }
}