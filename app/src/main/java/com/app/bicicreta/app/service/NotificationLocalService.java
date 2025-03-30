package com.app.bicicreta.app.service;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.app.bicicreta.R;

public class NotificationLocalService {
    private final String CHANNEL_NAME = "Notificações Gerais";
    private final String CHANNEL_ID = "canal_padrao";
    private final int REQUEST_CODE = 0;
    private final int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private NotificationManager notificationManager;
    private Context context;
    private Class<?> classe;
    public NotificationLocalService(Context context, Class<?> classe){
        this.context = context;
        this.classe = classe;
        createChannel(context);
    }

    public void createNotification(String title, String texto){
        Intent intent = new Intent( context, classe);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S)
            pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_MUTABLE);
        else
            pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            builder = new Notification.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.bike)
                    .setContentTitle(title)
                    .setContentText(texto)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private void createChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String channelDescription = "Canal para notificações do app";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(channelDescription);
            notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
