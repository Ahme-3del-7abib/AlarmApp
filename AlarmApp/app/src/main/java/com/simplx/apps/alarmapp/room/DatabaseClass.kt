package com.simplx.apps.alarmapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simplx.apps.alarmapp.pojo.EntityClass


@Database(entities = [EntityClass::class], version = 1)
abstract class DatabaseClass : RoomDatabase() {


    abstract fun eventDao(): EventDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseClass? = null

        fun getInstance(context: Context): DatabaseClass {

            val temInstance =
                INSTANCE

            if (temInstance != null) {
                return temInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseClass::class.java,
                    "application_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}