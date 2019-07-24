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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.example.app.R;
import com.example.app.fragment.FragmentDatePicker;
import com.example.app.model.Sprint;
import com.example.app.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class ActivityStartSprint extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Spinner spinner;
    EditText etName,etStart, etEnd, etGoal;
    Button btn;
    ImageView img1, img2;
    Intent intent;
    Sprint sprint;
    Date begda,endda;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_sprint);

        intent = getIntent();
        user =intent.getParcelableExtra("User");

        spinner = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Duration,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        etName = findViewById(R.id.editText4);
        etStart = findViewById(R.id.editText5);
        Date now = new Date();
        etStart.setText(formatDate(now));
        etEnd = findViewById(R.id.editText6);
        etGoal = findViewById(R.id.etGoal);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        img1 = findViewById(R.id.imageView6);
        img1.setOnClickListener(this);
        img2 = findViewById(R.id.imageView7);
        img2.setOnClickListener(this);
        etStart.setFocusable(false);
        etEnd.setFocusable(false);
//        etEnd.setEnabled(false);
        sprint = intent.getParcelableExtra("Sprint");

        etName.setText(sprint.getName());
        etGoal.setText(sprint.getSprintGoal());

        begda = new Date();
        endda = new Date();
    }

    @Override
    public void onClick(View view) {
        if (view==btn){
            Date now = new Date();
            /*if (begda.before(now)) {
                Toast.makeText(this,"Start date must after today ",Toast.LENGTH_SHORT).show();
            }else*/
            if (begda.after(endda)){
                Toast.makeText(this,"Start date must before end date",Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("Endda",endda.toString());
                Sprint newSprint = new Sprint(sprint.getId(),
                        sprint.getIdProject(),
                        etName.getText().toString(),
                        begda,
                        endda,
                        etGoal.getText().toString(),
                        sprint.getCreateddate(),
                        sprint.getCreatedby(),
                        new Date(),
                        user.getEmail());
                intent.putExtra("Sprint", newSprint);
                setResult(RESULT_OK, intent);
                finish();
            }
        }else if (view ==img1){
            openDateRangePicker(etStart,1);
        }else if (view == img2){
            openDateRangePicker(etEnd,2);
            spinner.setSelection(4);
//            etEnd.setText(formatDate(endda));
        }
    }

    private void openDateRangePicker(final TextView textView, final int i) {
        FragmentDatePicker pickerFrag = new FragmentDatePicker();
        pickerFrag.setCallback(new FragmentDatePicker.Callback() {
            @Override
            public void onCancelled() {
                Toast.makeText(ActivityStartSprint.this, "User cancel",
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


    public Date formatString(String string) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date();
        try {
            date = formatDate.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            etStart.setError("Please enter valid date");
        }
        return date;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0: etEnd.setText("");break;
            case 1:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),1));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),1);
                break;
            case 2:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),2));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),2);
                break;
            case 3:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),3));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),3);
                break;
            case 4:
//                etEnd.setText("");
                break;
            default:break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    String getAddWeek(Date date,int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR,i);
        return formatDate(calendar.getTime());
    }

    Date getAddWeekDate(Date date,int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR,i);
        return calendar.getTime();
    }
}
