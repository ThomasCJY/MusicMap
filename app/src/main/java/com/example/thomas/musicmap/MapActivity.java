package com.example.thomas.musicmap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String LOG_TAG = MapActivity.class.getSimpleName();

    private final LatLng nanjingLatLng = new LatLng(32.057961, 118.792683);
    private final LatLng shanghaiLatLng = new LatLng(31.223175, 121.489112);
    private final LatLng beijingLatLng = new LatLng(39.903601, 116.398146);
    private final LatLng guangzhouLatLng = new LatLng(23.136964, 113.248444);

    private final float ZOOMFLOATE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //Read the location info from the intent
        //add Marker of all the places
        String locationInfo = getIntent().getStringExtra("LString");
//        Log.e(LOG_TAG, locationInfo);

        switch (locationInfo){
            case "nanjing":
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(nanjingLatLng,ZOOMFLOATE));
                break;
            case "shanghai":
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(shanghaiLatLng,ZOOMFLOATE));
                break;
            case "beijing":
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(beijingLatLng,ZOOMFLOATE));
                break;
            case "guangzhou":
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(guangzhouLatLng,ZOOMFLOATE));
                break;
        }

        ArrayList<String> strings = getIntent().getStringArrayListExtra("info");

        for(String ast : strings){
            String[] parser = ast.split("&-&");
            double lat = Double.parseDouble(parser[3]);
            double lng = Double.parseDouble(parser[4]);
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(parser[0])
            .snippet(parser[1] + " - " + parser[2]));
        }


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_map, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
