package dev.itsu.trendanalyzer.mecab

import dev.itsu.trendanalyzer.dictionary.Dictionary
import net.moraleboost.mecab.Lattice
import net.moraleboost.mecab.impl.StandardTagger

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』の単語から炎上中の単語を抽出する～」
 *
 * @author 野牧　樹　(2130)
 */

object MeCabManager {

    private val tagger: StandardTagger
    private val lattice: Lattice

    init {
        tagger = StandardTagger("")
        lattice = tagger.createLattice()
    }

    fun textToWords(text: String): MutableList<MeCabWord> {
        lattice.setSentence(text)
        tagger.parse(lattice)

        val result = mutableListOf<MeCabWord>()

        lattice.toString().split("\n").forEach {
            val temp = it.split("\t")
            if (temp.size < 2) return@forEach

            val word = temp[0]
            val data = temp[1].split(",")
            if (data.size < 9) return@forEach

            if (Dictionary.getType(data[0]) != Dictionary.TYPE_UNKNOWN) {
                var endForm = data[6]
                if (endForm == "*") endForm = word
                result.add(MeCabWord(word, endForm, Dictionary.getType(data[0])))
            }
        }

        return result
    }

    fun testMecab(text: String) {
        lattice.setSentence(text)
        tagger.parse(lattice)
        println(lattice.toString())
    }

    fun close() {
        lattice.destroy()
        tagger.destroy()
    }
}