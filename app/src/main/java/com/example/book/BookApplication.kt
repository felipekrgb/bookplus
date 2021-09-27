package com.example.book

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.*
import com.example.book.service.notification.NotificationHandler
import com.example.book.service.notification.NotificationWorkMenage
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BookApplication : Application(), LifecycleObserver {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        CoroutineScope(Dispatchers.Main).async {
            showNotification()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppDestroy() {
            startWork()
    }

    suspend fun showNotification() {
        delay(5000)
        notificationHandler.createNotification(
            "Como está sua leitura?",
            "Muitos homens iniciaram uma nova era na sua vida a partir da leitura de um livro \uD83D\uDE09"
        )
            .let {
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(1, it)
            }
    }

    fun startWork() {
        WorkManager.getInstance(applicationContext).let {
            val workManager = WorkManager.getInstance(applicationContext)

            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(true)
                .build()

            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorkMenage>(
                30,
                TimeUnit.MINUTES
            ).setConstraints(constraints).build()

            workManager.enqueueUniquePeriodicWork(
                UUID.randomUUID().toString(),
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        }
    }
}

