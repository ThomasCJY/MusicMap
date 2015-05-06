package com.example.thomas.musicmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thomas on 15-5-3.
 */
public class DetailFragment extends Fragment {
    //content is used to convey information
    private String content;
    private String settingLocation;

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_performance_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            ArrayList<String> aList = new ArrayList<>(Arrays.asList(content));

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putStringArrayListExtra("info", aList);
            intent.putExtra("LString",settingLocation);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            content = intent.getStringExtra(Intent.EXTRA_TEXT);
            String[] temp = content.split("&-&");
            settingLocation = temp[6];

            TextView musician =  (TextView) rootView.findViewById(R.id.detail_musician);
            TextView time = (TextView) rootView.findViewById(R.id.detail_time);
            TextView place = (TextView) rootView.findViewById(R.id.detail_place);
            TextView description = (TextView) rootView.findViewById(R.id.detail_description);

            musician.setText(temp[0]);
            time.setText(temp[1]);
            place.setText(temp[2]);
            description.setText(temp[5]);

        }

        return rootView;
    }


}
