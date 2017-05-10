package com.example.android.another_titanium;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.os.Handler;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

import static android.R.attr.handle;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabHost tabHost;
    private Menu mMenu;
    String buttonClicked = "Nothing";
    Backup_Adapter adapter;
    ArrayList<Backup_Item> userApps = new ArrayList<>();
    ArrayList<Backup_Item> systemApps = new ArrayList<>();
    ProgressDialog progressDialog;

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


        systemApps.add(new Backup_Item("gallery.png", "Gallery", "16MB", ""));
        systemApps.add(new Backup_Item("messages.png", "Messages", "500MB", ""));
        systemApps.add(new Backup_Item("email.png", "Emails", "6MB", ""));
        systemApps.add(new Backup_Item("wifi.png", "WiFi Information", "1MB", ""));

        adapter = new Backup_Adapter(getBaseContext(), new ArrayList<>(userApps));
        v.setAdapter(adapter);

        // Archive
        v = (ListView) findViewById(R.id.lstArchive);
        ArrayList<Backup_Item> archivedApps = new ArrayList<>();
        archivedApps.add(new Backup_Item("navup.png", "NavUP", "1MB", "01/01/2017"));
        archivedApps.add(new Backup_Item("facebook.png", "Facebook", "18MB", "08/09/2016"));
        archivedApps.add(new Backup_Item("instagram.png", "Instagram", "50MB", "28/03/2017"));
        archivedApps.add(new Backup_Item("email.png", "Emails", "14MB", "15/01/2017"));

        Backup_Adapter adapter2 = new Backup_Adapter(getBaseContext(), archivedApps);
        v.setAdapter(adapter2);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int i = host.getCurrentTab();
                if (i == 0) {
                    mMenu.findItem(R.id.btn_appbar_filter).setVisible(false);
                    mMenu.findItem(R.id.btn_appbar_search).setVisible(false);
                } else {
                    mMenu.findItem(R.id.btn_appbar_filter).setVisible(true);
                    mMenu.findItem(R.id.btn_appbar_search).setVisible(true);
                }
            }
        });

        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(-1);
        }

        // Dialogs for backing up etc
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
        View menuItemView = findViewById(R.id.btn_appbar_filter);
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
        if (id == R.id.btn_appbar_search) {
            return true;
        } else if (id == R.id.btn_appbar_filter) {
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

    public void backupInstalledApps(View view) {
        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(MainActivity.this, R.style.AppTheme_Checkbox));
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait, we're making progress...");
        progressDialog.setTitle("Backing Up");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(1);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog
                            .getMax()) {
                        Thread.sleep(50);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void restoreArchive(View view)
    {
        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(MainActivity.this, R.style.AppTheme_Checkbox));
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait, we're making progress...");
        progressDialog.setTitle("Restoring");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(1);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog
                            .getMax()) {
                        Thread.sleep(50);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(MainActivity.this, R.style.AppTheme_Checkbox));
                        progressDialog.setMax(100);
                        progressDialog.setMessage("Encrypted data being uploaded to Google Drive...");
                        progressDialog.setTitle("Sending to Cloud");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        final Handler handle = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                progressDialog.incrementProgressBy(1);
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (progressDialog.getProgress() <= progressDialog
                                            .getMax()) {
                                        Thread.sleep(50);
                                        handle.sendMessage(handle.obtainMessage());
                                        if (progressDialog.getProgress() == progressDialog
                                                .getMax()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(MainActivity.this, R.style.AppTheme_Checkbox));
                        progressDialog.setMax(100);
                        progressDialog.setMessage("Data being uploaded to Google Drive...");
                        progressDialog.setTitle("Sending to Cloud");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        final Handler handle = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                progressDialog.incrementProgressBy(1);
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (progressDialog.getProgress() <= progressDialog
                                            .getMax()) {
                                        Thread.sleep(50);
                                        handle.sendMessage(handle.obtainMessage());
                                        if (progressDialog.getProgress() == progressDialog
                                                .getMax()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showActionSheet(View view) {
        buttonClicked = view.getTag().toString();
        final BottomSheetLayout actionSheet = (BottomSheetLayout) findViewById(R.id.actionsheet);
        actionSheet.showWithSheetView(LayoutInflater.from(this).inflate(R.layout.layout_action_sheet, actionSheet, false));
    }

    public void hideActionSheet() {
        final BottomSheetLayout actionSheet = (BottomSheetLayout) findViewById(R.id.actionsheet);
        actionSheet.dismissSheet();
    }

    public void restoreClick(View view) {
        hideActionSheet();
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.actionsheet),
                buttonClicked + " successfully restored.", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mySnackbar.show();
    }

    public void backupClick(View view) {
        hideActionSheet();
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.actionsheet),
                buttonClicked + " successfully backed up.", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    public void sendToCloud(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder1.setTitle("Do you want to encrypt your " + buttonClicked + "?");
        builder1.setMessage("Encrypted backups will be more secure and can only be accessed by you.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.actionsheet),
                                buttonClicked + " successfully encrypted and sent to cloud.", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.actionsheet),
                                buttonClicked + " successfully sent to cloud.", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void clickListBackups(View view) {
        hideActionSheet();
    }
}
