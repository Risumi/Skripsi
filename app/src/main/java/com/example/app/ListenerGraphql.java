package com.example.app;

public interface ListenerGraphql {
    void startProgressDialog();
    void endProgressDialog();
    void startAlert(String error,String code);
}
