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

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import graphql.UserMutation;
import okhttp3.OkHttpClient;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener{
    EditText etEmail;
    EditText etName;
    EditText etPassword;
    Button  button;
    AlertDialog.Builder builder;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        button = findViewById(R.id.button7);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        validateFields(etName);
        validateFields(etEmail);
        validateFields(etPassword);
        if (validateFields(etName) && validateFields(etEmail) && validateFields(etPassword)){
            createUser(etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
        }
    }

    private void createUser(String name,String email,String password){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityRegister.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                    ActivityRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityRegister.this,"The email has been registered by another user.\nPlease use another email address ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();
                }else {
                    ActivityRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityRegister.this,"User created", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    });
                }
            }
            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
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
        builder.setMessage("An error has occurred"+"\nRetry ?");
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

    private boolean validateFields(EditText editText) {
        if (editText.getText().toString() == "") {
            editText.setError("Field cannot be blank");
            return false;
        }else if (editText.getText().length() < 6) {
            editText.setError("Field must be at least 6 characters");
            return false;
        }else if (editText.getText().length() > 50){
            editText.setError("Field must be at most 50 characters");
            return false;
        }else if (editText == etEmail ){
            if (!isValid(editText.getText().toString())){
                editText.setError("Please enter a valid email address");
                return false;
            }else {
                return true;
            }
        }
        else{
            return true;
        }
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
