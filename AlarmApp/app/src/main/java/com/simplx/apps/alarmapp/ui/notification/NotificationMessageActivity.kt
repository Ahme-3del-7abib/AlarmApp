package com.simplx.apps.alarmapp.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simplx.apps.alarmapp.R
import kotlinx.android.synthetic.main.activity_notification_message.*

class NotificationMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_message)

        val bundle = intent.extras
        tv_message.text = bundle!!.getString("message")
    }
}