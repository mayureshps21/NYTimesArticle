package com.mayuresh.nytimes.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.mayuresh.nytimes.R
import com.mayuresh.nytimes.presentation.ui.components.AppToolbar
import com.mayuresh.nytimes.presentation.ui.theme.NYTimesTheme
import com.mayuresh.nytimes.presentation.viewmodel.ArticleViewModel

@Composable
fun NYTimesApp(
    widthSizeClass: WindowWidthSizeClass,
    viewModel: ArticleViewModel
) {
    NYTimesTheme {
        val navController = rememberNavController()
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val isShowBackButton  = remember { mutableStateOf(false) }
        // Add a destination changed listener
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            // Perform actions based on destination change
            when (destination.route) {
                NYTimesDestinations.ARTICLE_LIST_ROUTE -> {
                    isShowBackButton.value = false
                }
                else -> {
                    isShowBackButton.value = true
                }
            }
        }
        Scaffold(
            topBar = {
                AppToolbar(
                    title = stringResource(id = R.string.title),
                    onUpClick = {
                        navController.navigateUp()
                    },
                    isShowBackButton = isShowBackButton.value
                )

            },
            content = { paddingValues ->
                NYTimesNavGraph(
                    isExpandedScreen = isExpandedScreen,
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            })

    }
}

