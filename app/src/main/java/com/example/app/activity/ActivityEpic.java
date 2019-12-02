package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.R;
import com.example.app.fragment.FragmentDetailEpic;
import com.example.app.fragment.FragmentTask;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import graphql.BacklogEpicQuery;
import okhttp3.OkHttpClient;
import type.CustomType;

public class ActivityEpic extends AppCompatActivity {

    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";
    ArrayList<Backlog> listBacklogEpic;
    Intent intent;
    int totalTask;
    private static final String TAG = "ActivityEpic";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_epic_detail:
                    fragment = FragmentDetailEpic.newInstance(epic,totalTask);
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
    String epicID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        epicID = intent.getStringExtra("epicID");
        epic =intent.getParcelableExtra("epic");
        getData(epicID);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intent =getIntent();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentDetailEpic.newInstance(epic,0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.epic_menu,menu);
        return true;
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
                                response.data().backlogE().get(i).id(),
                                "",
                                "",
                                epicID,
                                response.data().backlogE().get(i).name(),
                                response.data().backlogE().get(i).status(),
                                response.data().backlogE().get(i).id(),
                                response.data().backlogE().get(i).description(),
                                null,
                                "",
                                null,
                                ""));
//                        Log.d("Backlog :",response.data().backlogE().get(i).name());
                        totalTask = listBacklogEpic.size();
                    }
                }catch (Exception e){
                    Log.d("Exception",e.getMessage());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((FragmentDetailEpic) fragment).setTotalTask(((Integer) totalTask).toString());
                    }
                });
            }
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
                progressDialog.dismiss();
                Log.d("Gagal",e.toString());
                startAlert(e.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                intent.putExtra("epic",epic);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.delete_epic:
                AlertDialog.Builder builderDelete = new AlertDialog.Builder(this);
                builderDelete.setMessage("Are you sure ?");
                builderDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        intent.putExtra("epic",epic);
                        setResult(RESULT_FIRST_USER,intent);
                        finish();
                    }
                });
                builderDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builderDelete.create();
                alertDialog.show();
                break;
            case R.id.edit_epic:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("epic",epic);
        setResult(RESULT_OK,intent);
        finish();
    }

    AlertDialog.Builder builder;

    public void startAlert(String error) {
        ActivityEpic.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initializeAlertDialog(error);
                AlertDialog alert11 = builder.create();
                alert11.show();
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
                        getData(epicID);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intent.putExtra("epic",epic);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
    }
}
