package dev.itsu.trendanalyzer.util

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する～」
 *
 * @author 野牧　樹　(2130)
 */

import dev.itsu.trendanalyzer.core.AnalyzedTrend
import java.io.File
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private var csvFileName: String = "output__###_.csv"

    fun saveAsCSV(list: MutableList<AnalyzedTrend>) {
        val dateFormat = SimpleDateFormat("yMMddHHmm")
        csvFileName = csvFileName.replace("_###_", dateFormat.format(Date()))

        val file = File(csvFileName)
        if (!file.exists()) file.createNewFile()

        var index = 0
        file.writeText("Index,Name,Evaluation,TweetCount,Time\n", StandardCharsets.UTF_8)
        list.forEach {
            file.appendText("${index + 1},${it.trendName},${it.evaluation},${it.tweetCount},${SimpleDateFormat("y/MM/dd HH:mm:ss").format(Date(it.timeStamp))}\n")
            index++
        }
    }

    fun setCSVFile(fileName: String) {
        this.csvFileName = fileName
    }
}