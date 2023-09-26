package com.mayuresh.nytimes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.nytimes.MainCoroutineRule
import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.domain.usecase.GetMostPopularArticleUseCase
import com.mayuresh.nytimes.utils.NetworkHelper
import com.mayuresh.nytimes.utils.Response
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ArticleViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var getMostPopularArticleUseCase: GetMostPopularArticleUseCase

    @RelaxedMockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewModel: ArticleViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        viewModel = ArticleViewModel(
            getMostPopularArticleUseCase = getMostPopularArticleUseCase,
            networkHelper = networkHelper,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get article list success test`() = runTest {
        // Given
        val period = 7
        val isNetworkAvailable = true
        val articleList = mutableListOf<ArticleModel>()
        articleList.add(ArticleModel("Test"))
        val responseFlow: Flow<Response<List<ArticleModel>>> = flow {
            emit(Response.Success(articleList))
        }

        every {
            networkHelper.isNetworkConnected()
        } returns isNetworkAvailable

        coEvery {
            getMostPopularArticleUseCase.getMostPopularArticleList(period)
        } returns responseFlow

        // When
        viewModel.fetchArticleList()
        // Then
        responseFlow.collectLatest {
            Assert.assertEquals("Test", viewModel.articleList.value.get(0).title)
        }
    }

    @Test
    fun `get article list error test`() = runTest {
        // Given
        val period = 7
        val isNetworkAvailable = true
        val responseFlow: Flow<Response<List<ArticleModel>>> = flow {
            emit(Response.Error(code = 400, "Test"))
        }

        coEvery {
            networkHelper.isNetworkConnected()
        } returns isNetworkAvailable

        coEvery {
            getMostPopularArticleUseCase.getMostPopularArticleList(period)
        } returns responseFlow

        // When
        viewModel.fetchArticleList()
        // Then
        responseFlow.collectLatest {
            Assert.assertEquals(true, viewModel.isError.value)
        }
    }

    @Test
    fun `get article list exception test`() = runTest {
        // Given
        val period = 7
        val isNetworkAvailable = true
        val responseFlow: Flow<Response<List<ArticleModel>>> = flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }

        coEvery {
            networkHelper.isNetworkConnected()
        } returns isNetworkAvailable

        coEvery {
            getMostPopularArticleUseCase.getMostPopularArticleList(period)
        } returns flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }

        // When
        viewModel.fetchArticleList()
        // Then
        responseFlow.collectLatest {
            Assert.assertEquals(true, viewModel.isError.value)
        }
    }

    @Test
    fun `set current article`() = runTest {
        // Given
        val articleModel = ArticleModel(title = "Test1")

        // When
        viewModel.setCurrentArticle(article = articleModel)

        // Then
        Assert.assertEquals("Test1", viewModel.getCurrentArticle().title)
    }

}