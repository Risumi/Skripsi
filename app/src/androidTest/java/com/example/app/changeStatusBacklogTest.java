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



public class changeStatusBacklogTest {
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
//        new Thread().wait(10000);
        Thread.sleep(5000);
        fragment= FragmentSprint.newInstance("","");
        mActivity.loadFragment(fragment);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fragment= FragmentSprint.newInstance("","");
//                mActivity.loadFragment(fragment);
//            }
//        }, 5000);
    }

    @Test
    @UiThreadTest
    public void Jalur1(){
        task = fragment.setStatusBacklog(0,0,task);
        Assert.assertEquals("To Do",task.getStatus());
    }

    @Test
    @UiThreadTest
    public void Jalur2(){
        task = fragment.setStatusBacklog(1,0,task);
        Assert.assertEquals("To Do",task.getStatus());
    }

    @Test
    @UiThreadTest
    public void Jalur3(){
        task = fragment.setStatusBacklog(0,1,task);
        Assert.assertEquals("On Progress",task.getStatus());
    }

    @Test
    @UiThreadTest
    public void Jalur4(){
        task = fragment.setStatusBacklog(0,2,task);
        Assert.assertEquals("Completed",task.getStatus());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        fragment =null;
    }
}