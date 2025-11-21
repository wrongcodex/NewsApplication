package com.example.newsapplication.core.utils.services

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.util.Log

class TestService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("abvbav", "onCreate: Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("abvbav", "onStartCommand: Service is doing something")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        Log.d("abvbav", "onDestroy: Service destroyed")
        super.onDestroy()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onTimeout(startId: Int) {
        super.onTimeout(startId)
    }

    override fun onTimeout(startId: Int, fgsType: Int) {
        super.onTimeout(startId, fgsType)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    val serviceIntent = Intent(this, TestService::class.java)
    override fun startService(service: Intent?): ComponentName? {
        startForegroundService(serviceIntent)
        return super.startService(serviceIntent)
    }

    override fun stopService(name: Intent?): Boolean {
        stopForeground(STOP_FOREGROUND_DETACH)
        stopForeground(BIND_AUTO_CREATE)
        stopSelf()
        return super.stopService(serviceIntent)
    }
}