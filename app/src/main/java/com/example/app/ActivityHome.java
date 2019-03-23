package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Project> listProject;
    final int ADD_PROJECT =1;

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
        listProject.add(new Project("Blog Project","BP"));
        listProject.add(new Project("IS Project","IP"));
        listProject.add(new Project("IoT Project","IoP"));
        mAdapter = new ProjectAdapter(this, listProject);
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
        if (requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                Project newProject = data.getParcelableExtra("result");
                listProject.add(newProject);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}

