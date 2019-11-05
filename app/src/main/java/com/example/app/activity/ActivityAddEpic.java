package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;
import com.example.app.model.Epic;
import com.example.app.model.Project;
import com.example.app.model.User;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddEpic extends AppCompatActivity implements View.OnClickListener {

    EditText etEName, etEStatus, etEDesc;
    Button btn;
    User user;
    Intent resultIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Epic");
//        getActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_add_epic);
        resultIntent = getIntent();
        user = resultIntent.getParcelableExtra("User");
        etEName = findViewById(R.id.etEpicName);
        etEStatus= findViewById(R.id.eEpicStatus);
        etEDesc = findViewById(R.id.etEpicDesc);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn){
            if (validateFields(etEName)){
                Epic newEpic = new Epic(resultIntent.getStringExtra("PID")+"-E "+(resultIntent.getIntExtra("epID",0)+1),resultIntent.getStringExtra("PID"),etEName.getText().toString(),etEDesc.getText().toString(),new Date(),user.getEmail(),null,null);
                resultIntent.putExtra("result",newEpic);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    private boolean validateFields(EditText editText) {
        if (editText.getText().toString() == "") {
            editText.setError("Field cannot be blank");
            return false;
        }else if (editText.getText().length() < 3) {
            editText.setError("Field must be at least 3 characters");
            return false;
        }
        else if (editText.getText().length() > 50){
            if (editText.getId()==etEName.getId()){
                editText.setError("Field must be at most 50 characters");
                return false;
            }
            return true;
        }
        else{
            return true;
        }
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