package com.mayuresh.nytimes.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mayuresh.nytimes.R
import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.presentation.ui.theme.NYTimesTypography

/**
 * This is Articles detail screen
 * @param article
 * @param onReadMoreButtonClick
 */
@Composable
fun ArticleDetailScreenComponent(
    article: ArticleModel,
    onReadMoreButtonClick: (String) -> Unit
) {
    val placeholderImage = R.drawable.thumbnail

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 10.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.largeImageUrl)
                .placeholder(placeholderImage)
                .build(),
            contentDescription = null,
            error = painterResource(id = placeholderImage),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = article.byLine,
            maxLines = 1,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            style = NYTimesTypography.labelSmall
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.publishDate,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            color = Color.DarkGray,
            textAlign = TextAlign.End,
            style = NYTimesTypography.labelSmall

        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.descriptionAbstract,
            modifier = Modifier.fillMaxWidth(),
            color = Color.DarkGray,
            style = NYTimesTypography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Read More",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onReadMoreButtonClick.invoke(article.webpageURL) },
            maxLines = 1,
            color = Color.Blue,
            textAlign = TextAlign.End,
            style = NYTimesTypography.labelSmall
        )
    }
}