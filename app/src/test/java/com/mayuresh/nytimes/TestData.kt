package com.mayuresh.nytimes

import com.mayuresh.nytimes.data.model.ArticleResponse
import com.mayuresh.nytimes.data.model.Media
import com.mayuresh.nytimes.data.model.MediaMetadata
import com.mayuresh.nytimes.data.model.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object TestData {
    fun getArticleResponseRetrofit(isError: Boolean): Response<ArticleResponse> {
        if (isError) {
            return Response.error(
                400, "{\"key\":[\"somestuff\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        } else {
            return Response.success(getArticleResponseData())
        }
    }

    fun getArticleResponseData(): ArticleResponse {
        val media = Media(
            approvedForSyndication = 1, caption = "Test", copyright = "", mediaMetadata = listOf(
                MediaMetadata(format = "", height = 2, url = "", width = 3)
            ), subtype = "", type = ""
        )
        val result = Result(
            abstract = "Test",
            adxKeywords = "",
            assetId = 1,
            byline = "By Mayuresh",
            column = Any(),
            desFacet = listOf(),
            etaId = 1,
            geoFacet = listOf(),
            id = 1,
            media = listOf(media),
            nytdsection = "",
            orgFacet = listOf(),
            perFacet = listOf(),
            publishedDate = "",
            section = "",
            subsection = "",
            source = "",
            title = "",
            type = "",
            updated = "",
            url = "",
            uri = ""
        )

        return ArticleResponse(numResults = 1, results = mutableListOf(result))

    }
}