package com.wildiney.google_trends.data

import com.wildiney.google_trends.domain.model.RssFeed

interface TrendsRepository {
    suspend fun getTrends():RssFeed
    fun close()
}