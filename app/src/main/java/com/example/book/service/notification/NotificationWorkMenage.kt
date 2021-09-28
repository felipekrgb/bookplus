package com.example.book.service.notification

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorkMenage(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {

    private val notificationHandler: NotificationHandler = NotificationHandler(appContext)

    override fun doWork(): Result {

        notificationHandler.createNotification(
            "Como está sua leitura?",
            "Leitura é a chave para se ter um universo de ideias e uma tempestade de palavras \uD83C\uDF0C ⚡️"
        ).let {
            NotificationManagerCompat.from(applicationContext)
                .notify(4, it)
        }
        return Result.success()
    }
}