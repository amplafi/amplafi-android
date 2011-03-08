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
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import org.amplafi.android.task.HttpRequestResult;
import org.amplafi.android.task.MessageInfoRequestTask;
import org.amplafi.android.task.PublishMessageRequestTask;
import org.amplafi.json.JSONObject;

import static org.amplafi.android.MessagesListActivity.*;
import static org.amplafi.android.PreferenceUtils.*;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

public class MessageEditActivity extends Activity {

    private EditText title;
	private EditText description;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_edit);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        final CharSequence flowClientId = getIntent().getCharSequenceExtra(flowClientUserId.toString());
		final long envelopeId = getIntent().getLongExtra(ENTITY_ID, 0);
		if(envelopeId > 0){
			new MessageInfoRequestTask(getFlowServerURI(this), flowClientId, envelopeId){

	        	ProgressDialog dialog;

	        	@Override
	        	protected void onPreExecute() {
	        		dialog = ProgressDialog.show(MessageEditActivity.this, getString(R.string.loading), "Loading message info");
	        	}

	        	@Override
	        	protected void onPostExecute(HttpRequestResult result) {
	        		JSONObject response = JSONObject.toJsonObject(result.getResult());
	        		JSONObject flowSate = response.getJSONObject(flowState.toString());
					flowSate = flowSate.getJSONObject("fsParameters");
					JSONObject envelope = flowSate.getJSONObject("broadcastEnvelope");
					title.setText(envelope.getString("title"));
					description.setText(envelope.getString("description"));
	        		dialog.dismiss();
	        	}
	        }.execute();
		}
        findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new PublishMessageRequestTask(getFlowServerURI(MessageEditActivity.this), flowClientId, envelopeId, title.getText(), description.getText()){

					private ProgressDialog dialog;

					@Override
                    protected void onPreExecute() {
						dialog = ProgressDialog.show(MessageEditActivity.this, "Sending message", "Wait a second..");
					};
					@Override
                    protected void onPostExecute(HttpRequestResult result) {
						dialog.setOnDismissListener(new OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface dialog) {
								finish();
							}
						});
						dialog.dismiss();
					};
				}.execute();
			}
		});
    }
}