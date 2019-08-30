package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.app.R;
import com.example.app.UserLoginQuery;
import com.example.app.UserMutation;
import com.example.app.model.User;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener{
    EditText etEmail;
    EditText etName;
    EditText etPassword;
    Button  button;
    AlertDialog.Builder builder;
    private static final String BASE_URL = "http://jectman.herokuapp.com/api/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etEmail = findViewById(R.id.editText7);
        etName = findViewById(R.id.editText9);
        etPassword = findViewById(R.id.editText8);
        button = findViewById(R.id.button7);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (etName.getText().toString().length()<3 || etPassword.getText().toString().length()<3 || etEmail.getText().toString().length() < 10){
            Toast.makeText(this,"Field must be at least 3 characters",Toast.LENGTH_SHORT).show();
        }else {
            createUser(etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
        }
    }

    private void createUser(String name,String email,String password){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityRegister.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        UserMutation userMutation= UserMutation.builder().
                nama(name).
                email(email).
                password(password).
                role("User").
                build();
        apolloClient.mutate(userMutation).enqueue(new ApolloCall.Callback<UserMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UserMutation.Data> response) {
                if (response.hasErrors()) {
                    progressDialog.dismiss();
                    Toast.makeText(ActivityRegister.this,response.errors().get(0).message(), Toast.LENGTH_SHORT).show();
                }else {
                    ActivityRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityRegister.this,"User created", Toast.LENGTH_LONG).show();
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
                    finish();
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                progressDialog.dismiss();
                ActivityRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeAlertDialog(e.getMessage());
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                });
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
                        createUser(etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
    }
}
