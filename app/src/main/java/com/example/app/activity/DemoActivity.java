package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.app.R;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        String[] COUNTRIES = new String[] {"To Do", "On Progress", "Completed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.dropdown_menu_popup_item, COUNTRIES);
        AutoCompleteTextView editTextFilledExposedDropdown =  findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setText(adapter.getItem(0));
        editTextFilledExposedDropdown.setAdapter(adapter);
    }
}
