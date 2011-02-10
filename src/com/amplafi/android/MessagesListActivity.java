package com.amplafi.android;

import static com.amplafi.android.PreferenceUtils.getPreference;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowState;

import java.net.URI;

import org.amplafi.json.JSONArray;
import org.amplafi.json.JSONObject;

import android.app.Dialog;
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

import com.amplafi.android.task.HttpRequestResult;
import com.amplafi.android.task.MessageListRequestTask;

public class MessagesListActivity extends ListActivity {

	private static final int GET_AUTH = 1;
	
	private RemoteListAdapter listAdapter;
	
	private boolean requestLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestLogin = true;
		listAdapter = new RemoteListAdapter();
		setListAdapter(listAdapter);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(MessagesListActivity.this, MessageEditActivity.class));
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
			return messages.getJSONObject(position).getLong("entityId");
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
		if (requestLogin) {
			startActivityForResult(new Intent(this, AuthActivity.class), GET_AUTH);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		requestLogin = true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case GET_AUTH:
			if(RESULT_OK == resultCode){
				CharSequence clientId = data.getCharSequenceExtra(flowClientUserId.toString());
				requestLogin = clientId == null || clientId.length() == 0;
				if (!requestLogin) {
					new MessageListRequestTask(getFlowServerURI(), clientId.toString()) {
						
						private ProgressDialog dialog;

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
							dialog.dismiss();
						}
						
					}.execute();
				}
			}
			break;
		default:
			break;
		}
	}
	
	private URI getFlowServerURI() {
		return URI.create(getPreference(this, getString(R.string.flowserveruri_preference_key), getString(R.string.flowserveruri_preference_default)) + "/apiv1");
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
			startActivity(new Intent(this, MessageEditActivity.class));
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
