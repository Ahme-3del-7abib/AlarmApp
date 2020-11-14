package com.simplx.apps.alarmapp.ui.add

import android.app.*
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.simplx.apps.alarmapp.R
import com.simplx.apps.alarmapp.alarm.AlarmBroadCast
import com.simplx.apps.alarmapp.factory.ViewModelFactory
import com.simplx.apps.alarmapp.model.EventViewModel
import com.simplx.apps.alarmapp.pojo.EntityClass
import kotlinx.android.synthetic.main.activity_add_alarm.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddAlarmActivity : AppCompatActivity() {

    lateinit var mCalendar: Calendar

    private var mMonth: Int? = null
    private var mYear: Int? = null
    private var mDay: Int? = null

    private var mHour: Int? = null
    private var mMinute: Int? = null

    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)

        declareInstances()

        btn_record.setOnClickListener {
            recordSpeech()
        }

        btn_time.setOnClickListener {
            selectTime()
        }

        btn_date.setOnClickListener {
            selectDate()
        }

        btn_done.setOnClickListener {
            submit()
        }
    }

    private fun declareInstances() {

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this.application))
                .get(EventViewModel::class.java)

        mCalendar = Calendar.getInstance()

        mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
        mMinute = mCalendar.get(Calendar.MINUTE)
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DATE)

    }

    private fun selectTime() {

        var calendar: Calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minutes = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

                mHour = hourOfDay
                mMinute = minute

                btn_time.text = formatTime(hourOfDay, minute)

            }, hour, minutes, false)

        timePickerDialog.show()
    }

    private fun selectDate() {
        var calendar = Calendar.getInstance()

        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        val datePickerDialog = DatePickerDialog(
            this,
            OnDateSetListener { _, year, month, day ->

                mYear = year
                mMonth = month
                mDay = day

                btn_date.text = day.toString() + "-" + (month + 1) + "-" + year
            }, year, month, day
        )

        datePickerDialog.show()
    }

    private fun submit() {
        val text = editext_message.text.toString().trim()

        if (text.isEmpty()) {
            Toast.makeText(this, "Please Enter or record the text", Toast.LENGTH_SHORT).show()
        } else {
            if (btn_time.text.toString() == "Select Time" || btn_date.text.toString() == "Select date") {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show()
            } else {
                var entityClass = EntityClass(
                    event_name = editext_message.text.toString().trim(),
                    event_date = btn_date.text.toString().trim(),
                    event_time = btn_time.text.toString().trim()
                )
                viewModel.insertEvent(entityClass)

                setAlarm(
                    editext_message.text.toString(),
                    btn_date.text.toString().trim(),
                    btn_time.text.toString().trim()
                )
            }
        }
    }

    private fun recordSpeech() {

        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-us")

        try {
            startActivityForResult(intent, 1)
        } catch (e: Exception) {
            Toast.makeText(this, "Your device don't support record recognize", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK && data != null) {
                var text: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                editext_message.setText(text[0])
            }
        }
    }

    private fun formatTime(hour: Int, minute: Int): String? {

        val formattedMinute: String = if (minute < 10) {
            "0$minute"
        } else {
            "" + minute
        }

        return when {
            hour == 0 -> {
                "12:$formattedMinute AM"
            }
            hour < 12 -> {
                "$hour:$formattedMinute AM"
            }
            hour == 12 -> {
                "12:$formattedMinute PM"
            }
            else -> {
                var hour2 = hour - 12
                "$hour2 - $12:$formattedMinute PM"
            }
        }
    }

    private fun setAlarm(text: String, date: String, time: String) {

        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var intent = Intent(applicationContext, AlarmBroadCast::class.java)
        intent.putExtra("event", text)
        intent.putExtra("date", date)
        intent.putExtra("time", time)


        mMonth?.let { mCalendar.set(Calendar.MONTH, it) }
        mYear?.let { mCalendar.set(Calendar.YEAR, it) }
        mDay?.let { mCalendar.set(Calendar.DAY_OF_MONTH, it) }
        mHour?.let { mCalendar.set(Calendar.HOUR_OF_DAY, it) }
        mMinute?.let { mCalendar.set(Calendar.MINUTE, it) }
        mCalendar.set(Calendar.SECOND, 0)

        val selectedTimestamp = mCalendar.timeInMillis

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        try {
            alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTimestamp, pendingIntent)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        finish()
    }

}