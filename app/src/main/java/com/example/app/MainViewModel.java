package com.example.app;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import type.CustomType;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Backlog>> listUserStories;
    private MutableLiveData<ArrayList<Backlog>> listSprint;
    private MutableLiveData<Sprint> sprint;
    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql/graphql";

    public MainViewModel() {
        listUserStories = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        listUserStories.setValue(backlog);
        ArrayList<Backlog> backlog2 = new ArrayList<>();
        fetchBacklog();
        listSprint = new MutableLiveData<>();
        listSprint.setValue(backlog2);
        sprint = new MutableLiveData<>();
    }

    protected ArrayList<Backlog> fetchBacklog(){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        CustomTypeAdapter <Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
            @Override public Date decode(CustomTypeValue value) {
                try {
                    return formatDate.parse(value.value.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override public CustomTypeValue encode(Date value) {
                return new CustomTypeValue.GraphQLString(formatDate.format(value));
            }
        };
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.DATE,dateCustomTypeAdapter)
                .build();
        ArrayList<Backlog> backlog = new ArrayList<>();
        BacklogQuery backlogQuery= BacklogQuery.builder().id("IS").build();
        apolloClient.query(backlogQuery).enqueue(new ApolloCall.Callback<BacklogQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogQuery.Data> response) {
                for (int i = 0 ; i<response.data().backlog.size();i++){
                    backlog.add(new Backlog(response.data().backlog.get(i).name,
                            response.data().backlog.get(i).status,
                            response.data().backlog.get(i).begindate,
                            response.data().backlog.get(i).enddate,
                            "",
                            response.data().backlog.get(i).description,
                            response.data().backlog.get(i).id,
                            "",
                            response.data().backlog.get(i).id,
                            ""));
                }
                Log.d("Berhasil","yay");
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.getMessage());
                e.printStackTrace();
            }
        }
        );
        return backlog;
    }

    public void  setListUserStories(ArrayList<Backlog> backlog){
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
