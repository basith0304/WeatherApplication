package com.weather.app.common

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.weather.app.R

class Notification:BroadcastReceiver (){

    private val notificationID = 1
    private val channelID = "channel1"
    private val title = "title"
    private val message = "message"


    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = NotificationCompat.Builder(context!!, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent?.getStringExtra(title))
            .setContentText(intent?.getStringExtra(message))
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }


}