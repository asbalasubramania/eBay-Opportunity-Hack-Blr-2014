package com.sevaikarangal.blooddonationapp;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

public class NotifyService extends Service {

final static String ACTION = "NotifyServiceAction";
final static String STOP_SERVICE = "";
final static int RQS_STOP_SERVICE = 1;

NotifyServiceReceiver notifyServiceReceiver;

private static final int MY_NOTIFICATION_ID=1;
private NotificationManager notificationManager;
private Notification myNotification;
private final String myBlog = "http://android-er.blogspot.com/";

@Override
public void onCreate() {
// TODO Auto-generated method stub
notifyServiceReceiver = new NotifyServiceReceiver();
super.onCreate();

}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
// TODO Auto-generated method stub

	final Bundle bundle = intent.getExtras();
	String bgp = new String();String city =  new String();
	if (bundle.getString("bgp") != null) {
		bgp = bundle.getString("info");

	}
	if (bundle.getString("city") != null) {
		city = bundle.getString("info");

	}
IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction(ACTION);
registerReceiver(notifyServiceReceiver, intentFilter);

// Send Notification
notificationManager =
 (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
myNotification = new Notification(R.drawable.icon,
  "Notification!",
  System.currentTimeMillis());
Context context = getApplicationContext();
String notificationTitle = "URGENT blood needed";
String notificationText = "Someone is in need of blood !Click ";
//Intent myint = new Intent()
//Intent myIntent = new Intent(NotifyService.this, GetNotification.class);

/*AsyncHttpClient client = new AsyncHttpClient();
client.get(getApplicationContext(), 
		"http://1-dot-blood-donor-svc.appspot.com/datastore/requestor/",
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
*/

//startActivity(myIntent);
//Intent myIntent = new Intent(Intent.ACTION_VIEW, );
Intent myIntent = new Intent(NotifyService.this, GetNotification.class);
PendingIntent pendingIntent
  = PendingIntent.getActivity(getBaseContext(),
    0, myIntent,
    Intent.FLAG_ACTIVITY_NEW_TASK);

myNotification.defaults |= Notification.DEFAULT_SOUND;
myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
myNotification.setLatestEventInfo(context,
   notificationTitle,
   notificationText,
   pendingIntent);
notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

return super.onStartCommand(intent, flags, startId);
}

@Override
public void onDestroy() {
// TODO Auto-generated method stub
this.unregisterReceiver(notifyServiceReceiver);
super.onDestroy();
}

@Override
public IBinder onBind(Intent arg0) {
// TODO Auto-generated method stub
return null;
}

public class NotifyServiceReceiver extends BroadcastReceiver{

@Override
public void onReceive(Context arg0, Intent arg1) {
 // TODO Auto-generated method stub
 int rqs = arg1.getIntExtra("RQS", 0);
 if (rqs == RQS_STOP_SERVICE){
  stopSelf();
 }
}
}
}