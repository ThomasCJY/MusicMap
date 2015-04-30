package com.example.thomas.musicmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thomas on 15-4-27.
 */
public class performanceFragment extends Fragment {
    private final String LOG_TAG = performanceFragment.class.getSimpleName();
    private String[] strings;

    private ArrayAdapter<String> arrayAdapter;
    /**
     * A placeholder fragment containing a simple view.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    public performanceFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_performance, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_map) {
            //Get all the string from strings
            //put them into the intent
            ArrayList<String> aList = new ArrayList<>(Arrays.asList(strings));

            Intent intent = new Intent(getActivity(), mapActivity.class);
            intent.putStringArrayListExtra("info", aList);
            startActivity(intent);
            return true;
        }


        if(id == R.id.action_refresh){
            updatePerformance();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_performance);
        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_performance_textView,
                new ArrayList<String>()
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

    @Override
    public void onStart() {
        super.onStart();
        updatePerformance();
    }


    private void updatePerformance(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        String date = prefs.getString(getString(R.string.pref_date_key),
                getString(R.string.pref_date_default));
//        Log.e(LOG_TAG, "TEST GETSTRING: " + location + "/"+date);

        //Build Url and Connect the localhost to
        //query the required information
        try {
            // Construct the URL for the performance query
            // Possible parameters are avaiable at Settings menu, at
            // http://localhost/data
            final String FORECAST_BASE_URL =
                    "http://localhost/data?";
            final String LOCATION_PARAM = "location";
            final String DATE_PARAM = "date";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(LOCATION_PARAM, location)
                    .appendQueryParameter(DATE_PARAM, date)
                    .build();

            URL url = new URL(builtUri.toString());

//            Log.e(LOG_TAG, url.toString());

            //Connect localhost and acquire the information
            //in JSON format


            //decode the JSON info into String[]


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

        } finally {

        }

        //It's the temp string, will be replaced by
        //the JSON decoded ones
        strings = new String[]{
                "Coldplay - 4/2 - 青果 - 32.059076 - 118.770744",
                "逃跑计划 - 4/7 - MAO LIVE - 32.048338 - 118.788389",
                "苏阳 - 4/8 - 61LiveHouse - 32.054876 - 118.772693",
                "国家交响乐团 - 4/15 - 南京大剧院 - 32.033714 - 118.775600",
        };

        //This part may be moved into OnPostExecute later
        if (strings != null) {
            arrayAdapter.clear();

            for(String dayForecastStr : strings) {
                arrayAdapter.add(dayForecastStr);
            }
        }


    }


}
