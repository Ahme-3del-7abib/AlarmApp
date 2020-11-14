package com.simplx.apps.alarmapp.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.simplx.apps.alarmapp.R
import com.simplx.apps.alarmapp.ui.notification.NotificationMessageActivity


class AlarmBroadCast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle = intent!!.extras
        val text = bundle!!.getString("event")
        val date = bundle!!.getString("date") + " " + bundle!!.getString("time")

        // -- When Click on Notification
        val intent1 = Intent(context, NotificationMessageActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent1.putExtra("message", text)

        // -- Build Notification
        val pendingIntent =
            PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT)

        val notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context!!, "notify_001")

        val contentView =
            RemoteViews(context.packageName, R.layout.notification_layout)

        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher)

        val pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent)

        contentView.setTextViewText(R.id.message, text)
        contentView.setTextViewText(R.id.date, date)

        mBuilder.setSmallIcon(R.drawable.ic_baseline_mic_24)

        mBuilder.setAutoCancel(true)
        mBuilder.setOngoing(true)

        mBuilder.priority = Notification.PRIORITY_HIGH
        mBuilder.setOnlyAlertOnce(true)
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH

        mBuilder.setContent(contentView)
        mBuilder.setContentIntent(pendingIntent)

        // -- for oreo android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel =
                NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }

        val notification = mBuilder.build()
        notificationManager.notify(1, notification)

    }
}