package com.example.app.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ActivityAddBacklog extends AppCompatActivity implements View.OnClickListener
{
    Button button;
    EditText etBlName, etBlDesc, etBlAssignee;
    String mDateStart;
    String mDateEnd;
    String status, name,desc,assignee;
    TextView tvDate;
    Date begdda, endda;
    Spinner spinner,spinner2;
    Backlog newBacklog, editBacklog;
    Intent resultIntent;
    String epicId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_backlog);

        etBlName = findViewById(R.id.etBlName);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        etBlDesc =findViewById(R.id.etBlDesc);
        spinner =  findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner3);
        etBlAssignee = findViewById(R.id.etBlAssignee);

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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,spinnerArray);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner :","2");
                epicId = epicID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (resultIntent.getIntExtra("req code",1)==2){
            editBacklog = resultIntent.getParcelableExtra("backlog");
            etBlName.setText(editBacklog.getName());
            int spinnerPos = adapter.getPosition(editBacklog.getStatus());
            spinner.setSelection(spinnerPos);
            etBlAssignee.setText(editBacklog.getAssignee());
            etBlDesc.setText(editBacklog.getDescription());
            begdda = editBacklog.getBegda();
            endda = editBacklog.getEndda();
            mDateStart = formatDate(begdda);
            mDateEnd = formatDate(endda);
            tvDate.setText(mDateStart+" - "+mDateEnd);
            Log.d("position", ((Integer) resultIntent.getIntExtra("position",0)).toString());

        }
        Log.d("PID",resultIntent.getStringExtra("PID"));
    }

    @Override
    public void onClick(View view) {
        if (view == tvDate){
            openDateRangePicker();
        }else if (view == button){
            if (begdda == null || endda == null){
                Toast.makeText(this,"Date cannot be empty",Toast.LENGTH_LONG).show();
            }else {
//                if (resultIntent.getIntExtra("req code",1)==1){
                    name = etBlName.getText().toString();
                    desc = etBlDesc.getText().toString();
                    assignee = etBlAssignee.getText().toString();
                    if (resultIntent.getIntExtra("req code",1)==2){
                        newBacklog = new Backlog(name,status,begdda,endda,assignee,desc,resultIntent.getStringExtra("blsID"),resultIntent.getStringExtra("PID"),"","",epicId);
                    }else {
                        newBacklog = new Backlog(name,status,begdda,endda,assignee,desc,resultIntent.getStringExtra("PID")+"-"+(resultIntent.getIntExtra("blID",0)+1),resultIntent.getStringExtra("PID"),"","",epicId);
                    }
                    Log.d("BlID",resultIntent.getStringExtra("PID")+"-"+(resultIntent.getIntExtra("blID",0)));
                    resultIntent.putExtra("result",newBacklog);
                    if (resultIntent.getIntExtra("req code",1)==2){
                        resultIntent.putExtra("position",resultIntent.getIntExtra("position",0));
                        Log.d("position", ((Integer) resultIntent.getIntExtra("position",0)).toString());
                    }
                    setResult(RESULT_OK, resultIntent);
                    finish();
//                }else if (resultIntent.getIntExtra("req code",1)==2){
//                    name = etBlName.getText().toString();
//                    desc = etBlDesc.getText().toString();
//                    assignee = etBlAssignee.getText().toString();
//                    newBacklog = new Backlog(name,status,begda,endda,assignee,desc);
//                    resultIntent.putExtra("result",newBacklog);
//                    setResult(RESULT_OK, resultIntent);
//                    finish();
//                }
            }
        }
    }

    private void openDateRangePicker(){
        FragmentDatePicker pickerFrag = new FragmentDatePicker();
        pickerFrag.setCallback(new FragmentDatePicker.Callback() {
            @Override
            public void onCancelled() {
                Toast.makeText(ActivityAddBacklog.this, "User cancel",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateTimeRecurrenceSet(final SelectedDate selectedDate, int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {

//                @SuppressLint("SimpleDateFormat")
//                SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
                begdda = selectedDate.getStartDate().getTime();
                endda = selectedDate.getEndDate().getTime();
                mDateStart = formatDate(begdda);
                mDateEnd = formatDate(endda);
                tvDate.setText(mDateStart+" - "+mDateEnd);
            }
        });

        SublimeOptions options = new SublimeOptions();
        options.setCanPickDateRange(true);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", options);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getFragmentManager(), "SUBLIME_PICKER");
    }

    public String formatDate(Date rawDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }
}