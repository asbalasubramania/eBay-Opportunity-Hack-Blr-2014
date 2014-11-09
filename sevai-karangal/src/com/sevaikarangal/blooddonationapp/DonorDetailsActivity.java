package com.sevaikarangal.blooddonationapp;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sevaikarangal.blooddonationapp.bean.DonorDetail;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class DonorDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donor_details);

		final Bundle bundleExtras = getIntent().getExtras();
		
		String donarId = null;
		if (bundleExtras.getString("donarId") != null) {
			donarId = bundleExtras.getString("donarId");
		}

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (donarId != null)
			params.add("donarId", donarId);

		client.get(getApplicationContext(),
				"http://1-dot-blood-donor-svc.appspot.com/datastore/donor",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						String responseString = new String(response);
						Toast.makeText(getApplicationContext(),
								new String(responseString), Toast.LENGTH_LONG)
								.show();

						System.out.println(new String(responseString));

						Gson gson = new Gson();
						DonorDetail donorDetails = gson.fromJson(new String(
								responseString), DonorDetail.class);

						TextView dname = (TextView) findViewById(R.id.dname_value);
						dname.setText(donorDetails.getName());
						TextView bloodGrp = (TextView) findViewById(R.id.dbloodgroup_value);
						bloodGrp.setText(donorDetails.getBloodGroup());
						TextView gender = (TextView) findViewById(R.id.dgender_value);
						gender.setText(donorDetails.getGender());
						TextView locality = (TextView) findViewById(R.id.dlocality_value);
						locality.setText(donorDetails.getLocality());
						TextView city = (TextView) findViewById(R.id.dcity_value);
						city.setText(donorDetails.getCity());
						TextView mobile = (TextView) findViewById(R.id.dmobile_value);
						mobile.setText(String.valueOf(donorDetails
								.getPhoneNumber()));
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
		getMenuInflater().inflate(R.menu.donor_details, menu);
		return true;
	}

}
