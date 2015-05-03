package com.example.thomas.musicmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thomas on 15-5-3.
 */
public class PerformanceAdapter extends ArrayAdapter {
    private ArrayList<String> list;
    private Context context;

    public PerformanceAdapter(Context context, ArrayList<String> arraylist) {
        super(context, R.layout.list_item_forecast, arraylist);
        this.list = arraylist;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast,
                null, false);
        ImageView leftIcon = (ImageView) view.findViewById(R.id.list_item_icon);
        TextView upperLabel = (TextView) view.findViewById(R.id.list_item_musician);
        TextView lowerLeftLabel = (TextView) view.findViewById(R.id.list_item_time);
        TextView lowerRightLabel = (TextView) view.findViewById(R.id.list_item_place);

        String s = list.get(position);
        String[] temp = s.split("&-&");

        upperLabel.setText(temp[0]);
        lowerLeftLabel.setText(temp[1]);
        lowerRightLabel.setText(temp[2]);

        return view;
    }

}
