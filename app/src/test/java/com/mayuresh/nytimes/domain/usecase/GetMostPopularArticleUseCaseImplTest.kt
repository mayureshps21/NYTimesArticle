package com.mayuresh.nytimes.domain.usecase

import com.mayuresh.nytimes.TestData
import com.mayuresh.nytimes.data.model.ArticleResponse
import com.mayuresh.nytimes.domain.repository.ArticleRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetMostPopularArticleUseCaseImplTest {
    @MockK
    private lateinit var articleRepository: ArticleRepository

    private lateinit var getMostPopularArticleUseCaseImpl: GetMostPopularArticleUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMostPopularArticleUseCaseImpl = GetMostPopularArticleUseCaseImpl(articleRepository)
    }

    @Test
    fun `Should validate and pass article response success test`() = runTest {
        val response: Response<ArticleResponse> = TestData.getArticleResponseRetrofit(false)
        // Given
        coEvery { articleRepository.getArticleList(7) } returns response
        // When
        val responseFlow = getMostPopularArticleUseCaseImpl.getMostPopularArticleList(7)
        // Then
        responseFlow.collectLatest { data ->
            when (data) {
                is com.mayuresh.nytimes.utils.Response.Success -> {
                    Assert.assertEquals("Test", data.data.get(0).descriptionAbstract)
                }

                else -> {
                    Assert.assertEquals(true, false)
                }
            }
        }
    }

    @Test
    fun `Should validate and pass article response error test`() = runTest {
        val response: Response<ArticleResponse> = TestData.getArticleResponseRetrofit(true)
        // Given
        coEvery { articleRepository.getArticleList(7) } returns response
        // When
        val responseFlow = getMostPopularArticleUseCaseImpl.getMostPopularArticleList(7)
        // Then
        responseFlow.collectLatest { data ->
            when (data) {
                is com.mayuresh.nytimes.utils.Response.Error -> {
                    Assert.assertEquals(400, data.code)
                }

                else -> {
                    Assert.assertEquals(true, false)
                }
            }
        }
    }
}