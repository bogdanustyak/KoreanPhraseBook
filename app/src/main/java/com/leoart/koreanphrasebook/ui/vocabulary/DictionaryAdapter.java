package com.leoart.koreanphrasebook.ui.vocabulary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Word;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictViewHolder> {

    private Context context;
    private Dictionary dictionary;

    public DictionaryAdapter(Context context, Dictionary dictionary) {
        this.context = context;
        this.dictionary = dictionary;
    }

    @Override
    public DictViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dict_item, parent, false);

        return new DictViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DictViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (dictionary != null && dictionary.data() != null) {
            return dictionary.wordsCount();
        }
        return 0;
    }

    public static class DictViewHolder extends RecyclerView.ViewHolder {

        public DictViewHolder(View itemView) {
            super(itemView);

        }
    }
}
