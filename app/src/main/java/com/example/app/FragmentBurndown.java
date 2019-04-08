package com.example.app;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
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

        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0 ; i<5;i++){
            entries.add(new Entry(i,4-i));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Ideal Effort");
        LineData lineData = new LineData(dataSet);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);    // Hide the description
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false); 
        chart.getXAxis().setDrawGridLines(false);
        chart.setData(lineData);
        chart.invalidate();
        return view;
    }

}
