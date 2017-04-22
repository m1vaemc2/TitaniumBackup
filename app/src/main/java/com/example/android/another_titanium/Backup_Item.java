package com.example.android.another_titanium;

/**
 * Created by user on 2017/04/22.
 */

public class Backup_Item
{
    public String image;
    public String appName;
    public String appSize;
    public boolean selected;
    public String backupDate;

    public Backup_Item(String im, String name, String size, String date)                                                            {
        this.image = im;
        this.appName = name;
        this.appSize = size;
        this.backupDate = date;
    }
}
