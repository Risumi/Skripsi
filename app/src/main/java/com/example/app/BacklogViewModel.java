package com.example.app;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Calendar;

public class BacklogViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Backlog>> listUserStories;
    private MutableLiveData<ArrayList<Backlog>> listBacklog;

    public BacklogViewModel() {
        listUserStories = new MutableLiveData<>();
        listBacklog = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        ArrayList<Backlog> backlog2 = new ArrayList<>();
        listBacklog.setValue(backlog2);
        backlog.add(new Backlog("Recycler View","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog "));
        backlog.add(new Backlog("Burndown Chart","In Progress", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat chart untuk merepresentasikan backlog ke dalam bentuk chart sesuai dengan kaidah scrum"));
        backlog.add(new Backlog("Sprint","To Do", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat automatisasi proses sprint"));
        listUserStories.setValue(backlog);
    }

    private void  setListUserStories(ArrayList<Backlog> backlog){
        listUserStories.setValue(backlog);
    }

    public MutableLiveData<ArrayList<Backlog>> getListUserStories() {
        return listUserStories;
    }

    public MutableLiveData<ArrayList<Backlog>> getListBacklog() {
        return listBacklog;
    }

    private void  setListBacklog (ArrayList<Backlog> backlog){
        listBacklog.setValue(backlog);
    }

    public  MutableLiveData<ArrayList<Backlog>> getToDoBacklog(){
        MutableLiveData<ArrayList<Backlog>> listToDo = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listUserStories.getValue().size(); i++){
            if (listUserStories.getValue().get(i).getStatus().equalsIgnoreCase("To Do")){
                backlog.add(listUserStories.getValue().get(i));
            }
        }
        listToDo.setValue(backlog);
        return listToDo;
    }

    public MutableLiveData<ArrayList<Backlog>> getOnProgressBacklog(){
        MutableLiveData<ArrayList<Backlog>> listOnProgress = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listUserStories.getValue().size(); i++){
            if (listUserStories.getValue().get(i).getStatus().equalsIgnoreCase("In Progress")){
                backlog.add(listUserStories.getValue().get(i));
            }
        }
        listOnProgress.setValue(backlog);
        return listOnProgress;
    }

    public MutableLiveData<ArrayList<Backlog>> getCompletedBacklog(){
        MutableLiveData<ArrayList<Backlog>> listCompleted = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listUserStories.getValue().size(); i++){
            if (listUserStories.getValue().get(i).getStatus().equalsIgnoreCase("Completed")){
                backlog.add(listUserStories.getValue().get(i));
            }
        }
        listCompleted.setValue(backlog);
        return listCompleted;
    }
}
