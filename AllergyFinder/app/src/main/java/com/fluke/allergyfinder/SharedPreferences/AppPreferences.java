package com.fluke.allergyfinder.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

    // shared preferences keys
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_PHOTO = "KEY_PHOTO";

    public static final String KEY_DAILY_CALORIES = "KEY_DAILY_CALORIES";
    public static final String KEY_DAILY_PROTEIN = "KEY_DAILY_PROTEIN";
    public static final String KEY_DAILY_CARBOHYDRATE = "KEY_DAILY_CARBOHYDRATE";
    public static final String KEY_DAILY_FAT = "KEY_DAILY_FAT";
    public static final String KEY_DAILY_SUGAR = "KEY_DAILY_SUGAR";
    public static final String KEY_DAILY_SODIUM = "KEY_DAILY_SODIUM";

    public static final String KEY_TYPE = "KEY_TYPE";
    public static final String KEY_AGE = "KEY_AGE";
    public static final String KEY_CORN = "KEY_CORN";
    public static final String KEY_FLUCTOSE = "KEY_FLUCTOSE";
    public static final String KEY_GENDER = "KEY_GENDER";
    public static final String KEY_GLUTEN = "KEY_GLUTEN";
    public static final String KEY_HEIGHT = "KEY_HEIGHT";
    public static final String KEY_LACTOSE = "KEY_LACTOSE";
    public static final String KEY_NO_SUGAR = "KEY_NO_SUGAR";
    public static final String KEY_NUT = "KEY_NUT";
    public static final String KEY_SHELLFISH = "KEY_SHELLFISH";
    public static final String KEY_VEGAN = "KEY_VEGAN";
    public static final String KEY_WEIGHT = "KEY_WEIGHT";
    public static final String KEY_EXERCISE = "KEY_EXERCISE";
    public static final String KEY_SAVE_USER = "KEY_SAVE_USER";

    private static final String PREFERENCES_NAME = "ANDROID_TUTORIALS_HUB_PREFERENCES";

    private SharedPreferences sharedPreferences;

    /**
     * constructor
     *
     * @param context
     */
    public AppPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * method to set string prefs
     *
     * @param key
     * @param value
     */
    public void setStringPrefs(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * method to get string prefs
     *
     * @param key
     * @return
     */
    public String getStringPrefs(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * method to set int prefs
     *
     * @param key
     * @param value
     */
    public void setIntPrefs(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * method to get int prefs
     *
     * @param key
     * @return
     */
    public int getIntPrefs(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * method to set boolean prefs
     *
     * @param key
     * @param value
     */
    public void setBooleanPrefs(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * method to get boolean prefs
     *
     * @param key
     * @return
     */
    public boolean getBooleanPrefs(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * method to set long prefs
     *
     * @param key
     * @param value
     */
    public void setLongPrefs(String key, long value) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * method to get long prefs
     *
     * @param key
     * @return
     */
    public long getLongPrefs(String key) {
        return sharedPreferences.getLong(key, 0L);
    }

    /**
     * method to set float prefs
     *
     * @param key
     * @param value
     */
    public void setFloatPrefs(String key, float value) {
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * method to get float prefs
     *
     * @param key
     * @return
     */
    public float getFloatPrefs(String key) {
        return sharedPreferences.getFloat(key, 0.0F);
    }

    /**
     * method to clear specific prefs
     *
     * @param key
     */
    public void clearPrefs(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * method to clear all prefs
     */
    public void clearPrefs() {
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}