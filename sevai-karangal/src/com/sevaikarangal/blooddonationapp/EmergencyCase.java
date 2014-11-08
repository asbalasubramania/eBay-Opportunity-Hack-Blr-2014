package com.sevaikarangal.blooddonationapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class EmergencyCase   extends Activity  {

	LocationManager locMgr = null;
	LocationListener locListener = null;
	
	public static Double lat,lon;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
        
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new geoUpdate());
        Location lc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        
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


