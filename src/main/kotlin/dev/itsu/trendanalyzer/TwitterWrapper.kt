package dev.itsu.trendanalyzer

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する～」
 *
 * @author 野牧　樹　(2130)
 */

import dev.itsu.trendanalyzer.util.PropertyLoader
import twitter4j.*
import twitter4j.conf.ConfigurationBuilder
import java.util.stream.Collectors

object TwitterWrapper {

    private const val COUNTRY_JAPAN = "Japan"

    private lateinit var twitter: Twitter
    var authorized: Boolean = false

    fun authorize() {
        val properties = PropertyLoader.loadProperties()
        val configurationBuilder = ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(properties["OAuthConsumerKey"]!!)
                .setOAuthConsumerSecret(properties["OAuthConsumerSecret"]!!)
                .setOAuthAccessToken(properties["OAuthAccessToken"]!!)
                .setOAuthAccessTokenSecret(properties["OAuthAccessSecret"]!!)

        authorized = true
        twitter = TwitterFactory(configurationBuilder.build()).instance
    }

    fun getJapaneseTrend(): MutableList<Trend> {
        val locations = twitter.trends().availableTrends
                .stream()
                .filter{ it.name == COUNTRY_JAPAN }
                .collect(Collectors.toList())

        if (locations.size != 0) {
            val result = twitter.trends().getPlaceTrends(locations[0].woeid).trends.toMutableList()
            result.sortBy { it.tweetVolume }
            return result
        }

        return mutableListOf()
    }

    fun search(keyword: String, count: Int): MutableList<Status> {
        val query = Query()
                .query(keyword)
                .count(count)
        return twitter.search().search(query).tweets
    }

}