package com.example.thomas.musicmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ArrayAdapter<String> arrayAdapter;

        public PlaceholderFragment() {
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
            ListView listView = (ListView)rootView.findViewById(R.id.listView_performance);
            arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_forecast,
                    R.id.list_item_performance_textView,
                    arrayList
            );
            listView.setAdapter(arrayAdapter);


            return rootView;
        }
    }
}
