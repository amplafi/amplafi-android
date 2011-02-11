package com.amplafi.android;

import java.net.URI;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferenceUtils {

	public static String getPreference(Context context, String key, String defaultValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
	}
	
	public static URI getFlowServerURI(Context context) {
		return URI.create(getPreference(context, context.getString(R.string.flowserveruri_preference_key), context.getString(R.string.flowserveruri_preference_default)) + "/apiv1");
	}
}
