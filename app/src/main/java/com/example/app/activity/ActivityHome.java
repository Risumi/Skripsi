package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.R;
import com.example.app.adapter.ProjectAdapter;
import com.example.app.model.Progress;
import com.example.app.model.Project;
import com.example.app.model.Sprint;
import com.example.app.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import graphql.ProgressQuery;
import graphql.ProjectMutation;
import graphql.ProjectUQuery;
import graphql.ProjectUserQuery;
import graphql.SprintMutation;
import okhttp3.OkHttpClient;
import type.CustomType;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Project> listProject;
    ArrayList<Progress> listProgress;
    final int ADD_PROJECT =1;
    User user;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";
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

        mAdapter = new ProjectAdapter(this, listProject,listProgress,user);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutAnimation(animation);

        progressDialog = new ProgressDialog(ActivityHome.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProjectUser(user.getEmail());
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
                createProject(newProject);
            }
        }
    }
    ProgressDialog progressDialog;

    void fetchProjectUser(String email){
        progressDialog.show();
        listProject.clear();
        listProgress.clear();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProjectUQuery projectUQuery = ProjectUQuery.builder().email(email).build();
        apolloClient.query(projectUQuery).enqueue(new ApolloCall.Callback<ProjectUQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProjectUQuery.Data> response) {
                Log.d("Size", ((Integer) response.data().projectuser().size()).toString());
                if(response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }
                for (int i = 0 ;i<response.data().projectuser().size();i++){
                    listProject.add(new Project(
                            response.data().projectuser().get(i).idProject().name(),
                            response.data().projectuser().get(i).idProject().id(),
                            response.data().projectuser().get(i).idProject().status(),
                            response.data().projectuser().get(i).idProject().description()));
                    Log.d("project name",response.data().projectuser().get(i).idProject().name());
                }
                for (int i = 0 ;i<response.data().progress().size();i++){
                    listProgress.add(new Progress(
                            response.data().progress().get(i).id(),
                            response.data().progress().get(i).count(),
                            response.data().progress().get(i).complete()
                    ));
                }
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (!event.name().equalsIgnoreCase("COMPLETED")){

                }else {
                    progressDialog.dismiss();
                    ActivityHome.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
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

    private void createProject(Project project){
        progressDialog.show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProjectMutation projectMutation= ProjectMutation.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status("")
                .email(user.getEmail())
                .build();
        apolloClient.mutate(projectMutation).enqueue(new ApolloCall.Callback<ProjectMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProjectMutation.Data> response) {
                Log.d("berhasil","yay");
                ActivityHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.hasErrors()){
                            listProject.add(project);
                            mAdapter.notifyDataSetChanged();

                        }else {
                            Log.d("Error",response.errors().get(0).message());
                            Toast.makeText(ActivityHome.this, "There is an error, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                ActivityHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ActivityHome.this, "There is an error, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("COMPLETED")){
                    Sprint sprint = new Sprint(
                            project.getId()+"-S 1",
                            project.getId(),
                            "Sprint 1",
                            null,
                            null,
                            "",
                            "Not Active",null,new Date(),user.getEmail(),null,null);
                    createSprint(sprint);
                }
            }
        });
    }

    public void createSprint(Sprint sprint){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        CustomTypeAdapter<Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
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
                progressDialog.dismiss();
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("COMPLETED")){
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
                        fetchProjectUser(user.getEmail());
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

