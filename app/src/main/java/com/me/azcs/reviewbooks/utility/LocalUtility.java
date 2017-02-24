package com.me.azcs.reviewbooks.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by azcs on 13/02/17.
 */

public class LocalUtility {
    private static final String SAVED_LANG = "LOCALE_SAVED_LANG";

    public static void onCreate(Context context) {
        String lang = getSavedData(context, Locale.getDefault().getLanguage());
        setLocale(context, lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getSavedData(context, defaultLanguage);
        setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getSavedData(context, Locale.getDefault().getLanguage());
    }

    public static void setLocale(Context context, String language) {
        save(context, language);
        updateConfiguration(context, language);
    }

    private static String getSavedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SAVED_LANG, defaultLanguage);
    }

    private static void save(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_LANG, language);
        editor.apply();
    }

    private static void updateConfiguration(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
