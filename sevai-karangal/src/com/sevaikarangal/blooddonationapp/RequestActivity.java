package com.sevaikarangal.blooddonationapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sevaikarangal.blooddonationapp.bean.RequestInfo;

public class RequestActivity extends Activity {

	LocationManager locMgr = null;
	LocationListener locListener = null;
	Bundle bundle;
	Spinner spinner;
	String spinnerout = "";
	String[] bloudgroups = { "A+", "B+", "A", "O", "O+" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);

		addItemsOnSpinner1();
		
		Button submit = (Button) findViewById(R.id.button1);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText mEdit1 = (EditText) findViewById(R.id.units);
				EditText mEdit2 = (EditText) findViewById(R.id.patientn);
				EditText mEdit3 = (EditText) findViewById(R.id.hospital);
				EditText mEdit4 = (EditText) findViewById(R.id.contactp);
				EditText mEdit5 = (EditText) findViewById(R.id.contactn);
				EditText mEdit6 = (EditText) findViewById(R.id.location);
				EditText mEdit7 = (EditText) findViewById(R.id.date);
				EditText mEdit8 = (EditText) findViewById(R.id.city);

				RequestInfo rq = new RequestInfo();
				rq.setBloodGroup(spinner.getSelectedItem().toString());
				rq.setBloodUnits(Integer.parseInt(mEdit1.getText().toString()));
				rq.setPatientName(mEdit2.getText().toString());
				rq.setHospital(mEdit3.getText().toString());
				rq.setContactPerson(mEdit4.getText().toString());
				if (mEdit5.getText() != null) rq.setContactNumber(Long.parseLong(mEdit5.getText().toString()));
				rq.setLocality(mEdit6.getText().toString());
				rq.setRequestDate(new  Date());//mEdit7.getText().toString());
				rq.setCity(mEdit8.getText().toString());
			
				AsyncHttpClient client = new AsyncHttpClient();
				
				StringEntity entity = null;
		        try {
					entity = new StringEntity(rq.toString());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
		        System.out.println("####################################" + rq.toString());
				Toast.makeText(getApplicationContext(),
						rq.toString(),
						Toast.LENGTH_LONG).show();


				client.post(getApplicationContext(), 
						"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor",
						entity,
						"application/json",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] response) {
								Toast.makeText(getApplicationContext(),
										new String(response),
										Toast.LENGTH_LONG).show();
							}
							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] errorResponse,
									Throwable e) {
								Toast.makeText(getApplicationContext(),
										new String(errorResponse),
										Toast.LENGTH_LONG).show();
							}
						});

				// SEND teh request object to the server and call the API
				Intent reqobj = new Intent(RequestActivity.this,
						RequestActivity2.class);
				System.out.println(rq.getinfoinstr());
				reqobj.putExtra("info", rq.getinfoinstr());

				startActivity(reqobj);

				// remove later
				Toast.makeText(getApplicationContext(),
						(String) spinner.getSelectedItem().toString(),
						Toast.LENGTH_LONG).show();

				Toast.makeText(getApplicationContext(),
						(String) mEdit1.getText().toString(), Toast.LENGTH_LONG)
						.show();

			}
		});
		
		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new geoUpdate());
	    Location lc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	}
	
	

	public void addItemsOnSpinner1() {

		spinner = (Spinner) findViewById(R.id.spinner1);
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
		spinner.setAdapter(dataAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request, menu);
		return true;
	}

}

class geoUpdate implements LocationListener
{

	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		// Get the location and send the values 
		
	// Instead we send dummy values .  
			       	
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	
		
		
	}
	
}

