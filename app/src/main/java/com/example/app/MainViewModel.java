package com.example.app;


import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.model.Progress;
import com.example.app.utils.ListenerAdapter;
import com.example.app.utils.ListenerEpic;
import com.example.app.utils.ListenerGraphql;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;
import com.example.app.model.Project;
import com.example.app.model.Sprint;
import com.example.app.model.User;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import graphql.AddUserMutation;
import graphql.BacklogEditMutation;
import graphql.BacklogMutation;
import graphql.CompleteSprintMutation;
import graphql.DeleteBacklogMutation;
import graphql.DeleteEpicMutation;
import graphql.EditProjectMutation;
import graphql.EpicEditMutation;
import graphql.EpicMutation;
import graphql.MainQuery;
import graphql.ProgressEpicQuery;
import graphql.RemoveUserMutation;
import graphql.SprintEditMutation;
import graphql.SprintMutation;
import okhttp3.OkHttpClient;

import type.BacklogInput;
import type.CustomType;
import type.SprintInput;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Backlog>> listBacklog;
    private MutableLiveData<ArrayList<Backlog>> listBacklogSprint;

    private MutableLiveData<ArrayList<Sprint>> listSprint;
    private MutableLiveData<Sprint> currentSprint;

    private MutableLiveData<ArrayList<Epic>> listEpic;
    private MutableLiveData<ArrayList<User>> listUser ;

    private MutableLiveData<ArrayList<Progress>> listEpicProgress;

    private User user;

    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";

    private ListenerGraphql listener;
    private ListenerAdapter listenerAdapter;
    private ListenerEpic listenerEpic;

    public MainViewModel() {
        initializeVariable();
    }

    private void initializeVariable(){
        listBacklog = new MutableLiveData<>();
        listBacklog.setValue(new ArrayList<>());

        listBacklogSprint = new MutableLiveData<>();
        listBacklogSprint.setValue(new ArrayList<>());

        listSprint = new MutableLiveData<>();
        listSprint.setValue(new ArrayList<>());

        currentSprint = new MutableLiveData<>();
        currentSprint.setValue(new Sprint());

        listEpic = new MutableLiveData<>();
        listEpic.setValue(new ArrayList<>());

        listUser = new MutableLiveData<>();
        listUser.setValue(new ArrayList<>());

        listEpicProgress = new MutableLiveData<>();
        listEpicProgress.setValue(new ArrayList<>());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MutableLiveData<ArrayList<User>> getListUser() {
        return listUser;
    }

    public User getUser() {
        return user;
    }

    public void setListEpic(ArrayList<Epic> listEpic){
        this.listEpic.setValue(listEpic);
    }

    public MutableLiveData<ArrayList<Epic>> getListEpic() {
        return listEpic;
    }

    public void instantiateListener(ListenerGraphql listener){
        this.listener = listener;
    }

    public void instantiateListener(ListenerEpic listenerEpic){
        this.listenerEpic = listenerEpic;
    }

    public void instantiateListenerAdapter(ListenerAdapter listener){
        listenerAdapter = listener;
    }

    public MutableLiveData<ArrayList<Sprint>> getListSprint() {
        return listSprint;
    }

    public MutableLiveData<ArrayList<Progress>> getListEpicProgress() {
        return listEpicProgress;
    }

    private void setActiveSprint(){
        if (listSprint.getValue().size()!=0){
            Sprint sprint =listSprint.getValue().get(listSprint.getValue().size()-1);
            int last=0;
            int temp=0;
            for (int i=0;i<listSprint.getValue().size();i++){
                temp = Integer.parseInt(listSprint.getValue().get(i).getId().replaceAll("[^0-9]",""));
                if (last<temp){
                    last = temp;
                    sprint =listSprint.getValue().get(i);
                }
            }
            Log.d("Last",Integer.toString(last));
            if (sprint.getStatus().equalsIgnoreCase("Active")){
                for (int i = 0; i < listBacklog.getValue().size(); i++) {
                    Backlog backlog =  listBacklog.getValue().get(i);
                    if (backlog.getIdSprint().equalsIgnoreCase(sprint.getId())){
                        listBacklogSprint.getValue().add(backlog);
                    }
                }
            }
            currentSprint.postValue(sprint);
        }
    }

    public void fetchMain(String PID){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
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
        MainQuery mainQuery= MainQuery.builder().id(PID).build();
        apolloClient.query(mainQuery).enqueue(new ApolloCall.Callback<MainQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<MainQuery.Data> response) {
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }else {
                    for (int i = 0 ; i<response.data().sprint().size();i++) {
                        Log.d("Size", ((Integer) response.data().sprint().size()).toString());
                        listSprint.getValue().add(new Sprint(
                                response.data().sprint().get(i).id(),
                                PID,
                                response.data().sprint().get(i).name(),
                                response.data().sprint().get(i).begindate(),
                                response.data().sprint().get(i).enddate(),
                                response.data().sprint().get(i).goal(),
                                response.data().sprint().get(i).status(),
                                "",
                                response.data().sprint().get(i).createddate(),
                                response.data().sprint().get(i).createdby()!=null?response.data().sprint().get(i).createdby().nama():"",
                                response.data().sprint().get(i).modifieddate(),
                                response.data().sprint().get(i).modifiedby()!=null?response.data().sprint().get(i).modifiedby().nama():""));
                    }

                    for (int i = 0 ; i<response.data().backlog().size();i++) {
                        listBacklog.getValue().add(new Backlog(
                                response.data().backlog().get(i).id(),
                                PID,
                                response.data().backlog().get(i).idSprint()!=null?response.data().backlog().get(i).idSprint().id():"",
                                response.data().backlog().get(i).idEpic()!=null?response.data().backlog().get(i).idEpic().id():"",
                                response.data().backlog().get(i).name(),
                                response.data().backlog().get(i).status(),
                                response.data().backlog().get(i).assignee()!=null?response.data().backlog().get(i).assignee().email():"",
                                response.data().backlog().get(i).description(),
                                response.data().backlog().get(i).createddate(),
                                response.data().backlog().get(i).createdby()!=null?response.data().backlog().get(i).createdby().nama():"",
                                response.data().backlog().get(i).modifieddate(),
                                response.data().backlog().get(i).modifiedby()!=null?response.data().backlog().get(i).modifiedby().nama():""));
                    }

                    for (int i = 0 ; i<response.data().userproject().size();i++){
                        listUser.getValue().add(new User(
                                response.data().userproject().get(i).email().email(),
                                response.data().userproject().get(i).email().nama()));
                    }

                    for (int i = 0 ; i<response.data().epic().size();i++){
                        listEpic.getValue().add(new Epic(
                                response.data().epic().get(i).id(),
                                PID,
                                response.data().epic().get(i).name(),
                                response.data().epic().get(i).summary(),
                                response.data().epic().get(i).createddate(),
                                response.data().epic().get(i).createdby()!=null?response.data().epic().get(i).createdby().nama():"",
                                response.data().epic().get(i).modifieddate(),
                                response.data().epic().get(i).createdby()!=null?response.data().epic().get(i).createdby().nama():""));
                        listEpicProgress.getValue().add(new Progress(
                                response.data().epicProgress().get(i).id(),
                                response.data().epicProgress().get(i).count(),
                                response.data().epicProgress().get(i).complete()
                        ));
                    }

                }
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("COMPLETED")){
                    listener.endProgressDialog();
                    setActiveSprint();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.getMessage());
                e.printStackTrace();
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"fetch");
            }
        });
    }

    void fetchEpicProgress(String PID){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProgressEpicQuery progressEpicQuery= ProgressEpicQuery.builder().id(PID).build();
        apolloClient.query(progressEpicQuery).enqueue(new ApolloCall.Callback<ProgressEpicQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProgressEpicQuery.Data> response) {
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }else {
                    listEpicProgress.getValue().clear();
                    for (int i = 0 ; i<response.data().epicProgress().size();i++){
                        listEpicProgress.getValue().add(new Progress(
                                response.data().epicProgress().get(i).id(),
                                response.data().epicProgress().get(i).count(),
                                response.data().epicProgress().get(i).complete()
                        ));
                    }

                }
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("COMPLETED")){
                    listener.endProgressDialog();
                    setActiveSprint();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.getMessage());
                e.printStackTrace();
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"fetch");
            }
        });
    }

    public void createBacklog(Backlog backlog){
        listener.startProgressDialog();
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
        Log.d("ID Backlog",backlog.getId());
        BacklogMutation backlogMutation = BacklogMutation.builder()
                .id(backlog.getId())
                .idProject(backlog.getIdProject())
                .idEpic(backlog.getEpicName())
                .assignee(backlog.getAssignee())
                .name(backlog.getName())
                .status(backlog.getStatus())
                .description(backlog.getDescription())
                .createddate(backlog.getCreateddate())
                .createdby(backlog.getCreatedby())
                .build();
        apolloClient.mutate(backlogMutation).enqueue(new ApolloCall.Callback<BacklogMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogMutation.Data> response) {
                Log.d("Berhasil","yay");

                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createBacklog");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
                Log.d("event",event.name());
            }
        });
    }

    public void editBacklog(Backlog backlog){
        listener.startProgressDialog();
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

        BacklogEditMutation backlogMutation = BacklogEditMutation.builder()
                .id(backlog.getId())
                .idEpic(backlog.getEpicName())
                .idSprint(backlog.getIdSprint())
                .assignee(backlog.getAssignee())
                .name(backlog.getName())
                .status(backlog.getStatus())
                .description(backlog.getDescription())
                .modifieddate(backlog.getModifieddate())
                .modifiedby(backlog.getModifiedby())
                .build();
        apolloClient.mutate(backlogMutation).enqueue(new ApolloCall.Callback<BacklogEditMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogEditMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }

            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createBacklog");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
                Log.d("event",event.name());
            }
        });
    }

    public void completeSprint(ArrayList<Backlog> listB, Sprint sprint, Sprint newSprint){
        listener.startProgressDialog();
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

        List<BacklogInput> backlogInputs= new ArrayList<>();
        for (int i = 0 ; i< listB.size();i++){
            Backlog backlog = listB.get(i);
            backlogInputs.add(BacklogInput.builder()
                    .idBacklog(backlog.getId())
                    .idSprint(backlog.getIdSprint())
                    .status(backlog.getStatus())
                    .date(new Date())
                    .build());
        }

        SprintInput newSprintInput  = SprintInput.builder()
                .id(newSprint.getId())
                .idProject(newSprint.getIdProject())
                .name(newSprint.getName())
                .goal(newSprint.getSprintGoal())
                .status(newSprint.getStatus())
                .createddate(new Date())
                .createdby(user.getEmail())
                .build();

        SprintInput sprintInput  = SprintInput.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .begindate(sprint.getBegda())
                .enddate(sprint.getEndda())
                .goal(sprint.getSprintGoal())
                .status(sprint.getStatus())
                .retrospective(" ")
                .modifieddate(new Date())
                .modifiedby(user.getEmail())
                .build();

        CompleteSprintMutation completeSprintMutation= CompleteSprintMutation.builder()
                .newSprint(newSprintInput)
                .sprint(sprintInput)
                .backlog(backlogInputs)
                .modifiedby(user.getEmail())
                .build();

        apolloClient.mutate(completeSprintMutation).enqueue(new ApolloCall.Callback<CompleteSprintMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<CompleteSprintMutation.Data> response) {
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                listener.endProgressDialog();
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
                Log.d("event",event.name());
            }
        });
    }

    public void deleteBacklog(Backlog backlog){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        DeleteBacklogMutation deleteBacklogMutation  = DeleteBacklogMutation.builder().id(backlog.getId()).build();
        apolloClient.mutate(deleteBacklogMutation).enqueue(new ApolloCall.Callback<DeleteBacklogMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<DeleteBacklogMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createSprint");
                e.printStackTrace();
            }
        });
    }

    public void deleteEpic(Epic epic){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        DeleteEpicMutation deleteEpicMutation = DeleteEpicMutation.builder().id(epic.getId()).build();
        apolloClient.mutate(deleteEpicMutation).enqueue(new ApolloCall.Callback<DeleteEpicMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<DeleteEpicMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }else{
                    listenerEpic.onDeleteFinished(epic);
                }
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createSprint");
                e.printStackTrace();
            }
        });
    }

    public void createSprint(Sprint sprint){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
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
        SprintMutation sprintMutation = SprintMutation.builder()
                .id(sprint.getId())
                .idProject(sprint.getIdProject())
                .name(sprint.getName())
                .goal(sprint.getSprintGoal())
                .status(sprint.getStatus())
                .createddate(sprint.getCreateddate())
                .createdby(sprint.getCreatedby())
                .build();
        apolloClient.mutate(sprintMutation).enqueue(new ApolloCall.Callback<SprintMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<SprintMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createSprint");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void editSprint(Sprint sprint){
        listener.startProgressDialog();
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
        SprintEditMutation sprintMutation = SprintEditMutation.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .begindate(sprint.getBegda())
                .enddate(sprint.getEndda())
                .goal(sprint.getSprintGoal())
                .status(sprint.getStatus())
                .retrospective(" ")
                .modifieddate(sprint.getModifieddate())
                .modifiedby(sprint.getModifiedby())
                .build();
        apolloClient.mutate(sprintMutation).enqueue(new ApolloCall.Callback<SprintEditMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<SprintEditMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createSprint");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void createEpic(Epic epic){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
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
        EpicMutation epicMutation = EpicMutation.builder()
                .id(epic.getId())
                .idProject(epic.getIdProject())
                .name(epic.getName())
                .summary(epic.getSummary())
                .createddate(epic.getCreateddate())
                .createdby(epic.getCreatedby())
                .build();
        apolloClient.mutate(epicMutation).enqueue(new ApolloCall.Callback<EpicMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<EpicMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createEpic");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void editEpic(Epic epic){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
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
        EpicEditMutation epicMutation = EpicEditMutation.builder()
                .id(epic.getId())
                .idProject(epic.getIdProject())
                .name(epic.getName())
                .summary(epic.getSummary())
                .modifieddate(epic.getModifieddate())
                .modifiedby(epic.getModifiedby())
                .build();
        apolloClient.mutate(epicMutation).enqueue(new ApolloCall.Callback<EpicEditMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<EpicEditMutation.Data> response) {
                Log.d("Berhasil","yay");
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createEpic");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void projectEdit(Project project){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        EditProjectMutation editProjectMutation = EditProjectMutation .builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status("")
                .build();
        apolloClient.mutate(editProjectMutation).enqueue(new ApolloCall.Callback<EditProjectMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<EditProjectMutation.Data> response) {
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }else {
                    Log.d("Berhasil","yay");
                    listener.setToast("Setting saved");
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
//                listener.startAlert(e.getMessage(),"createEpic");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void addUser(String email,String PID){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        AddUserMutation addUserMutation= AddUserMutation.builder()
                .idProject(PID)
                .email(email)
                .build();
        apolloClient.mutate(addUserMutation).enqueue(new ApolloCall.Callback<AddUserMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<AddUserMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                    listener.endProgressDialog();
                    listener.startAlert("No email found","No email");
                }else {
                    listUser.getValue().add(new User
                            (response.data().addUser().email(),
                            response.data().addUser().name()));
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"createEpic");
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public void removeUser(User user,String PID){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        RemoveUserMutation addUserMutation= RemoveUserMutation.builder()
                .idProject(PID)
                .email(user.getEmail())
                .build();
        apolloClient.mutate(addUserMutation).enqueue(new ApolloCall.Callback<RemoveUserMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<RemoveUserMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }else {
                    listUser.getValue().remove(user);

                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("completed")){
                    listener.endProgressDialog();
                }
            }
        });
    }

    public MutableLiveData<ArrayList<Backlog>> getListBacklogSprint() {
        return listBacklogSprint;
    }

    public void setListBacklogSprint(ArrayList<Backlog> backlog){
        listBacklogSprint.setValue(backlog);
    }

    public  MutableLiveData<ArrayList<Backlog>> getToDoBacklog(){
        MutableLiveData<ArrayList<Backlog>> listToDo = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        if (getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")){
            for (int i = 0; i< listBacklog.getValue().size(); i++){
                if (listBacklog.getValue().get(i).getIdSprint().equals(currentSprint.getValue().getId())){
                    if (listBacklog.getValue().get(i).getStatus().equalsIgnoreCase("To Do")){
                        Log.d(listBacklog.getValue().get(i).getIdSprint(),currentSprint.getValue().getId());
                        backlog.add(listBacklog.getValue().get(i));
                    }
                }
            }

        }
        listToDo.setValue(backlog);
        return listToDo;
    }

    public MutableLiveData<ArrayList<Backlog>> getOnProgressBacklog(){
        MutableLiveData<ArrayList<Backlog>> listOnProgress = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        if (getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")) {
            for (int i = 0; i< listBacklog.getValue().size(); i++){
                if (listBacklog.getValue().get(i).getIdSprint().equals(currentSprint.getValue().getId())) {
                    if (listBacklog.getValue().get(i).getStatus().equalsIgnoreCase("On Progress")) {
                        Log.d(listBacklog.getValue().get(i).getIdSprint(),currentSprint.getValue().getId());
                        backlog.add(listBacklog.getValue().get(i));
                    }
                }
            }
        }
        listOnProgress.setValue(backlog);
        return listOnProgress;
    }

    public MutableLiveData<ArrayList<Backlog>> getCompletedBacklog(){
        MutableLiveData<ArrayList<Backlog>> listCompleted = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        if (getCurrentSprint().getValue().getStatus().equalsIgnoreCase("Active")) {
            for (int i = 0; i< listBacklog.getValue().size(); i++){
                if (listBacklog.getValue().get(i).getIdSprint().equals(currentSprint.getValue().getId())) {
                    if (listBacklog.getValue().get(i).getStatus().equalsIgnoreCase("Completed")) {
                        Log.d(listBacklog.getValue().get(i).getIdSprint(),currentSprint.getValue().getId());
                        backlog.add(listBacklog.getValue().get(i));
                    }
                }
            }
        }
        listCompleted.setValue(backlog);
        return listCompleted;
    }

    public void setCurrentSprint(Sprint currentSprint){
        this.currentSprint.setValue(currentSprint);
    }

    public MutableLiveData<Sprint> getCurrentSprint(){
        return this.currentSprint;
    }



    public MutableLiveData<ArrayList<Backlog>> getListBacklog() {
        return listBacklog;
    }

    public void reset(){
        initializeVariable();
    }

    public String getIDEpic(String name){
        String id="";
        for (int i=0;i<listEpic.getValue().size();i++){
            if (listEpic.getValue().get(i).getName().equalsIgnoreCase(name)){
                id = listEpic.getValue().get(i).getId();
            }
        }
        return id;
    }

    public String getEpicName(String id){
        String name="";
        for (int i=0;i<listEpic.getValue().size();i++){
            if (listEpic.getValue().get(i).getId().equalsIgnoreCase(id)){
                name = listEpic.getValue().get(i).getName();
                break;
            }
            Log.d("Epic ID",id);
            Log.d("Epic Name",name);
        }
        return name;
    }

    public String getUserName(String email){
        String name="";
        for (int i=0;i<listUser.getValue().size();i++){
            if (listUser.getValue().get(i).getEmail().equalsIgnoreCase(email)){
                name = listUser.getValue().get(i).getName();
                break;
            }
        }
        return name;
    }

    public void updateList(Backlog backlog, String todo){
        if (todo.equalsIgnoreCase("add")){
            listBacklogSprint.getValue().remove(backlog);
        }else if (todo.equalsIgnoreCase("remove")){
            listBacklogSprint.getValue().add(backlog);
        }
    }

    public int getLargestBacklogID(){
        int last=0;
        int temp=0;
        for (int i=0;i<listBacklog.getValue().size();i++){
            temp = Integer.parseInt(listBacklog.getValue().get(i).getId().replaceAll("[^0-9]",""));
            if (last<temp){
                last = temp;
            }
        }
        Log.d("Last",Integer.toString(last));
        return last;
    }

    public int getLargestSprintID(){
        int last=0;
        int temp=0;
        for (int i=0;i<listSprint.getValue().size();i++){
            temp = Integer.parseInt(listSprint.getValue().get(i).getId().replaceAll("[^0-9]",""));
            if (last<temp){
                last = temp;
            }
        }
        Log.d("Last",Integer.toString(last));
        return last;
    }

    public int getLargestEpicID(){
        int last=0;
        int temp=0;
        for (int i=0;i<listEpic.getValue().size();i++){
            temp = Integer.parseInt(listEpic.getValue().get(i).getId().replaceAll("[^0-9]",""));
            if (last<temp){
                last = temp;
            }
        }
        Log.d("Last",Integer.toString(last));
        return last;
    }

    public ArrayList<Sprint> getListSprintDone(){
        ArrayList<Sprint> sprints = new ArrayList<>();
        for (int i = 0 ;i<listSprint.getValue().size();i++){
            Log.d("Status",listSprint.getValue().get(i).getStatus());
            if (listSprint.getValue().get(i).getStatus().equalsIgnoreCase("Done")){
                Log.d("ID",listSprint.getValue().get(i).getId());
                sprints.add(listSprint.getValue().get(i));
            }
        }
        return sprints;
    }

    public void removeEpic(Epic epic){
        for (int i = 0 ; i< listEpic.getValue().size();i++){
            if (listEpic.getValue().get(i).getId().equalsIgnoreCase(epic.getId())){
                listEpic.getValue().remove(i);
                break;
            }
        }
        for (int i = 0 ; i< listEpicProgress.getValue().size();i++){
            if (listEpicProgress.getValue().get(i).getIdProject().equalsIgnoreCase(epic.getId())){
                listEpicProgress.getValue().remove(i);
                break;
            }
        }
        for (int i = 0 ; i<listBacklog.getValue().size();i++){
            if (listBacklog.getValue().get(i).getEpicName().equalsIgnoreCase(epic.getId())){
                listBacklog.getValue().get(i).setEpicName("");
            }
        }
    }
}
