package com.example.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;
import com.example.app.model.Epic;
import com.example.app.model.Project;

public class ActivityAddEpic extends AppCompatActivity implements View.OnClickListener {

    EditText etEName, etEStatus, etEDesc;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_add_epic);
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
                Intent resultIntent = getIntent();
                Epic newEpic = new Epic(etEName.getText().toString(),etEStatus.getText().toString(),etEDesc.getText().toString(),resultIntent.getStringExtra("PID")+"-E "+(resultIntent.getIntExtra("epID",0)+1),resultIntent.getStringExtra("PID"));
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