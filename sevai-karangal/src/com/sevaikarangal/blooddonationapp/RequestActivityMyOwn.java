package com.sevaikarangal.blooddonationapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sevaikarangal.blooddonationapp.bean.DonorDetail;
import com.sevaikarangal.blooddonationapp.bean.RequestInfo;

public class RequestActivityMyOwn extends ListActivity {

	//Bundle bundle;
	//Spinner spinner;
	//String spinnerout = "";
//	String[] bloudgroups = { "A+", "B+", "A", "O", "O+" };

	//String reqid = new String();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_list_my_own);
		
		final Bundle bundleExtras = getIntent().getExtras();


		String phoneNumber = null;
		//String city = null;
		//String locality = null;

		if (bundleExtras.getString("phoneNumber") != null) {
			phoneNumber = bundleExtras.getString("phoneNumber");
		}
		

		
		
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				if (phoneNumber != null) params.add("phoneNumber", phoneNumber);
				
				
				client.get(getApplicationContext(),
						"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor" ,
						params, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers,
									byte[] response) {
								String responseString = new String(response);
								Toast.makeText(getApplicationContext(),
										new String(responseString), Toast.LENGTH_LONG).show();

								System.out.println(responseString);
								
								List<RequestInfo> values = new ArrayList<RequestInfo>();
								RequestActivityMyOwnAdapter adapter = new RequestActivityMyOwnAdapter(getApplicationContext(),
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
		getMenuInflater().inflate(R.menu.request, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RequestInfo item = (RequestInfo) getListAdapter().getItem(position);
		Toast.makeText(this, item.getBloodGroup() + " selected",
				Toast.LENGTH_LONG).show();
	}

	private class RequestActivityMyOwnAdapter extends ArrayAdapter<RequestInfo> {

		HashMap<RequestInfo, Integer> mIdMap = new HashMap<RequestInfo, Integer>();
		private final Context context;

		public RequestActivityMyOwnAdapter(Context context, int textViewResourceId,
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
			View rowView = inflater.inflate(R.layout.activity_request_item_my_own,
					parent, false);
			RequestInfo item = getItem(position);
			TextView phoneNumber = (TextView) rowView.findViewById(R.id.phoneNumber);
			phoneNumber.setText(item.getContactNumber().toString());
			TextView nameAddress = (TextView) rowView
					.findViewById(R.id.nameAddress);
			nameAddress.setText(item.getContactPerson() + "\n"
					+ item.getLocality());
			return rowView;
		}

	}

}