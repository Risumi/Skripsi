package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.model.Project;
import com.example.app.R;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddProject extends AppCompatActivity implements View.OnClickListener{

    EditText etPName, etPKey, etDesc;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etPName = findViewById(R.id.etPName);
        etPKey = findViewById(R.id.etPDesc);
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
                Intent resultIntent = getIntent();
                Project newProject = new Project(etPName.getText().toString(),etPKey.getText().toString(),"",etDesc.getText().toString());
                resultIntent.putExtra("result",newProject);
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
