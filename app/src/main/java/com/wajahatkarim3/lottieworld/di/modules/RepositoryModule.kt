package com.wajahatkarim3.lottieworld.di.modules

import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import com.wajahatkarim3.lottieworld.data.repository.DefaultNewsRepository
import com.wajahatkarim3.lottieworld.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
}