package com.leoart.koreanphrasebook.ui.vocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Word;

import org.zakariya.stickyheaders.SectioningAdapter;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DictionaryAdapter extends SectioningAdapter {

    private static final int VIEW_TYPE_HEADER = 0x01;
    private static final int VIEW_TYPE_CONTENT = 0x00;

    private Dictionary dictionary;
    private Context context;

    public DictionaryAdapter(Context context, Dictionary dictionary) {
        this.context = context;
        this.dictionary = dictionary;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dict_header, parent, false);
        return new HeaderViewHolder(itemView);
    }

    @Override
    public DictViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dict_header, parent, false);
        return new DictViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerUserType) {
        if (dictionary != null) {
            Character s = dictionary.data().keySet().iterator().next();
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.charHeader.setText(s);
        }
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemUserType) {
        if (dictionary != null) {
            Character s = dictionary.data().keySet().iterator().next();
            DictViewHolder holder = (DictViewHolder) viewHolder;
            Word word = dictionary.data().get(s).get(itemIndex);
            holder.text.setText(word.getWord());
        }
    }


    @Override
    public int getItemCount() {
        if (dictionary != null) {
            return dictionary.totalCount();
        }
        return 0;
    }

    public static class DictViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView text;

        public DictViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView charHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            charHeader = (TextView) itemView.findViewById(R.id.charHeader);
        }
    }
}
