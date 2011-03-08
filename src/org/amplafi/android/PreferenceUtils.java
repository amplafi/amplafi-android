/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package org.amplafi.android;

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
