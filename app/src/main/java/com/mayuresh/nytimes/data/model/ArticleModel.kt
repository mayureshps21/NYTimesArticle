package com.mayuresh.nytimes.data.model

/**
 * This is a data model class for passing required information to view
 * @param title // article title
 * @param byLine // who is writer of article
 * @param publishDate // published date
 * @param descriptionAbstract // short description
 * @param webpageURL // article webpage link
 * @param source // source of article
 * @param smallImageUrl // small size image url for article
 * @param largeImageUrl // large size image url for article
 */
data class ArticleModel(
    var title: String = "",
    var byLine: String = "",
    var publishDate: String = "",
    var descriptionAbstract: String ="",
    var webpageURL:String = "",
    var source:String ="",
    var smallImageUrl:String ="",
    var largeImageUrl:String = ""
)
