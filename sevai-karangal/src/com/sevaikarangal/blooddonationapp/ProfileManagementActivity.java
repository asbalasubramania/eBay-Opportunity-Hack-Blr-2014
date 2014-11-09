package com.sevaikarangal.blooddonationapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ProfileManagementActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_mgmt);
	}
	
	public void openUpdateProfileActivity(View view) {
		Intent intent = new Intent(this, UpdateProfileActivity.class);
		startActivity(intent);
	}


}
