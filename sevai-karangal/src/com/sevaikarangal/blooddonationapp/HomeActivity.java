package com.sevaikarangal.blooddonationapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	public void openRequestActivity(View view) {
		Intent intent = new Intent(this, RequestActivity.class);
		startActivity(intent);
	}

	public void openDonateActivity(View view) {
		Intent intent = new Intent(this, SubscribeActivity.class);
		startActivity(intent);
	}

	public void openMyListActivity(View view) {
		Intent intent = new Intent(this, RequestActivityMyOwn.class);
		SharedPreferences pref = ((DonorApplication) getApplication())
				.getPref();
		intent.putExtra("phoneNumber", pref.getString("PhoneNumber", ""));
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
