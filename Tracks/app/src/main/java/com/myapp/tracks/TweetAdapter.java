package com.myapp.tracks;

/**
 * Created by hugo on 25/04/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TweetAdapter extends ArrayAdapter<String> {



    public TweetAdapter(Context context, List<String> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_tweet,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.time = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.speed = (TextView) convertView.findViewById(R.id.text);
            viewHolder.km = (TextView) convertView.findViewById(R.id.Textview3);

            convertView.setTag(viewHolder);
        }


       // String value = (new ArrayList<String>(LocationService.storespeedandtime.values())).get(position);
       // String value1 = (new ArrayList<String>(LocationService.storespeedandtime.keySet())).get(position);

        //String test = getItem(position);

        viewHolder.time.setText("Time : "+LocationService.storetime.get(position)+" s");
        viewHolder.speed.setText("Average Speed : "+LocationService.storetaveragespeed.get(position)+" Km/h");
        viewHolder.km.setText(Integer.toString(position+1));

        return convertView;
    }

    public void EditItem (View view){}

    private class TweetViewHolder{
        public TextView time;
        public TextView speed;
        public TextView km;

    }
}