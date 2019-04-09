package pl.daftacademy.alu.ppoljanski.homework5.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory

private const val TAG = "[alu]ScanningService"

class ScanningService : Service() {

    private val notificationFactory by lazy { NotificationFactory() }
    private val NOTIFICATION_ID = 1
    private val NOTIFICATION_TITLE by lazy(LazyThreadSafetyMode.NONE) { getString(R.string.notification_4_title) }
    private val NOTIFICATION_BODY by lazy(LazyThreadSafetyMode.NONE) { getString(R.string.notification_4_text) }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "created")
        startForeground(NOTIFICATION_ID, notificationFactory.create(this, NOTIFICATION_TITLE, NOTIFICATION_BODY))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "start command")
        showScanningToast()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun showScanningToast() {
        Toast.makeText(this, R.string.scanning_toast, Toast.LENGTH_SHORT).show()
    }
}
