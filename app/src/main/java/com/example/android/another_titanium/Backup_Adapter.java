package com.example.android.another_titanium;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017/04/22.
 */

public class Backup_Adapter implements ListAdapter
{
    private Context context;
    public ArrayList<Backup_Item> apps;

    public Backup_Adapter(Context c, ArrayList<Backup_Item> inApps)
    {
        context = c;
        apps = inApps;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Backup_Item getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.backup_item, parent, false);
        Backup_Item b = getItem(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon1);
        TextView name = (TextView) convertView.findViewById(R.id.txtAppName);
        TextView size = (TextView) convertView.findViewById(R.id.txtAppSize);
        TextView date = (TextView) convertView.findViewById(R.id.txtAppDate);
        CheckBox selected = (CheckBox) convertView.findViewById(R.id.cbxSelected);

        name.setText(b.appName);
        size.setText(b.appSize);
        date.setText(b.backupDate);
        selected.setChecked(b.selected);
        icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_menu_camera));

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
