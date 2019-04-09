package pl.daftacademy.alu.ppoljanski.homework5.work

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory

private const val TAG = "[alu]UpdateWorker"

class UpdateWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun doWork(): Result {
        Log.d(TAG, "do work")
        Handler(Looper.getMainLooper()).post {
            with(applicationContext) { notificationFactory.show(this, getString(R.string.notification_3_title), getString(R.string.notification_3_text)) }
        }
        return Result.success()
    }
}