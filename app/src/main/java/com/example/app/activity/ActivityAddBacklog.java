package com.example.app.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityAddBacklog extends AppCompatActivity implements View.OnClickListener
{
    Button button, button2;
    EditText etBlName, etBlDesc;
    String mDateStart;
    String mDateEnd;
    String status, name,desc,assignee;
    TextView tvDate;
    Date begdda, endda;
    Spinner spinner,spinner2, spinner3;
    Backlog newBacklog, editBacklog;
    Intent resultIntent;
    String epicId="";
    String sprintId="";
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
        spinner =  findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner3);
        spinner3 = findViewById(R.id.spinner4);
        spinner3 = findViewById(R.id.spinner4);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner :","1");
                status = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        resultIntent  = getIntent();

        ArrayList<String> spinnerArray = resultIntent.getStringArrayListExtra("spinner");
        ArrayList<String> epicID = resultIntent.getStringArrayListExtra("epicID");
        spinnerArray.add(0,"Select Epic");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,spinnerArray){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable @android.support.annotation.Nullable View convertView, @NonNull @android.support.annotation.NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner :","2");
                if (epicID.size()!=0){
                    Log.d("index spinner",Integer.toString(i));
                    if (adapterView.getAdapter().getCount()>0){
                        if ((i-2)<0){

                        }else {
                            epicId = epicID.get(i - 2);
                            if (epicId.equalsIgnoreCase("---")) {
                                epicId = "";
                            }
                        }
                    }
                    Log.d("epic ID",epicId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Asignee, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner :","1");
                assignee = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (resultIntent.getIntExtra("req code",1)==2){
            button2.setVisibility(View.VISIBLE);
            editBacklog = resultIntent.getParcelableExtra("backlog");
            etBlName.setText(editBacklog.getName());
            int spinnerPos = adapter.getPosition(editBacklog.getStatus());
            spinner.setSelection(spinnerPos);
//            Log.d("Loh",resultIntent.getStringExtra("epicName"));
            int spinnerPos2 = adapter2.getPosition(resultIntent.getStringExtra("epicName"));
            Log.d("Epic Namee",resultIntent.getStringExtra("epicName"));
            spinner2.setSelection(spinnerPos2);
//            int spinnerPos3 = adapter.getPosition(editBacklog.getAssignee());
//            spinner.setSelection(spinnerPos3);
            sprintId = editBacklog.getIdSprint();
            etBlDesc.setText(editBacklog.getDescription());
//            tvDate.setText(mDateStart+" - "+mDateEnd);
            Log.d("position", ((Integer) resultIntent.getIntExtra("position",0)).toString());

        }
        Log.d("PID",resultIntent.getStringExtra("PID"));
    }


    @Override
    public void onClick(View view) {
         if (view == button){
            if (validateFields(etBlName)){
                name = etBlName.getText().toString();
                desc = etBlDesc.getText().toString();
//                    assignee = "";
                if (resultIntent.getIntExtra("req code",1)==2){
                    newBacklog = new Backlog(resultIntent.getStringExtra("blsID"),resultIntent.getStringExtra("PID"),sprintId,epicId,name,status,"admin@admin.com",desc,new Date(),"admin@admin.com",new Date(),"admin@admin.com");
                }else {
                    int lastNum = Integer.parseInt(resultIntent.getStringExtra("blID").substring(resultIntent.getStringExtra("blID").length() - 1))+1;
                    newBacklog = new Backlog(resultIntent.getStringExtra("PID")+"-"+lastNum,resultIntent.getStringExtra("PID"),"",epicId,name,status,"admin@admin.com",desc,new Date(),"admin@admin.com",null,null);
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