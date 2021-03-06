package com.simplx.apps.alarmapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.simplx.apps.alarmapp.ui.main.MainActivity


class BootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent1 = Intent(context, MainActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(intent1)
    }
}