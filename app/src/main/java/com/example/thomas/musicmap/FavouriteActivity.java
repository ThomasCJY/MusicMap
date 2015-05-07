package com.example.thomas.musicmap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FavouriteActivity extends ActionBarActivity {
    private final String LOG_TAG = FavouriteActivity.class.getSimpleName();
    private ArraySwipeAdapter<String> swipePerformAdapter;
    private SessionManager session;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        pDialog.setMessage("Getting Information...");
        showDialog();

        session = new SessionManager(getApplicationContext());

        final String uuid = session.checkUID();

        // POST unique_id to the server
        // Parse JSON data and show it on the screen
        StringRequest strReq = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        ListView listView = (ListView) findViewById(R.id.listView_performance);
                        try {

                            JSONObject jObj = new JSONObject(response);
                            final String[] strings = Utility.getPerformanceDataFromJson(jObj);
                            final ArrayList<String> astring = new ArrayList<>(Arrays.asList(strings));

                            swipePerformAdapter = new ArraySwipeAdapter<String>(
                                    FavouriteActivity.this,
                                    R.layout.swipe_item,
                                    astring
                            ) {

                                @Override
                                public int getSwipeLayoutResourceId(int i) {
                                    return R.id.swipe_sample;
                                }

                                @Override
                                public View getView(final int position, View convertView, ViewGroup parent) {
                                    final View v = LayoutInflater.from(FavouriteActivity.this).inflate(R.layout.swipe_item,
                                            null, false);

                                    ImageView leftIcon = (ImageView) v.findViewById(R.id.list_item_icon);
                                    TextView upperLabel = (TextView) v.findViewById(R.id.list_item_musician);
                                    TextView lowerLeftLabel = (TextView) v.findViewById(R.id.list_item_time);
                                    TextView lowerRightLabel = (TextView) v.findViewById(R.id.list_item_place);

                                    String s = astring.get(position);
                                    String[] temp = s.split("&-&");

                                    upperLabel.setText(temp[0]);
                                    lowerLeftLabel.setText(temp[1]);
                                    lowerRightLabel.setText(temp[2]);

                                    final String pid = temp[7];

                                    ImageView imageView = (ImageView) v.findViewById(R.id.image);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe_sample);
                                            swipeLayout.close();
                                            deleteItem(uuid, pid);

                                            astring.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    });

                                    return v;
                                }

                            };

                            listView.setAdapter(swipePerformAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String content = strings[i];
                                    Intent intent = new Intent(FavouriteActivity.this, PerformanceDetail.class);
                                    intent.putExtra(Intent.EXTRA_TEXT, content);
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Get Favourite information Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "favourite");
                params.put("unique_id", uuid);

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(strReq);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void deleteItem(final String uniqueID, final String pid) {
        StringRequest strReq = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_LIKE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (error){
                                Toast.makeText(getApplicationContext(),
                                        jObj.getString("error_msg"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),
                                volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "delete");
                params.put("uuid", uniqueID);
                params.put("p_id",pid);

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(strReq);
    }


}
