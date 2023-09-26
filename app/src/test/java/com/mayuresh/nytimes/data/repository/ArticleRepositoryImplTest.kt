package com.mayuresh.nytimes.data.repository

import com.mayuresh.nytimes.TestData
import com.mayuresh.nytimes.data.service.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ArticleRepositoryImplTest {

    @MockK
    lateinit var apiService: ApiService

    private lateinit var articleRepositoryImpl: ArticleRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        articleRepositoryImpl = ArticleRepositoryImpl(apiService)
    }

    @Test
    fun `Should validate api service call`() = runTest {
        // Given
        val apiResponse = Response.success(TestData.getArticleResponseData())

        // When
        coEvery {
            apiService.getArticleList(7)
        } returns apiResponse

        articleRepositoryImpl.getArticleList(7)
        // Then
        coVerify(atLeast = 1) { apiService.getArticleList(7, "b61Rg7v1AwLS7FM9z6Ri2dhAIJLKvfsX") }
    }
}