package com.example.thomas.musicmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thomas on 15-5-5.
 */
public class Utility {
    private final String LOG_TAG = Utility.class.getSimpleName();

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Encode password using SHA function
     */
    public static String shaPassword(String pwd) {
        String hashStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(pwd.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            hashStr = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashStr;
    }

    public static boolean checkEmailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String[] getPerformanceDataFromJson(JSONObject performJson)
            throws JSONException {
        final String OWM_PERFORM = "performance";
        final String OWM_PLACE = "place";
        final String OWM_PNAME = "pname";
        final String OWM_TIME = "time";
        final String OWM_MUSICIAN = "musician";
        final String OWM_LAT = "lat";
        final String OWM_LNG = "lng";
        final String OWM_DES = "description";
        final String OWM_CITY = "city";
        final String OWM_PID = "pid";

        final String FLAG = "&-&";

        JSONArray performanceArray = performJson.getJSONArray(OWM_PERFORM);

        int len = performanceArray.length();
        String resultStrs[] = new String[len];

        for (int i = 0; i < len; i++) {
            String pname;
            String time;
            String musician;
            String description;
            String city;
            String pid;

            // Get the JSON object representing the day
            JSONObject singlePerformance = performanceArray.getJSONObject(i);
            time = singlePerformance.getString(OWM_TIME);
            musician = singlePerformance.getString(OWM_MUSICIAN);
            description = singlePerformance.getString(OWM_DES);
            city = singlePerformance.getString(OWM_CITY);
            pid = singlePerformance.getString(OWM_PID);

            JSONObject placeObject = singlePerformance.getJSONObject(OWM_PLACE);
            pname = placeObject.getString(OWM_PNAME);
            double lat = placeObject.getDouble(OWM_LAT);
            double lng = placeObject.getDouble(OWM_LNG);

            resultStrs[i] = musician + FLAG + time + FLAG +
                    pname + FLAG + lat + FLAG + lng + FLAG + description +
                    FLAG + city + FLAG + pid;
        }

        return resultStrs;
    }

}
