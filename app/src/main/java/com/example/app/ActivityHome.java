package com.example.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Project> listProject;
    final int ADD_PROJECT =1;
    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv_project);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        listProject = new ArrayList<>();
        new getData().execute();
//        listProject.add(new Project("Blog Project","BP",true));
//        listProject.add(new Project("IS Project","IP",true));
//        listProject.add(new Project("IoT Project","IoP",false));
        mAdapter = new ProjectAdapter(this, listProject);
        Log.d("fool", ((Integer) mAdapter.getItemCount()).toString());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutAnimation(animation);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab){
            Intent addProject = new Intent(this, ActivityAddProject.class);
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
                new setData(newProject).execute();
                mAdapter.notifyDataSetChanged();

            }
        }
    }

    private class getData extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityHome.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            ApolloClient apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(okHttpClient)
                    .build();
            AllProjectQuery allProjectQuery = AllProjectQuery.builder().build();
            apolloClient.query(allProjectQuery).enqueue(new ApolloCall.Callback<AllProjectQuery.Data>() {
                @Override
                public void onResponse(@NotNull Response<AllProjectQuery.Data> response) {
                    for (int i = 0 ;i<response.data().allProject.size();i++){
                        listProject.add(new Project(response.data().allProject.get(i).name,response.data().allProject.get(i).id,response.data().allProject.get(i).status,response.data().allProject.get(i).description));
                    }
//                    Log.d("Berhasil","yay");
                    ActivityHome.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            Log.d("foo", ((Integer) mAdapter.getItemCount()).toString());
                            Log.d("Berhasil","yay");
                        }
                    });
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.d("Gagal",e.toString());
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

    }
    private class setData extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        Project project;

        public setData(Project project) {
            this.project = project;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityHome.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            ApolloClient apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(okHttpClient)
                    .build();
            ProjectMutation projectMutation= ProjectMutation.builder().id(project.id).name(project.name).description(project.description).status("").build();
            apolloClient.mutate(projectMutation).enqueue(new ApolloCall.Callback<ProjectMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<ProjectMutation.Data> response) {
                    Log.d("berhasil","yay");
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.d("gagal","shit");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

    }
}

