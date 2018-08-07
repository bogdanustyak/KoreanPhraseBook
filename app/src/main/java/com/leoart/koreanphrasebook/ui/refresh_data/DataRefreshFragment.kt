package com.leoart.koreanphrasebook.ui.refresh_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment

class DataRefreshFragment : BaseFragment() {

    lateinit var dataRefreshClickListener: DataRefreshClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.data_refresh_fragment, container, false)
        v.findViewById<Button>(R.id.syncButton).setOnClickListener {
            dataRefreshClickListener.onSync()
        }
        v.findViewById<TextView>(R.id.dismissButton).setOnClickListener {
            dataRefreshClickListener.onDismiss()
        }
        return v
    }

    companion object {
        fun newInstance(dataRefreshClickListener: DataRefreshClickListener): DataRefreshFragment {
            val fragment = DataRefreshFragment()
            fragment.dataRefreshClickListener = dataRefreshClickListener
            return fragment
        }
    }
}