package com.example.app;

import android.widget.EditText;

import com.example.app.activity.ActivityAddEpic;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;


public class UnitTest3 {
    @Rule
    public ActivityTestRule<ActivityAddEpic> rule = new ActivityTestRule<>(ActivityAddEpic.class);

    ActivityAddEpic mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = rule.getActivity();
    }

    @Test
    @UiThreadTest
    public void addEpic1(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "";
        view.setText(test);
//        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
        Assert.assertEquals("Field cannot be blank",mActivity.validateField(view));
    }

    @Test
    @UiThreadTest
    public void addEpic2(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Mo";
        view.setText(test);
//        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
//        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
        Assert.assertEquals("Field must be at least 3 characters",mActivity.validateField(view));
    }

    @Test
    @UiThreadTest
    public void addEpic3(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
        view.setText(test);
//        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
//        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
//        Assert.assertThat("Field must be at most 50 characters",view.getText().toString().length(),lessThan(50));
        Assert.assertEquals("Field must be at most 50 characters",mActivity.validateField(view));
    }

    @Test
    @UiThreadTest
    public void addEpic4(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Front End";
        view.setText(test);
//        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
//        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
//        Assert.assertThat("Field must be at most 50 characters",view.getText().toString().length(),lessThan(50));
        Assert.assertEquals("",mActivity.validateField(view));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}