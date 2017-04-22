package com.example.android.another_titanium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println(toolbar.getTitle());

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Home");
        spec.setContent(R.id.Home);
        spec.setIndicator("Home");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Installed");
        spec.setContent(R.id.Installed);
        spec.setIndicator("Installed");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Archive");
        spec.setContent(R.id.Archive);
        spec.setIndicator("Archive");
        host.addTab(spec);

        // Add Backed up Apps
        ListView v = (ListView) findViewById(R.id.lstApps);
        ArrayList<Backup_Item> apps = new ArrayList<>();
        apps.add(new Backup_Item("", "Google Chrome", "16MB", ""));
        apps.add(new Backup_Item("", "WhatsApp Messenger", "500MB", ""));
        apps.add(new Backup_Item("", "Uber", "6MB", ""));
        apps.add(new Backup_Item("", "Youtube", "1MB", ""));

        Backup_Adapter adapter = new Backup_Adapter(getBaseContext(), apps);
        v.setAdapter(adapter);

        // Archive
        v = (ListView) findViewById(R.id.lstArchive);
        ArrayList<Backup_Item> archivedApps = new ArrayList<>();
        archivedApps.add(new Backup_Item("", "Google Chrome", "16MB", "01/01/2017"));
        archivedApps.add(new Backup_Item("", "Uberrrr", "66MB", "08/09/2016"));
        archivedApps.add(new Backup_Item("", "WhatsApp Messenger", "500MB", "28/03/2017"));

        adapter = new Backup_Adapter(getBaseContext(), archivedApps);
        v.setAdapter(adapter);

        ((Button) findViewById(R.id.btnBackup)).setEnabled(false);

        registerForContextMenu(findViewById(R.id.btnContacts));
        registerForContextMenu(findViewById(R.id.btnMusic));
        registerForContextMenu(findViewById(R.id.btnCallLog));
        registerForContextMenu(findViewById(R.id.btnGallery));
        System.out.println("Memory");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_thingy, menu);
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
        System.out.print(id +"");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_schedules) {
            Intent i = new Intent(this, Schedule.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
