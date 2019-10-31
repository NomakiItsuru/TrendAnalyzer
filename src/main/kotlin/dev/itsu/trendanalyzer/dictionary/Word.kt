package dev.itsu.trendanalyzer.dictionary

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』の単語から炎上中の単語を抽出する～」
 *
 * @author 野牧　樹　(2130)
 */

data class Word(val word: String, val evaluation: Double, val type: String)