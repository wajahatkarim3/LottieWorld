package com.wajahatkarim3.lottieworld.di.modules

import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import com.wajahatkarim3.lottieworld.data.repository.NewsRepository
import com.wajahatkarim3.lottieworld.data.usecases.FetchAllBlogsUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchAnimationsUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchFeaturedAnimatorsUseCase
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

    @ViewModelScoped
    @Provides
    fun provideFetchFeaturedAnimatorsUseCase(
        repository: AnimationsRepository
    ): FetchFeaturedAnimatorsUseCase {
        return FetchFeaturedAnimatorsUseCase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideFetchFeaturedAnimationsUseCase(
        repository: AnimationsRepository
    ): FetchAnimationsUseCase {
        return FetchAnimationsUseCase(repository, Dispatchers.IO)
    }
}