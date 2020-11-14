package com.simplx.apps.alarmapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplx.apps.alarmapp.R
import com.simplx.apps.alarmapp.pojo.EntityClass


class EventAdapter(private val context: Context) :
    RecyclerView.Adapter<EventAdapter.AlarmViewHolder>() {

    var entityClasses: List<EntityClass> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.alarm_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return entityClasses.size
    }

    fun setList(entityClasses: List<EntityClass>) {
        this.entityClasses = entityClasses
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.eventText?.text = entityClasses.get(position).event_name

        holder.timeAndDateText?.text =
            entityClasses[position].event_date + " " + entityClasses[position].event_time;
    }

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var eventText: TextView? = null
        var timeAndDateText: TextView? = null
        var topLayout: LinearLayout? = null

        init {
            eventText = itemView.findViewById(R.id.event)
            timeAndDateText = itemView.findViewById(R.id.time_and_date)
            topLayout = itemView.findViewById(R.id.toplayout)
        }
    }

}

