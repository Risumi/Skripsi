package com.example.app;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Calendar;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Backlog>> listUserStories;
    private MutableLiveData<ArrayList<Backlog>> listSprint;
    private MutableLiveData<Sprint> sprint;

    public MainViewModel() {
        listUserStories = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        backlog.add(new Backlog("Recycler View","Completed", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat recycler view untuk menampilkan list backlog serta menghapus backlog ","","","",""));
        backlog.add(new Backlog("Burndown Chart","In Progress", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat chart untuk merepresentasikan backlog ke dalam bentuk chart sesuai dengan kaidah scrum","","","",""));
        backlog.add(new Backlog("Sprint","To Do", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"","Membuat automatisasi proses sprint","","","",""));
        listUserStories.setValue(backlog);

        ArrayList<Backlog> backlog2 = new ArrayList<>();
        listSprint = new MutableLiveData<>();
        listSprint.setValue(backlog2);

        sprint = new MutableLiveData<>();
    }

    private void  setListUserStories(ArrayList<Backlog> backlog){
        listUserStories.setValue(backlog);
    }

    public MutableLiveData<ArrayList<Backlog>> getListUserStories() {
        return listUserStories;
    }

    public MutableLiveData<ArrayList<Backlog>> getListSprint() {
        return listSprint;
    }

    public void setListSprint(ArrayList<Backlog> backlog){
        listSprint.setValue(backlog);
    }

    public  MutableLiveData<ArrayList<Backlog>> getToDoBacklog(){
        MutableLiveData<ArrayList<Backlog>> listToDo = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listSprint.getValue().size(); i++){
            if (listSprint.getValue().get(i).getStatus().equalsIgnoreCase("To Do")){
                backlog.add(listSprint.getValue().get(i));
            }
        }
        listToDo.setValue(backlog);
        return listToDo;
    }

    public MutableLiveData<ArrayList<Backlog>> getOnProgressBacklog(){
        MutableLiveData<ArrayList<Backlog>> listOnProgress = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listSprint.getValue().size(); i++){
            if (listSprint.getValue().get(i).getStatus().equalsIgnoreCase("In Progress")){
                backlog.add(listSprint.getValue().get(i));
            }
        }
        listOnProgress.setValue(backlog);
        return listOnProgress;
    }

    public MutableLiveData<ArrayList<Backlog>> getCompletedBacklog(){
        MutableLiveData<ArrayList<Backlog>> listCompleted = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listSprint.getValue().size(); i++){
            if (listSprint.getValue().get(i).getStatus().equalsIgnoreCase("Completed")){
                backlog.add(listSprint.getValue().get(i));
            }
        }
        listCompleted.setValue(backlog);
        return listCompleted;
    }

    public void setSprint(Sprint sprint){
        this.sprint.setValue(sprint);
    }

    public MutableLiveData<Sprint> getSprint(){
        return this.sprint;
    }
}
