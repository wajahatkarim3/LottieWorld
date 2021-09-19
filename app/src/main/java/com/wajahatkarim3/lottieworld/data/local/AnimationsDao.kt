package com.wajahatkarim3.lottieworld.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimationsDao {

    @Query("SELECT * FROM AnimationModel")
    fun queryAllAsync(): Flow<List<AnimationModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(animations: List<AnimationModel>)

    @Query("SELECT * FROM AnimationModel")
    fun queryAll(): List<AnimationModel>

    @Query("DELETE FROM AnimationModel")
    fun clear()
}