package com.leoart.koreanphrasebook.ui.dialogs

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.ui.SimpleTextItemUI
import org.jetbrains.anko.AnkoContext

/**
 * Created by bogdan on 11/5/16.
 */
class DialogsRecyclerAdapter(private var dialogs: List<DialogResponse>?, private val interactionListener: DialogsRecyclerAdapter.DialogsListInteractionListener?) : RecyclerView.Adapter<DialogsRecyclerAdapter.DialogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val viewHolder = DialogViewHolder(SimpleTextItemUI().createView(AnkoContext.create(parent.context, parent)))
        viewHolder.llHolder.setOnClickListener {
            interactionListener?.onDialogClick(dialogs!![viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        dialogs?.let {
            it[position].let {
                if (!TextUtils.isEmpty(it.name)) {
                    holder.tvDialogName.text = it.name
                } else {
                    holder.tvDialogName.text = ""
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (dialogs != null) {
            return dialogs!!.size
        }
        return 0
    }

    fun setDialogs(dialogs: List<DialogResponse>) {
        this.dialogs = dialogs
        notifyDataSetChanged()
    }

    class DialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llHolder = itemView.findViewById<LinearLayout>(R.id.ll_holder)
        val tvDialogName = itemView.findViewById<TextView>(R.id.tv_name)
    }

    interface DialogsListInteractionListener {
        fun onDialogClick(dialog: DialogResponse)
    }
}
