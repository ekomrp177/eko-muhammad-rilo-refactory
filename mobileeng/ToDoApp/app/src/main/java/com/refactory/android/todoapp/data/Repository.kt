package com.refactory.android.todoapp.data

import com.refactory.android.todoapp.data.local.LocalDataSource
import com.refactory.android.todoapp.data.local.entity.TaskEntity

class Repository private constructor(
    private val localDataSource: LocalDataSource
): DataSource{
    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(localDataSource: LocalDataSource): Repository =
            instance?: synchronized(this) {
                instance?: Repository(localDataSource)
            }
    }

    override fun getAllTask(): List<TaskEntity> {
        return localDataSource.getAllTask()
    }

    override fun insertTask(taskEntity: TaskEntity) {
        localDataSource.insertTask(taskEntity)
    }

    override fun deleteTask(id: Long) {
        localDataSource.deleteTask(id)
    }

    override fun updateTask(taskEntity: TaskEntity) {
        localDataSource.updateTask(taskEntity)
    }

    override fun getTaskToday(date: String): List<TaskEntity> {
        return localDataSource.getTaskToday(date)
    }
}