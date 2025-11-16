package com.example.newsapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Handler
import android.os.Looper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels(){
        val channels = listOf(
            NotificationChannel(
                "1",
                name = "Posts",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            ),
            NotificationChannel(
                "2",
                "Comments",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            ),
            NotificationChannel(
                "3",
                "Broadcasts",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            )
        )

        val channelsTwo = listOf(
            NotificationChannel(
                "4",
                name = "Posts Two",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            ),
            NotificationChannel(
                "5",
                "Comments Two",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            ),
            NotificationChannel(
                "6",
                "Broadcasts Two",
                importance = NotificationManager.IMPORTANCE_HIGH,
                description = "Quiz application was the first ever application made my Android Developer Hasnain"
            )
        )

        // now I'll register that channel with system

        val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val realChannels = channels.map { config->
            val channel = NotificationChannel(
                config.channelId,
                config.name,
                config.importance,
            )
            channel.group = "new_group"
            channel.description = config.description
            channel
        }

        val realChannelsTwo = channelsTwo.map { config->
            val channel = NotificationChannel(
                config.channelId,
                config.name,
                config.importance,
            )
            channel.group = "new_group_two"
            channel.description = config.description
            channel
        }

        val groupChannel = NotificationChannelGroup("new_group", "MyGroup")
        val groupChannelTwo = NotificationChannelGroup("new_group_two", "MyGroupTwo")
        notificationManager.createNotificationChannelGroup(groupChannel)
        notificationManager.createNotificationChannelGroup(groupChannelTwo)
        notificationManager.createNotificationChannels(realChannels)
        notificationManager.createNotificationChannels(realChannelsTwo)
    }
}
data class NotificationChannel(
    val channelId: String,
    val name: String,
    val importance: Int,
    var description: String? = null
)