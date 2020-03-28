package com.example.app.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.fragment.FragmentHistory;
import com.example.app.utils.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.AlertAddUser;
import com.example.app.fragment.FragmentBacklog;
import com.example.app.fragment.FragmentSprintReport;
import com.example.app.fragment.FragmentEpic;
import com.example.app.fragment.FragmentSetting;
import com.example.app.fragment.FragmentSprint;
import com.example.app.model.Backlog;
import com.example.app.model.Epic;
import com.example.app.model.Project;
import com.example.app.model.Sprint;
import com.example.app.model.User;
import com.example.app.utils.ListenerData;
import com.example.app.utils.ListenerGraphql;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener, ListenerGraphql, AlertAddUser.AlertListener , ListenerData {
    private Fragment fragment;
    private FloatingActionButton fab,fab2,fab3;
    FloatingActionsMenu fam;
    Intent intent;
    private MainViewModel model;
    String PID;
    User user;
    Project project;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    final int REQ_ADD_BACKLOG = 1;
    final int REQ_EDIT_BACKLOG = 2;
    final int REQ_ADD_SPRINT= 3;
    final int REQ_ADD_EPIC= 4;
    final int REQ_START_SPRINT= 5;
    final int REQ_EDIT_SPRINT_ACTIVE= 6;
    final int REQ_EDIT_SPRINT_NOT_ACTIVE= 8;
    final int REQ_EPIC = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = findViewById(R.id.fab2);
        //Sprint
        fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(this);
        fab.setVisibility(View.GONE);
        //Backlog
        fab2 = (FloatingActionButton) findViewById(R.id.fab4);
        fab2.setOnClickListener(this);
        fab2.setVisibility(View.GONE);
        //Epic
        fab3 = (FloatingActionButton) findViewById(R.id.fab5);
        fab3.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txtName);
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.textView);




        if (savedInstanceState==null){
            intent = getIntent();
            user = intent.getParcelableExtra("User");
            PID = intent.getStringExtra("PID");
            project = intent.getParcelableExtra("project");

            getSupportActionBar().setTitle(intent.getStringExtra("PName"));
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = FragmentEpic.newInstance(intent.getStringExtra("PID"),"");
            transaction.add(R.id.fragmentContainer,fragment);
            transaction.commit();

            model = ViewModelProviders.of(this).get(MainViewModel.class);
            model.instantiateListener((ListenerGraphql) this);
            model.instantiateListener((ListenerData) this);
            model.fetchMain(PID);
            model.setUser(user);
        }else {
            user = savedInstanceState.getParcelable("User");
            PID = savedInstanceState.getString("PID");
            project =savedInstanceState.getParcelable("Project");
        }
        navUsername.setText(user.getName());
        navUserEmail.setText(user.getEmail());
//        setFab();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.drawer_layout);
        setFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_epic) {
            fragment = FragmentEpic.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
            fab3.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_history) {
            fragment = FragmentHistory.newInstance(project);
            fam.collapse();
            fam.setVisibility(View.GONE);
        } else if (id == R.id.nav_setting) {
            fragment = FragmentSetting.newInstance(project,PID);
            fam.collapse();
            fam.setVisibility(View.GONE);
        }
        else if (id == R.id.nav_sprint_report){
            fragment = FragmentSprintReport.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        }else if (id == R.id.nav_demo){
            fragment = FragmentSprint.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        }else if (id == R.id.nav_demo2){
            fragment = FragmentBacklog.newInstance(PID,"");
            fam.collapse();
            fam.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.GONE);
        }
        loadFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_logout){
            Intent intent = new Intent(this,ActivityLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if (id ==R.id.nav_logout_project){
            finish();
        }
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void setFab(){
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentBacklog){
//            fab.show();
            fam.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.GONE);
        }else if (fragmentInFrame instanceof FragmentEpic){
            fam.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
            fab3.setVisibility(View.VISIBLE);
        }/*else if (fragmentInFrame instanceof FragmentSprint){
            fam.setVisibility(View.GONE);
        }*/
        else {
//            fab.hide();
            fam.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        if (view==fab){
            /*Intent intent = new Intent(this, ActivityAddSprint.class);
            intent.putExtra("req code",REQ_ADD_SPRINT);
            intent.putExtra("PID",PID);
            intent.putExtra("SCount",model.getSprintCount().getValue());
            intent.putExtra("User",model.getUser());
            startActivityForResult(intent,REQ_ADD_SPRINT);*/
        }else if(view==fab2){
            Intent intent = new Intent(this, ActivityAddBacklog.class);
            ArrayList<String> spinnerArray = new ArrayList<>();
            ArrayList<String> idEpic = new ArrayList<>();
            for (int i=0;i<model.getListEpic().getValue().size();i++){
                spinnerArray.add(model.getListEpic().getValue().get(i).getName());
                idEpic.add(model.getListEpic().getValue().get(i).getId());
            }
            intent.putStringArrayListExtra("spinner",spinnerArray);
            intent.putStringArrayListExtra("epicID",idEpic);
            ArrayList<String> spinnerArray2 = new ArrayList<>();
            ArrayList<String> emailUser = new ArrayList<>();
            for (int i=0;i<model.getListUser().getValue().size();i++){
                spinnerArray2.add(model.getListUser().getValue().get(i).getName());
                emailUser.add(model.getListUser().getValue().get(i).getEmail());
            }
            intent.putStringArrayListExtra("spinner2",spinnerArray2);
            intent.putStringArrayListExtra("emailUser",emailUser);
            intent.putExtra("req code", REQ_ADD_BACKLOG);
            intent.putExtra("PID",PID);
            intent.putExtra("User",model.getUser());
            intent.putExtra("blID",model.getLargestBacklogID());
//            Log.d("last bl id",model.getListAllBacklog().getValue().get(model.getListAllBacklog().getValue().size()-1).getId());
            Log.d("Email",model.getUser().getEmail());
            startActivityForResult(intent, REQ_ADD_BACKLOG);
        }else if(view==fab3){
            Intent intent = new Intent(this, ActivityAddEpic.class);
            intent.putExtra("req code",REQ_ADD_EPIC);
            intent.putExtra("PID",PID);
            intent.putExtra("epID",model.getLargestEpicID());
            intent.putExtra("User",model.getUser());
            startActivityForResult(intent,REQ_ADD_EPIC);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ADD_BACKLOG) {
            if (resultCode == RESULT_OK) {
                Backlog newBacklog = data.getParcelableExtra("result");
                Log.d("isi",newBacklog.getIdProject());
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentBacklog){
                    ((FragmentBacklog) fragmentInFrame).AddDataSet(newBacklog);
                }
            }
        }
        if (requestCode == REQ_EDIT_BACKLOG) {
            if (resultCode == RESULT_OK) {
                Backlog editBacklog = data.getParcelableExtra("result");
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                if (fragmentInFrame instanceof FragmentBacklog){
//                    ((FragmentBacklog) fragmentInFrame).EditDataSet(data.getIntExtra("position",0),editBacklog,data.getStringExtra("adapter"));
//                }
                if (fragmentInFrame instanceof FragmentBacklog){
                    ((FragmentBacklog) fragmentInFrame).EditDataSet(
                            data.getIntExtra("group pos",0),
                            data.getIntExtra("child pos",0),
                            editBacklog,
                            data.getIntExtra("index",0),
                            data.getIntExtra("index2",-1));
                }
            }
            else if (resultCode == 2){
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                Backlog delBacklog = data.getParcelableExtra("backlog");
                if (fragmentInFrame instanceof FragmentBacklog){
                    ((FragmentBacklog) fragmentInFrame).RemoveDataSet(data.getIntExtra("group pos",0),data.getIntExtra("child pos",0),delBacklog);
                }
                Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQ_ADD_SPRINT) {
            if (resultCode == RESULT_OK) {
                Sprint newSprint = data.getParcelableExtra("sprint");
//                model.getSprintCount().setValue(model.getSprintCount().getValue()+1);
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                if (fragmentInFrame instanceof FragmentBacklog){
//                    ((FragmentBacklog) fragmentInFrame).addSprint(newSprint);
//                }
            }
        }
        if (requestCode == REQ_ADD_EPIC) {
            if (resultCode == RESULT_OK) {
                Epic newEpic = data.getParcelableExtra("result");
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentEpic){
                    ((FragmentEpic) fragmentInFrame).AddDataSet(newEpic);
                }
            }
        }
        if (requestCode == REQ_START_SPRINT || requestCode == REQ_EDIT_SPRINT_ACTIVE|| requestCode == REQ_EDIT_SPRINT_NOT_ACTIVE) {
            if (resultCode == RESULT_OK) {
                Sprint newSprint = data.getParcelableExtra("Sprint");
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentBacklog) {
                    ((FragmentBacklog) fragmentInFrame).startSprint(newSprint);
                }
            }
        }
        if (requestCode == REQ_EPIC) {
            if (resultCode == RESULT_OK) {
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentEpic) {
                    Epic epic = data.getParcelableExtra("epic");
                    ((FragmentEpic) fragmentInFrame).editEpic(epic);

                }
            }else if(resultCode==RESULT_FIRST_USER){
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentEpic) {
                    Epic epic = data.getParcelableExtra("epic");
                    ((FragmentEpic) fragmentInFrame).deleteEpic(epic);
                }
            }
        }
        Log.d("request code",Integer.toString(requestCode));
    }

    @Override
    public void startProgressDialog() {

        ActivityMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(ActivityMain.this);
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });
        Log.d("start","true");
    }

    @Override
    public void endProgressDialog() {
        progressDialog.dismiss();
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentEpic){
            ActivityMain.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((FragmentEpic) fragmentInFrame).notifyAdapter();
                }
            });
        }
        Log.d("end","true");
    }

    @Override
    public void startAlert(String error,String code) {
        ActivityMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initializeAlertDialog(error,code);
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
    }

    @Override
    public void setToast(String message) {
        ActivityMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityMain.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initializeAlertDialog(String error,String code){
        builder = new AlertDialog.Builder(this);
        if (code.equalsIgnoreCase("no email")){
            builder.setMessage("Error : "+ error);
            builder.setCancelable(false);
            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }else {
            builder.setMessage("An error has occurred"+"\nRetry ?");
            builder.setCancelable(false);

            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (code=="fetch"){
                                model.reset();
                                model.fetchMain(PID);
                            }

                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }
    }

    @Override
    public void addUser(String email) {
        boolean same = false ;
        for (int i=0;i<model.getListUser().getValue().size();i++){
            if (model.getListUser().getValue().get(i).getEmail().equalsIgnoreCase(email)){
                same = true;
                break;
            }
        }
        if (!same){
            model.addUser(email,PID);
        }else{
            Toast.makeText(this, "User already exist !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("User",user);
        outState.putParcelable("Project",project);
        outState.putString("PID",PID);
    }

    @Override
    public void onDeleteFinished(Epic epic) {
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentEpic){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((FragmentEpic) fragmentInFrame).onDeleteCompleted(epic);
                }
            });
        }
    }

    @Override
    public void onCreateFinished(Epic epic) {
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentEpic){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((FragmentEpic) fragmentInFrame).onCreateCompleted(epic);
                }
            });
        }
    }

    @Override
    public void onCreateFinished(Backlog backlog) {
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentBacklog){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((FragmentBacklog) fragmentInFrame).onAddCompleted(backlog);
                }
            });
        }
    }

    @Override
    public void onDeleteFinished(int groupPos,int childPos,Backlog backlog) {
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentBacklog){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((FragmentBacklog) fragmentInFrame).onDeleteCompleted(groupPos,childPos,backlog);
                }
            });
        }
    }
}
