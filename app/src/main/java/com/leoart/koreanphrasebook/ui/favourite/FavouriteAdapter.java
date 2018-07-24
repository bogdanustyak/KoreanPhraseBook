package com.leoart.koreanphrasebook.ui.favourite;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.favourite.Favourite;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<Favourite> favouriteList;
    private FavouriteFragment.Companion.OnFavouriteClickListener listener;

    public FavouriteAdapter(List<Favourite> phraseList,
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
        Favourite favourite = favouriteList.get(position);
        if (favourite != null) {
            if (!TextUtils.isEmpty(favourite.getWord())) {
                holder.tv_word.setText(favourite.getWord());
            } else {
                holder.tv_word.setText("");
            }

            if (!TextUtils.isEmpty(favourite.getTranslation())) {
                holder.tv_translation.setText(favourite.getTranslation());
            } else {
                holder.tv_translation.setText("");
            }

            if (!TextUtils.isEmpty(favourite.getTranscription())) {
                holder.tv_transcription.setText(favourite.getTranscription());
            } else {
                holder.tv_transcription.setText("");
            }
            holder.ivFavourite.setSelected(favourite.isFavourite());
            holder.ivFavourite.setImageResource(getFavoriteResource(favourite.isFavourite()));
        }
    }

    @Override
    public int getItemCount() {
        if (favouriteList != null) {
            return favouriteList.size();
        }
        return 0;
    }

    public void updatePhrases(List<Favourite> phrases) {
        this.favouriteList = phrases;
        notifyDataSetChanged();
    }

    public Favourite getItemByPosition(int position) {
        return favouriteList.get(position);
    }

    private int getFavoriteResource(boolean isFavourite) {
        if (isFavourite) {
            return R.drawable.ic_favorite_selected;
        } else {
            return R.drawable.ic_favorite_unselected;
        }
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
        }
    }
}