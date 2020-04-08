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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import graphql.CompleteSprintMutation;
import type.BacklogInput;
import type.SprintInput;


public class UnitTest1 {
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
        intent.putExtra("User",new User("rizky@gmail.com","Suhaimi Rizky Pro"));
        intent.putExtra("project",new Project("Project Management","JECT","",null));
        rule.launchActivity(intent);
        mActivity = rule.getActivity();
        Thread.sleep(5000);
        fragment= FragmentSprint.newInstance("","");
        mActivity.loadFragment(fragment);
    }

    @Test
    @UiThreadTest
    public void CompleteSprint1(){
        CompleteSprintMutation completeSprintMutation = fragment.completeSprint();
        SprintInput sprintInput = completeSprintMutation.variables().sprint().value;
        Assert.assertEquals("Done",sprintInput.status());
    }

    @Test
    @UiThreadTest
    public void CompleteSprint2(){
        ArrayList<Backlog> backlogs = new ArrayList<>();

        fragment.setList(backlogs,0);
        fragment.setList(backlogs,1);
        fragment.setList(backlogs,2);

        CompleteSprintMutation completeSprintMutation = fragment.completeSprint();
        SprintInput sprintInput = completeSprintMutation.variables().sprint().value;
        Assert.assertEquals("Done",sprintInput.status());

        List<BacklogInput> backlogInputList = completeSprintMutation.variables().backlog().value;
        Assert.assertEquals(0,backlogInputList.size());
    }

    @Test
    @UiThreadTest
    public void CompleteSprint3(){
        ArrayList<Backlog> backlogs = new ArrayList<>();

        fragment.setList(backlogs,0);
        fragment.setList(backlogs,1);
        fragment.setList(backlogs,2);

        CompleteSprintMutation completeSprintMutation = fragment.completeSprint();
        SprintInput sprintInput = completeSprintMutation.variables().sprint().value;
        Assert.assertEquals("Done",sprintInput.status());

        List<BacklogInput> backlogInputList = completeSprintMutation.variables().backlog().value;
        Assert.assertEquals(0,backlogInputList.size());
    }

    @Test
    @UiThreadTest
    public void CompleteSprint4(){
        ArrayList<Backlog> backlogs = new ArrayList<>();
        backlogs.add(new Backlog("JECT 1","JECT","JECT-S 1","",
                "Test 1","To Do","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 2","JECT","JECT-S 1","",
                "Test 2","To Do","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 3","JECT","JECT-S 1","",
                "Test 3","To Do","","",new Date(),"",
                new Date(),""));
        fragment.setList(backlogs,0);

        backlogs = new ArrayList<>();
        backlogs.add(new Backlog("JECT 4","JECT","JECT-S 1","",
                "Test 4","To Do","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 5","JECT","JECT-S 1","",
                "Test 5","To Do","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 6","JECT","JECT-S 1","",
                "Test 6","To Do","","",new Date(),"",
                new Date(),""));
        fragment.setList(backlogs,1);

        backlogs = new ArrayList<>();
        fragment.setList(backlogs,2);

        CompleteSprintMutation completeSprintMutation = fragment.completeSprint();
        SprintInput sprintInput = completeSprintMutation.variables().sprint().value;
        Assert.assertEquals("Done",sprintInput.status());

        SprintInput newSprintInput = completeSprintMutation.variables().newSprint().value;
        List<BacklogInput> backlogInputList = completeSprintMutation.variables().backlog().value;
        for (int i = 0 ; i< backlogInputList.size();i++){
            Assert.assertEquals(newSprintInput.id(),backlogInputList.get(i).idSprint());
        }
    }

    @Test
    @UiThreadTest
    public void CompleteSprint5(){
        ArrayList<Backlog> backlogs = new ArrayList<>();
        fragment.setList(backlogs,0);

        fragment.setList(backlogs,1);

        backlogs.add(new Backlog("JECT 1","JECT","JECT-S 1","",
                "Test 1","Completed","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 2","JECT","JECT-S 1","",
                "Test 2","Completed","","",new Date(),"",
                new Date(),""));
        backlogs.add(new Backlog("JECT 3","JECT","JECT-S 1","",
                "Test 3","Completed","","",new Date(),"",
                new Date(),""));
        fragment.setList(backlogs,2);

        CompleteSprintMutation completeSprintMutation = fragment.completeSprint();
        SprintInput sprintInput = completeSprintMutation.variables().sprint().value;
        Assert.assertEquals("Done",sprintInput.status());

        List<BacklogInput> backlogInputList = completeSprintMutation.variables().backlog().value;
        for (int i = 0 ; i< backlogInputList.size();i++){
            Assert.assertEquals("Done",backlogInputList.get(i).status());
        }
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        fragment =null;
    }
}