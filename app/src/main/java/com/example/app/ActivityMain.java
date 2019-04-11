package com.example.app;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{
    private Fragment fragment;
    private FloatingActionButton fab,fab2;
    FloatingActionsMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fam = findViewById(R.id.fab2);

        fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(this);

        fab2 = (FloatingActionButton) findViewById(R.id.fab4);
        fab2.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentBacklog2.newInstance("","");
        transaction.add(R.id.fragmentContainer,fragment);
        transaction.commit();
//        setFab();

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("PName"));
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
        // Inflate the menu; this adds items     to the action bar if it is present.
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_roadmap) {
            fragment = FragmentRoadmap.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        } else if (id == R.id.nav_backlog) {
            fragment = FragmentBacklog2.newInstance("","");
            fam.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_sprint) {
            fragment = FragmentSprint.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        } else if (id == R.id.nav_setting) {
            fragment = FragmentSetting.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        } else if (id == R.id.nav_burndown) {
            fragment = FragmentBurndown.newInstance("","");
            fam.collapse();
            fam.setVisibility(View.GONE);
        }
        loadFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void setFab(){
        Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragmentInFrame instanceof FragmentBacklog2){
//            fab.show();
            fam.setVisibility(View.VISIBLE);
        }
        else {
//            fab.hide();
            fam.setVisibility(View.GONE);
        }
    }

    final int REQ_ADD_PROJECT= 1;
    final int REQ_ADD_SPRINT= 3;
    @Override
    public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        if (view==fab){
            Intent intent = new Intent(this,ActivityAddSprint.class);
            intent.putExtra("req code",REQ_ADD_SPRINT);
            startActivityForResult(intent,REQ_ADD_SPRINT);
        }else if(view==fab2){
            Intent intent = new Intent(this,ActivityAddBacklog.class);
            intent.putExtra("req code",REQ_ADD_PROJECT);
            startActivityForResult(intent,REQ_ADD_PROJECT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ADD_PROJECT) {
            if (resultCode == RESULT_OK) {
                Backlog newBacklog = data.getParcelableExtra("result");
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentBacklog2){
                    ((FragmentBacklog2) fragmentInFrame).AddDataSet(newBacklog);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Backlog newBacklog = data.getParcelableExtra("result");
                Fragment fragmentInFrame = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragmentInFrame instanceof FragmentBacklog2){
                    ((FragmentBacklog2) fragmentInFrame).EditDataSet(data.getIntExtra("position",0),newBacklog);
                }
            }
        }
    }
}
