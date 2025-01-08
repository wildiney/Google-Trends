package com.wildiney.google_trends.domain.model

data class RssItem(
    val title: String,
    val traffic:String,
    val link: String,
    val pubDate: String,
    val description: String,
    val picture: String
)

data class RssFeed(
    val items: List<RssItem>
)