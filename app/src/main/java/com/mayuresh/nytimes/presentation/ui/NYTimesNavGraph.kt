package com.mayuresh.nytimes.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayuresh.nytimes.presentation.ui.components.ArticleDetailScreenComponent
import com.mayuresh.nytimes.presentation.ui.components.ArticleListScreenComponent
import com.mayuresh.nytimes.presentation.viewmodel.ArticleViewModel

@Composable
fun NYTimesNavGraph(
    isExpandedScreen: Boolean,
    viewModel: ArticleViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NYTimesDestinations.ARTICLE_LIST_ROUTE,
) {
    val activity = (LocalContext.current as Activity)
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = NYTimesDestinations.ARTICLE_LIST_ROUTE,
        ) {
            ArticleListScreenComponent(viewModel = viewModel, onArticleClick = {
                navController.navigate(route = NYTimesDestinations.ARTICLE_DETAIL_ROUTE)
            })
        }
        composable(route = NYTimesDestinations.ARTICLE_DETAIL_ROUTE) {
            ArticleDetailScreenComponent(article = viewModel.getCurrentArticle(), onReadMoreButtonClick = {
                val uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activity.startActivity(intent)
            })
        }
    }
}
