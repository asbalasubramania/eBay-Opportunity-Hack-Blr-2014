package com.sevaikarangal.blooddonationapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DonorApplication extends Application{
	
	SharedPreferences pref;
	
	@Override
	public void onCreate(){
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public SharedPreferences getPref() {
		return pref;
	}

	public void setPref(SharedPreferences pref) {
		this.pref = pref;
	}
	


}
