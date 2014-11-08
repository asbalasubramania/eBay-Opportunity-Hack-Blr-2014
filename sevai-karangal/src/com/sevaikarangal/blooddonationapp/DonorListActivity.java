package com.sevaikarangal.blooddonationapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sevaikarangal.blooddonationapp.bean.DonorDetail;

public class DonorListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donor_list);

		List<DonorDetail> values = new ArrayList<DonorDetail>();
		values.add(new DonorDetail("Ashok", "O+ve", "9886788822"));
		values.add(new DonorDetail("Harish", "O+ve", "9886788822"));

		DonorArrayAdapter adapter = new DonorArrayAdapter(this,
				R.layout.activity_donor_item, values);
		setListAdapter(adapter);

	}

	public void invokeCall(View view) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:0123456789"));
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.donor_list, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DonorDetail item = (DonorDetail) getListAdapter().getItem(position);
		Toast.makeText(this, item.name + " selected", Toast.LENGTH_LONG).show();
	}

	private class DonorArrayAdapter extends ArrayAdapter<DonorDetail> {

		HashMap<DonorDetail, Integer> mIdMap = new HashMap<DonorDetail, Integer>();
		private final Context context;

		public DonorArrayAdapter(Context context, int textViewResourceId,
				List<DonorDetail> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			DonorDetail item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.activity_donor_item,
					parent, false);
			DonorDetail item = getItem(position);
			TextView bloodGrp = (TextView) rowView.findViewById(R.id.bloodGrp);
			bloodGrp.setText(item.bloodGroup);
			TextView nameAddress = (TextView) rowView.findViewById(R.id.nameAddress);
			nameAddress.setText(item.name + "\n" + item.phoneNumber);
			return rowView;
		}

	}

}
