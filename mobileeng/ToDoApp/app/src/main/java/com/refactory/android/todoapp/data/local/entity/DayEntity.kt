package com.refactory.android.todoapp.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayEntity (
    var id: Long? = null,
    var date: Int = 0,
    var infoLight: Boolean = false
): Parcelable