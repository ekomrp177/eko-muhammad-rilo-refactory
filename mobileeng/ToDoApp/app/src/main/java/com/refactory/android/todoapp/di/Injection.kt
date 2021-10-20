package com.refactory.android.todoapp.di

import android.content.Context
import com.refactory.android.todoapp.data.Repository
import com.refactory.android.todoapp.data.local.LocalDataSource
import com.refactory.android.todoapp.data.local.room.TaskDatabase

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = TaskDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.TaskDao())
        return Repository.getInstance(localDataSource)
    }
}