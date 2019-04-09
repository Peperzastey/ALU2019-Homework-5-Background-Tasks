package pl.daftacademy.alu.ppoljanski.homework5.service

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory

const val EXTRA_NOTIFICATION_TITLE = "pl.daftacademy.alu.ppoljanski.EXTRA_NOTIF_TITLE"
const val EXTRA_NOTIFICATION_BODY= "pl.daftacademy.alu.ppoljanski.EXTRA_NOTIF_BODY"

class AlarmNotificationService : IntentService("AlarmNotificationService") {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null)
            Handler(Looper.getMainLooper()).post {
                with(applicationContext) {
                    notificationFactory.show(this, intent.getStringExtra(EXTRA_NOTIFICATION_TITLE), intent.getStringExtra(EXTRA_NOTIFICATION_BODY))
                }
            }
        // intent may be null if the service is being restarted after its process has gone away
        // then do nothing
    }
}
