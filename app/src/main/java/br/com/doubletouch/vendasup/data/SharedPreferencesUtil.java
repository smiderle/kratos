package br.com.doubletouch.vendasup.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class SharedPreferencesUtil {

    private Context context;

    public static final String PREFERENCES = "KRATOS_PREFERENCES";
    public static final String PREFERENCES_LOGIN = "PREFERENCES_LOGIN";
    public static final String PREFERENCES_PASSWORD = "PREFERENCES_PASSWORD";
    public static final String PREFERENCES_COMPANY = "PREFERENCES_COMPANY";
    public static final String PREFERENCES_USER_ID = "PREFERENCES_USER_ID";

    public SharedPreferencesUtil(Context context) {
        this.context = context;
    }

    public  void addString(String key, String value){
        SharedPreferences.Editor preferences  = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE ).edit();
        preferences.putString(key, value);
        preferences.commit();
    }

    public String getString(String key){
        SharedPreferences preferences  = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
        return preferences.getString(key, null);

    }


    public  void addInteger(String key, Integer value){
        SharedPreferences.Editor preferences  = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE ).edit();
        preferences.putInt(key, value);
        preferences.commit();
    }


    public int getInteger(String key){
        SharedPreferences preferences  = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
        return preferences.getInt(key, 0);

    }
}
