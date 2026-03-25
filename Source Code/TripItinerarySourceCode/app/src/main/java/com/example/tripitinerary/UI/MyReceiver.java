package com.example.tripitinerary.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.tripitinerary.R;

public class MyReceiver extends BroadcastReceiver {
    final String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context);
        Notification n = new NotificationCompat.Builder(context, channel_id).setSmallIcon(R.drawable.ic_launcher_foreground).setContentText(intent.getStringExtra("key")).setContentTitle("Vacation Alert").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }

    private void createNotificationChannel(Context context) {
        CharSequence name = "mychannelname";
        String description = "mychanneldescription";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("test", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
