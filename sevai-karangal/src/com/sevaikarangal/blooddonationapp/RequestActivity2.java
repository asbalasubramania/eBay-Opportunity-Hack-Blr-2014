package com.sevaikarangal.blooddonationapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RequestActivity2 extends Activity {

	public void openDonorList(View view) {
		Intent intent = new Intent(this, DonorListActivity.class);
		SharedPreferences pref = ((DonorApplication) getApplication())
				.getPref();
		intent.putExtra("city", pref.getString("City", "Bangalore"));
		intent.putExtra("locality", pref.getString("Locality", "hst"));
		intent.putExtra("bloodGroup", pref.getString("BloodGroup", "O+ve"));
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request2);

		final Bundle bundle = getIntent().getExtras();

		Button sms = (Button) findViewById(R.id.sendsms);
		sms.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"You can now send SMS to YOUR contacts ",
						Toast.LENGTH_LONG).show();

				String str = new String();
				if (bundle.getString("info") != null) {
					str = bundle.getString("info");

				}
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:"));

				sendIntent.putExtra("sms_body", "I need blood" + str);

				startActivity(sendIntent);

			}
		});

		Button emer = (Button) findViewById(R.id.emer);
		emer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}

}
