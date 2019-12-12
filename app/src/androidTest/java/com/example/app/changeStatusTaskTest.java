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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;


public class changeStatusTaskTest {
    @Rule
    public ActivityTestRule<ActivityAddEpic> rule = new ActivityTestRule<>(ActivityAddEpic.class);

    ActivityAddEpic mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = rule.getActivity();
    }

    @Test
    @UiThreadTest
    public void Jalur1(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "";
        view.setText(test);
        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
    }

    @Test
    @UiThreadTest
    public void Jalur2(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Mo";
        view.setText(test);
        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
    }

    @Test
    @UiThreadTest
    public void Jalur3(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
        view.setText(test);
        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
        Assert.assertThat("Field must be at most 50 characters",view.getText().toString().length(),lessThan(50));
    }

    @Test
    @UiThreadTest
    public void Jalur4(){
        EditText view = mActivity.findViewById(R.id.etEpicName);
        String test  = "Front End";
        view.setText(test);
        Assert.assertThat("Field cannot be blank",view.getText().toString(),is(not(isEmptyString())));
        Assert.assertThat("Field must be at least 3 characters",view.getText().toString().length(),greaterThan(3));
        Assert.assertThat("Field must be at most 50 characters",view.getText().toString().length(),lessThan(50));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}