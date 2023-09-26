package com.mayuresh.nytimes.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mayuresh.nytimes.R
import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.presentation.ui.theme.NYTimesShapes
import com.mayuresh.nytimes.presentation.ui.theme.NYTimesTypography
import com.mayuresh.nytimes.presentation.viewmodel.ArticleViewModel

/**
 * This composable is responsible for list view representation of articles
 * @param viewModel
 * @param onArticleClick
 */
@Composable
fun ArticleListScreenComponent(
    viewModel: ArticleViewModel,
    onArticleClick: () -> Unit
) {
    val articles by viewModel.articleList.collectAsState()
    val isInternetAvailable by viewModel.isInternetAvailable.collectAsState()
    val isError by viewModel.isError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        if (articles.isNotEmpty()) {
            LazyColumn {
                items(articles) { article ->
                    ArticleListItem(article, {
                        viewModel.setCurrentArticle(it)
                        onArticleClick.invoke()
                    })
                }
            }
        } else if (!isInternetAvailable || isError) {
            Text(
                text = if (!isError) stringResource(id = R.string.no_articles_error_message) else stringResource(
                    id = R.string.no_articles_api_error_message
                ),
                style = NYTimesTypography.labelSmall,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * This composable is used for article list item view
 * @param article,
 * @param selectedArticle
 */
@Composable
fun ArticleListItem(
    article: ArticleModel, selectedArticle: (ArticleModel) -> Unit
) {
    Card(
        shape = NYTimesShapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selectedArticle.invoke(article)
            }
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val placeholderImage = R.drawable.thumbnail
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.smallImageUrl)
                    .placeholder(placeholderImage)
                    .build(),
                error = painterResource(placeholderImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(50.dp)
                    .width(50.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = NYTimesTypography.labelMedium,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.byLine,
                    color = Color.DarkGray,
                    maxLines = 1,
                    style = NYTimesTypography.labelMedium
                )
                Row {

                    Text(
                        text = article.source,
                        color = Color.DarkGray,
                        maxLines = 1,
                        modifier = Modifier.weight(1f),
                        style = NYTimesTypography.labelMedium
                    )

                    Text(
                        text = article.publishDate,
                        color = Color.DarkGray,
                        maxLines = 1,
                        style = NYTimesTypography.labelMedium
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

