package pl.daftacademy.alu.ppoljanski.homework5.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import pl.daftacademy.alu.ppoljanski.homework5.R
import pl.daftacademy.alu.ppoljanski.homework5.view.MainActivity
import java.util.*

class NotificationFactory {

    fun show(context: Context, title: String?, message: String?,
             @StringRes channelResId: Int = R.string.notification_channel_default_id) =
            create(context, title, message, channelResId)
                    .let { context.notificationManager?.notify(Random().nextInt(), it) }

    fun create(context: Context, title: String?, message: String?,
               @StringRes channelResId: Int = R.string.notification_channel_default_id): Notification {
        val defaultTitle = context.getString(R.string.app_name)
        val channelId = context.getString(channelResId)

        return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title ?: defaultTitle)
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(createPendingIntent(context))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)   // for Android 7.1 and lower
                .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannels(context: Context) {
        val channelId = context.getString(R.string.notification_channel_default_id)
        val channelName = context.getString(R.string.notification_channel_default_name)
        val channelDescription = context.getString(R.string.notification_channel_default_description)
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                .apply { description = channelDescription }
                .let { context.notificationManager?.createNotificationChannel(it) }
    }

    private val Context.notificationManager: NotificationManager?
        get() = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

    private fun createPendingIntent(context: Context) =
            Intent(context, MainActivity::class.java)
                    .let { it.toPendingIntent(context) }

    private fun Intent.toPendingIntent(context: Context) =
            PendingIntent.getActivity(context, 0, this, 0)
}