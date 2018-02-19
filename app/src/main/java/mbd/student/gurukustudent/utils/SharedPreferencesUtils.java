package mbd.student.gurukustudent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Naufal on 10/02/2018.
 */

public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;
    private static Gson gson = new Gson();

    public SharedPreferencesUtils(Context context, String preferencesName) {
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    public void storeData(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public void storeData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public boolean checkIfDataExists(String key) {
        return sharedPreferences.contains(key);
    }

    public <T> T getObjectData(String key, Class<T> object) {
        String json = sharedPreferences.getString(key, null);
        return gson.fromJson(json, object);
    }

    public void clearAllData() {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
