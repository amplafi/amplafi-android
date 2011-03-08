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

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

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
