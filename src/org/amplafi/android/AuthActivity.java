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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

/**
 * Asks user to provide a flow client id.
 * At the moment the client id is just an entity id of a User within amplafi.
 * Later it will be changed to somewhat more secure and reliable. (i.e. we might 
 * consider making amplafi to be an OAuth provider).
 * 
 * 
 * @author Konstantin Burov
 *
 */
public class AuthActivity extends Activity {

	TextView input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		input = (TextView) findViewById(R.id.input);
		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK, new Intent().putExtra(flowClientUserId.toString(), input.getText()));
				finish();
			}

		});
	}

}
