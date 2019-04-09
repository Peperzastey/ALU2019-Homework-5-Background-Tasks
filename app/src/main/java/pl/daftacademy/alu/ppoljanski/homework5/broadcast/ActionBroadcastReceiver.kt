package pl.daftacademy.alu.ppoljanski.homework5.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.daftacademy.alu.ppoljanski.homework5.service.AlarmNotificationService
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.service.EXTRA_NOTIFICATION_BODY
import pl.daftacademy.alu.ppoljanski.homework5.service.EXTRA_NOTIFICATION_TITLE

const val ACTION_NOTIFY_FAMILIADA = "pl.daftacademy.alu.ppoljanski.NOTIFY"

private const val TAG = "[alu]ActionBReceiver"

class ActionBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "broadcast received, action: ${intent.action}")

        data class NotificationParams(val title: String?, val body: String?)

        val (title, body) = when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> with(context) {
                NotificationParams(getString(R.string.notification_1_title), getString(R.string.notification_1_text))
            }
            ACTION_NOTIFY_FAMILIADA -> with(context) {
                NotificationParams(getString(R.string.notification_2_title), getString(R.string.notification_2_text))
            }
            else -> NotificationParams(null, null)
        }

        with(context) {
            startService(Intent(this, AlarmNotificationService::class.java)
                    .putExtra(EXTRA_NOTIFICATION_TITLE, title)
                    .putExtra(EXTRA_NOTIFICATION_BODY, body)
            )
        }

    }
}