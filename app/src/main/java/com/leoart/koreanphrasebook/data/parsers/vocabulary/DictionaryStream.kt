package com.leoart.koreanphrasebook.data.parsers.vocabulary

import com.leoart.koreanphrasebook.data.parsers.TextFileParser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryStream(private val inputStream: InputStream) : TextFileParser<Dictionary> {

    override fun parse(): Dictionary {
        val dictionary = Dictionary()

        // Open the file
        val br = BufferedReader(InputStreamReader(inputStream))

        var readingFirstLetter = true

        var letter = 'A'
        var words = ArrayList<Word>()
        br.forEachLine {
            try {
                val line = it.substring(1, it.length)
                if (lineIsLetter(line)) {
                    if (readingFirstLetter) {
                        readingFirstLetter = false
                    } else {
                        dictionary.add(letter, words)
                        words = ArrayList<Word>()
                    }
                    letter = line[0]
                } else {
                    val word = parseWords(line)
                    words.add(word)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        br.close()

        return dictionary
    }

    private fun parseWords(line: String): Word {
        val spaceIndex = line.indexOfFirst { it == ' ' }
        val word = line.substring(0, spaceIndex)
        val translation = line.substring(spaceIndex, line.length)
        return Word(word, translation)
    }

    private fun lineIsLetter(line: String) = !line.isEmpty() && line.length == 1

}