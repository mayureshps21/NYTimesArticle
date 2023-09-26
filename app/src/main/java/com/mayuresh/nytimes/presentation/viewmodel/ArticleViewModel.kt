package com.mayuresh.nytimes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.domain.usecase.GetMostPopularArticleUseCase
import com.mayuresh.nytimes.utils.NetworkHelper
import com.mayuresh.nytimes.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is responsible for pass article list to composable functions
 * @param getMostPopularArticleUseCase
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(
    val getMostPopularArticleUseCase: GetMostPopularArticleUseCase,
    val networkHelper: NetworkHelper
) : ViewModel() {

    // how much older article in days
    var period: Int = 7

    val articleList: StateFlow<List<ArticleModel>> get() = _articleList.asStateFlow()
    private var _articleList = MutableStateFlow(listOf<ArticleModel>())

    lateinit private var dataItem: ArticleModel

    var isInternetAvailable = MutableStateFlow(true)

    var isError = MutableStateFlow(false)

    init {
        fetchArticleList()
    }

    /**
     * This method is used to fetch article list
     */
    fun fetchArticleList() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                getMostPopularArticleUseCase.getMostPopularArticleList(period)
                    .collect() { response ->
                        when (response) {
                            is Response.Success -> {
                                _articleList.value = response.data
                                isError.value = false
                            }

                            is Response.Error -> {
                                isError.value = true
                            }
                            else -> {
                                isError.value = true
                            }
                        }
                    }
                isInternetAvailable.value = true
            } else {
                isInternetAvailable.value = false
            }
        }
    }

    /**
     * Set detail article details
     * @param item
     */
    fun setCurrentArticle(article: ArticleModel) {
        dataItem = article
    }

    /**
     * get currently selected data item details
     */
    fun getCurrentArticle(): ArticleModel {
        return dataItem
    }
}