package com.example.app.utils;

public interface ListenerGraphqlHistory {
    void startProgressDialog();
    void endProgressDialog();
    void setDataToRecyclerView();
    void setToast(String message);
}
