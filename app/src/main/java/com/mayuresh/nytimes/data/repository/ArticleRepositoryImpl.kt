package com.mayuresh.nytimes.data.repository

import com.mayuresh.nytimes.data.model.ArticleResponse
import com.mayuresh.nytimes.data.service.ApiService
import com.mayuresh.nytimes.domain.repository.ArticleRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * This class is Implementation class of Article Repository, This class will collect
 * results from retrofit service and pass the data to Use case
 * @param apiService // retrofit service class
 */
class ArticleRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ArticleRepository {

    override suspend fun getArticleList(period: Int): Response<ArticleResponse> =
        apiService.getArticleList(period)

}