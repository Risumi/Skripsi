package com.example.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.model.Project;
import com.example.app.R;

public class ActivityAddProject extends AppCompatActivity implements View.OnClickListener{

    EditText etPName, etPKey, etDesc;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        etPName = findViewById(R.id.etBlName);
        etPKey = findViewById(R.id.etBlStatus);
        etDesc = findViewById(R.id.etPDesc);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn){
            Intent resultIntent = getIntent();
            Project newProject = new Project(etPName.getText().toString(),etPKey.getText().toString(),"",etDesc.getText().toString());
            resultIntent.putExtra("result",newProject);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}
