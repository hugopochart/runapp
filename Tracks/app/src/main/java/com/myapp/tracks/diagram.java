package com.myapp.tracks;

import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class diagram extends AppCompatActivity {

    public static int time0 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Double.parseDouble(LocationService.returndistance()) < 1)
        {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            PixelGridView pixelGrid = new PixelGridView(this);
            pixelGrid.setNumColumns(0 );
            pixelGrid.setNumRows(0);
            TextView textView = new TextView(this);
            textView.setText("Total distance : "+(LocationService.returndistance())+" kilometers");
            textView.setTypeface(null, Typeface.BOLD);
            ll.addView(pixelGrid);
            ll.addView(textView);
            setContentView(ll);


        }
        else{
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ListView listView = new ListView(this);
        listView.setDividerHeight(0);
        TextView textView = new TextView(this);
        textView.setText("Total distance : "+(LocationService.returndistance())+" kilometers");
        textView.setTypeface(null, Typeface.BOLD);

        final List<String> contacts1 = LocationService.returnlisttime();

        final ListAdapter adapter = new TweetAdapter(diagram.this, contacts1);
        listView.setAdapter(adapter);

        //Only to have the number of kilometers//////////

        PixelGridView pixelGrid = new PixelGridView(this);
        CharSequence didi = MainActivity.dist.getText();
        final StringBuilder sb = new StringBuilder(didi.length());
        sb.append(didi);
        String chartostring = sb.toString();
        double number = Double.parseDouble(chartostring);
        int coco = (int)number;



        //////////////////////////////////////////

         time0 = Integer.parseInt(LocationService.storetime.get(0));

        for(int i = 0; i+1 < LocationService.storetime.size();i++)
        {
            if (time0 < Integer.parseInt(LocationService.storetime.get(i+1)))
            {
                time0 = Integer.parseInt(LocationService.storetime.get(i+1));
            }
        }

        pixelGrid.setNumColumns(coco );
        pixelGrid.setNumRows(time0 );
        List<String> viewtest = LocationService.returnlistaveragespeed();
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB   "+ time0);

        pixelGrid.storespeed1 = LocationService.returnlisttime();
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+pixelGrid.storespeed1);


        ll.addView(pixelGrid);
        ll.addView(textView);
        ll.addView(listView);

        setContentView(ll);
        //setContentView(pixelGrid);
    }

    }

}
