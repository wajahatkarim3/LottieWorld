package com.wajahatkarim3.lottieworld.di.modules

import android.content.Context
import com.wajahatkarim3.lottieworld.data.local.AnimationsDao
import com.wajahatkarim3.lottieworld.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.init(context)
    }

    @Singleton
    @Provides
    fun provideAnimationsDao(roomDB: AppDatabase): AnimationsDao {
        return roomDB.animationsDao()
    }
}