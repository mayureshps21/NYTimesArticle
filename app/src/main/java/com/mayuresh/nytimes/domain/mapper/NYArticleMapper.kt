package com.mayuresh.nytimes.domain.mapper

import com.mayuresh.nytimes.data.model.ArticleModel
import com.mayuresh.nytimes.data.model.Result
import javax.inject.Inject

/**
 * This class is used for map the server response to desired response.
 */
class NYArticleMapper @Inject constructor() : Mapper<List<Result>, List<ArticleModel>> {

    override fun mapFrom(fromList: List<Result>): List<ArticleModel> {
        val articleList = mutableListOf<ArticleModel>()
        for (from in fromList){
            articleList.add(ArticleModel(
                title = from.title,
                byLine = from.byline,
                publishDate = from.publishedDate,
                descriptionAbstract = from.abstract,
                webpageURL = from.url,
                source = from.source,
                smallImageUrl = if (from.media.isNotEmpty()) from.media.get(0).mediaMetadata.get(0).url else "",
                largeImageUrl = if (from.media.isNotEmpty()) from.media.get(0).mediaMetadata.get(from.media.get(0).mediaMetadata.size-1).url else ""
            ))
        }
        return articleList
    }
}