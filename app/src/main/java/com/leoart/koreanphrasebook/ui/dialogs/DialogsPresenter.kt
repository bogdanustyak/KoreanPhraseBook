package com.leoart.koreanphrasebook.ui.dialogs

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.repository.DialogsRepository
import com.leoart.koreanphrasebook.ui.dialogs.models.Dialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khrystyna on 11/24/16.
 */
class DialogsPresenter(private val view: DialogsView?,
                       context: Context) {

    private val dialogsRepository = DialogsRepository(context)

    fun requestDialogs() {
        dialogsRepository.getDialogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dialogs ->
                    view?.showDialogs(reformatDialogsNames(dialogs))
                }, { throwable ->
                    Log.d(DIALOGS_ERROR_TAG, throwable.message)
                })
    }

    private fun reformatDialogsNames(dialogs : List<DialogResponse>) : List<DialogResponse>{
        val formattedDialogs = mutableListOf<DialogResponse>()
        dialogs.forEach {
            val index = it.name.indexOfFirst { Character.UnicodeBlock.of(it) == Character.UnicodeBlock.HANGUL_JAMO
                    || Character.UnicodeBlock.of(it) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    || Character.UnicodeBlock.of(it) == Character.UnicodeBlock.HANGUL_SYLLABLES }
            val newName = it.name.removeRange(index, it.name.lastIndex + 1)
            formattedDialogs.add(DialogResponse(it.uid, newName))
        }
        return formattedDialogs
    }

    companion object {
        const val DIALOGS_ERROR_TAG = "dialogs error"
    }
}
