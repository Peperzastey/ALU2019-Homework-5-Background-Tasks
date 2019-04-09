package pl.daftacademy.alu.ppoljanski.homework5.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.daftacademy.alu.ppoljanski.homework5.service.AlarmNotificationService
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory
import pl.daftacademy.alu.ppoljanski.homework5.view.FAMILIADA_NOTIFY_ACTION

private const val TAG = "[alu]ActionBReceiver"

class ActionBroadcastReceiver : BroadcastReceiver() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "broadcast received, action: ${intent.action}")

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> with(context) {
                //TODO move to IntentService
                notificationFactory.show(this, getString(R.string.notification_1_title), getString(R.string.notification_1_text))
            }
            FAMILIADA_NOTIFY_ACTION -> with(context) {
                startService(Intent(this, AlarmNotificationService::class.java))
                //?TODO? putExtra to intent?
            }
        }

    }
}
