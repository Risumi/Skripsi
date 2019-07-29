package com.example.app;

import com.example.app.model.Sprint;

public interface ListenerGraphql {
    void startProgressDialog();
    void endProgressDialog();
    void startAlert(String error,String code);
    void setCurrentSprint(Sprint sprint);
    void setToast(String message);
}
