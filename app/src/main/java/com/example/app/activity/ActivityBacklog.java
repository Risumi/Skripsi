package com.example.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.app.R;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import graphql.BacklogDQuery;
import okhttp3.OkHttpClient;

public class ActivityBacklog extends AppCompatActivity {

    EditText etName;
    EditText etID;
    EditText etStatus;
    EditText etAssignee;
    EditText etEpic;
    EditText etDescription;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        etName = findViewById(R.id.backlog_name);
        etID = findViewById(R.id.backlog_id);
        etStatus = findViewById(R.id.backlog_status);
        etAssignee = findViewById(R.id.backlog_assignee);
        etEpic = findViewById(R.id.backlog_epic);
        etDescription = findViewById(R.id.backlog_description);

        String idBacklog = getIntent().getStringExtra("ID Backlog");
        getBacklog(idBacklog);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getBacklog(String id){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        BacklogDQuery backlogDQuery = BacklogDQuery.builder().
                id(id).
                build();
        apolloClient.query(backlogDQuery).enqueue(new ApolloCall.Callback<BacklogDQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogDQuery.Data> response) {
                if (response.hasErrors()) {
                    ActivityBacklog.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityBacklog.this, "An error has occurred, Please try again !", Toast.LENGTH_SHORT).show();
                            ActivityBacklog.this.finish();
                        }
                    });
                }else {
                    ActivityBacklog.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etID.setText(response.data().backlogD().get(0).id());
                            etName.setText(response.data().backlogD().get(0).name());
                            etStatus.setText(response.data().backlogD().get(0).status());
                            etEpic.setText(response.data().backlogD().get(0).idEpic()!=null?response.data().backlogD().get(0).idEpic().name():" ");
                            etAssignee.setText(response.data().backlogD().get(0).assignee()!=null?response.data().backlogD().get(0).assignee().nama():" ");
                            etDescription.setText(response.data().backlogD().get(0).description());
                        }
                    });
                }
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (!event.name().equalsIgnoreCase("COMPLETED")){
                }else {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                progressDialog.dismiss();
                ActivityBacklog.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ActivityBacklog.this, "An error has occurred, Please try again !", Toast.LENGTH_SHORT).show();
                        ActivityBacklog.this.finish();
                    }
                });
            }
        });
    }
}
