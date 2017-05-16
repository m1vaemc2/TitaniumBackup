package com.example.android.another_titanium;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView v = (ListView) findViewById(R.id.searchResults);
        ArrayList<Backup_Item> apps = new ArrayList<>();

        apps.add(new Backup_Item("chrome.png", "Google Chrome", "16MB", ""));
        apps.add(new Backup_Item("whatsapp.png", "WhatsApp Messenger", "500MB", ""));
        apps.add(new Backup_Item("uber.jpg", "Uber", "6MB", ""));
        apps.add(new Backup_Item("youtube.png", "Youtube", "1MB", ""));

        final Backup_Adapter adapter = new Backup_Adapter(getBaseContext(), apps);
        v.setAdapter(adapter);

        SearchView input = (SearchView) findViewById(R.id.searchInput);
        input.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Submit: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("Change: " + newText);
                Backup_Adapter newAdapter = adapter.searchAdapter(newText);
                if (newAdapter != null)
                    v.setAdapter(newAdapter);
                else
                    v.setAdapter(adapter);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        System.out.println("MEMORYX1");
        if (id == R.id.btn_appbar_search) {
            System.out.println("MEMORYX2");
            Intent i = new Intent(this, SearchActivity.class);
            startActivity(i);
            return true;
        }
        System.out.println("MEMORYX4");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    public void backup(View view)
    {
        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(this, R.style.AppTheme_Checkbox));
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait, we're making progress...");
        progressDialog.setTitle("Backing up...");
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
    public void restore(View view)
    {
        progressDialog = new ProgressDialog(new android.support.v7.view.ContextThemeWrapper(this, R.style.AppTheme_Checkbox));
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait, we're making progress...");
        progressDialog.setTitle("Restoring..");
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
}
