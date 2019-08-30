package com.example.app.fragment;



import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.SprintAdapter;
import com.example.app.model.Sprint;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSprintReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSprintReports extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LineChart chart ;
    TextView txtSprint;

    public FragmentSprintReports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSprintReports.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSprintReports newInstance(String param1, String param2) {
        FragmentSprintReports fragment = new FragmentSprintReports();
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
        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    Spinner spinner;
    MainViewModel model;
    TextView txtDate, tvRvBot;
    View view2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sprint_reports, container, false);

        initializeChart(view);

        spinner = view.findViewById(R.id.spinner7);
        etGoal = view.findViewById(R.id.editText3);
        txtDate = view.findViewById(R.id.textView22);
        List<String> spinnerArray = new ArrayList<>();
        for (int i=0;i<model.getListSprintDone().getValue().size();i++){
            spinnerArray.add(model.getListSprintDone().getValue().get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,spinnerArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        rvCompleted = view.findViewById(R.id.rvTop);
        rvNotCompleted = view.findViewById(R.id.rvBottom);


//        ArrayList<Backlog> listCompleted= new ArrayList<>();
//        listCompleted.add(new Backlog("DOT-1",null,null,"DOT-E 1","Graphql query","Completed",null,null,null,null,null,null));
//        listCompleted.add(new Backlog("DOT-2",null,null,"DOT-E 2","Halaman profil","Completed",null,null,null,null,null,null));
//
//        ArrayList<Backlog> listNotCompleted= new ArrayList<>();
//        listNotCompleted.add(new Backlog("DOT-3",null,null,"DOT-E 1","Graphql mutation","On Progress",null,null,null,null,null,null));
//
//        ArrayList<Epic> listEpic = new ArrayList<>();
//        listEpic.add(new Epic("DOT-E 1",null,"Back end",null,null,null,null,null));
//        listEpic.add(new Epic("DOT-E 2",null,"Front end",null,null,null,null,null));

        rvCompleted.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        AdapterCompleted = new SprintAdapter(model.getListBacklogSprintDone().getValue(),model.getListEpic().getValue());
        rvCompleted.setAdapter(AdapterCompleted);

//        rvNotCompleted.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        AdapterNotCompleted = new SprintAdapter(listNotCompleted,listEpic);
//        rvNotCompleted.setAdapter(AdapterNotCompleted);
//
//        view2 = view.findViewById(R.id.view2);
//        tvRvBot = view.findViewById(R.id.textView15);
//
//        view2.setVisibility(View.GONE);
//        tvRvBot.setVisibility(View.GONE);
//        rvNotCompleted.setVisibility(View.GONE);

        return view;
    }
    SprintAdapter AdapterCompleted, AdapterNotCompleted;
    void initializeChart(View view){
        chart = view.findViewById(R.id.chart);
        int daysDiff = 7;

        DateTime dateTime = new DateTime();
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

    }

    EditText etGoal;
    RecyclerView rvCompleted, rvNotCompleted;
    Sprint selectedSprint;
    int indexSprint;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (model.getListSprintDone().getValue().get(i)!=null) {
            if (model.getListSprintDone().getValue().get(i).getBegda()!=null){
                etGoal.setText(model.getListSprintDone().getValue().get(i).getSprintGoal());
                txtDate.setText(formatDate(model.getListSprintDone().getValue().get(i).getBegda())+" - "+formatDate(model.getListSprintDone().getValue().get(i).getEndda()));
            }
        }
        selectedSprint= model.getListSprintDone().getValue().get(i);
        indexSprint = i ;
        model.filterSprintDone(selectedSprint.getId());
        AdapterCompleted.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public String formatDate(Date rawDate) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");

        String formattedDate = formatDate.format(rawDate);
        return formattedDate;
    }
}

