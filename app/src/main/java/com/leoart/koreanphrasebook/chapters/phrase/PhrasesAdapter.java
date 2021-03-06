package com.leoart.koreanphrasebook.chapters.phrase;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.chapters.models.Phrase;

import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class PhrasesAdapter extends RecyclerView.Adapter<PhrasesAdapter.PhraseViewHolder> {

    private final List<Phrase> phraseList;

    public PhrasesAdapter(List<Phrase> phraseList) {
        this.phraseList = phraseList;
    }

    @Override
    public PhraseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_phrase, parent, false);
        final PhraseViewHolder viewHolder = new PhraseViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhraseViewHolder holder, int position) {
        Phrase phrase = phraseList.get(position);
        if (phrase != null) {
            if (!TextUtils.isEmpty(phrase.getWord())) {
                holder.tv_word.setText(phrase.getWord());
            } else {
                holder.tv_word.setText("");
            }

            if (!TextUtils.isEmpty(phrase.getTranslation())) {
                holder.tv_translation.setText(phrase.getTranslation());
            } else {
                holder.tv_translation.setText("");
            }

            if (!TextUtils.isEmpty(phrase.getTranscription())) {
                holder.tv_transcription.setText(phrase.getTranscription());
            } else {
                holder.tv_transcription.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (phraseList != null) {
            return phraseList.size();
        }
        return 0;
    }

    static class PhraseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_word;
        TextView tv_translation;
        TextView tv_transcription;

        public PhraseViewHolder(View itemView) {
            super(itemView);
            tv_word = (TextView) itemView.findViewById(R.id.tv_word);
            tv_translation = (TextView) itemView.findViewById(R.id.tv_translation);
            tv_transcription = (TextView) itemView.findViewById(R.id.tv_transcription);
        }
    }
}
