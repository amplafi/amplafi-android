package com.amplafi.android;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;

import java.net.URI;

import android.app.ListActivity;
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

import com.amplafi.android.task.HttpRequestTask;

public class MessagesListActivity extends ListActivity {

	private static final int GET_AUTH = 1;
	
	private BaseAdapter listAdapter;
	
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
		@Override
		public View getView(int position, View convertView, ViewGroup listView) {
			TextView view;
			if (convertView != null){
				view = (TextView) convertView;
			} else {
				view = (TextView) getLayoutInflater().inflate(R.layout.list_item_message, null);
			}
			view.setText(getItem(position) + " " + String.valueOf(position));
			return view;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return "Dummy message";
		}
		
		@Override
		public int getCount() {
			return 10;
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
				listAdapter.notifyDataSetChanged();
				CharSequence clientId = data.getCharSequenceExtra(flowClientUserId.toString());
				requestLogin = clientId == null || clientId.length() == 0;
				new HttpRequestTask(URI.create("http://amplafi.net")) {
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						listAdapter.notifyDataSetChanged();
					}
				}.execute();
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
