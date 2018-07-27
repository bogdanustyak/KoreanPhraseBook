package com.leoart.koreanphrasebook.ui.favourite;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<FavouriteModel> favouriteList;
    private FavouriteFragment.Companion.OnFavouriteClickListener listener;

    public FavouriteAdapter(List<FavouriteModel> phraseList,
                            FavouriteFragment.Companion.OnFavouriteClickListener listener) {
        this.favouriteList = phraseList;
        this.listener = listener;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_phrase, parent, false);
        return new FavouriteViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        FavouriteModel favourite = favouriteList.get(position);
        if (favourite != null) {
            holder.tv_word.setText(getWord(favourite));
            holder.tv_translation.setText(getTranslation(favourite));
            holder.tv_transcription.setText(getTranscription(favourite));
            holder.ivFavourite.setSelected(true);
        }
    }

    private String getWord(FavouriteModel favourite) {
        if (!TextUtils.isEmpty(favourite.getWord())) {
            return favourite.getWord();
        } else {
            return "";
        }
    }

    private String getTranslation(FavouriteModel favourite) {
        if (!TextUtils.isEmpty(favourite.getTranslation())) {
            return favourite.getTranslation();
        } else {
            return "";
        }
    }

    private String getTranscription(FavouriteModel favourite) {
        if (!TextUtils.isEmpty(favourite.getTranscription())) {
            return favourite.getTranscription();
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        if (favouriteList != null) {
            return favouriteList.size();
        }
        return 0;
    }

    public void updatePhrases(List<FavouriteModel> phrases) {
        this.favouriteList = phrases;
        notifyDataSetChanged();
    }

    public FavouriteModel getItemByPosition(int position) {
        return favouriteList.get(position);
    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView tv_word;
        TextView tv_translation;
        TextView tv_transcription;
        ImageView ivFavourite;

        public FavouriteViewHolder(View itemView, final FavouriteFragment.Companion.OnFavouriteClickListener listener) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.tv_word);
            tv_translation = itemView.findViewById(R.id.tv_translation);
            tv_transcription = itemView.findViewById(R.id.tv_transcription);
            ivFavourite = itemView.findViewById(R.id.ivFavourite);
            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onFavouriteClick(getAdapterPosition());
                }
            });
            ivFavourite.setImageResource(R.drawable.ic_favorite_selected);
        }
    }
}