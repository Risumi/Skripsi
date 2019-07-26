package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.app.AllProjectQuery;
import com.example.app.ProgressQuery;
import com.example.app.ProjectMutation;
import com.example.app.R;
import com.example.app.adapter.ProjectAdapter;
import com.example.app.model.Progress;
import com.example.app.model.Project;
import com.example.app.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Project> listProject;
    ArrayList<Progress> listProgress;
    final int ADD_PROJECT =1;
    User user;
    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = getIntent().getParcelableExtra("User");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv_project);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        listProject = new ArrayList<>();
        listProgress = new ArrayList<>();
//        new fetchProject().execute();
        fetchProject();
//        listProject.add(new Project("Blog Project","BP",true));
//        listProject.add(new Project("IS Project","IP",true));
//        listProject.add(new Project("IoT Project","IoP",false));
        mAdapter = new ProjectAdapter(this, listProject,listProgress);
        Log.d("fool", ((Integer) mAdapter.getItemCount()).toString());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutAnimation(animation);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab){
            Intent addProject = new Intent(this, ActivityAddProject.class);
            addProject.putExtra("User",user);
            startActivityForResult(addProject,ADD_PROJECT);
//            listProject.add(new Project("IoT Project","IoP"));
//            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PROJECT) {
            if (resultCode == RESULT_OK) {
                Project newProject = data.getParcelableExtra("result");
                listProject.add(newProject);
//                new setData(newProject).execute();
                mAdapter.notifyDataSetChanged();
                setData(newProject);

            }
        }
    }
    ProgressDialog progressDialog;
    private void fetchProject(){
        progressDialog = new ProgressDialog(ActivityHome.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        AllProjectQuery allProjectQuery = AllProjectQuery.builder().build();
        apolloClient.query(allProjectQuery).enqueue(new ApolloCall.Callback<AllProjectQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<AllProjectQuery.Data> response) {
                for (int i = 0 ;i<response.data().project().size();i++){
                    listProject.add(new Project(response.data().project().get(i).name(),response.data().project().get(i).id(),response.data().project().get(i).status(),response.data().project().get(i).description()));
                }
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (!event.name().equalsIgnoreCase("COMPLETED")){

                }else {
                    fetchProgress();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.toString());
                progressDialog.dismiss();
                ActivityHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeAlertDialog(e.getMessage());
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                });
            }
        });
    }

    private void fetchProgress(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProgressQuery progressQuery = ProgressQuery.builder().build();
        apolloClient.query(progressQuery ).enqueue(new ApolloCall.Callback<ProgressQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProgressQuery.Data> response) {
                for (int i = 0 ;i<response.data().progress().size();i++){
                    listProgress.add(new Progress(
                            response.data().progress().get(i).id(),
                            response.data().progress().get(i).count(),
                            response.data().progress().get(i).complete()
                    ));
                }
                ActivityHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (!event.name().equalsIgnoreCase("COMPLETED")){

                }else {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                progressDialog.dismiss();
                ActivityHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeAlertDialog(e.getMessage());
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                });
            }
        });
    }

    private void setData(Project project){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityHome.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProjectMutation projectMutation= ProjectMutation.builder().id(project.getId()).name(project.getName()).description(project.getDescription()).status("").build();
        apolloClient.mutate(projectMutation).enqueue(new ApolloCall.Callback<ProjectMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProjectMutation.Data> response) {
                Log.d("berhasil","yay");
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("gagal","shit");
            }

            /**
             * Gets called whenever any action happen to this {@link ApolloCall}.
             *
             * @param event status that corresponds to a {@link ApolloCall} action
             */
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (!event.name().equalsIgnoreCase("COMPLETED")){
                    progressDialog.show();
                }else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    void initializeAlertDialog(String error){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Error : "+ error+"\nRetry ?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        fetchProject();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
    }

}

