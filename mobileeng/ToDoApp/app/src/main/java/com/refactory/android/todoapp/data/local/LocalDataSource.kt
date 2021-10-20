package com.refactory.android.todoapp.data.local

import com.refactory.android.todoapp.data.local.entity.TaskEntity
import com.refactory.android.todoapp.data.local.room.TaskDao

class LocalDataSource constructor(private val taskDao: TaskDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(taskDao: TaskDao): LocalDataSource {
            return INSTANCE?: LocalDataSource(taskDao)
        }
    }

    fun getAllTask(): List<TaskEntity> = taskDao.getAllTask()
    fun insertTask(taskEntity: TaskEntity) = taskDao.insertTask(taskEntity)
    fun deleteTask(id: Long) = taskDao.deleteTask(id)
    fun updateTask(taskEntity: TaskEntity) = taskDao.updateTask(taskEntity)
    fun getTaskToday(date: String): List<TaskEntity> = taskDao.getTaskToday(date)
}