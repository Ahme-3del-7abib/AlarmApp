package com.simplx.apps.alarmapp.repository

import androidx.lifecycle.LiveData
import com.simplx.apps.alarmapp.pojo.EntityClass
import com.simplx.apps.alarmapp.room.EventDao


class EventRepository(private val eventDao: EventDao) {

    suspend fun insertAll(entityClass: EntityClass) {
        eventDao.insertAll(entityClass)
    }

    fun getAllData(): LiveData<List<EntityClass>> {
        return eventDao.getAllData()
    }
}