package com.mayuresh.nytimes.domain.usecase

import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.utils.Response
import kotlinx.coroutines.flow.Flow

/**
 * This use case interface is responsible for get and filter the data which is required for view
 */
interface GetMostPopularArticleUseCase {
    /**
     * This method is responsible for collecting data from repository and pass Flow to view model
     * @param period // number of days old articles
     */
    suspend fun getMostPopularArticleList(period:Int): Flow<Response<List<ArticleModel>>>
}