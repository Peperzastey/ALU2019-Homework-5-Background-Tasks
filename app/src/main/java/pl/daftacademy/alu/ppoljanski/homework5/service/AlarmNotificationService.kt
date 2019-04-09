package pl.daftacademy.alu.ppoljanski.homework5.service

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory

class AlarmNotificationService : IntentService("AlarmNotificationService") {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onHandleIntent(intent: Intent?) {
        Handler(Looper.getMainLooper()).post {
            with(applicationContext) { notificationFactory.show(this, getString(R.string.notification_2_title), getString(R.string.notification_2_text)) }
        }
    }
}
