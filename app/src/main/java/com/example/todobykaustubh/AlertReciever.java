package com.example.todobykaustubh;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context);
        String name=intent.getStringExtra("name");
        String message=intent.getStringExtra("desc");
        int id=intent.getIntExtra("id",0);
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +context.getPackageName()+"/"+R.raw.mytone);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyme")
                .setSmallIcon(R.drawable.hook)
                .setChannelId("notifid")
                .setContentTitle(name)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(0x68e0cf,1000,1000)
                .setSound(alarmSound)
                .setColor(0x68e0cf);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification=builder.build();
        notification.flags=Notification.FLAG_INSISTENT;
        notificationManagerCompat.notify(id,notification);
    }
    public void createNotification(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Uri alarmSound = Uri.parse( ContentResolver.SCHEME_ANDROID_RESOURCE+"://" +context.getPackageName()+ "/"+R.raw.mytone);
            CharSequence name = "todo";
            String description = "Notification channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifid", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(0x68e0cf);
            channel.setShowBadge(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
            channel.setSound(alarmSound,audioAttributes);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}
