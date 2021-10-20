package com.refactory.android.todoapp.data.local.entity

import android.os.Parcelable
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "taskTable")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = BaseColumns._ID) var id: Long? = null,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "desc") var desc: String = "",
    @ColumnInfo(name = "date") var date: String = "",
    @ColumnInfo(name = "startTime") var startTime: String = "",
    @ColumnInfo(name = "endTime") var endTime: String = ""
): Parcelable