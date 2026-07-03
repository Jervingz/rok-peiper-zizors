package com.escudero.rokpeiperzizors

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        showNotification()
        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification() {

        val notification = NotificationCompat.Builder(applicationContext, "game_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🎮 Te extrañamos")
            .setContentText("Vuelve a jugar, la partida te está esperando")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(1001, notification)
    }
}