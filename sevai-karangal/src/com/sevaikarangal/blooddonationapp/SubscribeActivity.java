package com.sevaikarangal.blooddonationapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sevaikarangal.blooddonationapp.bean.DonorDetail;

public class SubscribeActivity extends Activity {

	Spinner bloodGrp;
	Spinner gender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);

		addItemsOnSpinners();

		// donorid 5639445604728832
		Button submit = (Button) findViewById(R.id.button1);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText personName = (EditText) findViewById(R.id.personName);
				EditText contactNum = (EditText) findViewById(R.id.contactn);
				EditText locality = (EditText) findViewById(R.id.locality);
				// EditText age = (EditText) findViewById(R.id.age);
				EditText height = (EditText) findViewById(R.id.height);
				EditText weight = (EditText) findViewById(R.id.weight);
				EditText city = (EditText) findViewById(R.id.city);

				final DonorDetail rq = new DonorDetail();
				rq.setBloodGroup(bloodGrp.getSelectedItem().toString());
				rq.setGender(gender.getSelectedItem().toString());
				rq.setName(personName.getText().toString());
				if (contactNum.getText() != null)
					rq.setPhoneNumber(Long.parseLong(contactNum.getText()
							.toString()));
				rq.setLocality(locality.getText().toString());
				rq.setLastDonatedDate(null);
				rq.setCity(city.getText().toString());
				rq.setHeight(Double.parseDouble(height.getText().toString()));
				rq.setWeight(Double.parseDouble(weight.getText().toString()));

				AsyncHttpClient client = new AsyncHttpClient();

				StringEntity entity = null;
				try {
					entity = new StringEntity(rq.toString());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), rq.toString(),
						Toast.LENGTH_LONG).show();

				client.post(
						getApplicationContext(),
						"http://1-dot-blood-donor-svc.appspot.com/datastore/donor",
						entity, "application/json",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] response) {
								Toast.makeText(getApplicationContext(),
										R.string.subSuccess, Toast.LENGTH_LONG)
										.show();
								Intent intent = new Intent(
										SubscribeActivity.this,
										RequestListActivity.class);
								intent.putExtra("bloodGrp", rq.getBloodGroup());
								intent.putExtra("locality", rq.getLocality());
								intent.putExtra("city", rq.getCity());
								startActivity(intent);
							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] errorResponse,
									Throwable e) {
								Toast.makeText(getApplicationContext(),
										R.string.subFailed, Toast.LENGTH_LONG)
										.show();
							}
						});
			}
		});

		AsyncHttpClient client = new AsyncHttpClient();

		client.get(
				getApplicationContext(),
				"http://1-dot-blood-donor-svc.appspot.com/datastore/donor/5639445604728832",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						
						Gson gson = new Gson();
						DonorDetail donorDetail = gson.fromJson(new String(response), DonorDetail.class);
						
						//Add the Donor details to shared preference
						Editor edit = ((DonorApplication)getApplication()).getPref().edit();
						edit.putLong("DonorId", donorDetail.getDonorId());
						edit.putString("City", donorDetail.getCity());
						edit.putString("BloodGroup", donorDetail.getBloodGroup());
						edit.putString("Locality", donorDetail.getLocality());
						edit.commit();
						
						Intent intent = new Intent(SubscribeActivity.this,
								NotifyService.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
						intent.putExtra("bgp", donorDetail.getBloodGroup());
						intent.putExtra("city", donorDetail.getCity());
						intent.putExtra("loc", donorDetail.getLocality());
						startService(intent);

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

	public void addItemsOnSpinners() {

		bloodGrp = (Spinner) findViewById(R.id.bloodGrp);
		List<String> list = new ArrayList<String>();
		list.add("O+ve");
		list.add("O-ve");
		list.add("A-ve");
		list.add("B-ve");
		list.add("B+ve");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bloodGrp.setAdapter(dataAdapter);

		gender = (Spinner) findViewById(R.id.gender);
		list = new ArrayList<String>();
		list.add("Male");
		list.add("Female");
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(dataAdapter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}
}
