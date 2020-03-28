package com.example.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.example.app.R;
import com.example.app.adapter.SprintDetailAdapter;
import com.example.app.model.Backlog;
import com.example.app.model.Sprint;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import graphql.BacklogSQuery;
import okhttp3.OkHttpClient;
import type.CustomType;

public class ActivityDetailSprint extends AppCompatActivity {

    LineChart chart ;
    private static final String BASE_URL = "http://jectman.risumi.online/api/graphql";
    TextView txtDate, txtTitle;
    EditText etGoal;
    RecyclerView rvCompleted, rvNotCompleted;
    SprintDetailAdapter AdapterCompleted, AdapterNotCompleted;
    Sprint sprint;
    ArrayList<Backlog> completedList, notCompletedList;
    ArrayList<BacklogSQuery.SprintReport> chartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sprint Report");
        setContentView(R.layout.fragment_sprint_reports);
        Intent intent = getIntent();
        sprint = intent.getParcelableExtra("sprint");
        txtTitle = findViewById(R.id.textView2);
        txtTitle.setText(sprint.getName()+" Report");
        initializeComponent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData(sprint.getId());
    }

    void initializeComponent(){
        etGoal = findViewById(R.id.editText3);
        txtDate = findViewById(R.id.textView22);
        String date = formatDate(sprint.getBegda())+" - "+formatDate(sprint.getEndda());
        txtDate.setText(date);
        etGoal.setText(sprint.getSprintGoal());


        rvCompleted = findViewById(R.id.rvTop);
        rvCompleted.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        completedList = new ArrayList<>();
        AdapterCompleted = new SprintDetailAdapter(completedList);
        rvCompleted.setAdapter(AdapterCompleted);

        rvNotCompleted= findViewById(R.id.rvBottom);
        rvNotCompleted.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        notCompletedList = new ArrayList<>();
        AdapterNotCompleted= new SprintDetailAdapter(notCompletedList);
        rvNotCompleted.setAdapter(AdapterNotCompleted);

        chartData = new ArrayList<>();
    }

    void initializeChart(){
        chart = findViewById(R.id.chart);
        int largestTask = 0;

        DateTime dateTime = new DateTime();

        LineData lineData = new LineData();
        DateTime tempDate = dateTime;

        for (int i = 0; i < chartData.size(); i++) {
            if (i==0){
                largestTask = chartData.get(i).totalBacklog();
            }else {
                if (chartData.get(i).totalBacklog()>largestTask){
                    largestTask=chartData.get(i).totalBacklog();
                }
            }
        }

        List<Entry> entries = new ArrayList<Entry>();
        LineDataSet dataSet = new LineDataSet(entries, "Ideal Effort");
        int color = ContextCompat.getColor(this, R.color.line1);
        dataSet.setColor(color);
        lineData.addDataSet(dataSet);

        List<Entry> entries1 = new ArrayList<Entry>();
        for (int i = 0 ;i<chartData.size();i++){
            int inCompletedBacklog = chartData.get(i).totalBacklog()-chartData.get(i).completeBacklog();
            entries1.add(new Entry(i,(inCompletedBacklog)));
            float calc1= i/ ((float) (chartData.size()-1));
            float calc2= largestTask*calc1;
            float idealEffort = largestTask -calc2;
            entries.add(new Entry(i,idealEffort));
        }

//        }
        LineDataSet dataSet1 = new LineDataSet(entries1, "Actual Effort");
        color = ContextCompat.getColor(this, R.color.line2);
        dataSet1.setColor(color);
        lineData.addDataSet(dataSet1);
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int f = Math.round(value);
                return Integer.toString(f);
            }
        };
        lineData.setValueFormatter(valueFormatter);

        Description description = new Description();
        description.setText("");
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setDescription(description);    // Hide the description
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setData(lineData);
        chart.invalidate();

    }

    private void getData(String sprintID){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        CustomTypeAdapter<Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
            @Override public Date decode(CustomTypeValue value) {
                try {
                    return formatDate.parse(value.value.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override public CustomTypeValue encode(Date value) {
                return new CustomTypeValue.GraphQLString(formatDate.format(value));
            }
        };
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.DATE,dateCustomTypeAdapter)
                .build();
        BacklogSQuery backlogSQuery = BacklogSQuery
                .builder()
                .id(sprintID)
                .build();

        apolloClient.query(backlogSQuery).enqueue(new ApolloCall.Callback<BacklogSQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BacklogSQuery.Data> response) {
                if(response.hasErrors()){
                    Log.d("Error",response.errors().get(0).message());
                    Toast.makeText(ActivityDetailSprint.this, "An Error has occured", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }
                else{
                    for (int i = 0 ;i<response.data().backlogS().size();i++){
                        if (response.data().backlogS().get(i).status().equalsIgnoreCase("Done")){
                            completedList.add(new Backlog(
                                    response.data().backlogS().get(i).idBacklog().id(),
                                    "",
                                    sprintID,
                                    "",
                                    response.data().backlogS().get(i).idBacklog().name(),
                                    response.data().backlogS().get(i).status(),
                                    "",
                                    "",
                                    null,
                                    "",
                                    null,
                                    ""));
                        }else {
                            notCompletedList.add(new Backlog(
                                    response.data().backlogS().get(i).idBacklog().id(),
                                    "",
                                    sprintID,
                                    "",
                                    response.data().backlogS().get(i).idBacklog().name(),
                                    response.data().backlogS().get(i).status(),
                                    "",
                                    "",
                                    null,
                                    "",
                                    null,
                                    ""));
                        }
                    }
                    if (response.data().sprintReport()!=null){
                        chartData.addAll(response.data().sprintReport());
                    }
                }
            }

            @Override
            public void onStatusEvent(@NotNull ApolloCall.StatusEvent event) {
                super.onStatusEvent(event);
                Log.d("event",event.name());
                if (!event.name().equalsIgnoreCase("COMPLETED")){
                    progressDialog.show();
                }else {
                    progressDialog.dismiss();
                    ActivityDetailSprint.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideRV();
                            AdapterCompleted.notifyDataSetChanged();
                            AdapterNotCompleted.notifyDataSetChanged();
                            initializeChart();
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("Gagal",e.toString());
                Toast.makeText(ActivityDetailSprint.this, "An Error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    void hideRV(){
        if (notCompletedList.size()==0){
            rvNotCompleted.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.textView15);
            textView.setVisibility(View.GONE);
        }
        if (completedList.size()==0){
            rvCompleted.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.textView14);
            textView.setVisibility(View.GONE);
        }
    }

    public String formatDate(Date rawDate){
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
}
