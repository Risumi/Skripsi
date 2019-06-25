package com.example.app;
import com.example.app.model.Backlog;


public interface ListenerSprint {
    void setEmptyListTop(boolean visibility);
    void setEmptyListMiddle(boolean visibility);
    void setEmptyListBottom(boolean visibility);
    void setStatus(Backlog backlog, String status);
}
