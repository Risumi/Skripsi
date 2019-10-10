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

public abstract class AbstractExpandableDataProvider {
    static abstract class BaseData {

    }

    public static abstract class GroupData extends BaseData {
        public abstract boolean isSectionHeader();
        public abstract long getGroupId();
        public abstract void setSprint(Sprint sprint);
//        public abstract void setGroupId(int id);
        public abstract Sprint getSprint();
    }

    public static abstract class ChildData extends BaseData {
        public abstract long getChildId();
        public abstract void setBacklog (Backlog backlog);
        public abstract Backlog getBacklog();
    }

    public abstract int getGroupCount();
    public abstract int getChildCount(int groupPosition);

    public abstract GroupData getGroupItem(int groupPosition);
    public abstract ChildData getChildItem(int groupPosition, int childPosition);

    public abstract void moveGroupItem(int fromGroupPosition, int toGroupPosition);
    public abstract void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition);

    public abstract void removeGroupItem(int groupPosition);
    public abstract void removeChildItem(int groupPosition, int childPosition);

    public abstract void insertGroupItem(Sprint sprint);
    public abstract void insertChildItem(int groupPosition, Backlog backlog);

    public abstract long undoLastRemoval();

    public abstract void editChildItem(int groupPosition,int childPosition,Backlog backlog);
    public abstract void editGroupItem(int groupPosition,Sprint sprint);
}
