package com.example.app;

import android.content.Intent;

import com.example.app.activity.ActivityMain;
import com.example.app.fragment.FragmentSprint;
import com.example.app.model.Backlog;
import com.example.app.model.Project;
import com.example.app.model.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;



public class addEpicTest {
    @Rule
    public ActivityTestRule<ActivityMain> rule = new ActivityTestRule<>(ActivityMain.class,false,false);

    ActivityMain mActivity = null;
    FragmentSprint fragment = null;
    Backlog task;

    @Before
    public void setUp() throws Exception {
        task= new Backlog("JECT-1","JECT",null,null,"Backlog","To Do",null,null,null,null,null,null);
        Intent intent = new Intent();
        intent.putExtra("PID","JECT");
        intent.putExtra("User",new User("rizky@gmail.com","Rizky Suhaimi"));
        intent.putExtra("project",new Project("Project Management","JECT","",null));
        rule.launchActivity(intent);
        mActivity = rule.getActivity();
        fragment= FragmentSprint.newInstance("","");
        mActivity.loadFragment(fragment);
    }

    @Test
    @UiThreadTest
    public void Jalur1(){
        task = fragment.setStatus(1,1,task);
    }

    @Test
    @UiThreadTest
    public void Jalur2(){
        fragment.setStatus(2,1,task);
        Assert.assertEquals("To do",task.getStatus());
    }

    @Test
    @UiThreadTest
    public void Jalur3(){
        fragment.setStatus(1,2,task);
        Assert.assertEquals("On Progress",task.getStatus());
    }

    @Test
    @UiThreadTest
    public void Jalur4(){
        fragment.setStatus(1,3,task);
        Assert.assertEquals("Completed",task.getStatus());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        fragment =null;
    }
}