package dev.itsu.trendanalyzer.core

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する～」
 *
 * @author 野牧　樹　(2130)
 */

import dev.itsu.trendanalyzer.TwitterWrapper
import dev.itsu.trendanalyzer.dictionary.Dictionary
import dev.itsu.trendanalyzer.dictionary.Word
import dev.itsu.trendanalyzer.mecab.MeCabManager
import dev.itsu.trendanalyzer.util.Utils

object MainLogic {

    fun start(TREND_COUNT: Int, TWEET_COUNT: Int) {
        if (!TwitterWrapper.authorized) TwitterWrapper.authorize()

        println("Started analyzing with TrendCount($TREND_COUNT) TweetCount($TWEET_COUNT)")

        val trends = TwitterWrapper.getJapaneseTrend().sortedBy { it.tweetVolume }.reversed()
        val result = mutableListOf<AnalyzedTrend>()
        var trendIndex = 0

        while (trendIndex < TREND_COUNT) {
            val trendName = trends[trendIndex].name
            val timeStamp = System.currentTimeMillis()
            var trendEvaluation = 0.0
            var tweetCount = 0

            TwitterWrapper.search(trendName, TWEET_COUNT).forEach {
                val deAssembledWords = MeCabManager.textToWords(it.text)
                var tweetEvaluation = 0.0
                var analyzedCount = 0

                deAssembledWords.forEach { word ->
                    val tempWord: Word? = Dictionary.getWord(word.endForm)
                    if (tempWord != null) {
                        tweetEvaluation += tempWord.evaluation
                        analyzedCount++
                    }
                }

                if (analyzedCount != 0) {
                    trendEvaluation += (tweetEvaluation / analyzedCount)
                }
                tweetCount++
            }

            val analyzedTrend = AnalyzedTrend((trendEvaluation / tweetCount.toDouble()), trendName, tweetCount, timeStamp)
            result.add(analyzedTrend)

            println("TrendIndex: $trendIndex / TrendName: ${analyzedTrend.trendName} / Evaluation: ${analyzedTrend.evaluation}")

            trendIndex++
        }

        Utils.saveAsCSV(result)
    }

    fun test(text: String): Double {
        val deAssembledWords = MeCabManager.textToWords(text)
        var tweetEvaluation = 0.0
        var analyzedCount = 0

        deAssembledWords.forEach { word ->
            val tempWord: Word? = Dictionary.getWord(word.endForm)
            if (tempWord != null) {
                tweetEvaluation += tempWord.evaluation
                analyzedCount++
            }
        }

        if (analyzedCount != 0) {
            return tweetEvaluation / analyzedCount
        }

        return 0.0
    }
}