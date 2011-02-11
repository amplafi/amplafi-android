package com.amplafi.android;

import static com.amplafi.android.MessagesListActivity.ENTITY_ID;
import static com.amplafi.android.PreferenceUtils.getFlowServerURI;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowState;

import org.amplafi.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.amplafi.android.task.HttpRequestResult;
import com.amplafi.android.task.MessageInfoRequestTask;

public class MessageEditActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_edit);
        CharSequence flowClientId = getIntent().getCharSequenceExtra(flowClientUserId.toString());
		long envelopeId = getIntent().getLongExtra(ENTITY_ID, 0);
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
				((EditText) findViewById(R.id.title)).setText(envelope.getString("title"));
				((EditText) findViewById(R.id.description)).setText(envelope.getString("description"));
        		dialog.dismiss();
        	}
        }.execute();
        findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }
}