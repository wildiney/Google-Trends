package com.wildiney.google_trends.repository

import com.wildiney.google_trends.parsers.RssParser
import com.wildiney.google_trends.models.RssFeed
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrendsRepository {
    private val client = HttpClient()
    private val rssParser = RssParser()

    suspend fun getTrends(): RssFeed = withContext(Dispatchers.IO){
        val response = client.get("https://trends.google.com.br/trending/rss?geo=BR")
        val xml = response.bodyAsText()
        rssParser.parse(xml)

    }
}