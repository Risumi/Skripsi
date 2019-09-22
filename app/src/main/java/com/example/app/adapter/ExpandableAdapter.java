package com.example.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpandableAdapter extends AbstractExpandableItemAdapter <ExpandableAdapter.GVH, ExpandableAdapter.CVH>{
    List<MyGroupItem> mItems;
    static abstract class MyBaseItem {
        public final long id;
        public final String text;

        public MyBaseItem(long id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    static class MyGroupItem extends MyBaseItem {
        public final List<MyChildItem> children;

        public MyGroupItem(long id, String text) {
            super(id, text);
            children = new ArrayList<>();
        }
    }
    static class MyChildItem extends MyBaseItem {
        public MyChildItem(long id, String text) {
            super(id, text);
        }
    }


    public ExpandableAdapter() {
        setHasStableIds(true);
//        this.mItems = mItems;
        mItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyGroupItem group = new MyGroupItem(i, "GROUP " + i);
            for (int j = 0; j < 5; j++) {
                group.children.add(new MyChildItem(j, "child " + j));
            }
            mItems.add(group);
        }

    }


    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children count should be returned
     * @return the number of children
     */
    @Override
    public int getChildCount(int groupPosition) {
        return mItems.get(groupPosition).children.size();
    }

    /**
     * <p>Gets the ID for the group at the given position. This group ID must be unique across groups.</p>
     * <p>The combined ID (see {@link RecyclerViewExpandableItemManager#getCombinedGroupId(long)})
     * must be unique across ALL items (groups and all children).</p>
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return mItems.get(groupPosition).id;
    }

    /**
     * <p>Gets the ID for the given child within the given group.</p>
     * <p>This ID must be unique across all children within the group.
     * The combined ID (see {@link RecyclerViewExpandableItemManager#getCombinedChildId(long, long)})
     * must be unique across ALL items (groups and all children).</p>
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mItems.get(groupPosition).children.get(childPosition).id;
    }

    /**
     * Called when RecyclerView needs a new {@link GVH} of the given type to represent a group item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return A new group ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public GVH onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_epic2,parent,false);
        return new GVH(view);
    }

    /**
     * Called when RecyclerView needs a new {@link CVH} of the given type to represent a child item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return A new child ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public CVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_backlog2,parent,false);
        return new CVH(view);
    }

    /**
     * Called by RecyclerView to display the group data at the specified position.
     * This method should update the contents of the {@link RecyclerView.ViewHolder#itemView}
     * to reflect the item at the given position.
     *
     * @param holder        The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param groupPosition The position of the group item within the adapter's data set
     * @param viewType      The view type code
     */
    @Override
    public void onBindGroupViewHolder(@NonNull GVH holder, int groupPosition, int viewType) {
    }

    /**
     * Called by RecyclerView to display the child data at the specified position.
     * This method should update the contents of the {@link RecyclerView.ViewHolder#itemView}
     * to reflect the item at the given position.
     *
     * @param holder        The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param groupPosition The position of the group item within the adapter's data set
     * @param childPosition The position of the child item within the group
     * @param viewType      The view type code
     */
    @Override
    public void onBindChildViewHolder(@NonNull CVH holder, int groupPosition, int childPosition, int viewType) {

    }

    /**
     * <p>Called when a user attempt to expand/collapse a group item by tapping.</p>
     * <p>Tips: If you want to set your own click event listener to group items, make this method always return false.
     * It will disable auto expanding/collapsing when a group item is clicked.</p>
     *
     * @param holder        The ViewHolder which is associated to group item user is attempt to expand/collapse
     * @param groupPosition Group position
     * @param x             Touched X position. Relative from the itemView's top-left
     * @param y             Touched Y position. Relative from the itemView's top-left
     * @param expand        true: expand, false: collapse
     * @return Whether to perform expand/collapse operation.
     */
    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull GVH holder, int groupPosition, int x, int y, boolean expand) {
        return true;
    }

    class GVH extends AbstractExpandableItemViewHolder{
        public GVH(@NonNull View itemView) {
            super(itemView);
        }
    }

    class CVH extends AbstractExpandableItemViewHolder {
        public CVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
