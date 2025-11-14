package com.example.newsapplication.ui.presentation.components


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.newsapplication.R


object Notification {

    private const val channelId = "Test_Id"
    private const val notificationId = 1

    fun showNotification(context: Context){
        val builder = NotificationCompat.Builder(context, channelId )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("FIELD MARSHAL ASIM MUNIR")
            .setContentText("I AM VERY HAPPY TO SHOW THAT I AM FIELD MARSHAL ASIM MUNIR AT PAKISTAN ARMY")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            //.setOngoing(true)

        // check for permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
            return
        }
        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }
    fun cancelNotification(context: Context){
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(notificationId)
    }
}