package com.simplx.apps.alarmapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.simplx.apps.alarmapp.pojo.EntityClass


@Dao
interface EventDao {

    @Insert
    fun insertAll(entityClass: EntityClass)

    @Query("SELECT * FROM myTable")
    fun getAllData(): LiveData<List<EntityClass>>

}