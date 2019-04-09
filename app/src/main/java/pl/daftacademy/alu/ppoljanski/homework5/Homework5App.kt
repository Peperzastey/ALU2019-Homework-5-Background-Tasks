package pl.daftacademy.alu.ppoljanski.homework5

import android.app.Application
import android.os.Build
import pl.daftacademy.alu.ppoljanski.homework5.util.NotificationFactory

class Homework5App : Application() {

    private val notificationFactory by lazy { NotificationFactory() }

    override fun onCreate() {
        super.onCreate()
        initNotificationChannels()
    }

    private fun initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationFactory.createNotificationChannels(this)
    }
}