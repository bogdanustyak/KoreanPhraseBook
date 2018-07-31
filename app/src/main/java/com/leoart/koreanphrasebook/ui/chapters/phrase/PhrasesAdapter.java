package com.leoart.koreanphrasebook.ui.chapters.phrase;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.repository.models.EDictionary;
import com.leoart.koreanphrasebook.data.repository.models.EPhrase;
import com.leoart.koreanphrasebook.ui.models.Phrase;

import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class PhrasesAdapter extends RecyclerView.Adapter<PhrasesAdapter.PhraseViewHolder> {

    private List<EPhrase> phraseList;
    private OnPhrasesAdapterInteractionListener listener;

    public PhrasesAdapter(List<EPhrase> phraseList, OnPhrasesAdapterInteractionListener listener) {
        this.phraseList = phraseList;
        this.listener = listener;
    }

    @Override
    public PhraseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_phrase, parent, false);
        return new PhraseViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(PhraseViewHolder holder, int position) {
        EPhrase phrase = phraseList.get(position);
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
            holder.ivFavourite.setSelected(phrase.isFavourite());
            holder.ivFavourite.setImageResource(getFavoriteResource(phrase.isFavourite()));
        }
    }

    @Override
    public int getItemCount() {
        if (phraseList != null) {
            return phraseList.size();
        }
        return 0;
    }

    public void updatePhrases(List<EPhrase> phrases) {
        this.phraseList = phrases;
        notifyDataSetChanged();
    }

    public EPhrase getPhraseByPosition(int position) {
        return phraseList.get(position);
    }

    private int getFavoriteResource(boolean isFavourite) {
        if (isFavourite) {
            return R.drawable.ic_favorite_selected;
        } else {
            return R.drawable.ic_favorite_unselected;
        }
    }

    static class PhraseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_word;
        TextView tv_translation;
        TextView tv_transcription;
        ImageView ivFavourite;

        public PhraseViewHolder(View itemView, final OnPhrasesAdapterInteractionListener listener) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.tv_word);
            tv_translation = itemView.findViewById(R.id.tv_translation);
            tv_transcription = itemView.findViewById(R.id.tv_transcription);
            ivFavourite = itemView.findViewById(R.id.ivFavourite);
            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onFavouriteClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnPhrasesAdapterInteractionListener {
        public void onFavouriteClicked(int position);
    }
}
