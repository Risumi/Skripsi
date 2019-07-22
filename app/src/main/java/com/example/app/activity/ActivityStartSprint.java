package com.example.app.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityStartSprint extends AppCompatActivity implements View.OnClickListener{

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
        etName = findViewById(R.id.editText4);
        etStart = findViewById(R.id.editText5);
        etEnd = findViewById(R.id.editText6);
        etGoal = findViewById(R.id.etGoal);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        img1 = findViewById(R.id.imageView6);
        img1.setOnClickListener(this);
        img2 = findViewById(R.id.imageView7);
        img2.setOnClickListener(this);

        sprint = intent.getParcelableExtra("Sprint");

        etName.setText(sprint.getId());
        etGoal.setText(sprint.getSprintGoal());
    }

    @Override
    public void onClick(View view) {
        if (view==btn){
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
        }else if (view ==img1){
            openDateRangePicker(etStart,1);
        }else if (view == img2){
            openDateRangePicker(etEnd,2);
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
}
