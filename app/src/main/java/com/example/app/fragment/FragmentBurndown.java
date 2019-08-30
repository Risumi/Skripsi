package com.example.app.fragment;


import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.MainViewModel;
import com.example.app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBurndown#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBurndown extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainViewModel model;
    LineChart chart ;
    TextView txtSprint;
    public FragmentBurndown() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBurndown.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBurndown newInstance(String param1, String param2) {
        FragmentBurndown fragment = new FragmentBurndown();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_burndown, container, false);
        chart = view.findViewById(R.id.chart);
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        int daysDiff;
        txtSprint = view.findViewById(R.id.textView9);
//        txtSprint.setText("Sprint "+model.getCurrentSprint().getValue().getId().substring(model.getCurrentSprint().getValue().getId().length()-1));
//        txtSprint.setText("Sprint 2");
        DateTime dateTime = new DateTime();
        if (model.getCurrentSprint()!=null){
            try {
                dateTime = new DateTime(model.getCurrentSprint().getValue().getBegda());
                daysDiff= dateDiff(model.getCurrentSprint().getValue().getBegda(),model.getCurrentSprint().getValue().getEndda());
                txtSprint.setText("Sprint "+model.getCurrentSprint().getValue().getId().substring(model.getCurrentSprint().getValue().getId().length()-1));
            }catch (Exception e){
                daysDiff = 7;
            }
        }else {

            daysDiff = 7;
        }
        Log.d("daysDiff", ((Integer) daysDiff).toString());

        LineData lineData = new LineData();
        DateTime tempDate = dateTime;

        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0 ; i<daysDiff;i++){
            dateTime = tempDate.plusDays(i);
            entries.add(new Entry(i,(daysDiff-1)-i));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Ideal Effort");
        int color = ContextCompat.getColor(this.getContext(), R.color.line1);
        dataSet.setColor(color);
        lineData.addDataSet(dataSet);

        List<Entry> entries1 = new ArrayList<Entry>();
        List<DateTime> exTime = new ArrayList<>();
        for(int i = 0 ;i<daysDiff;i++) {
            exTime.add(tempDate.plusDays(i));
        }
//        dateTime = tempDate.plusDays(i);
        entries1.add(new Entry(0,(6)));
        entries1.add(new Entry(1,(5)));
        entries1.add(new Entry(2,(5)));
        entries1.add(new Entry(3,(3)));
        entries1.add(new Entry(4,(3)));
        entries1.add(new Entry(5,(2)));
        entries1.add(new Entry(6,(0)));


//        }
        LineDataSet dataSet1 = new LineDataSet(entries1, "Actual Effort");
        color = ContextCompat.getColor(this.getContext(), R.color.line2);
        dataSet1.setColor(color);
        lineData.addDataSet(dataSet1);
        ValueFormatter valueFormatter = new ValueFormatter() {
             /**
             * Called when drawing any label, used to change numbers into formatted strings.
             *
             * @param value float to be formatted
             * @return formatted string label
             */
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(Math.round(value));
            }
        };
        lineData.setValueFormatter(valueFormatter);


        Description description = new Description();
        description.setText("");
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new formatter());
//        xAxis.setLabelCount(daysDiff/3);
        chart.setDescription(description);    // Hide the description
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);
        chart.setData(lineData);
        chart.invalidate();
        return view;
    }
    int dateDiff(Date beggda, Date endda){
        DateTime startDate = new DateTime(beggda);
        DateTime endDate = new DateTime(endda);
        Days d = Days.daysBetween(startDate, endDate);
        int days = d.getDays();
        return days;
    }

    class formatter extends ValueFormatter{
        /**
         * Used to draw axis labels, calls {@link #getFormattedValue(float)} by default.
         *
         * @param value float to be formatted
         * @param axis  axis being labeled
         * @return formatted string label
         */
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
            return fmt.print(((Float) value).longValue());
        }
    }
}
