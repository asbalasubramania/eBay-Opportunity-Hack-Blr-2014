package com.sevaikarangal.blooddonationapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		
		 String str = new String();
         if (bundle.getString("reqid") != null) {
                 str = bundle.getString("reqid");

         }

         Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

         //System.out.println("got reqdid"+str);

         //make the rest call here
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
