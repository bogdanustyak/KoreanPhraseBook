package com.leoart.koreanphrasebook.data.parsers.favourite

class Favourite(
        val letter: Char?,
        val word: String,
        val translation: String,
        val transcription: String?,
        var isFavourite: Boolean = false,
        val category: String?,
        val type: FavouriteType
)