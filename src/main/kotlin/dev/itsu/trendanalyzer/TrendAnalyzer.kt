package dev.itsu.trendanalyzer

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する～」
 *
 * @author 野牧　樹　(2130)
 */

import dev.itsu.trendanalyzer.core.MainLogic
import dev.itsu.trendanalyzer.dictionary.Dictionary
import dev.itsu.trendanalyzer.mecab.MeCabManager
import dev.itsu.trendanalyzer.util.Utils
import java.lang.NumberFormatException
import javax.rmi.CORBA.Util


private val runningTime: Long = System.currentTimeMillis()

fun main(args: Array<String>) {
    System.out.println("Started TrendAnalyzer ------------------")
    setShutdownHook()

    analyzeArgs(args)
}

private fun analyzeArgs(args: Array<String>) {
    args.forEach {
        when {
            it.startsWith("-create-dictionary") -> {
                Thread {
                    val path = it.split(":")
                    if (path.size >= 2) Dictionary.createVerbsDictionary(path[1])
                }.start()
            }

            it.startsWith("-authorize-twitter") -> {
                TwitterWrapper.authorize()
            }

            it.startsWith("-start-analyze") -> {
                val data = it.split(":")
                try {
                    if (data.size >= 3) MainLogic.start(data[1].toInt(), data[2].toInt())
                    else MainLogic.start(5, 10)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }

            it.startsWith("-test-mecab") -> {
                val text = it.split(":")
                if (text.size >= 2) MeCabManager.testMecab(text[1])
            }

            it.startsWith("-test-analyze") -> {
                val text = it.split(":")
                var result = 0.0
                if (text.size >= 2) result = MainLogic.test(text[1])
                System.out.println("Evaluation=$result")
            }

            it.startsWith("-output") -> {
                val path = it.split(":")
                if (path.size >= 2) Utils.setCSVFile(path[1])
            }

        }
    }
}

private fun setShutdownHook() {
    Runtime.getRuntime().addShutdownHook(Thread {
        Dictionary.close()
        MeCabManager.close()
        System.out.println("Running time: ${System.currentTimeMillis() - runningTime}ms")
        System.out.println("---------------------------------------------------------------")
    })
}
