package com.example.app;

import com.example.app.model.Backlog;

public interface Listener {
    void setEmptyListTop(boolean visibility);
    void setEmptyListBottom(boolean visibility);
    void updateSprint(Backlog backlog,String todo);
}
