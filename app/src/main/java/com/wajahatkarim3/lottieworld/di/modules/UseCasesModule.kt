package com.wajahatkarim3.lottieworld.di.modules

import com.wajahatkarim3.lottieworld.data.repository.NewsRepository
import com.wajahatkarim3.lottieworld.data.usecases.FetchAllBlogsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideFetchAllBlogsUseCase(
        repository: NewsRepository
    ): FetchAllBlogsUseCase {
        return FetchAllBlogsUseCase(repository, Dispatchers.IO)
    }
}