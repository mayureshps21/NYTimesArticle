package com.mayuresh.nytimes.di

import com.mayuresh.nytimes.data.repository.ArticleRepositoryImpl
import com.mayuresh.nytimes.domain.repository.ArticleRepository
import com.mayuresh.nytimes.domain.usecase.GetMostPopularArticleUseCase
import com.mayuresh.nytimes.domain.usecase.GetMostPopularArticleUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This is dependency injection module class for dagger hilt
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Provide the [ArticleRepository] instance
     *
     * @param ArticleRepositoryImpl this [ArticleRepository] class is implemented in the [ArticleRepositoryImpl] interface
     * @return the [ArticleRepository]  instance
     */
    @Binds
    abstract fun bindArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository

    /**
     * Provide the [GetMostPopularArticleUseCase] instance
     *
     * @param GetMostPopularArticleUseCaseImpl this [ArticleRepository] class is implemented in the [GetMostPopularArticleUseCaseImpl] interface
     * @return the [GetMostPopularArticleUseCase]  instance
     */
    @Binds
    abstract fun bindArticleUseCase(
        getMostPopularArticleUseCaseImpl: GetMostPopularArticleUseCaseImpl
    ): GetMostPopularArticleUseCase
}