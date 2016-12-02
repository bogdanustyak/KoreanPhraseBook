package com.leoart.koreanphrasebook.dialogs.dialog

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.dialogs.models.Message

/**
 * Created by bogdan on 11/5/16.
 */
internal class DialogMessagesRecyclerAdapter(private var messages: List<Replic>?) : RecyclerView.Adapter<DialogMessagesRecyclerAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_message_left, parent, false)
        val viewHolder = MessageViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages?.get(position)
        if (message != null) {
//            if (!TextUtils.isEmpty(message.author.name)) {
//                holder.tvAuthorName.text = message.author.name + ":"
//            } else {
//                holder.tvAuthorName.text = ""
//            }

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
        var tvAuthorName: TextView
        var tvMessage: TextView

        init {
            tvAuthorName = itemView.findViewById(R.id.tv_author_name) as TextView
            tvMessage = itemView.findViewById(R.id.tv_message) as TextView
        }
    }

    fun updateReplics(list: List<Replic>?) {
        this.messages = list
        notifyDataSetChanged()
    }
}
