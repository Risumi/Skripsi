package com.example.app;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAddBacklog extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    Button button;
    EditText etBlName, etBlDesc, etBlAssignee;
    String mDateStart;
    String mDateEnd;
    String status, name,desc,assignee;
    TextView tvDate;
    Date begdda, endda;
    Spinner spinner;
    Backlog newBacklog;
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
        spinner = (Spinner) findViewById(R.id.spinner);
        etBlAssignee = findViewById(R.id.etBlAssignee);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvDate){
            openDateRangePicker();
        }else if (view == button){
            name = etBlName.getText().toString();
            desc = etBlDesc.getText().toString();
            assignee = etBlAssignee.getText().toString();
            newBacklog = new Backlog(name,status,begdda,endda,assignee,desc);
            Intent resultIntent = getIntent();
            resultIntent.putExtra("result",newBacklog);
            setResult(RESULT_OK, resultIntent);
            finish();
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

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
                mDateStart = formatDate.format(selectedDate.getStartDate().getTime());
                mDateEnd = formatDate.format(selectedDate.getEndDate().getTime());
                begdda = selectedDate.getStartDate().getTime();
                endda = selectedDate.getEndDate().getTime();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        status = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
