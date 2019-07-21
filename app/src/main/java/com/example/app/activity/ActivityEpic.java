package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.BacklogEpicQuery;
import com.example.app.EpicQuery;
import com.example.app.R;
import com.example.app.fragment.FragmentBacklog;
import com.example.app.fragment.FragmentDetailEpic;
import com.example.app.fragment.FragmentTask;
import com.example.app.model.Epic;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.app.model.Backlog;

import okhttp3.OkHttpClient;
import type.CustomType;

public class ActivityEpic extends AppCompatActivity {

    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql";
    ArrayList<Backlog> listBacklogEpic;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_epic_detail:
                    fragment = FragmentDetailEpic.newInstance(epic,"");
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_list_task:
                    fragment = FragmentTask.newInstance(listBacklogEpic,"");
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    Fragment fragment;
    Epic epic;

    EditText evName, evStatus, evDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String epicID = intent.getStringExtra("epicID");
        epic =intent.getParcelableExtra("epic");
        getData(epicID);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentDetailEpic.newInstance(epic,"");
        transaction.add(R.id.fragmentContainer,fragment);
        listBacklogEpic = new ArrayList<>();
        transaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    private void getData(String epicID){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityEpic.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);

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
        BacklogEpicQuery backlogEpicQuery= BacklogEpicQuery.builder().idEpic(epicID).build();
        Log.d("Epic ID ",epicID);

        apolloClient.query(backlogEpicQuery).enqueue(new ApolloCall.Callback<BacklogEpicQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogEpicQuery.Data> response) {
                try {
                    Log.d("BL Epic count ", ((Integer) response.data().backlogE().size()).toString());
                    Log.d("Backlog :",response.data().backlogE().get(0).toString());
                    for (int i = 0 ;i<response.data().backlogE().size();i++){
                        listBacklogEpic.add(new Backlog(
                                response.data().backlogE().get(i).name(),
                                response.data().backlogE().get(i).status(),
                                "",
                                "",
                                "",
                                response.data().backlogE().get(i).description(),
                                response.data().backlogE().get(i).id(),
                                "",
                                null,
                                "",
                                null,
                                ""));
                        Log.d("Backlog :",response.data().backlogE().get(i).name());
                    }
                }catch (Exception e){
                    Log.d("Exception",e.getMessage());
                }

            }
            /**
             * Gets called whenever any action happen to this {@link ApolloCall}.
             *
             * @param event status that corresponds to a {@link ApolloCall} action
             */
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (!event.name().equalsIgnoreCase("COMPLETED")){
                    progressDialog.show();
                }else {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
}
