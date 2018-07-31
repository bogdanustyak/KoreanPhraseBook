package com.leoart.koreanphrasebook.data.network.firebase.search

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchResult(val pathInDB: String, val title: String, val type: DictType)

enum class DictType(val title: String) {
    DICTIONARY("dictionary"),
    CATEGORY_PHRASES("categoryPhrases"),
    DIALOGS("dialogs"),
    REPLICS("replics"),
    CHAPTERS("chapters"),
    PHRASE("phrases")
}