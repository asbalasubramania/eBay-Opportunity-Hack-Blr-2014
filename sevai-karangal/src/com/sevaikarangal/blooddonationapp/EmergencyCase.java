package com.sevaikarangal.blooddonationapp;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class EmergencyCase extends Activity {

	LocationManager locMgr = null;
	LocationListener locListener = null;

	public static Double lat, lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency);
		final Bundle bundle = getIntent().getExtras();
		//locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
			//	new geoUpdate());
		//Location lc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		// Though it says reqid its actually phone no. 
		 String str = new String();
         if (bundle.getString("reqid") != null) {
                 str = bundle.getString("reqid");

         }

         Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

         //System.out.println("got reqdid"+str);

         //make the rest call here
         
         
        /* AsyncHttpClient client = new AsyncHttpClient();

 		client.get(
 				getApplicationContext(),
 				"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor/" +str,
 				new AsyncHttpResponseHandler() {
 					@Override
 					public void onSuccess(int statusCode, Header[] headers,
 							byte[] response) {
 						// Toast.makeText(getApplicationContext(),
 						// new String(response),
 						// Toast.LENGTH_LONG).show();

 						JSONObject jsobobj = null;
 						try {
 							jsobobj = new JSONObject(new String(response));
 						} catch (JSONException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
 						// JSONObject job2 = new JSONObject();
 						String bgp = new String();
 						String city = new String();
 						System.out.println(jsobobj);
 						try {
 							// job2 =
 							// jsobobj.getJSONObject("bloodRequestorRequest");

 							bgp = jsobobj.getString("bloodGroup");
 							city = jsobobj.getString("city");
 							
 							Editor edit = ((DonorApplication)getApplication()).getPref().edit();
 							edit.putString("DonorId", jsobobj.getString("donorId"));
 							edit.putString("City", city);
 							edit.putString("BloodGroup", bgp);
 							edit.putString("Locality", jsobobj.getString("locality"));
 							edit.commit();

 						} catch (JSONException e1) {
 							// TODO Auto-generated catch block
 							e1.printStackTrace();
 						}
 						System.out.println(bgp);
 						System.out.println(city);
 					}
				});*/
 			
         
	}

	public void openRequestActivity(View view) {
		Intent intent = new Intent(this, RequestActivity.class);
		startActivity(intent);
	}

	public void openDonateActivity(View view) {
		Intent intent = new Intent(this, SubscribeActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
