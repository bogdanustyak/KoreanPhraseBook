package com.leoart.koreanphrasebook.ui.alphabet

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.models.Letter

class AlphabetAdapter : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    private val letters = mutableListOf<Letter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(letters[position])
    }

    fun refreshList(letters : List<Letter>){
        this.letters.clear()
        this.letters.addAll(letters)
        notifyDataSetChanged()
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvKoreanLetter = view.findViewById<TextView>(R.id.korean_letter)
        private val tvTranslateLetter = view.findViewById<TextView>(R.id.translate_letter)

        fun bind(letter : Letter){
            tvKoreanLetter.text = letter.koreanLetter
            tvTranslateLetter.text = letter.translateLetter
        }
    }
}