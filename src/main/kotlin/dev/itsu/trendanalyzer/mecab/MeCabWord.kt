package dev.itsu.trendanalyzer.mecab

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』に対する人々の感情を機械的に予想する～」
 *
 * @author 野牧　樹　(2130)
 */

data class MeCabWord(
        val word: String,
        val endForm: String,
        val type: String
)