package com.example.app.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStartSprint extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    AutoCompleteTextView spinner;
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

        setTitle("Start Sprint");

        intent = getIntent();
        user =intent.getParcelableExtra("User");
        etName = findViewById(R.id.editText4);
        etStart = findViewById(R.id.editText5);
        Date now = new Date();
        
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
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Duration,R.layout.dropdown_menu_popup_item);
        spinner=  findViewById(R.id.filled_exposed_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(this);
        begda = new Date();
        endda = new Date();

        etStart.setText(formatDate(now));
        etName.setText(sprint.getName());
        etGoal.setText(sprint.getSprintGoal());
        if (intent.getIntExtra("Req code",0)==6){
//            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
            setTitle("Edit Sprint");
            etStart.setText(formatDate(sprint.getBegda()));
            etEnd.setText(formatDate(sprint.getEndda()));
            begda =sprint.getBegda();
            endda=sprint.getEndda();
            int weeksBeetwen= Weeks.weeksBetween(new DateTime(sprint.getBegda()), new DateTime(sprint.getEndda())).getWeeks();
            switch (weeksBeetwen){
                case 1: spinner.setText("1 week",false);break;
                case 2: spinner.setText("2 week",false);break;
                case 3: spinner.setText("3 week",false);break;
                default: spinner.setText("Custom",false);
            }
        }else if (intent.getIntExtra("Req code",0)==8){
            etStart.setVisibility(View.GONE);
            etEnd.setVisibility(View.GONE);
            TextInputLayout textInputLayout =findViewById(R.id.textInputLayout11);
            textInputLayout.setVisibility(View.GONE);
//            spinner.setVisibility(View.GONE);
//            etGoal.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
        }
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
                if (validateFields(etName) && validateFields(etEnd)){
                    Log.d("Endda",endda.toString());
                    Sprint newSprint = new Sprint(sprint.getId(),
                            sprint.getIdProject(),
                            etName.getText().toString(),
                            begda,
                            endda,
                            etGoal.getText().toString(),
                            "Active",
                            "",
                            sprint.getCreateddate(),
                            sprint.getCreatedby(),
                            new Date(),
                            user.getEmail());
                    intent.putExtra("Sprint", newSprint);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                if (intent.getIntExtra("Req code",0)==8){
                    if (validateFields(etName) ){
                        Sprint newSprint = new Sprint(sprint.getId(),
                                sprint.getIdProject(),
                                etName.getText().toString(),
                                sprint.getBegda(),
                                sprint.getEndda(),
                                etGoal.getText().toString(),
                                sprint.getStatus(),
                                "",
                                sprint.getCreateddate(),
                                sprint.getCreatedby(),
                                new Date(),
                                user.getEmail());
                        intent.putExtra("Sprint", newSprint);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        }else if (view ==img1){
            openDateRangePicker(etStart,1);
        }else if (view == img2){
            openDateRangePicker(etEnd,2);
//            etEnd.setText(formatDate(endda));
        }
    }
    int selection = 0;
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
                    String text = spinner.getText().toString();
                    if (selection!=0 && !text.equalsIgnoreCase("Custom")){
                        etEnd.setText(getAddWeek(begda,selection));
                        endda = getAddWeekDate(begda,selection);

                    }
                } else {
                    endda = date;
                    spinner.setText("Custom",false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),1));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),1);
                selection =1;
                break;
            case 1:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),2));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),2);
                selection =2;
                break;
            case 2:
                etEnd.setText(getAddWeek(formatString(etStart.getText().toString()),3));
                endda = getAddWeekDate(formatString(etStart.getText().toString()),3);
                selection =3;
                break;
        }
    }

    private boolean validateFields(EditText editText) {
        if (editText.getText().toString() == "") {
            editText.setError("Field cannot be blank");
            return false;
        }
        else if (editText.getText().length() > 50){
            if (editText.getId()==etName.getId()){
                editText.setError("Field must be at most 50 characters");
                return false;
            }
            return true;
        }
        else if (editText.getText().length() < 3) {
            editText.setError("Field must be at least 3 characters");
            return false;
        }
        else{
            return true;
        }
    }
}
