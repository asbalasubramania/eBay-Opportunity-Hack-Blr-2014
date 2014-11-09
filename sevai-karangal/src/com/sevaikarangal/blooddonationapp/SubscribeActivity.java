package com.sevaikarangal.blooddonationapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sevaikarangal.blooddonationapp.bean.DonorRequest;

public class SubscribeActivity extends Activity {

	Spinner bloodGrp;
	Spinner gender;
	Spinner locality;
	
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
//				EditText locality = (EditText) findViewById(R.id.locality);
				// EditText age = (EditText) findViewById(R.id.age);
				EditText height = (EditText) findViewById(R.id.height);
				EditText weight = (EditText) findViewById(R.id.weight);

				
				if (personName.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Mandatory : Name", Toast.LENGTH_SHORT).show();
					return;
				}
				if (contactNum.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Mandatory : Contact Number", Toast.LENGTH_SHORT).show();
					return;
				}
				Pattern pattern = Pattern.compile("\\d{10}");
		        Matcher matcher = pattern.matcher(contactNum.getText().toString());
			    if (matcher.matches()) {
				  System.out.println("Phone Number Valid");
			    }
			    else
			    {
				  Toast.makeText(getApplicationContext(), "Mandatory : Phone Number must be 10 digits", Toast.LENGTH_SHORT).show();
				  return;
			    }
			    
				if (height.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Mandatory : Height", Toast.LENGTH_SHORT).show();
					return;
				}
				if (weight.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Mandatory : Weight", Toast.LENGTH_SHORT).show();
					return;
				}

				final DonorRequest rq = new DonorRequest();
				rq.setBloodGroup(bloodGrp.getSelectedItem().toString());
				rq.setGender(gender.getSelectedItem().toString());
				rq.setName(personName.getText().toString());
				if (contactNum.getText() != null)
					rq.setPhoneNumber(Long.parseLong(contactNum.getText()
							.toString()));
				rq.setBloodGroup(locality.getSelectedItem().toString());
				rq.setLastDonatedDate(null);
				rq.setCity("Bangalore");
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
								
								String donorId = (new String(response));
								Intent requestListActivityIntent = new Intent(
										SubscribeActivity.this,
										RequestListActivity.class);

								
								Intent notifyServiceIntent = new Intent(SubscribeActivity.this,
										NotifyService.class);
								notifyServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								notifyServiceIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
								notifyServiceIntent.putExtra("bloodGroup", rq.getBloodGroup());
								notifyServiceIntent.putExtra("city", rq.getCity());
								notifyServiceIntent.putExtra("locality", rq.getLocality());
								startService(notifyServiceIntent);
								
								
								//Add the Donor details to shared preference
								Editor edit = ((DonorApplication)getApplication()).getPref().edit();
								edit.putString("DonorId", donorId);
								edit.putString("City", rq.getCity());
								edit.putString("BloodGroup", rq.getBloodGroup());
								edit.putString("Locality", rq.getLocality());
								edit.commit();
								
								
								requestListActivityIntent.putExtra("bloodGroup", rq.getBloodGroup());
								requestListActivityIntent.putExtra("locality", rq.getLocality());
								requestListActivityIntent.putExtra("city", rq.getCity());
								startActivity(requestListActivityIntent);
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

//		SharedPreferences pref = ((DonorApplication) getApplication())
//				.getPref();
//		//Back upplan for demo
//		String donorId = pref.getString("DonorId", "5639445604728832");
//		
//		AsyncHttpClient client = new AsyncHttpClient();
//
//		client.get(
//				getApplicationContext(),
//				"http://1-dot-blood-donor-svc.appspot.com/datastore/donor/"+donorId,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] response) {
//						
//						Gson gson = new Gson();
//						DonorRequest donorDetail = gson.fromJson(new String(response), DonorRequest.class);
//						
//						
//						Intent intent = new Intent(SubscribeActivity.this,
//								NotifyService.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//						intent.putExtra("bloodGroup", donorDetail.getBloodGroup());
//						intent.putExtra("city", donorDetail.getCity());
//						intent.putExtra("locality", donorDetail.getLocality());
//						startService(intent);
//
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] errorResponse, Throwable e) {
//						Toast.makeText(getApplicationContext(),
//								new String(errorResponse), Toast.LENGTH_LONG)
//								.show();
//					}
//				});

	}

	public void addItemsOnSpinners() {

		bloodGrp = (Spinner) findViewById(R.id.bloodGrp);
		List<String> list = new ArrayList<String>();
		list.add("O+ve");
		list.add("O-ve");
		list.add("A+ve");
		list.add("A-ve");
		list.add("B+ve");
		list.add("B-ve");
		list.add("AB+ve");
		list.add("AB-ve");
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
		dataAdapter2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(dataAdapter2);
		
		locality = (Spinner) findViewById(R.id.subLocality);
		List<String> list2 = new ArrayList<String>();
		list2.add("Abbigere");
		list2.add("AECS Layout");
		list2.add("Airport Area");
		list2.add("Airport Road");
		list2.add("Akshaya Nagar");
		list2.add("Anekal");
		list2.add("Anjanapura");
		list2.add("Attibele");
		list2.add("Bagalur");
		list2.add("Banashankari");
		list2.add("Banaswadi");
		list2.add("Bannerghatta Road");
		list2.add("Basavanagar");
		list2.add("Basavanagudi");
		list2.add("Basaveshwaranagar");
		list2.add("Begur Road");
		list2.add("Belandur");
		list2.add("Bellary Road");
		list2.add("Benson Town");
		list2.add("Bilekahalli");
		list2.add("Bommanahalli");
		list2.add("Bommasandra");
		list2.add("Brooke Field");
		list2.add("BTM Layout");
		list2.add("C V Raman Nagar");
		list2.add("Central Silk Board");
		list2.add("Chamarajpet");
		list2.add("Chambal River");
		list2.add("Chandapur");
		list2.add("Chikkaballapur");
		list2.add("Chikkajala");
		list2.add("Cookes Town");
		list2.add("Cox Town");
		list2.add("Defence Colony");
		list2.add("Devanahalli");
		list2.add("Dodballapur Road");
		list2.add("Doddaballapur");
		list2.add("Doddaballapur Road");
		list2.add("Dollars Colony");
		list2.add("Domlur");
		list2.add("Electronic City");
		list2.add("Fraser Town");
		list2.add("G M Palya");
		list2.add("Ganganagar");
		list2.add("Gottigere");
		list2.add("Hanumanth Nagar");
		list2.add("HBR Layout");
		list2.add("Hebbal");
		list2.add("Hebbal Kempapura");
		list2.add("Hegde Nagar");
		list2.add("Hennur");
		list2.add("Hennur Road");
		list2.add("Hesaraghatta Main Road");
		list2.add("HMT Layout");
		list2.add("Hoodi Village");
		list2.add("Horamavu");
		list2.add("Hoskote");
		list2.add("Hoskote");
		list2.add("Hosur Road");
		list2.add("HRBR Layout");
		list2.add("HSR Layout");
		list2.add("Hulimavu");
		list2.add("Huskur");
		list2.add("Indira Nagar");
		list2.add("Indraprastha");
		list2.add("ISRO Layout");
		list2.add("ITPL");
		list2.add("Jakkur");
		list2.add("Jalahalli");
		list2.add("Jayanagar");
		list2.add("Jeevan Bima Nagar");
		list2.add("Jigani Industrial Area");
		list2.add("JP Nagar");
		list2.add("Kaggadaspura");
		list2.add("Kalyan Nagar");
		list2.add("Kanaka Nagar");
		list2.add("Kanakapura Road");
		list2.add("Kasturi Nagar");
		list2.add("Kengeri");
		list2.add("Kodigehalli");
		list2.add("Koramangala");
		list2.add("KR Puram");
		list2.add("Kudlu Gate");
		list2.add("Kumaraswamy Layout");
		list2.add("Kundalahalli");
		list2.add("Lavelle Road");
		list2.add("Madiwala");
		list2.add("Magadi Road");
		list2.add("Mahadevapura");
		list2.add("Majestic");
		list2.add("Mallesh Palaya");
		list2.add("Malleshwaram");
		list2.add("Manek Chowk");
		list2.add("Marathahalli");
		list2.add("Mathikere");
		list2.add("MG Road");
		list2.add("Millers Road");
		list2.add("Mysore Road");
		list2.add("Naganathapura");
		list2.add("Nagarbhavi");
		list2.add("Nagawara");
		list2.add("Nagwar");
		list2.add("Nandi Hills");
		list2.add("NelaMangala");
		list2.add("Old Airport Road");
		list2.add("Old Madras Road");
		list2.add("OMBR Layout");
		list2.add("Outer Ring Road");
		list2.add("Padmanabhanagar");
		list2.add("Pai Layout");
		list2.add("Palace Road");
		list2.add("Peenya");
		list2.add("Prashanth Nagar");
		list2.add("Raj Bhavan");
		list2.add("Rajajinagar");
		list2.add("Rajanukunte");
		list2.add("Rajarajeshwari Nagar");
		list2.add("Ramamurthy Nagar");
		list2.add("RBI Layout");
		list2.add("Rest House Road");
		list2.add("Richards Town");
		list2.add("Richmond Road");
		list2.add("RMV Extension Stage");
		list2.add("RT Nagar");
		list2.add("Sadaramangala");
		list2.add("Sadaramangala");
		list2.add("Sahakar Nagar");
		list2.add("Sanjay Nagar");
		list2.add("Sarjapur");
		list2.add("Sarjapur Road");
		list2.add("Shanti Nagar");
		list2.add("Silkboard");
		list2.add("Thanisandra");
		list2.add("Thippasandra");
		list2.add("Thyagaraj Nagar");
		list2.add("Tippasandra");
		list2.add("Tumkur Road");
		list2.add("Ulsoor");
		list2.add("Uttarahalli");
		list2.add("Vasanth Nagar");
		list2.add("Vidyanagar");
		list2.add("Vidyaranyapura");
		list2.add("Vigyan Nagar");
		list2.add("Vijaya Bank Layout");
		list2.add("Vijayanagar");
		list2.add("Whitefield");
		list2.add("Wilson Garden");
		list2.add("Yelahanka");
		list2.add("Yeshwantpur");
		ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list2);
		dataAdapter3
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locality.setAdapter(dataAdapter3);
	}

}
