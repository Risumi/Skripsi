package com.example.app.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.example.app.model.Backlog;
import com.example.app.fragment.FragmentDatePicker;
import com.example.app.R;
import com.example.app.model.Project;
import com.example.app.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddBacklog extends AppCompatActivity implements View.OnClickListener
{
    Button button, button2;
    EditText etBlName, etBlDesc;
    String mDateStart;
    String mDateEnd;
    String status, name,desc;
    String assignee="";
    TextView tvDate;
    Date begdda, endda;
    Backlog newBacklog, editBacklog;
    Intent resultIntent;
    String epicId="";
    String sprintId="";
    User user;
    AutoCompleteTextView ddStatus, ddEpic, ddAssignee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_backlog);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etBlName = findViewById(R.id.etBlName);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        button2 = findViewById(R.id.button5);
        button2.setOnClickListener(this);
        etBlDesc =findViewById(R.id.etBlDesc);
        ddStatus = findViewById(R.id.ddStatus);
        ddEpic = findViewById(R.id.ddEpic);
        ddAssignee = findViewById(R.id.ddAssignee);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Status,R.layout.dropdown_menu_popup_item);
        ddStatus.setAdapter(adapter);
        ddStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                status = adapterView.getItemAtPosition(i).toString();
            }
        });

        resultIntent  = getIntent();
        user = resultIntent.getParcelableExtra("User");
        ArrayList<String> spinnerArray = resultIntent.getStringArrayListExtra("spinner");
        ArrayList<String> spinnerArray2 = resultIntent.getStringArrayListExtra("spinner2");
        ArrayList<String> epicID = resultIntent.getStringArrayListExtra("epicID");
        ArrayList<String> emailUser = resultIntent.getStringArrayListExtra("emailUser");
        spinnerArray2.add(0,"Unassigned");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,R.layout.dropdown_menu_popup_item,spinnerArray);
        ddEpic.setAdapter(adapter2);
        ddEpic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                epicId= epicID.get(i);
            }
        });

        ArrayAdapter<String> adapter3= new ArrayAdapter<>(this,R.layout.dropdown_menu_popup_item,spinnerArray2);
        ddAssignee.setAdapter(adapter3);
        ddAssignee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    assignee = "";
                }else {
                    assignee= emailUser.get(i-1);
                }
            }
        });


        if (resultIntent.getIntExtra("req code",1)==2){
            button2.setVisibility(View.VISIBLE);
            editBacklog = resultIntent.getParcelableExtra("backlog");
            etBlName.setText(editBacklog.getName());
            ddStatus.setText(editBacklog.getStatus(),false);
            status = editBacklog.getStatus();
            ddEpic.setText(resultIntent.getStringExtra("epicName"),false);
            String nama= resultIntent.getStringExtra("userName");
            if (nama.equalsIgnoreCase("")){
                ddAssignee.setText("Unassigned",false);
            }else {
                ddAssignee.setText(resultIntent.getStringExtra("userName"),false);
            }

            sprintId = editBacklog.getIdSprint();
            etBlDesc.setText(editBacklog.getDescription());

            Log.d("position", ((Integer) resultIntent.getIntExtra("position",0)).toString());

        }
        Log.d("PID",resultIntent.getStringExtra("PID"));
        Log.d("BlID",resultIntent.getStringExtra("PID")+"-"+(resultIntent.getIntExtra("blID",0)));

    }


    @Override
    public void onClick(View view) {
         if (view == button){
            if (validateFields(etBlName)){
                name = etBlName.getText().toString();
                desc = etBlDesc.getText().toString();
                if (status==null){
                    status = ddStatus.getAdapter().getItem(0).toString();
                }
                if (resultIntent.getIntExtra("req code",1)==2){
                    newBacklog = new Backlog(
                            resultIntent.getStringExtra("blsID"),
                            resultIntent.getStringExtra("PID"),
                            sprintId,
                            epicId,
                            name,
                            status,
                            assignee,
                            desc,
                            editBacklog.getCreateddate(),
                            editBacklog.getCreatedby(),
                            new Date(),
                            user.getEmail());
                }else {
                    newBacklog = new Backlog(
                            resultIntent.getStringExtra("PID")+"-"+(resultIntent.getIntExtra("blID",0)+1),
                            resultIntent.getStringExtra("PID"),
                            "",
                            epicId,
                            name,
                            status,
                            assignee,
                            desc,
                            new Date(),
                            user.getEmail(),
                            null,
                            null);
                }
                Log.d("BlID",resultIntent.getStringExtra("PID")+"-"+(resultIntent.getIntExtra("blID",0)));
                resultIntent.putExtra("result",newBacklog);
                if (resultIntent.getIntExtra("req code",1)==2){
                    resultIntent.putExtra("position",resultIntent.getIntExtra("position",0));
                    Log.d("position", ((Integer) resultIntent.getIntExtra("position",0)).toString());
                }
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }else if (view == button2){
            initializeAlertDialog();
            AlertDialog alert11 = builder.create();
            alert11.show();
        }
    }

    AlertDialog.Builder builder;

    void initializeAlertDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure  ?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        setResult(2, resultIntent);
                        finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
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