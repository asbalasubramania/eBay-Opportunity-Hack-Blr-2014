package com.sevaikarangal.blooddonationapp;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sevaikarangal.blooddonationapp.bean.RequestInfo;

public class RequestDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_details);

		final Bundle bundleExtras = getIntent().getExtras();
		String requestId = null;
		if (bundleExtras.getString("requestId") != null) {
			requestId = bundleExtras.getString("requestId");
		}

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (requestId != null)
			params.add("requestId", requestId);

		client.get(getApplicationContext(),
				"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						String responseString = new String(response);
						Toast.makeText(getApplicationContext(),
								new String(responseString), Toast.LENGTH_LONG)
								.show();

						Gson gson = new Gson();
						RequestInfo donorDetails = gson.fromJson(new String(
								responseString), RequestInfo.class);

						TextView pname = (TextView) findViewById(R.id.pName_value);
						pname.setText(donorDetails.getPatientName());
						TextView bloodGrp = (TextView) findViewById(R.id.pBloodgroup_value);
						bloodGrp.setText(donorDetails.getBloodGroup());
						TextView gender = (TextView) findViewById(R.id.pUnits_value);
						gender.setText(donorDetails.getBloodUnits());
						TextView locality = (TextView) findViewById(R.id.pLocality_value);
						locality.setText(donorDetails.getLocality());
						TextView city = (TextView) findViewById(R.id.pCity_value);
						city.setText(donorDetails.getCity());
						TextView cPerson = (TextView) findViewById(R.id.pContactPerson_value);
						cPerson.setText(String.valueOf(donorDetails
								.getContactPerson()));
						TextView contectNum = (TextView) findViewById(R.id.pContact_no_value);
						contectNum.setText(String.valueOf(donorDetails
								.getContactNumber()));
						TextView date = (TextView) findViewById(R.id.pDate_value);
						date.setText(String.valueOf(donorDetails
								.getRequestDate()));
						TextView hospital = (TextView) findViewById(R.id.pHospital_value);
						hospital.setText(String.valueOf(donorDetails
								.getHospital()));
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_details, menu);
		return true;
	}

}
