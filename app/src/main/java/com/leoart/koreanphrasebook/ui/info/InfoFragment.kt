package com.leoart.koreanphrasebook.ui.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R

/**
 * Created by bogdan on 6/14/17.
 */
class InfoFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            val fragment = InfoFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_info, container, false)

        val rvInfo = view.findViewById(R.id.rv_info) as RecyclerView


        return view
    }
}