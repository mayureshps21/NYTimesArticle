package com.mayuresh.nytimes.domain.usecase

import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.data.model.ArticleResponse
import com.mayuresh.nytimes.domain.mapper.NYArticleMapper
import com.mayuresh.nytimes.domain.repository.ArticleRepository
import com.mayuresh.nytimes.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This use case is used for fetch data from repository and pass to view model
 * @param articleRepository
 */
class GetMostPopularArticleUseCaseImpl @Inject constructor(private val articleRepository: ArticleRepository) :
    GetMostPopularArticleUseCase {

    override suspend fun getMostPopularArticleList(period: Int): Flow<Response<List<ArticleModel>>> =
        flow {
            val response = articleRepository.getArticleList(period)
            if (response.isSuccessful) {
                val articleResponse = response.body() as ArticleResponse
                val result = NYArticleMapper().mapFrom(articleResponse.results)
                emit(Response.Success(result))
            } else {
                emit(Response.Error(code = response.code(), message = response.message()))
            }
        }

}