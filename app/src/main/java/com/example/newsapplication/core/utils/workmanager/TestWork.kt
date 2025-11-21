package com.example.newsapplication.core.utils.workmanager

import android.content.Context
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.taskexecutor.TaskExecutor

abstract class TestWork(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {
        TODO("Not yet implemented")

    }

    override fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    override fun getTaskExecutor(): TaskExecutor {
        return super.getTaskExecutor()
    }

    override fun onStopped() {
        super.onStopped()
    }
}