package com.example.mobility.myplacespoc;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_closed);
        if(drawer != null){
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        if(navigationView != null){
            navigationView.setNavigationItemSelectedListener(this);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activitydetails,new MainActivityFragment(),"one");
        fragmentTransaction.addToBackStack("one");
        fragmentTransaction.commit();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.activity_main);
        switch (item.getItemId()){
            case R.id.nav_search:
                displayToast(getString(R.string.search_msg_txt));
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.favourites:
                displayToast(getString(R.string.favourites_msg_txt));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activitydetails,new RetriveAll());
                transaction.addToBackStack("one");
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                return false;
        }

    }

    private void displayToast(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }


}
