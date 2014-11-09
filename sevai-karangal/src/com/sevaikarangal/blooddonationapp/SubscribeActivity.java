package com.sevaikarangal.blooddonationapp;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubscribeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		
		// move the below code in button 
		
		// donorid 5639445604728832
		
		AsyncHttpClient client = new AsyncHttpClient();
		
        client.get(getApplicationContext(), 
				"http://1-dot-blood-donor-svc.appspot.com/datastore/donor/5639445604728832",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode,
							Header[] headers, byte[] response) {
						//Toast.makeText(getApplicationContext(),
							//	new String(response),
								//Toast.LENGTH_LONG).show();
							
					JSONObject jsobobj = null;
					try {
						jsobobj = new JSONObject(new String(response));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//JSONObject job2 = new JSONObject();
					String bgp = new String();
					String city = new String();
					System.out.println(jsobobj);
					try {
						// job2 = jsobobj.getJSONObject("bloodRequestorRequest");
						 
						 bgp = jsobobj.getString("bloodGroup");
						 city = jsobobj.getString("city");
						 
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(bgp);System.out.println(city);
					
				 	  Intent intent = new Intent(SubscribeActivity.this, NotifyService.class);
				 	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 	 intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK); 
				 	  intent.putExtra("bgp", bgp);
				 	 intent.putExtra("city", city);
					  startService(intent);
					
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
	
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}

}
