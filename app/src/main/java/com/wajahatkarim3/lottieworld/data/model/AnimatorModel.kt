package com.wajahatkarim3.lottieworld.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimatorModel(
    @PrimaryKey(autoGenerate = true)
    var animatorId: Long = 0,

    @ColumnInfo(name = "animatorName")
    val name: String,
    val avatarUrl: String
)