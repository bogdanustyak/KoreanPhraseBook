package com.leoart.koreanphrasebook.data.parsers.favourite

class FavouriteModel(
        val word: String,
        val translation: String,
        val transcription: String?,
        val type: FavouriteType
)