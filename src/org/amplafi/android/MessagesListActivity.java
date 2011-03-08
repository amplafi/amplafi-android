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

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import org.amplafi.android.task.HttpRequestResult;
import org.amplafi.android.task.MessageListRequestTask;
import org.amplafi.json.JSONArray;
import org.amplafi.json.JSONObject;

import static org.amplafi.android.PreferenceUtils.*;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

public class MessagesListActivity extends ListActivity {

	private static final int GET_AUTH = 1;
	public static final String ENTITY_ID = "entityId";

	private RemoteListAdapter listAdapter;


	private CharSequence clientId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listAdapter = new RemoteListAdapter();
		setListAdapter(listAdapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MessagesListActivity.this, MessageEditActivity.class);
				intent.putExtra(ENTITY_ID, id);
				intent.putExtra(flowClientUserId.toString(), clientId);
				startActivity(intent);
			}
		});
	}

	private class RemoteListAdapter extends BaseAdapter {

		JSONArray<JSONObject> messages = new JSONArray<JSONObject>();

		@Override
		public View getView(int position, View convertView, ViewGroup listView) {
			ViewGroup view;
			if (convertView != null){
				view = (ViewGroup) convertView;
			} else {
				view = (ViewGroup) getLayoutInflater().inflate(R.layout.list_item_message, null);
			}
			((TextView)view.findViewById(R.id.title)).setText(getItem(position).getString("title"));
			((TextView)view.findViewById(R.id.description)).setText(getItem(position).getString("description"));
			return view;
		}

		@Override
		public long getItemId(int position) {
			return messages.getJSONObject(position).getLong(ENTITY_ID);
		}

		@Override
		public JSONObject getItem(int position) {
			return messages.getJSONObject(position);
		}

		@Override
		public int getCount() {
			return messages.size();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isLoginNeeded()) {
			startActivityForResult(new Intent(this, AuthActivity.class), GET_AUTH);
		} else {
			new MessageListRequestTask(getFlowServerURI(this), clientId.toString(), getIntent().getStringExtra(flow.toString())) {

				private ProgressDialog dialog;

				@Override
                protected void onPreExecute() {
					dialog = ProgressDialog.show(MessagesListActivity.this, getString(R.string.loading), getString(R.string.getting_messages_list));
				};

				@Override
				protected void onPostExecute(HttpRequestResult result) {
					super.onPostExecute(result);
					try{
						JSONObject response = JSONObject.toJsonObject(result.getResult());
						JSONObject flowSate = response.getJSONObject(flowState.toString());
						flowSate = flowSate.getJSONObject("fsParameters");
						JSONArray<JSONObject> messages = flowSate.getJSONArray("broadcastEnvelopes");
						listAdapter.messages = messages;
						listAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						listAdapter.messages = new JSONArray<JSONObject>();
					}
					if(dialog.isShowing()){
						dialog.dismiss();
					}
				}

			}.execute();
		}
	}

	private boolean isLoginNeeded() {
		return clientId == null || clientId.length() == 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case GET_AUTH:
			if(RESULT_OK == resultCode){
				clientId = data.getCharSequenceExtra(flowClientUserId.toString());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.messages_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_create_message:
			Intent intent = new Intent(this, MessageEditActivity.class);
			intent.putExtra(flowClientUserId.toString(), clientId);
			startActivity(intent);
			break;
		case R.id.menu_preferences:
			startActivity(new Intent(this, PreferenceActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
