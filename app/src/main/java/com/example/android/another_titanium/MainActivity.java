package com.example.android.another_titanium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabHost tabHost;
    private Menu mMenu;
    Backup_Adapter adapter;
    ArrayList<Backup_Item> userApps = new ArrayList<>();
    ArrayList<Backup_Item> systemApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println(toolbar.getTitle());

        final TabHost host = (TabHost)findViewById(R.id.tabHost);
        tabHost = host;
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
        userApps.add(new Backup_Item("chrome.png", "Google Chrome", "16MB", ""));
        userApps.add(new Backup_Item("whatsapp.png", "WhatsApp Messenger", "500MB", ""));
        userApps.add(new Backup_Item("uber.jpg", "Uber", "6MB", ""));
        userApps.add(new Backup_Item("youtube.png", "Youtube", "1MB", ""));


        systemApps.add(new Backup_Item("", "Gallery", "16MB", ""));
        systemApps.add(new Backup_Item("", "Messages", "500MB", ""));
        systemApps.add(new Backup_Item("", "Emails", "6MB", ""));
        systemApps.add(new Backup_Item("", "WiFi Information", "1MB", ""));

        adapter = new Backup_Adapter(getBaseContext(), userApps);
        v.setAdapter(adapter);

        // Archive
        v = (ListView) findViewById(R.id.lstArchive);
        ArrayList<Backup_Item> archivedApps = new ArrayList<>();
        archivedApps.add(new Backup_Item("c2.png", "Google Chrome", "16MB", "01/01/2017"));
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


        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int i = host.getCurrentTab();
                if (i == 0) {
                    mMenu.findItem(R.id.btn_filter).setVisible(false);
                    mMenu.findItem(R.id.action_settings).setVisible(false);
                } else {
                    mMenu.findItem(R.id.btn_filter).setVisible(true);
                    mMenu.findItem(R.id.action_settings).setVisible(true);
                }
            }
        });

        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(-1);
        }


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

    public void showPopup(){
        View menuItemView = findViewById(R.id.btn_filter);
        PopupMenu popup = new PopupMenu(this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.filter_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btn_userApps) {
                    adapter.setNewData(userApps);
                    return true;
                } else if (item.getItemId() == R.id.btn_systemApps) {
                    adapter.setNewData(systemApps);
                    return true;
                }
                return false;
            }
        });
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
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.btn_filter) {
            showPopup();
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        System.out.println("HELLO");
        return super.onContextItemSelected(item);
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
    public void showDialog(View view)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder1.setTitle("Do you want to encrypt this data?");
        builder1.setMessage("Encrypted backups will be more secure and can only be accessed by you.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
