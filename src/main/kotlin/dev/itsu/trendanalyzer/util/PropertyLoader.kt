package dev.itsu.trendanalyzer.util

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』の単語から炎上中の単語を抽出する～」
 *
 * @author 野牧　樹　(2130)
 */

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

object PropertyLoader {

    private val properties = Properties()

    fun loadProperties(): MutableMap<String, String> {
        properties.load(InputStreamReader(FileInputStream(File("./data/settings.properties")), StandardCharsets.UTF_8))

        return mutableMapOf(
                Pair("OAuthConsumerKey", properties.getProperty("OAuthConsumerKey")),
                Pair("OAuthConsumerSecret", properties.getProperty("OAuthConsumerSecret")),
                Pair("OAuthAccessToken", properties.getProperty("OAuthAccessToken")),
                Pair("OAuthAccessSecret", properties.getProperty("OAuthAccessSecret"))
        )
    }

}