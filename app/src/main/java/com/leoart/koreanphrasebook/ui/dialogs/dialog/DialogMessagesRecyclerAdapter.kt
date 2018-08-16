package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.repository.models.EReplic

/**
 * Created by bogdan on 11/5/16.
 */
internal class DialogMessagesRecyclerAdapter(private var messages: List<EReplic>?) :
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
        val pos = messages?.get(position)?.number?.rem(2)
        if (pos == 1) {
            return LEFT_MESSAGE
        } else {
            return RIGHT_MESSAGE
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        messages?.get(position)?.let {
            val dotIndex = it.ukrainian.indexOfFirst { it -> it == ':' } + 1
            val author = it.ukrainian.substring(0, dotIndex)
            val ukr = it.ukrainian.substring(dotIndex, it.ukrainian.length)
            val messageText = ukr + "\n" + it.korean
            if (!TextUtils.isEmpty(author)) {
                holder.tvAuthorName.text = author
            } else {
                holder.tvAuthorName.text = ""
            }
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
        val tvAuthorName = itemView.findViewById<TextView>(R.id.tv_author_name)
        var tvMessage = itemView.findViewById<TextView>(R.id.txtMessage)

    }

    fun updateReplics(list: List<EReplic>?) {
        list?.sortedWith(compareBy(EReplic::number))?.let {
            this.messages = it
            notifyDataSetChanged()
        }
    }
}
