package com.example.app.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        DateTime dateTime = new DateTime();
        if (model.getCurrentSprint()!=null){
            try {
                dateTime = new DateTime(model.getCurrentSprint().getValue().getBegda());
                daysDiff= dateDiff(model.getCurrentSprint().getValue().getBegda(),model.getCurrentSprint().getValue().getEndda());
            }catch (Exception e){
                daysDiff = 5;
            }
        }else {
            daysDiff = 5;
        }
        Log.d("daysDiff", ((Integer) daysDiff).toString());
        List<Entry> entries = new ArrayList<Entry>();
        DateTime tempDate = dateTime;
        for (int i = 0 ; i<daysDiff;i++){
            dateTime = tempDate.plusDays(i);
            entries.add(new Entry(dateTime.getMillis(),(daysDiff-1)-i));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Ideal Effort");
        LineData lineData = new LineData(dataSet);
        Description description = new Description();
        description.setText("");
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new formatter());
        xAxis.setLabelCount(daysDiff/3);
        chart.setDescription(description);    // Hide the description
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
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
//    public class xAxisFormatter extends IAxisValueFormatter {
//
//        /**
//         * Called when a value from an axis is to be formatted
//         * before being drawn. For performance reasons, avoid excessive calculations
//         * and memory allocations inside this method.
//         *
//         * @param value the value to be formatted
//         * @param axis  the axis the value belongs to
//         * @return
//         * @deprecated Extend {@link ValueFormatter} and use {@link ValueFormatter#getAxisLabel(float, AxisBase)}
//         */
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return null;
//        }
//    }

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
