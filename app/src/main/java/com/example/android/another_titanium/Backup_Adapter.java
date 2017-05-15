package com.example.android.another_titanium;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 2017/04/22.
 */

public class Backup_Adapter extends BaseAdapter
{
    private Context context;
    public ArrayList<Backup_Item> apps;

    public Backup_Adapter(Context c, ArrayList<Backup_Item> inApps)
    {
        context = c;
        apps = inApps;
    }

    public void setNewData(ArrayList<Backup_Item> new_items) {
        apps.clear();
        apps.addAll(new_items);
        this.notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
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
        try {
            Bitmap bmp = BitmapFactory.decodeStream(context.getAssets().open(b.image));
            icon.setImageBitmap(bmp);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

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
        return (getCount()==0);
    }

    public Backup_Adapter searchAdapter(String searched) {
        if (searched == "" || searched == null) {
            System.out.println("No query");
            return null;
        }
        //System.out.println(searched);

        ArrayList<Backup_Item> results = new ArrayList<>();

        for (int x = 0; x < getCount(); ++x) {
            Backup_Item temp = getItem(x);

            if (temp.appName.contains(searched)) {
                //System.out.println(temp.appName);
                results.add(temp);
            }
        }

        if (results.size() == 0) {
            return null;
        }

        return new Backup_Adapter(context, results);
    }
}
