package com.leoart.koreanphrasebook.ui.refresh_data

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.leoart.koreanphrasebook.R

class DataRefreshDialog : DialogFragment() {

    private lateinit var listener : DataRefreshClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), R.style.AboutDialogTheme)
        builder.setMessage(R.string.refresh_data)
                .setPositiveButton(R.string.sync_data) { dialog, id ->
                    listener.onSync()
                }
                .setNegativeButton(R.string.data_dismiss_sync) { dialog, id ->
                    dismiss()
                }
        return builder.create()
    }

    companion object {
        fun newInstance(listener : DataRefreshClickListener) : DataRefreshDialog{
            val dialog = DataRefreshDialog()
            dialog.listener = listener
            return dialog
        }
    }

}