package com.leoart.koreanphrasebook.data.parsers

import java.io.IOException

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

interface TextFileParser<T> {
    @Throws(IOException::class)
    fun parse(): T
}
