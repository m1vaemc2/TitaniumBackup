package com.example.android.another_titanium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView v = (ListView) findViewById(R.id.searchResults);
        ArrayList<Backup_Item> apps = new ArrayList<>();
        apps.add(new Backup_Item("@drawable/chrome.png", "Google Chrome", "16MB", ""));
        apps.add(new Backup_Item("@drawable/chrome.png", "Google Docs", "16MB", ""));
        apps.add(new Backup_Item("", "WhatsApp Messenger", "500MB", ""));
        apps.add(new Backup_Item("", "Uber", "6MB", ""));
        apps.add(new Backup_Item("", "Youtube", "1MB", ""));

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
                v.setAdapter(newAdapter);
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

}
