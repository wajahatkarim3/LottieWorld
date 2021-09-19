package com.wajahatkarim3.lottieworld.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class AnimatorModel(
    @PrimaryKey(autoGenerate = true)
    var animatorId: Long = 0,

    @ColumnInfo(name = "animatorName")
    val name: String,
    val avatarUrl: String,

    var username: String? = null
): Parcelable