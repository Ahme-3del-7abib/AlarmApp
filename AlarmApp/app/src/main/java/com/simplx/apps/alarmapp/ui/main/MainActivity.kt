package com.simplx.apps.alarmapp.ui.main

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplx.apps.alarmapp.R
import com.simplx.apps.alarmapp.factory.ViewModelFactory
import com.simplx.apps.alarmapp.model.EventViewModel
import com.simplx.apps.alarmapp.ui.add.AddAlarmActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this.application))
                .get(EventViewModel::class.java)
        adapter = EventAdapter(this)

        setView()

        btn_createEvent.setOnClickListener {
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }
    }

    private fun setView() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        viewModel.getAllData()?.observe(this, Observer {
            adapter.setList(it)
            recyclerview.adapter = adapter
        })
    }
}