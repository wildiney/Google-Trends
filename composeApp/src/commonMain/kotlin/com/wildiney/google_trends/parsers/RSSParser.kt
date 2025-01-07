package com.wildiney.google_trends.parsers

import com.wildiney.google_trends.models.RssFeed
import com.wildiney.google_trends.models.RssItem
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class RssParser {
    fun parse(xml: String): RssFeed {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(xml.byteInputStream())

        val items = document.getElementsByTagName("item")
        val rssList = mutableListOf<RssItem>()

        for (i in 0 until items.length) {
            val element = items.item(i) as Element
            rssList.add(
                RssItem(
                    title = element.getElementsByTagName("title").item(0).textContent,
                    traffic = element.getElementsByTagName("ht:approx_traffic").item(0).textContent,
                    link = element.getElementsByTagName("link").item(0).textContent,
                    pubDate = element.getElementsByTagName("pubDate").item(0).textContent,
                    description = element.getElementsByTagName("description").item(0).textContent,
                    picture = element.getElementsByTagName("ht:picture").item(0).textContent
                )
            )
        }

        return RssFeed(rssList)
    }
}