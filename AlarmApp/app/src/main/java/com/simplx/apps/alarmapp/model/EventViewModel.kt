package com.simplx.apps.alarmapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.simplx.apps.alarmapp.pojo.EntityClass
import com.simplx.apps.alarmapp.repository.EventRepository
import com.simplx.apps.alarmapp.room.DatabaseClass
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    var eventList: LiveData<List<EntityClass>>? = null
    private var repository: EventRepository

    init {
        val database = DatabaseClass.getInstance(application).eventDao()
        repository = EventRepository(database)
        eventList = repository.getAllData()
    }
    
    fun getAllData(): LiveData<List<EntityClass>>? {
        return eventList
    }

    fun insertEvent(entityClass: EntityClass) = viewModelScope.launch(IO) {
        repository.insertAll(entityClass)
    }
}