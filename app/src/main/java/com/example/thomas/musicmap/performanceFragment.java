package com.example.thomas.musicmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by thomas on 15-4-27.
 */
public class performanceFragment extends Fragment {
    private final String LOG_TAG = performanceFragment.class.getSimpleName();
    private String[] strings;
    private String settingLocation;

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

    public performanceFragment() {
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

            Intent intent = new Intent(getActivity(), mapActivity.class);
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
                Intent intent = new Intent(getActivity(), performanceDetail.class);
                        intent.putExtra(Intent.EXTRA_TEXT, content);
                intent.putExtra("LString", settingLocation);
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


    public void updatePerformance() {
        FetchPerformance fetchPerformance = new FetchPerformance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String location = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        settingLocation = location;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = DateDialogPreference.getDateFor(prefs,"dob").getTime();
        String date = sdf.format(dateValue);

        String duration = prefs.getString(getString(R.string.pref_duration_key),
                getString(R.string.pref_duration_default));

        String[] params = new String[]{location, date, duration};
        fetchPerformance.execute(params);
    }


    public class FetchPerformance extends AsyncTask<String[], Void, String[]> {
        private final String LOG_TAG = FetchPerformance.class.getSimpleName();

        private String[] getPerformanceDataFromJson(String PerformanceJsonStr)
                throws JSONException {
            final String OWM_PERFORM = "performance";
            final String OWM_PLACE = "place";
            final String OWM_PNAME ="pname";
            final String OWM_TIME ="time";
            final String OWM_MUSICIAN="musician";
            final String OWM_LAT="lat";
            final String OWM_LNG="lng";
            final String OWM_DES="description";

            final String FLAG="&-&";

            JSONObject performJson = new JSONObject(PerformanceJsonStr);
            JSONArray performanceArray = performJson.getJSONArray(OWM_PERFORM);

            int len = performanceArray.length();
            String resultStrs[] = new String[len];

            for (int i = 0; i < len; i++){
                String pname;
                String time;
                String musician;
                String description;

                // Get the JSON object representing the day
                JSONObject singlePerformance = performanceArray.getJSONObject(i);
                time = singlePerformance.getString(OWM_TIME);
                musician = singlePerformance.getString(OWM_MUSICIAN);
                description = singlePerformance.getString(OWM_DES);

                JSONObject placeObject = singlePerformance.getJSONObject(OWM_PLACE);
                pname = placeObject.getString(OWM_PNAME);
                double lat = placeObject.getDouble(OWM_LAT);
                double lng = placeObject.getDouble(OWM_LNG);

                resultStrs[i] = musician + FLAG + time + FLAG +
                        pname + FLAG + lat +FLAG + lng + FLAG + description;
            }


            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;
        }

        @Override
        protected String[] doInBackground(String[]... params) {
            if (params.length == 0) {
                return null;
            }

            String location = params[0][0];
            String date = params[0][1];
            String duration = params[0][2];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            //Build Url and Connect the localhost to
            //query the required information
            try {
                // Construct the URL for the performance query
                // Possible parameters are avaiable at Settings menu, at
                // http://localhost/data
                final String FORECAST_BASE_URL =
                        "http://10.0.2.2/data.php?";
                final String LOCATION_PARAM = "location";
                final String DATE_PARAM = "date";
                final String DUR_PARAM = "duration";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(LOCATION_PARAM, location)
                        .appendQueryParameter(DATE_PARAM, date)
                        .appendQueryParameter(DUR_PARAM,duration)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                //Connect localhost and acquire the information
                //in JSON format
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Forecast string: " + forecastJsonStr);

                //decode the JSON info into String[]


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getPerformanceDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String[] result){
            strings = result;
            if (result != null){
                performanceAdapter.clear();
                for (String s : result){
                    performanceAdapter.add(s);
                }
            }
        }
    }


}
