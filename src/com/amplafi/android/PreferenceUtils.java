package com.amplafi.android;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferenceUtils {

	public static String getPreference(Context context, String key, String defaultValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
	}
	
}
