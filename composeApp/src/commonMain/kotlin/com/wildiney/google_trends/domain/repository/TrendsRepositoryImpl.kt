package com.wildiney.google_trends.domain.repository

import com.wildiney.google_trends.data.TrendsRepository
import com.wildiney.google_trends.domain.parsers.RssParser
import com.wildiney.google_trends.domain.model.RssFeed
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TrendsRepositoryImpl(
    private val httpClient: HttpClient = HttpClient()
):TrendsRepository {
    private val rssParser = RssParser()

    override suspend fun getTrends(): RssFeed = withContext(Dispatchers.IO){
        try {
            val response = httpClient.get("https://trends.google.com.br/trending/rss?geo=BR")
            val xml = response.bodyAsText()
            rssParser.parse(xml)
        } catch (e: IOException){
            throw RuntimeException("Failed to fetch trends", e)
        }
    }

    override fun close() {
        httpClient.close()
    }
}