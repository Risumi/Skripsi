package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.app.R;
import com.example.app.model.User;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import graphql.UserLoginQuery;
import okhttp3.OkHttpClient;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
//    EditText etEmail;
//    EditText etPassword;
    MaterialButton btnLogin;
    MaterialButton btnRegister;
    Intent intent;
    User user;
    AlertDialog.Builder builder;
    EditText etEmail ;
    EditText etPassword ;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail= findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.button3);
        btnLogin.setOnClickListener(this);
        btnRegister= findViewById(R.id.button4);
        btnRegister.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin){
            intent= new Intent(this,ActivityHome.class);
            fetchLogin(etEmail.getText().toString(),etPassword.getText().toString());
        }else if (view == btnRegister) {
            intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);
        }
    }

    private void fetchLogin(String email,String password){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityLogin.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        UserLoginQuery loginQuery = UserLoginQuery.builder().
                email(email).
                password(password).
                build();
        apolloClient.query(loginQuery).enqueue(new ApolloCall.Callback<UserLoginQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UserLoginQuery.Data> response) {
//                Log.d("User",response.data().user().get(0).nama());
                if (response.hasErrors()) {
                    progressDialog.dismiss();
                }else if (response.data().user().size()==1){

                    user = new User(response.data().user().get(0).email(), response.data().user().get(0).nama());
                    intent.putExtra("User",user);
                    startActivity(intent);
                }else if (response.data().user().size()!=1){
                    progressDialog.dismiss();
                    ActivityLogin.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(ActivityLogin.this, "Email and password didn't match", Toast.LENGTH_SHORT).show();
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
                ActivityLogin.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ActivityLogin.this, "An error has occurred, Please try again !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
