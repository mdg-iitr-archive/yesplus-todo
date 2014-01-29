package com.example.myyesproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	NotificationManager nManager;

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent(context, ShowTodayTask.class);
		nManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		CharSequence from = "Task!";
		CharSequence message = "You need to complete a task, your deadline is approaching!";
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				myIntent, 0);
		Notification notif = new Notification(R.drawable.ic_launcher, "Task!!",
				System.currentTimeMillis());
		notif.flags = Notification.FLAG_INSISTENT;
		notif.setLatestEventInfo(context, from, message, contentIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		nManager.notify(1, notif);

	}
}