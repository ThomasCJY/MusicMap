package com.example.thomas.musicmap;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thomas on 15-5-5.
 */
public class Utility {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Encode password using SHA function
     */
    public static String shaPassword(String pwd){
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

    public static boolean checkEmailFormat(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
