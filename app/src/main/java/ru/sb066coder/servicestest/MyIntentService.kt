package ru.sb066coder.servicestest

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService(NAME) {



    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
        createNotificationChannel()
        val notification = getNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyIntentService: $message")
    }

    private fun getNotification(): Notification {
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Intent service")
            .setContentText("Intent service is working now!")
            .setSmallIcon(R.drawable.ic_launcher_foreground).build()
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(notificationChannel)
        }
    }

    companion object {

        private const val CHANNEL_ID = "2"
        private const val CHANNEL_NAME = "intent_channel"
        private const val NOTIFICATION_ID = 2
        private const val NAME = "MyIntentService"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}