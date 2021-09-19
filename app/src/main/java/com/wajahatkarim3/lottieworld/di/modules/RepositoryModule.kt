package com.wajahatkarim3.lottieworld.di.modules

import android.content.Context
import com.wajahatkarim3.lottieworld.data.local.AnimationsDao
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import com.wajahatkarim3.lottieworld.data.repository.*
import com.wajahatkarim3.lottieworld.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideNewsRepository(
        apiService: LottieFilesApiService
    ): NewsRepository {
        return DefaultNewsRepository(
            apiService,
            Dispatchers.IO
        )
    }

    @ViewModelScoped
    @Provides
    fun provideAnimationsRepository(
        apiService: LottieFilesApiService,
        animationsDao: AnimationsDao
    ): AnimationsRepository {
        return DefaultAnimationsRepository(
            apiService,
            animationsDao,
            Dispatchers.IO
        )
    }

    @ViewModelScoped
    @Provides
    fun providePreferencesRepository(
        @ApplicationContext context: Context
    ): PreferencesRepository {
        return DefaultPreferencesRepository(context)
    }

    @ViewModelScoped
    @Provides
    fun provideUsersRepository(
        preferencesRepository: PreferencesRepository,
        stringUtils: StringUtils
    ): UsersRepository {
        return DefaultUsersRepository(
            Dispatchers.IO,
            preferencesRepository,
            stringUtils
        )
    }
}