package com.example.app.utils;

public interface ListenerGraphql {
    void startProgressDialog();
    void endProgressDialog();
    void startAlert(String error,String code);
    void setToast(String message);
}
