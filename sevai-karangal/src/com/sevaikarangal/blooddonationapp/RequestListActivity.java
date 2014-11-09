package com.sevaikarangal.blooddonationapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sevaikarangal.blooddonationapp.bean.RequestInfo;

public class RequestListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_list);

		final Bundle bundleExtras = getIntent().getExtras();

		String bloodGrp = null;
		String city = null;
		String locality = null;

		if (bundleExtras.getString("bloodGrp") != null) {
			bloodGrp = bundleExtras.getString("bloodGrp");
		}
		if (bundleExtras.getString("locality") != null) {
			locality = bundleExtras.getString("locality");
		}
		if (bundleExtras.getString("city") != null) {
			city = bundleExtras.getString("city");
		}

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (city != null) params.add("city", city);
		if (locality != null) params.add("locality", locality);
		if (bloodGrp != null) params.add("bloodGroup", bloodGrp);

		client.get(getApplicationContext(),
				"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						String responseString = new String(response);
						Toast.makeText(getApplicationContext(),
								new String(responseString), Toast.LENGTH_LONG).show();

						System.out.println(responseString);
						
						List<RequestInfo> values = new ArrayList<RequestInfo>();
						RequestArrayAdapter adapter = new RequestArrayAdapter(getApplicationContext(),
								R.layout.activity_request_item, values);
						setListAdapter(adapter);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] errorResponse, Throwable e) {
						Toast.makeText(getApplicationContext(),
								new String(errorResponse), Toast.LENGTH_LONG)
								.show();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.request_list, menu);
		return true;
	}

	public void invokeCall(View view) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:0123456789"));
		startActivity(intent);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RequestInfo item = (RequestInfo) getListAdapter().getItem(position);
		Toast.makeText(this, item.getBloodGroup() + " selected",
				Toast.LENGTH_LONG).show();
	}

	private class RequestArrayAdapter extends ArrayAdapter<RequestInfo> {

		HashMap<RequestInfo, Integer> mIdMap = new HashMap<RequestInfo, Integer>();
		private final Context context;

		public RequestArrayAdapter(Context context, int textViewResourceId,
				List<RequestInfo> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			RequestInfo item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.activity_request_item,
					parent, false);
			RequestInfo item = getItem(position);
			TextView bloodGrp = (TextView) rowView.findViewById(R.id.bloodGrp);
			bloodGrp.setText(item.getBloodGroup());
			TextView nameAddress = (TextView) rowView
					.findViewById(R.id.nameAddress);
			nameAddress.setText(item.getContactPerson() + "\n"
					+ item.getLocality());
			return rowView;
		}

	}

}
