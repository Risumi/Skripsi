package com.example.app.utils;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;


public interface ListenerData {
    void onDeleteFinished(Epic epic);
    void onCreateFinished(Epic epic);
    void onCreateFinished(Backlog backlog);
    void onDeleteFinished(int groupPos,int childPos,Backlog backlog);
}
