package dev.itsu.trendanalyzer.dictionary

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』の単語から炎上中の単語を抽出する～」
 *
 * @author 野牧　樹　(2130)
 */

import java.io.File
import java.nio.charset.StandardCharsets

object Dictionary {

    const val TYPE_VERB = "verb"
    const val TYPE_NOUN = "noun"
    const val TYPE_ADJECTIVE = "adjective"
    const val TYPE_ADVERB = "adverb"
    const val TYPE_UNKNOWN = "unknown"

    val sqLiteManager: SQLiteManager

    init {
        sqLiteManager = SQLiteManager()
    }

    fun createVerbsDictionary(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            System.err.println("$fileName does not found.")
            return
        }

        file
                .useLines(StandardCharsets.UTF_8) { lineSequences: Sequence<String> -> lineSequences.toList()}
                .forEach { line: String ->
                    val data = line.split(":")
                    if (data.size < 4) return

                    sqLiteManager.insert(getType(data[2]), data[0], data[3].toDouble())
                }
        println("Created dictionary: $fileName")

    }

    fun getWord(word: String): Word? {
        return sqLiteManager.getWordByWord(word)
    }

    fun getType(typeJapanese: String): String {
        return when(typeJapanese) {
            "動詞" -> TYPE_VERB
            "名詞" -> TYPE_NOUN
            "形容詞" -> TYPE_ADJECTIVE
            "副詞" -> TYPE_ADVERB
            else -> TYPE_UNKNOWN
        }
    }

    fun close() {
        sqLiteManager.close()
    }
}