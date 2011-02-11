package com.amplafi.android;

import static com.amplafi.android.MessagesListActivity.ENTITY_ID;
import static com.amplafi.android.PreferenceUtils.getFlowServerURI;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowState;

import org.amplafi.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.amplafi.android.task.HttpRequestResult;
import com.amplafi.android.task.MessageInfoRequestTask;
import com.amplafi.android.task.PublishMessageRequestTask;

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
					
					protected void onPreExecute() {
						dialog = ProgressDialog.show(MessageEditActivity.this, "Sending message", "Wait a second..");
					};
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