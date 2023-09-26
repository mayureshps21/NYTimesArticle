package com.mayuresh.nytimes.data.service

import com.mayuresh.nytimes.BuildConfig
import com.mayuresh.nytimes.data.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/svc/mostpopular/v2/viewed/{period}.json?")
    suspend fun getArticleList(
        @Path("period") period: Int,
        @Query("api-key") key: String = BuildConfig.NYTimesApiKey
    ): Response<ArticleResponse>

}