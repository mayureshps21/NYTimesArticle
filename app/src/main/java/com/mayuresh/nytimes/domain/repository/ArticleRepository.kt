package com.mayuresh.nytimes.domain.repository

import com.mayuresh.nytimes.data.model.ArticleResponse
import retrofit2.Response

/**
 * This interface is responsible for collecting results from retrofit service and pass
 * to use case.
 */
interface ArticleRepository {
    /**
     * This method is used for fetch article information from api service.
     * @param period // how many days older articles to be shown
     */
    suspend fun getArticleList(period: Int): Response<ArticleResponse>
}