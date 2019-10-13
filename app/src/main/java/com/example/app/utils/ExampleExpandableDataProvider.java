/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.app.utils;


import com.example.app.model.Backlog;
import com.example.app.model.Sprint;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.core.util.Pair;

public class ExampleExpandableDataProvider extends AbstractExpandableDataProvider {
    private List<Pair<GroupData, List<ChildData>>> mData;

    // for undo group item
    private Pair<GroupData, List<ChildData>> mLastRemovedGroup;
    private int mLastRemovedGroupPosition = -1;

    // for undo child item
    private ChildData mLastRemovedChild;
    private long mLastRemovedChildParentGroupId = -1;
    private int mLastRemovedChildPosition = -1;

    public ExampleExpandableDataProvider(ArrayList<Sprint> listSprint, /*ArrayList<Backlog> listBacklogSprint, */ArrayList<Backlog> listBacklog) {
        mData = new LinkedList<>();

        Sprint bl= new Sprint("", "", "Backlog", null, null, "", "", "", null, "", null, "");
        Sprint Sprint = new Sprint("", "", "Sprint", null, null, "", "", "", null, "", null, "");
        ConcreteGroupData backlog = new ConcreteGroupData(0, bl);
        if (listSprint.size()!=0){
            Sprint = listSprint.get(listSprint.size()-1);
        }
        ConcreteGroupData sprint= new ConcreteGroupData(1, Sprint);

        List<ChildData> children1 = new ArrayList<>();
        List<ChildData> children2 = new ArrayList<>();

        for (int j = 0; j < listBacklog.size(); j++) {
            final long childId = backlog.generateNewChildId();
            Backlog issue = listBacklog.get(j);
//            Log.d("Sprint ID",Sprint.getId());
            if (!issue.getIdSprint().equals(Sprint.getId())){
                if (!issue.getStatus().equalsIgnoreCase("Done")){
                    children1.add(new ConcreteChildData(childId, issue));
                }
            }else {
                children2.add(new ConcreteChildData(childId, issue));
            }

        }
        mData.add(new Pair<>(backlog, children1));
        mData.add(new Pair<>(sprint, children2));
    }


    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    @Override
    public GroupData getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).first;
    }

    @Override
    public ChildData getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<ChildData> children = mData.get(groupPosition).second;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

    @Override
    public void moveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition == toGroupPosition) {
            return;
        }

        final Pair<GroupData, List<ChildData>> item = mData.remove(fromGroupPosition);
        mData.add(toGroupPosition, item);
    }

    @Override
    public void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if ((fromGroupPosition == toGroupPosition) && (fromChildPosition == toChildPosition)) {
            return;
        }

        final Pair<GroupData, List<ChildData>> fromGroup = mData.get(fromGroupPosition);
        final Pair<GroupData, List<ChildData>> toGroup = mData.get(toGroupPosition);

        final ConcreteChildData item = (ConcreteChildData) fromGroup.second.remove(fromChildPosition);

        if (toGroupPosition != fromGroupPosition) {
            // assign a new ID
            final long newId = ((ConcreteGroupData) toGroup.first).generateNewChildId();
            item.setChildId(newId);
        }

        toGroup.second.add(toChildPosition, item);
    }

    @Override
    public void removeGroupItem(int groupPosition) {
        mLastRemovedGroup = mData.remove(groupPosition);
        mLastRemovedGroupPosition = groupPosition;

        mLastRemovedChild = null;
        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
    }

    @Override
    public void removeChildItem(int groupPosition, int childPosition) {
        mLastRemovedChild = mData.get(groupPosition).second.remove(childPosition);
        mLastRemovedChildParentGroupId = mData.get(groupPosition).first.getGroupId();
        mLastRemovedChildPosition = childPosition;

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;
    }

    public void editChildItem(int groupPosition,int childPosition,Backlog backlog){
        mData.get(groupPosition).second.get(childPosition).setBacklog(backlog);
    }

    @Override
    public void editGroupItem(int groupPosition, Sprint sprint) {
        mData.get(groupPosition).first.setSprint(sprint);
    }

    @Override
    public void insertGroupItem(Sprint sprint) {
        final ConcreteGroupData group = new ConcreteGroupData(mData.size(), sprint);
        final List<ChildData> children = new ArrayList<>();

        mData.add(new Pair<>(group,children));
    }

    @Override
    public void insertChildItem(int groupPosition, Backlog backlog) {
        final long childId = ((ConcreteGroupData)mData.get(groupPosition).first).generateNewChildId();
        mData.get(groupPosition).second.add(new ConcreteChildData(childId,backlog));
    }


    @Override
    public long undoLastRemoval() {
        if (mLastRemovedGroup != null) {
            return undoGroupRemoval();
        } else if (mLastRemovedChild != null) {
            return undoChildRemoval();
        } else {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }
    }

    private long undoGroupRemoval() {
        int insertedPosition;
        if (mLastRemovedGroupPosition >= 0 && mLastRemovedGroupPosition < mData.size()) {
            insertedPosition = mLastRemovedGroupPosition;
        } else {
            insertedPosition = mData.size();
        }

        mData.add(insertedPosition, mLastRemovedGroup);

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;

        return RecyclerViewExpandableItemManager.getPackedPositionForGroup(insertedPosition);
    }

    private long undoChildRemoval() {
        Pair<GroupData, List<ChildData>> group = null;
        int groupPosition = -1;

        // find the group
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).first.getGroupId() == mLastRemovedChildParentGroupId) {
                group = mData.get(i);
                groupPosition = i;
                break;
            }
        }

        if (group == null) {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }

        int insertedPosition;
        if (mLastRemovedChildPosition >= 0 && mLastRemovedChildPosition < group.second.size()) {
            insertedPosition = mLastRemovedChildPosition;
        } else {
            insertedPosition = group.second.size();
        }

        group.second.add(insertedPosition, mLastRemovedChild);

        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
        mLastRemovedChild = null;

        return RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, insertedPosition);
    }

    public static final class ConcreteGroupData extends GroupData {

        private final long mId;
        private Sprint mSprint;
        private boolean mPinned;
        private long mNextChildId;

        ConcreteGroupData(long id, Sprint sprint) {
            mId = id;
            mSprint = sprint;
            mNextChildId = 0;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public void setSprint(Sprint sprint) {
            mSprint = sprint;
        }

//        @Override
//        public void setGroupId(int id) {
//            mId = id;
//        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public Sprint getSprint() {
            return mSprint;
        }



        public long generateNewChildId() {
            final long id = mNextChildId;
            mNextChildId += 1;
            return id;
        }
    }

    public static final class ConcreteChildData extends ChildData {

        private long mId;
        private Backlog mBacklog;

        ConcreteChildData(long id, Backlog backlog) {
            mId = id;
            mBacklog = backlog;
        }

        @Override
        public long getChildId() {
            return mId;
        }

        @Override
        public Backlog getBacklog() {
            return mBacklog;
        }

        public void setBacklog(Backlog backlog){
            mBacklog = backlog;
        }

        public void setChildId(long id) {
            this.mId = id;
        }
    }
}
