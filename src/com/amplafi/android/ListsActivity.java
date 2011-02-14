package com.amplafi.android;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.flow;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ListsActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lists);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("approved").setIndicator("Published").setContent(new Intent(this, MessagesListActivity.class).putExtra(flow.toString(), "ApprovedList")));
		tabHost.addTab(tabHost.newTabSpec("drafts").setIndicator("Drafts").setContent(new Intent(this, MessagesListActivity.class).putExtra(flow.toString(), "DraftMessages")));
		tabHost.addTab(tabHost.newTabSpec("inbox").setIndicator("Inbox").setContent(new Intent(this, MessagesListActivity.class).putExtra(flow.toString(), "PendingList")));
	}
	
}
