package me.brunofelix.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.brunofelix.news.feature.domain.repository.NewsLocalRepository
import me.brunofelix.news.feature.domain.repository.NewsRemoteRepository
import me.brunofelix.news.feature.domain.usecase.GetLocalNewsUseCase
import me.brunofelix.news.feature.domain.usecase.GetRemoteNewsUseCase
import me.brunofelix.news.feature.domain.usecase.SaveArticleUseCase
import me.brunofelix.news.feature.domain.usecase.SearchNewsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRemoteNewsUseCase(repository: NewsRemoteRepository): GetRemoteNewsUseCase {
        return GetRemoteNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocalNewsUseCase(repository: NewsLocalRepository): GetLocalNewsUseCase {
        return GetLocalNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveArticleUseCase(repository: NewsLocalRepository): SaveArticleUseCase {
        return SaveArticleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(repository: NewsRemoteRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }
}