package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class WordRequest : FireBaseRequest() {

    fun addWord(dialogUID: String, replic: Replic) {
        val key = dataBaseRef.child("replics").push().key
        dataBaseRef.child("replics")

        val childUpdates = HashMap<String, Any>()
        childUpdates.put("/replics/" + key, replic)
        childUpdates.put("/dialogReplics/$dialogUID/$key", replic.toMap())

        dataBaseRef.updateChildren(childUpdates)
    }

}