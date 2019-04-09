package pl.daftacademy.alu.ppoljanski.homework5.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.work.*
import pl.daftacademy.alu.ppoljanski.homework5.broadcast.ActionBroadcastReceiver
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.broadcast.ACTION_NOTIFY_FAMILIADA
import pl.daftacademy.alu.ppoljanski.homework5.service.ScanningService
import pl.daftacademy.alu.ppoljanski.homework5.work.UpdateWorker
import java.util.Calendar

private const val TAG = "[alu]MainActivity"

class MainActivity : AppCompatActivity() {

    private val alarmManager: AlarmManager
        get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //startForegroundService(Intent(this, ScanningService::class.java))    // since API level 26 (Build.VERSION_CODES.O)
        ContextCompat.startForegroundService(this, Intent(this, ScanningService::class.java))

        scheduleDailyAlarm()
        scheduleUpdate()
    }

    @Suppress("UNUSED_PARAMETER")
    fun stopScanning(view: View) {
        Log.d(TAG, "stopScanning clicked")
        stopService(Intent(this, ScanningService::class.java))
    }

    private fun scheduleDailyAlarm() {
        val now = Calendar.getInstance()
        val timeOfAlarm = now.clone() as Calendar
        timeOfAlarm.apply {
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 20)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)    // for completeness

            // if the time of setting the alarm is after the time of alarm being scheduled - start from tomorrow
            if (before(now))
                add(Calendar.DAY_OF_MONTH, 1)
        }

        val alarmMillisUtc = timeOfAlarm.timeInMillis

        // explicit intent
        Intent(this, ActionBroadcastReceiver::class.java)
                .apply { action = ACTION_NOTIFY_FAMILIADA }
                .let { PendingIntent.getBroadcast(this, 0, it, 0) }
                .let {
                    alarmManager.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            alarmMillisUtc,
                            AlarmManager.INTERVAL_DAY,
                            it
                    )
                }
        /* Note: as of API 19, all repeating alarms are inexact.
        If your application needs precise delivery times then it must use one-time exact alarms, rescheduling each time as described above.
        Legacy applications whose targetSdkVersion is earlier than API 19 will continue to have all of their alarms, including repeating alarms, treated as exact. */
    }

    private fun scheduleUpdate() {
        val constraint = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val request = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                .setConstraints(constraint)
                .build()
        WorkManager
                .getInstance()
                .enqueueUniqueWork(UpdateWorker::javaClass.name, ExistingWorkPolicy.KEEP, request)
    }
}
