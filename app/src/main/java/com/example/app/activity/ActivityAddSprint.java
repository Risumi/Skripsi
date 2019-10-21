package com.example.app.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.example.app.fragment.FragmentDatePicker;
import com.example.app.R;
import com.example.app.model.Sprint;
import com.example.app.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddSprint extends AppCompatActivity implements View.OnClickListener {

    TextView startDate, endDate;
    EditText txtSprint;
    EditText sprintGoal;
    Button button;
    Intent resultIntent;
    Date begda, endda;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sprint);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        startDate = findViewById(R.id.tvBegda);
//        startDate.setOnClickListener(this);

        resultIntent = getIntent();
        user = resultIntent.getParcelableExtra("User");

        txtSprint = findViewById(R.id.tvSprint);
        txtSprint.setText("Sprint "+(resultIntent.getIntExtra("SCount",0)+1));

//        endDate = findViewById(R.id.tvEndda);
//        endDate.setOnClickListener(this);

        sprintGoal = findViewById(R.id.etGoal);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == startDate) {
            openDateRangePicker(startDate, 1);
        } else if (view == endDate) {
            openDateRangePicker(endDate, 2);
        } else if (view == button) {
            if(validateFields(txtSprint)){
                Sprint newSprint = new Sprint(
                        resultIntent.getStringExtra("PID")+"-S "+(resultIntent.getIntExtra("SCount",0)+1),
                        resultIntent.getStringExtra("PID"),
                        txtSprint.getText().toString(),
                        null,
                        null,
                        sprintGoal.getText().toString(),
                        "Created",
                        null,
                        new Date(),
                        user.getEmail(),
                        null,
                        null);
                resultIntent.putExtra("sprint", newSprint);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
//            begda = new Date();
//            if (endda == null) {
//                Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_LONG).show();
//            } else {

//            }
        }
    }

    private void openDateRangePicker(final TextView textView, final int i) {
        FragmentDatePicker pickerFrag = new FragmentDatePicker();
        pickerFrag.setCallback(new FragmentDatePicker.Callback() {
            @Override
            public void onCancelled() {
                Toast.makeText(ActivityAddSprint.this, "User cancel",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateTimeRecurrenceSet(final SelectedDate selectedDate, int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {
                Date date = selectedDate.getStartDate().getTime();
                if (i == 1) {
                    begda = date;
                } else {
                    endda = date;
                }
                textView.setText(formatDate(date));
            }
        });

        SublimeOptions options = new SublimeOptions();
        options.setCanPickDateRange(false);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", options);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getFragmentManager(), "SUBLIME_PICKER");
    }

    public String formatDate(Date rawDate) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");

        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
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
}