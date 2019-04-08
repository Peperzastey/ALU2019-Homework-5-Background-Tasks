package pl.daftacademy.alu.ppoljanski.homework5

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import pl.daftacademy.alu.ppoljanski.homework5.util.DaftNotificationFactory

private const val TAG = "[alu]ScanningService"

class ScanningService : Service() {

    private val notificationFactory by lazy { DaftNotificationFactory() }
    private val NOTIFICATION_ID = 1
    private lateinit var NOTIFICATION_TITLE: String
    private lateinit var NOTIFICATION_TEXT: String

    init {
        //TODO check if resource constants can be initialized here
    }

    override fun onCreate() {
        super.onCreate()
        NOTIFICATION_TITLE = getString(R.string.notification_4_title)
        NOTIFICATION_TEXT = getString(R.string.notification_4_text)
        Log.d(TAG, "created")
        startForeground(NOTIFICATION_ID, notificationFactory.create(this, NOTIFICATION_TITLE, NOTIFICATION_TEXT))
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
