package com.refactory.android.todoapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.refactory.android.todoapp.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun TaskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, "task.db").allowMainThreadQueries().build()
            }
    }
}