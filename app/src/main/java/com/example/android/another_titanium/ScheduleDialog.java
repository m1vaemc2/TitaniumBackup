package com.example.android.another_titanium;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by keegan on 2017/04/23.
 */

public class ScheduleDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogeTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_schedule, null);
        builder.setView(v)
                .setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        System.out.println("Yay x2");
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ScheduleDialog.this.getDialog().cancel();
                }
        });

        Spinner s1 = (Spinner)v.findViewById(R.id.TypeSpinner);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(getContext(),
                R.array.type, android.R.layout.simple_spinner_item);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(a1);

        Spinner s2 = (Spinner)v.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> a2 = ArrayAdapter.createFromResource(getContext(),
                R.array.PowerStates, android.R.layout.simple_spinner_item);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(a2);

        Spinner s3 = (Spinner)v.findViewById(R.id.times);
        ArrayAdapter<CharSequence> a3 = ArrayAdapter.createFromResource(getContext(),
                R.array.Time, android.R.layout.simple_spinner_item);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(a3);


        return builder.create();
    }
}
