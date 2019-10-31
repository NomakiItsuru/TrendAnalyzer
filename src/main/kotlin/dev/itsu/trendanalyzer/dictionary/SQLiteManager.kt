package dev.itsu.trendanalyzer.dictionary

/**
 * 2019年度 栃木高校 SS課題研究
 * 「Twitterを用いた感情分析　～『トレンド』の単語から炎上中の単語を抽出する～」
 *
 * @author 野牧　樹　(2130)
 */

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLiteManager {

    private var connection: Connection

    init {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:./data/Dictionary.db")
        try {
            val statement = connection.createStatement()
            statement.queryTimeout = 50
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS word (id INTEGER PRIMARY KEY, word TEXT NOT NULL, evaluation REAL NOT NULL, word_type TEXT NOT NULL)")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun insert(type: String, word: String, evaluation: Double): Boolean {
        try {
            val statement = connection.prepareStatement("INSERT INTO word (word, evaluation, word_type) VALUES(?, ?, ?)")
            statement.queryTimeout = 50
            statement.setString(1, word)
            statement.setDouble(2, evaluation)
            statement.setString(3, type)
            statement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun getWordByWord(word: String): Word? {
        return getWord(word)
    }

    private fun getWord(word: String): Word? {
        try {
            var result: Word? = null
            val statement = connection.prepareStatement("SELECT * FROM word WHERE word = ?")
            statement.queryTimeout = 1000
            statement.setString(1, word)

            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                result = Word(resultSet.getString("word"), resultSet.getDouble("evaluation"), resultSet.getString("word_type"))
            }

            if (result != null) return result
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun close() {
        connection.close()
    }

}