package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.model.Project;
import com.example.app.R;
import com.example.app.model.User;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import graphql.ProjectValidationQuery;
import graphql.SprintMutation;
import okhttp3.OkHttpClient;
import type.CustomType;

public class ActivityAddProject extends AppCompatActivity implements View.OnClickListener{

    EditText etPName, etPKey, etDesc;
    Button btn;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etPName = findViewById(R.id.etPName);
        etPKey = findViewById(R.id.etPKey);
        etDesc = findViewById(R.id.etPDesc);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn){
            validateFields(etPKey);
            validateFields(etPName);
            if (validateFields(etPName) && validateFields(etPKey)){
                validateProject(etPKey.getText().toString());
            }
        }
    }

    private boolean validateFields(EditText editText) {
        if (editText.getText().toString().equals("")) {
            editText.setError("Field cannot be blank");
            return false;
        }else if (editText.getText().length() < 3) {
            editText.setError("Field must be at least 3 characters");
            return false;
        }else if (editText.getText().length() > 5){
            if (editText.getId()==etPKey.getId()){
                editText.setError("Field must be at most 5 characters");
                return false;
            }else {
                if (editText.getText().length() > 50){
                    editText.setError("Field must be at most 50 characters");
                    return false;
                }else {
                    return true;
                }
            }
        }
        else{
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }


    public void validateProject(String id){
        ProgressDialog progressDialog = new ProgressDialog(ActivityAddProject.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        progressDialog.show();
        final Integer[] size = new Integer[1];
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        ProjectValidationQuery projectValidationQuery= ProjectValidationQuery.builder()
                .id(id)
                .build();
        apolloClient.query(projectValidationQuery).enqueue(new ApolloCall.Callback<ProjectValidationQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<ProjectValidationQuery.Data> response) {
                Log.d("Berhasil","yay");
                if (response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                }else{
                    size[0] = response.data().projectValidation().size();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                if (event.name().equalsIgnoreCase("COMPLETED")){
                    progressDialog.dismiss();
                    if (size[0]==0){
                        Intent resultIntent = getIntent();
                        Project newProject = new Project(etPName.getText().toString(),etPKey.getText().toString(),"",etDesc.getText().toString());
                        resultIntent.putExtra("result",newProject);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(ActivityAddProject.this, "A Project Key with the specified ID already exist. Please choose a different one.", Toast.LENGTH_SHORT).show();
                                initializeAlertDialog();
                                AlertDialog alert11 = builder.create();
                                alert11.show();
                            }
                        });
                    }
                }
            }
        });
    }
    private AlertDialog.Builder builder;

    private void initializeAlertDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("A Project Key with the specified ID already exist. Please choose a different one.");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
    }

}
