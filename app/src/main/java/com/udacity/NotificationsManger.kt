package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.AvailableNetworkInfo.PRIORITY_HIGH
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0
private const val channelId = "channelId"

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context
        ,status:String,name:String) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra("status",status)
    contentIntent.putExtra("name",name)

    val contentPendintItnent = PendingIntent.getActivity(
        applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationCompat = NotificationCompat.Builder(
        applicationContext, channelId
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendintItnent)
        .setAutoCancel(true)
        .setPriority(PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .addAction(R.drawable.ic_assistant_black_24dp, applicationContext.getString(R.string.notification_button), contentPendintItnent)

    notify(NOTIFICATION_ID, notificationCompat.build())
}