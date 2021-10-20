package com.refactory.android.todoapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.refactory.android.todoapp.data.local.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskTable")
    fun getAllTask(): List<TaskEntity>

    @Query("SELECT * FROM taskTable WHERE date = :date")
    fun getTaskToday(date: String): List<TaskEntity>

    @Insert(onConflict = REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    @Query("DELETE FROM taskTable WHERE _id = :id")
    fun deleteTask(id: Long)

    @Update
    fun updateTask(taskEntity: TaskEntity)
}