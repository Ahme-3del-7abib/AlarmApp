package com.simplx.apps.alarmapp.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myTable")
data class EntityClass(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "event_name")
    val event_name: String,

    @ColumnInfo(name = "event_date")
    val event_date: String,

    @ColumnInfo(name = "event_time")
    val event_time: String

)