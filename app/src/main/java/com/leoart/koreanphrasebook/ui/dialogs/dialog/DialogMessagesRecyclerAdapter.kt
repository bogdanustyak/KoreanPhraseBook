package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic

/**
 * Created by bogdan on 11/5/16.
 */
internal class DialogMessagesRecyclerAdapter(private var messages: List<Replic>?) :
        RecyclerView.Adapter<DialogMessagesRecyclerAdapter.MessageViewHolder>() {

    companion object {
        val LEFT_MESSAGE = 0
        val RIGHT_MESSAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_right, parent, false)
        if (viewType == LEFT_MESSAGE) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_left, parent, false)
        }
        val viewHolder = MessageViewHolder(itemView)
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        val number = messages?.get(position)?.number?.mod(2) ?: return super.getItemViewType(position)
        return number
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages?.get(position)
        if (message != null) {

            var messageText = message.ukrainian + "\n" + message.korean

            if (!TextUtils.isEmpty(messageText)) {
                holder.tvMessage.text = messageText
            } else {
                holder.tvMessage.text = ""
            }
        }
    }

    override fun getItemCount(): Int {
        if (messages != null) {
            return messages!!.size
        }
        return 0
    }

    internal class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMessage: TextView

        init {
            tvMessage = itemView.findViewById(R.id.txtMessage) as TextView
        }
    }

    fun updateReplics(list: List<Replic>?) {
        this.messages = list
        notifyDataSetChanged()
    }
}
