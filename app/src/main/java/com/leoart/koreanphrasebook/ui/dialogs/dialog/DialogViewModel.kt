package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DialogViewModel(private val dialogRepository: DiealogRepository) : ViewModel() {

    private var replics: MutableLiveData<List<EReplic>>? = null

    fun getDialog(categoryName: String): LiveData<List<EReplic>> {
        if (replics == null) {
            replics = MutableLiveData()
            loadDialog(categoryName)
        }
        return replics as MutableLiveData<List<EReplic>>
    }

    private fun loadDialog(categoryName: String) {
        dialogRepository
                .getDialog(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dict ->
                    replics?.value = dict
                }, { e ->
                    e.printStackTrace()
                })
    }
}