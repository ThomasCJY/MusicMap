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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 15-5-3.
 * When user click on the list item, the app will create this
 * fragment.
 */
public class DetailFragment extends Fragment {
    //content is used to convey information

    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    private String content;
    private String settingLocation;  //Store the location from intent and convey it to the MapView
    private String pid;  //Store the performance id from intent and convey it to addToFavourite();

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
            intent.putExtra("LString", settingLocation);
            startActivity(intent);
            return true;

        }

        if (id == R.id.action_like) {
            addToFavourite();
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
            pid = temp[7];

            TextView musician = (TextView) rootView.findViewById(R.id.detail_musician);
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

    private void addToFavourite() {
        // Before the POST action, we should first get unique_id and
        // p_id(Pid is a global final param)
        SessionManager session = new SessionManager(getActivity());
        final String uuid = session.checkUID();

        // Construct String Request
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LIKE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
//                    Log.e(LOG_TAG,response);
                    boolean error = jObj.getBoolean("error");
//                    Log.e(LOG_TAG,String.valueOf(error));
                    if (!error){
                        Toast.makeText(getActivity(), "Add into your favourites!",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        String e = jObj.getString("error_msg");
                        Toast.makeText(getActivity(), e,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                params.put("tag", "like");
                params.put("uuid", uuid);
                params.put("p_id", pid);

                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(strReq);

    }


}
