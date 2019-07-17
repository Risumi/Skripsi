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
import com.example.app.model.Backlog;
import com.example.app.model.Epic;
import com.example.app.model.Sprint;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import type.CustomType;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Backlog>> listAllBacklog;
    private MutableLiveData<ArrayList<Backlog>> listFilterBacklog;
    private MutableLiveData<ArrayList<Backlog>> listBacklog;
    private MutableLiveData<ArrayList<Backlog>> listBacklogSprint;
    private MutableLiveData<Sprint> currentSprint;
    private MutableLiveData<ArrayList<Epic>> listEpic;
    private MutableLiveData<Integer> sprintCount;
    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql/graphql";
    int sCount;
    ListenerGraphql listener;
    ListenerAdapter listenerAdapter;

    public MainViewModel() {
        listAllBacklog = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        setListAllBacklog(backlog);

        ArrayList<Backlog> backlog2 = new ArrayList<>();
        listBacklogSprint = new MutableLiveData<>();
        listBacklogSprint.setValue(backlog2);

        currentSprint = new MutableLiveData<>();
        currentSprint.setValue(new Sprint());
        sprintCount = new MutableLiveData<>();
//        sprintCount.setValue(0);

        ArrayList<Backlog> backlog3 = new ArrayList<>();
        listBacklog = new MutableLiveData<>();
        listBacklog.setValue(backlog3);

        listEpic = new MutableLiveData<>();
        ArrayList<Epic> epics= new ArrayList<>();
        listEpic.setValue(epics);

        listFilterBacklog = new MutableLiveData<>();
        ArrayList<Backlog> backlog4 = new ArrayList<>();
        listFilterBacklog.setValue(backlog4);
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

    public void instantiateListenerAdapter(ListenerAdapter listener){
        listenerAdapter = listener;
    }

    public void fetchBacklog(String PID){
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
        BacklogQuery backlogQuery= BacklogQuery.builder().id(PID).build();
        apolloClient.query(backlogQuery).enqueue(new ApolloCall.Callback<BacklogQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogQuery.Data> response) {
                for (int i = 0 ; i<response.data().backlog.size();i++){

                    String idSprint="";
                    String idEpic="";
//                    Log.d("idEpic",response.data().backlog.get(i).idEpic().id);
                    try {
                        if (response.data().backlog.get(i).idEpic().id!=null){
                            idEpic=response.data().backlog.get(i).idEpic().id;
                            Log.d("idEpic", idEpic);
                        }
                    }catch (NullPointerException e){

                    }
                    try{
                        if (response.data().backlog.get(i).idSprint().id!=null){
                            idSprint=response.data().backlog.get(i).idSprint().id;
                        }
                    }catch (NullPointerException e){

                    }finally {
                        listAllBacklog.getValue().add(new Backlog(response.data().backlog.get(i).name,
                                response.data().backlog.get(i).status,
                                response.data().backlog.get(i).begindate,
                                response.data().backlog.get(i).enddate,
                                "",
                                response.data().backlog.get(i).description,
                                response.data().backlog.get(i).id,
                                PID,
                                idSprint,
                                "",
                                idEpic));
                    }
                    if (response.hasErrors()){
                        Log.d("Error",response.errors().get(0).message());
                    }

                    Log.d("Berhasil",response.data().backlog.get(i).name);
                }
                Log.d("Berhasil","yay");
            }

             @Override
             public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                 super.onStatusEvent(event);
                 Log.d("event",event.name());
                 if (event.name().equalsIgnoreCase("completed")){
//                     listAllBacklog.postValue(backlog);

                     fetchSprint(PID);
                 }
             }

             @Override
             public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.getMessage());
                e.printStackTrace();
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"fetch");
             }
        }
        );
    }

    public void fetchSprint(String PID){
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
        CurrentSprintQuery currentSprintQuery= CurrentSprintQuery.builder().id(PID).build();
        final Sprint[] sprint = new Sprint[1];
        final Integer[] count = new Integer[1];
        apolloClient.query(currentSprintQuery).enqueue(new ApolloCall.Callback<CurrentSprintQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<CurrentSprintQuery.Data> response) {
                if (response.data().sprint().size()!=0){
                    sprint[0] = new Sprint(response.data().sprint.get(response.data().sprint.size()-1).id,PID,response.data().sprint.get(response.data().sprint.size()-1).begindate,response.data().sprint.get(response.data().sprint.size()-1).enddate,response.data().sprint.get(response.data().sprint.size()-1).goal);
                    Log.d("SID",sprint[0].getId());
                    count[0] = response.data().sprint.size();
                }
                Log.d("Berhasil","yay");
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (event.name().equalsIgnoreCase("COMPLETED")){
                    currentSprint.postValue(sprint[0]);
                    if (count[0]!=null){
                        Log.d("SCount", ((Integer) count[0]).toString());
                        sprintCount.postValue(((Integer) count[0]));
                        sCount = count[0];
                        splitData();
                    }else {
                        sprintCount.postValue(0);
                        sCount = 0;
                        splitData();
                    }
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

    public void fetchEpic(String PID){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        EpicQuery epicQuery= EpicQuery.builder().id(PID).build();
        apolloClient.query(epicQuery).enqueue(new ApolloCall.Callback<EpicQuery.Data>() {
             @Override
             public void onResponse(@NotNull Response<EpicQuery.Data> response) {
                 Log.d("Berhasil", ((Integer) response.data().epic.size()).toString());
                 for (int i = 0 ; i<response.data().epic.size();i++){
                     listEpic.getValue().add(new Epic(response.data().epic.get(i).name,
                             response.data().epic.get(i).status,
                             response.data().epic.get(i).description,
                             response.data().epic.get(i).id,
                             PID));
                     Log.d("Berhasil",response.data().epic.get(i).name);
                 }
                 Log.d("Berhasil","yay");
             }

             @Override
             public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                 super.onStatusEvent(event);
                 Log.d("event",event.name());
                 if (event.name().equalsIgnoreCase("completed")){
//                     listAllBacklog.postValue(backlog);
                     fetchBacklog(PID);
                 }
             }


             @Override
             public void onFailure(@NotNull ApolloException e) {
                 Log.d("Gagal",e.getMessage());
                 e.printStackTrace();
                 listener.endProgressDialog();
                 listener.startAlert(e.getMessage(),"fetch");
             }
         }
        );
    }

    public MutableLiveData<ArrayList<Backlog>> getListFilterBacklog() {
        return listFilterBacklog;
    }

    public void mutateBacklog(Backlog backlog){
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
        BacklogMutation backlogMutation = BacklogMutation.builder().id(backlog.getId()).idProject(backlog.getIdProject()).idEpic(backlog.getIdEpic()).name(backlog.getName()).status(backlog.getStatus()).begindate(backlog.getBegda()).enddate(backlog.getEndda()).description(backlog.getDescription()).build();
        apolloClient.mutate(backlogMutation).enqueue(new ApolloCall.Callback<BacklogMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogMutation.Data> response) {
                Log.d("Berhasil","yay");
//                Log.d("Response", response.errors().get(0).message());
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"mutateBacklog");
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

    public void mutateBacklogSprint(Backlog backlog){
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
        BacklogSprintMutation backlogMutation = BacklogSprintMutation.builder()
                .id(backlog.getId())
                .idProject(backlog.getIdProject())
                .idEpic(backlog.getIdEpic())
                .idSprint(backlog.getIdSprint())
                .name(backlog.getName())
                .status(backlog.getStatus())
                .begindate(backlog.getBegda())
                .enddate(backlog.getEndda())
                .description(backlog.getDescription())
                .build();
        apolloClient.mutate(backlogMutation).enqueue(new ApolloCall.Callback<BacklogSprintMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogSprintMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                e.printStackTrace();
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"mutateSprintBacklog");
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

    public void mutateBacklogSprintNull(Backlog backlog){
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
        BacklogSprintNullMutation backlogMutation = BacklogSprintNullMutation.builder()
                .id(backlog.getId())
                .idProject(backlog.getIdProject())
                .idEpic(backlog.getIdEpic())
                .name(backlog.getName())
                .status(backlog.getStatus())
                .begindate(backlog.getBegda())
                .enddate(backlog.getEndda())
                .description(backlog.getDescription())
                .build();
        apolloClient.mutate(backlogMutation).enqueue(new ApolloCall.Callback<BacklogSprintNullMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogSprintNullMutation.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Response", response.errors().get(0).message());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                e.printStackTrace();
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"mutateSprintBacklogNulll");
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
                listener.startAlert(e.getMessage(),"mutateSprint");
                e.printStackTrace();
            }
        });
    }

    public void mutateSprint(Sprint sprint){
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
        SprintMutation sprintMutation = SprintMutation.builder().id(sprint.getId()).idProject(sprint.getIdProject()).begindate(sprint.getBegda()).enddate(sprint.getEndda()).goal(sprint.getSprintGoal()).build();
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
                listener.startAlert(e.getMessage(),"mutateSprint");
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

    public void mutateEpic(Epic epic){
        listener.startProgressDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        EpicMutation epicMutation = EpicMutation.builder().id(epic.getId()).idProject(epic.getIdProject()).name(epic.getName()).status(epic.getStatus()).description(epic.getDescription()).build();
        apolloClient.mutate(epicMutation).enqueue(new ApolloCall.Callback<EpicMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<EpicMutation.Data> response) {
                Log.d("Berhasil","yay");
//                Log.d("Response", response.errors().get(0).message());
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal","shit");
                listener.endProgressDialog();
                listener.startAlert(e.getMessage(),"mutateEpic");
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

    public void setListAllBacklog(ArrayList<Backlog> backlog){
        listAllBacklog.setValue(backlog);
    }

    public MutableLiveData<ArrayList<Backlog>> getListAllBacklog() {
        return listAllBacklog;
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
        for (int i = 0; i< listBacklogSprint.getValue().size(); i++){
            if (listBacklogSprint.getValue().get(i).getStatus().equalsIgnoreCase("To Do")){
                backlog.add(listBacklogSprint.getValue().get(i));
            }
        }
        listToDo.setValue(backlog);
        return listToDo;
    }

    public MutableLiveData<ArrayList<Backlog>> getOnProgressBacklog(){
        MutableLiveData<ArrayList<Backlog>> listOnProgress = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listBacklogSprint.getValue().size(); i++){
            if (listBacklogSprint.getValue().get(i).getStatus().equalsIgnoreCase("On Progress")){
                backlog.add(listBacklogSprint.getValue().get(i));
            }
        }
        listOnProgress.setValue(backlog);
        return listOnProgress;
    }

    public MutableLiveData<ArrayList<Backlog>> getCompletedBacklog(){
        MutableLiveData<ArrayList<Backlog>> listCompleted = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        for (int i = 0; i< listBacklogSprint.getValue().size(); i++){
            if (listBacklogSprint.getValue().get(i).getStatus().equalsIgnoreCase("Completed")){
                backlog.add(listBacklogSprint.getValue().get(i));
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

    public MutableLiveData<Integer> getSprintCount() {
        return sprintCount;
    }

    void splitData(){
        Log.d("Backlog count", ((Integer) listAllBacklog.getValue().size()).toString());
        if (sCount == 0){
            listBacklog = listAllBacklog;
            listFilterBacklog.getValue().addAll(listBacklog.getValue());
            listener.endProgressDialog();
            Log.d("Sprint status", "No sprint");
        }else {
            for (int i = 0; i< listAllBacklog.getValue().size(); i++){
                //backlog di dalam sprint
                Log.d("Backlog : "+i ,listAllBacklog.getValue().get(i).getIdProject());
//                Log.d("Sprint",currentSprint.getValue().id);
                if (listAllBacklog.getValue().get(i).getIdSprint().equalsIgnoreCase(currentSprint.getValue().getId())){
                    listBacklogSprint.getValue().add(listAllBacklog.getValue().get(i));
                }else {
                    listBacklog.getValue().add(listAllBacklog.getValue().get(i));
                }
            }
            listFilterBacklog.getValue().addAll(listBacklog.getValue());
            listener.endProgressDialog();
        }
    }

    public MutableLiveData<ArrayList<Backlog>> getListBacklog() {
        return listBacklog;
    }

    public void reset(){
        listAllBacklog = new MutableLiveData<>();
        ArrayList<Backlog> backlog = new ArrayList<>();
        setListAllBacklog(backlog);

        ArrayList<Backlog> backlog2 = new ArrayList<>();
        listBacklogSprint = new MutableLiveData<>();
        listBacklogSprint.setValue(backlog2);

        currentSprint = new MutableLiveData<>();
        currentSprint.setValue(new Sprint());
        sprintCount = new MutableLiveData<>();
//        sprintCount.setValue(0);

        ArrayList<Backlog> backlog3 = new ArrayList<>();
        listBacklog = new MutableLiveData<>();
        listBacklog.setValue(backlog3);

        listEpic = new MutableLiveData<>();
        ArrayList<Epic> epics= new ArrayList<>();
        listEpic.setValue(epics);

        listFilterBacklog = new MutableLiveData<>();
        ArrayList<Backlog> backlog4 = new ArrayList<>();
        listFilterBacklog.setValue(backlog4);
    }

    public void filterBacklog(String id,String code){
//        listFilterBacklog.getValue().clear();
//        listBacklog.getValue().addAll(listFilterBacklog.getValue());
        if (code.equalsIgnoreCase("all")){
            listBacklog.getValue().clear();
            listBacklog.getValue().addAll(listFilterBacklog.getValue());
        }else if (code.equalsIgnoreCase("---")){
            ArrayList <Backlog> backlogArrayList = new ArrayList<>();
            backlogArrayList.clear();
            backlogArrayList.addAll(listFilterBacklog.getValue());
            listBacklog.getValue().clear();
            for (int i=0;i<backlogArrayList.size();i++){
                if (backlogArrayList.get(i).getIdEpic().equalsIgnoreCase("")){
                    listBacklog.getValue().add(backlogArrayList.get(i));
                }
            }
        }
        else {
            ArrayList <Backlog> backlogArrayList = new ArrayList<>();
            backlogArrayList.clear();
            backlogArrayList.addAll(listFilterBacklog.getValue());
//        ArrayList<Backlog> backlog = new ArrayList<>();
            listBacklog.getValue().clear();
            for (int i=0;i<backlogArrayList.size();i++){
                if (backlogArrayList.get(i).getIdEpic().equalsIgnoreCase(id)){
                    listBacklog.getValue().add(backlogArrayList.get(i));
                    Log.d("ID Epic",backlogArrayList.get(i).getIdEpic());
                }
            }
        }
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
            }
            Log.d("Epic ID",id);
            Log.d("Epic Name",name);
        }
        return name;
    }

    public void updateList(Backlog backlog, String todo){
        if (todo.equalsIgnoreCase("add")){
            listFilterBacklog.getValue().add(backlog);
        }else if (todo.equalsIgnoreCase("remove")){
            for (int i = 0;i<listFilterBacklog.getValue().size();i++){
                if (listFilterBacklog.getValue().get(i)==backlog){
                    listFilterBacklog.getValue().remove(i);
                }
            }
        }
    }
}
