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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by thomas on 15-4-27.
 */
public class PerformanceFragment extends Fragment {
    private final String LOG_TAG = PerformanceFragment.class.getSimpleName();
    private String[] strings; //Stores All the performance information in customized String format
    private String settingLocation; //Stores location name in setting preference
    private PerformanceAdapter performanceAdapter;

    /**
     * A placeholder fragment containing a simple view.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    public PerformanceFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_performance, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map) {
            //Get all the string from strings
            //put them into the intent
            ArrayList<String> aList = new ArrayList<>(Arrays.asList(strings));

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putStringArrayListExtra("info", aList);
            intent.putExtra("LString", settingLocation);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingActivity.class));
            return true;
        }

        if (id == R.id.action_profile){
            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_performance);

        performanceAdapter = new PerformanceAdapter(
                getActivity(),
                new ArrayList<String>()
        );
        listView.setAdapter(performanceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String content = strings[i];
                Intent intent = new Intent(getActivity(), PerformanceDetail.class);
                intent.putExtra(Intent.EXTRA_TEXT, content);
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


    private void updatePerformance() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        settingLocation = location;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = DateDialogPreference.getDateFor(prefs, "dob").getTime();
        String date = sdf.format(dateValue);

        String duration = prefs.getString(getString(R.string.pref_duration_key),
                getString(R.string.pref_duration_default));

        //Build URL
        String url = BuildURL(location, date, duration);

        //Create JSONobjectResquest
        RequestJSON(url);

    }

    private String BuildURL(String location, String date, String duration) {
        String url;

        // Construct the URL for the performance query
        // Possible parameters are avaiable at Settings menu, at
        // http://localhost/data
        final String FORECAST_BASE_URL =
                "http://10.0.2.2/androidProject/data.php?";
        final String LOCATION_PARAM = "location";
        final String DATE_PARAM = "date";
        final String DUR_PARAM = "duration";

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LOCATION_PARAM, location)
                .appendQueryParameter(DATE_PARAM, date)
                .appendQueryParameter(DUR_PARAM, duration)
                .build();

        url = builtUri.toString();
        Log.v(LOG_TAG, "Built URI " + builtUri.toString());
        return url;
    }

    private void RequestJSON(String url) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            strings = Utility.getPerformanceDataFromJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (strings != null) {
                            performanceAdapter.clear();
                            for (String s : strings) {
                                performanceAdapter.add(s);
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error in RequestJSON");
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }



}
