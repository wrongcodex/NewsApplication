package com.example.newsapplication.core.utils.notifications

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.newsapplication.R

class NotificationUtils() {

    companion object {
        fun sendNotification(
            context: Context,
            channelId: String,
            title: String,
            url: String,
            anyIntent: PendingIntent?
        ): NotificationCompat.Builder {
            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(url)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                //.setOngoing(true)
                .addAction(R.drawable.ic_launcher_background, "Open App", anyIntent)
        }

        @SuppressLint("MissingPermission")
        fun launchNotification(context: Context, channelId: String, title: String, url: String, anyIntent: PendingIntent?) {
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(0, sendNotification(
                    context,
                    channelId,
                    title,
                    url,
                    anyIntent
                ).build())
            }
        }
        fun browserPendingIntentWithUri(context: Context, imageLink: String): PendingIntent?{
            val browserIntent = Intent(Intent.ACTION_VIEW, imageLink.toUri())
            val cancelPendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, browserIntent,
                PendingIntent.FLAG_IMMUTABLE)
            return cancelPendingIntent
        }
    }
}