package com.amplafi.android;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
