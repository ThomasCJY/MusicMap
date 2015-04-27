package com.example.thomas.musicmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thomas on 15-4-27.
 */
public class performanceFragment extends Fragment {
    /**
     * A placeholder fragment containing a simple view.
     */

    ArrayAdapter<String> arrayAdapter;

    public performanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] strings = {
                "Coldplay - 4/2 - 愚公移山",
                "逃跑计划 - 4/7 - MAO LIVE",
                "苏阳 - 4/8 - 61LiveHouse",
                "国家交响乐团 - 4/15 - 国家大剧院",
                "Coldplay - 4/2 - 愚公移山",
                "逃跑计划 - 4/7 - MAO LIVE",
                "苏阳 - 4/8 - 61LiveHouse",
                "国家交响乐团 - 4/15 - 国家大剧院",
                "Coldplay - 4/2 - 愚公移山",
                "逃跑计划 - 4/7 - MAO LIVE",
                "苏阳 - 4/8 - 61LiveHouse",
                "国家交响乐团 - 4/15 - 国家大剧院"
        };

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(strings));

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_performance);
        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_performance_textView,
                arrayList
        );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String index = arrayAdapter.getItem(i);
                Intent intent = new Intent(getActivity(),performanceDetail.class)
                        .putExtra(Intent.EXTRA_TEXT, index);
                startActivity(intent);
            }
        });

        return rootView;
    }


}
